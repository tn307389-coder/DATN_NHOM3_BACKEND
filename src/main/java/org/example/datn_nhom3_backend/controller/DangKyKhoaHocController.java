package org.example.datn_nhom3_backend.controller;
import jakarta.persistence.Id;
import org.example.datn_nhom3_backend.dto.DangKyKhoaHocPublicRequest;
import org.example.datn_nhom3_backend.entity.DangKyKhoaHoc;
import org.example.datn_nhom3_backend.entity.HocVien;
import org.example.datn_nhom3_backend.entity.KhoaHoc;
import org.example.datn_nhom3_backend.exception.ResourceNotFoundException;
import org.example.datn_nhom3_backend.service.DangKyKhoaHocService;
import org.example.datn_nhom3_backend.service.HocVienService;
import org.example.datn_nhom3_backend.service.KhoaHocService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@RestController
@RequestMapping("/api/dang-ky-khoa-hoc")
@CrossOrigin(origins = "http://localhost:5173")
public class DangKyKhoaHocController {
    private final DangKyKhoaHocService service;
    private final HocVienService hocVienService;
    private final KhoaHocService khoaHocService;
    public DangKyKhoaHocController(DangKyKhoaHocService service,
                                   HocVienService hocVienService,
                                   KhoaHocService khoaHocService) {
        this.service = service;
        this.hocVienService = hocVienService;
        this.khoaHocService = khoaHocService;
    }
    @GetMapping
    public List<DangKyKhoaHoc> getAll() {
        return service.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<DangKyKhoaHoc> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dữ liệu với ID: " + id));
    }
    @PostMapping
    public DangKyKhoaHoc create(@RequestBody DangKyKhoaHoc data) {
        return service.save(data);
    }
    @PutMapping("/{id}")
    public DangKyKhoaHoc update(@PathVariable Integer id, @RequestBody DangKyKhoaHoc data) throws IllegalAccessException {
        setEntityId(data, id);
        return service.save(data);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    // Đăng ký khóa học công khai (trang web) - không cần xác thực
    @PostMapping("/public")
    public Map<String, Object> publicRegister(@RequestBody DangKyKhoaHocPublicRequest req) {
        HocVien hv = new HocVien();
        hv.setHoten(req.getHoten());
        hv.setNgaysinh(req.getNgaysinh());
        hv.setGioitinh(req.getGioitinh());
        hv.setCccd(req.getCccd());
        hv.setSodienthoai(req.getSodienthoai());
        hv.setEmail(req.getEmail());
        hv.setDiachi(req.getDiachi());
        hv.setNgaydangky(LocalDate.now());
        HocVien savedHv = hocVienService.save(hv);

        KhoaHoc kh = khoaHocService.getById(req.getMakh())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khóa học"));

        DangKyKhoaHoc dk = new DangKyKhoaHoc();
        dk.setHocVien(savedHv);
        dk.setKhoaHoc(kh);
        dk.setNgaydangky(LocalDate.now());
        dk.setTrangthai("Chờ duyệt");
        DangKyKhoaHoc saved = service.save(dk);

        return Map.of(
                "success", true,
                "message", "Đăng ký thành công, chúng tôi sẽ liên hệ bạn sau khi duyệt",
                "data", saved
        );
    }
    private void setEntityId(DangKyKhoaHoc data, Integer id) throws IllegalAccessException {
        for (Field field : data.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                field.set(data, id);
                return;
            }
        }
    }
}