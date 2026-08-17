package org.example.datn_nhom3_backend.controller;
import org.example.datn_nhom3_backend.annotation.LogAction;
import jakarta.persistence.Id;
import org.example.datn_nhom3_backend.entity.HoSoHocVien;
import org.example.datn_nhom3_backend.entity.HocVien;
import org.example.datn_nhom3_backend.entity.TaiKhoan;
import org.example.datn_nhom3_backend.exception.ResourceNotFoundException;
import org.example.datn_nhom3_backend.repository.HoSoHocVienRepository;
import org.example.datn_nhom3_backend.repository.TaiKhoanRepository;
import org.example.datn_nhom3_backend.service.HoSoHocVienService;
import org.example.datn_nhom3_backend.service.HocVienService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@RestController
@RequestMapping("/api/ho-so-hoc-vien")
@CrossOrigin(origins = "http://localhost:5173")
public class HoSoHocVienController {
    private final HoSoHocVienService service;
    private final HoSoHocVienRepository repository;
    private final TaiKhoanRepository taiKhoanRepository;
    private final HocVienService hocVienService;
    public HoSoHocVienController(HoSoHocVienService service,
                                 HoSoHocVienRepository repository,
                                 TaiKhoanRepository taiKhoanRepository,
                                 HocVienService hocVienService) {
        this.service = service;
        this.repository = repository;
        this.taiKhoanRepository = taiKhoanRepository;
        this.hocVienService = hocVienService;
    }
    @GetMapping
    public List<HoSoHocVien> getAll() {
        return service.getAll();
    }

    // Hồ sơ học viên của tài khoản đang đăng nhập
    @GetMapping("/me")
    public ResponseEntity<HoSoHocVien> getMe() {
        HocVien hv = getCurrentHocVienEntity();
        return repository.findByHocVien_Mahv(hv.getMahv())
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Chưa có hồ sơ học viên"));
    }

    // Học viên tự cập nhật hồ sơ của mình (chỉ một lần duy nhất)
    @PutMapping("/me")
    public ResponseEntity<?> updateMe(@RequestBody Map<String, Object> body) {
        HocVien hv = getCurrentHocVienEntity();
        HoSoHocVien hoSo = repository.findByHocVien_Mahv(hv.getMahv())
                .orElseThrow(() -> new ResourceNotFoundException("Chưa có hồ sơ học viên"));
        if (Boolean.TRUE.equals(hoSo.getDaChinhSua())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "success", false, "message", "Hồ sơ đã được chỉnh sửa, không thể chỉnh sửa lần nữa"));
        }
        Object hvObj = body.get("hocVien");
        if (hvObj instanceof Map<?, ?> hvMap) {
            if (hvMap.get("hoten") != null) hv.setHoten(String.valueOf(hvMap.get("hoten")));
            if (hvMap.get("ngaysinh") != null) hv.setNgaysinh(LocalDate.parse(String.valueOf(hvMap.get("ngaysinh"))));
            if (hvMap.get("gioitinh") != null) hv.setGioitinh(String.valueOf(hvMap.get("gioitinh")));
            if (hvMap.get("sodienthoai") != null) hv.setSodienthoai(String.valueOf(hvMap.get("sodienthoai")));
            if (hvMap.get("email") != null) hv.setEmail(String.valueOf(hvMap.get("email")));
            if (hvMap.get("diachi") != null) hv.setDiachi(String.valueOf(hvMap.get("diachi")));
        }
        if (body.get("anhCanhCuoc") != null) hoSo.setAnhCanhCuoc(String.valueOf(body.get("anhCanhCuoc")));
        if (body.get("fileHoSo") != null) hoSo.setFileHoSo(String.valueOf(body.get("fileHoSo")));
        hoSo.setDaChinhSua(true);

        hocVienService.save(hv);
        HoSoHocVien saved = service.save(hoSo);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HoSoHocVien> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dữ liệu với ID: " + id));
    }
    @LogAction(action = "Xử lý hồ sơ học viên", table = "ho_so_hoc_vien")
    @PostMapping
    public HoSoHocVien create(@RequestBody HoSoHocVien data) {
        return service.save(data);
    }
    @LogAction(action = "Xử lý hồ sơ học viên", table = "ho_so_hoc_vien")
    @PutMapping("/{id}")
    public HoSoHocVien update(@PathVariable Integer id, @RequestBody HoSoHocVien data) throws IllegalAccessException {
        setEntityId(data, id);
        return service.save(data);
    }
    @LogAction(action = "Xử lý hồ sơ học viên", table = "ho_so_hoc_vien")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    private HocVien getCurrentHocVienEntity() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth != null ? auth.getName() : null;
        if (username == null || "anonymousUser".equals(username))
            throw new ResourceNotFoundException("Chưa đăng nhập");
        TaiKhoan tk = taiKhoanRepository.findByTendangnhap(username)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tài khoản"));
        if (tk.getCccd() == null || tk.getCccd().isBlank())
            throw new ResourceNotFoundException("Tài khoản chưa liên kết với học viên");
        return hocVienService.findByCccd(tk.getCccd())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy hồ sơ học viên"));
    }

    private void setEntityId(HoSoHocVien data, Integer id) throws IllegalAccessException {
        for (Field field : data.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                field.set(data, id);
                return;
            }
        }
    }
}
