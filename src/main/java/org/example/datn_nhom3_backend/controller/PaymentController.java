package org.example.datn_nhom3_backend.controller;

import org.example.datn_nhom3_backend.entity.HocVien;
import org.example.datn_nhom3_backend.entity.ThanhToan;
import org.example.datn_nhom3_backend.exception.ResourceNotFoundException;
import org.example.datn_nhom3_backend.repository.HocVienRepository;
import org.example.datn_nhom3_backend.repository.TaiKhoanRepository;
import org.example.datn_nhom3_backend.service.HocVienService;
import org.example.datn_nhom3_backend.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/thanh-toan")
@CrossOrigin(origins = "http://localhost:5173")
public class PaymentController {

    private final PaymentService paymentService;
    private final HocVienService hocVienService;
    private final TaiKhoanRepository taiKhoanRepository;
    private final HocVienRepository hocVienRepository;

    public PaymentController(PaymentService paymentService,
                             HocVienService hocVienService,
                             TaiKhoanRepository taiKhoanRepository,
                             HocVienRepository hocVienRepository) {
        this.paymentService = paymentService;
        this.hocVienService = hocVienService;
        this.taiKhoanRepository = taiKhoanRepository;
        this.hocVienRepository = hocVienRepository;
    }

    private HocVien getCurrentHocVienEntity() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth != null ? auth.getName() : null;
        if (username == null || "anonymousUser".equals(username))
            throw new ResourceNotFoundException("Chưa đăng nhập");
        var tk = taiKhoanRepository.findByTendangnhap(username)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tài khoản"));
        if (tk.getCccd() == null || tk.getCccd().isBlank())
            throw new ResourceNotFoundException("Tài khoản chưa liên kết với học viên");
        return hocVienService.findByCccd(tk.getCccd())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy hồ sơ học viên"));
    }

    // Học viên đã đăng nhập khởi tạo thanh toán QR cho 1 đăng ký khóa học
    @PostMapping("/khoi-tao")
    public Map<String, Object> khoiTao(@RequestBody Map<String, Integer> body) {
        Integer madk = body.get("madk");
        if (madk == null)
            return Map.of("success", false, "message", "Thiếu mã đăng ký");

        try {
            HocVien hv = getCurrentHocVienEntity();
            ThanhToan tt = paymentService.khoiTaoPayment(madk, hv);

            String qrBase64 = paymentService.generateQrBase64(tt.getQrData(), 280, 280);

            Map<String, Object> data = new HashMap<>();
            data.put("matt", tt.getMatt());
            data.put("madk", madk);
            data.put("sotien", tt.getSotien());
            data.put("trangthai", tt.getTrangthai());
            data.put("phuongthuc", tt.getPhuongthuc());
            data.put("transactionRef", tt.getTransactionRef());
            data.put("paymentUrl", tt.getPaymentUrl());
            data.put("qrData", tt.getQrData());
            data.put("qrBase64", qrBase64);
            data.put("tenKhoaHoc", tt.getDangKyKhoaHoc() != null
                    && tt.getDangKyKhoaHoc().getKhoaHoc() != null
                    ? tt.getDangKyKhoaHoc().getKhoaHoc().getTenkhoahoc() : null);
            data.put("hoten", tt.getHocVien() != null ? tt.getHocVien().getHoten() : null);

            return Map.of("success", true, "data", data);
        } catch (Exception e) {
            return Map.of("success", false, "message", e.getMessage());
        }
    }

    // Frontend auto-poll trạng thái thanh toán
    @GetMapping("/{matt}/trang-thai")
    public Map<String, Object> trangThai(@PathVariable Integer matt) {
        try {
            ThanhToan tt = paymentService.getPayment(matt);
            checkQuyenTruyCap(tt);
            return Map.of("success", true, "matt", tt.getMatt(), "trangthai", tt.getTrangthai());
        } catch (Exception e) {
            return Map.of("success", false, "message", e.getMessage());
        }
    }

    // Lấy thông tin thanh toán + mã QR để hiển thị lại khi refresh trang
    @GetMapping("/{matt}/qr")
    public Map<String, Object> qrInfo(@PathVariable Integer matt) {
        try {
            ThanhToan tt = paymentService.getPayment(matt);
            checkQuyenTruyCap(tt);
            String qrBase64 = paymentService.generateQrBase64(tt.getQrData(), 280, 280);

            Map<String, Object> data = new HashMap<>();
            data.put("matt", tt.getMatt());
            data.put("madk", tt.getDangKyKhoaHoc() != null ? tt.getDangKyKhoaHoc().getMadk() : null);
            data.put("sotien", tt.getSotien());
            data.put("trangthai", tt.getTrangthai());
            data.put("phuongthuc", tt.getPhuongthuc());
            data.put("transactionRef", tt.getTransactionRef());
            data.put("paymentUrl", tt.getPaymentUrl());
            data.put("qrData", tt.getQrData());
            data.put("qrBase64", qrBase64);
            data.put("tenKhoaHoc", tt.getDangKyKhoaHoc() != null
                    && tt.getDangKyKhoaHoc().getKhoaHoc() != null
                    ? tt.getDangKyKhoaHoc().getKhoaHoc().getTenkhoahoc() : null);
            data.put("hoten", tt.getHocVien() != null ? tt.getHocVien().getHoten() : null);

            return Map.of("success", true, "data", data);
        } catch (Exception e) {
            return Map.of("success", false, "message", e.getMessage());
        }
    }

    // Xác nhận thanh toán thành công (mô phỏng callback từ cổng thanh toán / ngân hàng)
    @PostMapping("/xac-nhan/{matt}")
    public Map<String, Object> xacNhan(@PathVariable Integer matt) {
        try {
            ThanhToan tt = paymentService.getPayment(matt);
            checkQuyenTruyCap(tt);
            tt = paymentService.xacNhanPayment(matt);
            return Map.of("success", true, "matt", tt.getMatt(), "trangthai", tt.getTrangthai());
        } catch (Exception e) {
            return Map.of("success", false, "message", e.getMessage());
        }
    }

    // ADMIN/NV được toàn quyền; Học viên chỉ được truy cập giao dịch của chính mình
    private void checkQuyenTruyCap(ThanhToan tt) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getAuthorities() == null) return;
        boolean quanTriVien = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_NV"));
        if (quanTriVien) return;

        HocVien hv = getCurrentHocVienEntity();
        if (tt.getHocVien() == null || !tt.getHocVien().getMahv().equals(hv.getMahv())) {
            throw new ResourceNotFoundException("Không có quyền truy cập giao dịch này");
        }
    }
}
