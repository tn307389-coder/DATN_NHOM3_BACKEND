package org.example.datn_nhom3_backend.controller;
import jakarta.persistence.Id;
import org.example.datn_nhom3_backend.dto.DangKyKhoaHocPublicRequest;
import org.example.datn_nhom3_backend.entity.DangKyKhoaHoc;
import org.example.datn_nhom3_backend.entity.HangGPLX;
import org.example.datn_nhom3_backend.entity.HocVien;
import org.example.datn_nhom3_backend.entity.KhoaHoc;
import org.example.datn_nhom3_backend.exception.ResourceNotFoundException;
import org.example.datn_nhom3_backend.service.DangKyKhoaHocService;
import org.example.datn_nhom3_backend.service.HocVienService;
import org.example.datn_nhom3_backend.service.KhoaHocService;
import org.example.datn_nhom3_backend.service.OtpService;
import org.example.datn_nhom3_backend.repository.HangGPLXRepository;
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
    private final OtpService otpService;
    private final org.example.datn_nhom3_backend.repository.DangKyKhoaHocRepository dkRepository;
    private final org.example.datn_nhom3_backend.repository.HocVienRepository hvRepository;
    private final HangGPLXRepository hangGPLXRepository;
    public DangKyKhoaHocController(DangKyKhoaHocService service,
                                   HocVienService hocVienService,
                                   KhoaHocService khoaHocService,
                                   OtpService otpService,
                                   org.example.datn_nhom3_backend.repository.DangKyKhoaHocRepository dkRepository,
                                   org.example.datn_nhom3_backend.repository.HocVienRepository hvRepository,
                                   HangGPLXRepository hangGPLXRepository) {
        this.service = service;
        this.hocVienService = hocVienService;
        this.khoaHocService = khoaHocService;
        this.otpService = otpService;
        this.dkRepository = dkRepository;
        this.hvRepository = hvRepository;
        this.hangGPLXRepository = hangGPLXRepository;
    }
    @GetMapping
    public List<DangKyKhoaHoc> getAll() { return service.getAll(); }
    @GetMapping("/{id}")
    public ResponseEntity<DangKyKhoaHoc> getById(@PathVariable Integer id) {
        return service.getById(id).map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dữ liệu với ID: " + id));
    }
    @PostMapping
    public DangKyKhoaHoc create(@RequestBody DangKyKhoaHoc data) { return service.save(data); }
    @PutMapping("/{id}")
    public DangKyKhoaHoc update(@PathVariable Integer id, @RequestBody DangKyKhoaHoc data) throws IllegalAccessException {
        setEntityId(data, id); return service.save(data);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) { service.delete(id); }

    // Gửi OTP đến email
    @PostMapping("/send-otp")
    public Map<String, Object> sendOtp(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        if (email == null || email.isBlank())
            return Map.of("success", false, "message", "Email không được để trống");
        try {
            otpService.sendOtp(email);
            return Map.of("success", true, "message", "Mã OTP đã được gửi đến email của bạn");
        } catch (Exception e) {
            return Map.of("success", false, "message", "Gửi OTP thất bại: " + e.getMessage());
        }
    }

    // Xác thực OTP
    @PostMapping("/verify-otp")
    public Map<String, Object> verifyOtp(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String otp = body.get("otp");
        if (email == null || otp == null)
            return Map.of("success", false, "message", "Thiếu thông tin");
        boolean ok = otpService.verifyOtp(email, otp);
        if (ok) return Map.of("success", true, "message", "Xác thực OTP thành công");
        else return Map.of("success", false, "message", "Mã OTP không đúng hoặc đã hết hạn");
    }

    // Đăng ký khóa học công khai (đã xác thực OTP)
    @PostMapping("/public")
    public Map<String, Object> publicRegister(@RequestBody DangKyKhoaHocPublicRequest req) {
        if (req.getEmail() == null || req.getEmail().isBlank())
            return Map.of("success", false, "message", "Email không được để trống");

        // Kiểm tra OTP đã xác thực chưa
        boolean otpOk = otpService.isEmailVerified(req.getEmail());
        if (!otpOk)
            return Map.of("success", false, "message", "Vui lòng xác thực OTP trước khi đăng ký");

        HocVien hv = new HocVien();
        hv.setHoten(req.getHoten());
        hv.setNgaysinh(req.getNgaysinh());
        hv.setGioitinh(req.getGioitinh());
        hv.setSodienthoai(req.getSodienthoai());
        hv.setEmail(req.getEmail());
        hv.setDiachi(req.getDiachi());
        hv.setNgaydangky(LocalDate.now());
        HocVien savedHv = hocVienService.save(hv);

        KhoaHoc kh = khoaHocService.getById(req.getMakh())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khóa học"));

        HangGPLX hg = null;
        if (req.getMaHang() != null) {
            hg = hangGPLXRepository.findById(Integer.valueOf(req.getMaHang()))
                    .orElse(null);
        }

        DangKyKhoaHoc dk = new DangKyKhoaHoc();
        dk.setHocVien(savedHv);
        dk.setKhoaHoc(kh);
        dk.setHangGPLX(hg);
        dk.setNgaydangky(LocalDate.now());
        dk.setTrangthai("Chờ duyệt");
        DangKyKhoaHoc saved = service.save(dk);

        return Map.of("success", true, "message", "Đăng ký thành công, chúng tôi sẽ liên hệ bạn sau khi duyệt", "data", saved);
    }

    // Tra cứu đăng ký theo email (public)
    @PostMapping("/tra-cuu")
    public Map<String, Object> traCuu(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        if (email == null || email.isBlank())
            return Map.of("success", false, "message", "Email không được để trống");

        List<org.example.datn_nhom3_backend.entity.HocVien> dsHv = hvRepository.findByEmail(email);
        if (dsHv.isEmpty())
            return Map.of("success", true, "data", List.of(), "message", "Không tìm thấy đăng ký nào");

        List<Map<String, Object>> result = new java.util.ArrayList<>();
        for (org.example.datn_nhom3_backend.entity.HocVien hv : dsHv) {
            List<DangKyKhoaHoc> dks = dkRepository.findByHocVien_Mahv(hv.getMahv());
            for (DangKyKhoaHoc dk : dks) {
                Map<String, Object> item = new java.util.HashMap<>();
                item.put("madk", dk.getMadk());
                item.put("tenKhoaHoc", dk.getKhoaHoc() != null ? dk.getKhoaHoc().getTenkhoahoc() : null);
                item.put("ngaydangky", dk.getNgaydangky() != null ? dk.getNgaydangky().toString() : null);
                item.put("trangthai", dk.getTrangthai());
                item.put("hangGPLX", dk.getHangGPLX() != null ? dk.getHangGPLX().getTenHang() : null);
                result.add(item);
            }
        }
        return Map.of("success", true, "data", result);
    }

    private void setEntityId(DangKyKhoaHoc data, Integer id) throws IllegalAccessException {
        for (Field field : data.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) { field.setAccessible(true); field.set(data, id); return; }
        }
    }
}