package org.example.datn_nhom3_backend.controller;
import jakarta.persistence.Id;
import org.example.datn_nhom3_backend.annotation.LogAction;
import org.example.datn_nhom3_backend.entity.ThanhToan;
import org.example.datn_nhom3_backend.exception.ResourceNotFoundException;
import org.example.datn_nhom3_backend.service.HocVienService;
import org.example.datn_nhom3_backend.service.ThanhToanService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/thanh-toan")
@CrossOrigin(origins = "http://localhost:5173")
public class ThanhToanController {
    private final ThanhToanService service;
    private final HocVienService hocVienService;
    private final org.example.datn_nhom3_backend.repository.TaiKhoanRepository taiKhoanRepository;
    private final org.example.datn_nhom3_backend.repository.ThanhToanRepository thanhToanRepository;
    public ThanhToanController(ThanhToanService service,
                               HocVienService hocVienService,
                               org.example.datn_nhom3_backend.repository.TaiKhoanRepository taiKhoanRepository,
                               org.example.datn_nhom3_backend.repository.ThanhToanRepository thanhToanRepository) {
        this.service = service;
        this.hocVienService = hocVienService;
        this.taiKhoanRepository = taiKhoanRepository;
        this.thanhToanRepository = thanhToanRepository;
    }
    @GetMapping
    public List<ThanhToan> getAll() {
        return service.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ThanhToan> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dữ liệu với ID: " + id));
    }
    @PostMapping
    @LogAction(action = "Tạo thanh toán", table = "thanh_toan")
    public ThanhToan create(@RequestBody ThanhToan data) {
        return service.save(data);
    }
    @PutMapping("/{id}")
    @LogAction(action = "Cập nhật thanh toán", table = "thanh_toan")
    public ThanhToan update(@PathVariable Integer id, @RequestBody ThanhToan data) throws IllegalAccessException {
        setEntityId(data, id);
        return service.save(data);
    }
    @DeleteMapping("/{id}")
    @LogAction(action = "Xóa thanh toán", table = "thanh_toan")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    // Lịch sử thanh toán của học viên đang đăng nhập
    @GetMapping("/me")
    public ResponseEntity<List<Map<String, Object>>> getMyPayments() {
        org.example.datn_nhom3_backend.entity.HocVien hv = getCurrentHocVienEntity();
        List<Map<String, Object>> result = thanhToanRepository.findByHocVien_Mahv(hv.getMahv()).stream()
                .map(tt -> {
                    Map<String, Object> item = new LinkedHashMap<>();
                    item.put("matt", tt.getMatt());
                    item.put("madk", tt.getDangKyKhoaHoc() != null ? tt.getDangKyKhoaHoc().getMadk() : null);
                    item.put("tenKhoaHoc", tt.getDangKyKhoaHoc() != null && tt.getDangKyKhoaHoc().getKhoaHoc() != null
                            ? tt.getDangKyKhoaHoc().getKhoaHoc().getTenkhoahoc() : null);
                    item.put("sotien", tt.getSotien());
                    item.put("ngaythanhtoan", tt.getNgaythanhtoan() != null ? tt.getNgaythanhtoan().toString() : null);
                    item.put("phuongthuc", tt.getPhuongthuc());
                    item.put("trangthai", tt.getTrangthai());
                    return item;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    private org.example.datn_nhom3_backend.entity.HocVien getCurrentHocVienEntity() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth != null ? auth.getName() : null;
        if (username == null || "anonymousUser".equals(username))
            throw new ResourceNotFoundException("Chưa đăng nhập");
        org.example.datn_nhom3_backend.entity.TaiKhoan tk = taiKhoanRepository.findByTendangnhap(username)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tài khoản"));
        if (tk.getCccd() == null || tk.getCccd().isBlank())
            throw new ResourceNotFoundException("Tài khoản chưa liên kết với học viên");
        return hocVienService.findByCccd(tk.getCccd())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy hồ sơ học viên"));
    }

    private void setEntityId(ThanhToan data, Integer id) throws IllegalAccessException {
        for (Field field : data.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                field.set(data, id);
                return;
            }
        }
    }
}