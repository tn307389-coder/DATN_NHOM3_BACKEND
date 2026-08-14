package org.example.datn_nhom3_backend.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.datn_nhom3_backend.annotation.LogAction;
import org.example.datn_nhom3_backend.entity.NhatKyHeThong;
import org.example.datn_nhom3_backend.entity.TaiKhoan;
import org.example.datn_nhom3_backend.repository.NhatKyHeThongRepository;
import org.example.datn_nhom3_backend.repository.TaiKhoanRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

/**
 * Ghi nhật ký hệ thống tự động cho các phương thức được đánh dấu @LogAction.
 * Chạy trong transaction riêng (REQUIRES_NEW) để không bị rollback khi nghiệp vụ lỗi.
 */
@Aspect
@Component
public class LogActionAspect {

    private final NhatKyHeThongRepository nhatKyRepository;
    private final TaiKhoanRepository taiKhoanRepository;

    public LogActionAspect(NhatKyHeThongRepository nhatKyRepository,
                           TaiKhoanRepository taiKhoanRepository) {
        this.nhatKyRepository = nhatKyRepository;
        this.taiKhoanRepository = taiKhoanRepository;
    }

    @Around("@annotation(logAction)")
    public Object logAction(ProceedingJoinPoint joinPoint, LogAction logAction) throws Throwable {
        Object result = joinPoint.proceed();
        try {
            writeLog(logAction, joinPoint);
        } catch (Exception e) {
            // Ghi log thất bại không được phép ảnh hưởng nghiệp vụ
        }
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected void writeLog(LogAction logAction, ProceedingJoinPoint joinPoint) {
        NhatKyHeThong nk = new NhatKyHeThong();
        nk.setHanhdong(logAction.action());
        nk.setChiTiet(buildDetail(logAction, joinPoint));
        nk.setThoigian(LocalDateTime.now());
        nk.setIp(getClientIp());
        nk.setTaiKhoan(getCurrentTaiKhoan());
        nhatKyRepository.save(nk);
    }

    private String buildDetail(LogAction logAction, ProceedingJoinPoint joinPoint) {
        String table = logAction.table();
        String method = joinPoint.getSignature().getName();
        return String.format("Bảng: %s | Phương thức: %s",
                table.isEmpty() ? "N/A" : table, method);
    }

    private TaiKhoan getCurrentTaiKhoan() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getName())) {
            return null;
        }
        String username = auth.getName();
        if (username == null || username.isBlank()) {
            return null;
        }
        return taiKhoanRepository.findByTendangnhap(username).orElse(null);
    }

    private String getClientIp() {
        try {
            ServletRequestAttributes attrs =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attrs == null) return null;
            String ip = attrs.getRequest().getHeader("X-Forwarded-For");
            if (ip != null && !ip.isBlank()) {
                return ip.split(",")[0].trim();
            }
            return attrs.getRequest().getRemoteAddr();
        } catch (Exception e) {
            return null;
        }
    }
}
