package org.example.datn_nhom3_backend.controller;

import org.example.datn_nhom3_backend.annotation.LogAction;
import org.example.datn_nhom3_backend.entity.TaiKhoan;
import org.example.datn_nhom3_backend.repository.TaiKhoanRepository;
import org.example.datn_nhom3_backend.service.OtpService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/quen-mat-khau")
@CrossOrigin(origins = "http://localhost:5173")
public class QuenMatKhauController {

    private final TaiKhoanRepository taiKhoanRepository;
    private final OtpService otpService;
    private final PasswordEncoder passwordEncoder;

    public QuenMatKhauController(TaiKhoanRepository taiKhoanRepository,
                                 OtpService otpService,
                                 PasswordEncoder passwordEncoder) {
        this.taiKhoanRepository = taiKhoanRepository;
        this.otpService = otpService;
        this.passwordEncoder = passwordEncoder;
    }

    // Gửi OTP đến email: chỉ gửi nếu email có tài khoản, nhưng luôn trả success để không lộ email
    @PostMapping("/send-otp")
    public Map<String, Object> sendOtp(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        if (email == null || email.isBlank())
            return Map.of("success", false, "message", "Email không được để trống");
        try {
            Optional<TaiKhoan> tk = taiKhoanRepository.findByEmail(email.trim());
            if (tk.isPresent() && tk.get().getGoogleId() == null) {
                otpService.sendOtp(email.trim());
            }
            return Map.of("success", true, "message", "Nếu email tồn tại, mã OTP đã được gửi đến hộp thư của bạn");
        } catch (Exception e) {
            return Map.of("success", false, "message", "Gửi OTP thất bại: " + e.getMessage());
        }
    }

    // Xác thực OTP
    @PostMapping("/verify-otp")
    public Map<String, Object> verifyOtp(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String otp = body.get("otp");
        if (email == null || email.isBlank() || otp == null || otp.isBlank())
            return Map.of("success", false, "message", "Thiếu thông tin xác thực");
        boolean ok = otpService.verifyOtp(email.trim(), otp.trim());
        if (ok) return Map.of("success", true, "message", "Xác thực OTP thành công");
        return Map.of("success", false, "message", "Mã OTP không đúng hoặc đã hết hạn");
    }

    // Đặt lại mật khẩu sau khi đã xác thực OTP
    @PostMapping("/reset-password")
    @LogAction(action = "Đặt lại mật khẩu", table = "tai_khoan")
    public ResponseEntity<Map<String, Object>> resetPassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String otp = body.get("otp");
        String matKhauMoi = body.get("matKhauMoi");
        if (email == null || email.isBlank() || otp == null || otp.isBlank() || matKhauMoi == null || matKhauMoi.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Thiếu thông tin"));
        }
        if (matKhauMoi.length() < 6) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Mật khẩu mới phải có ít nhất 6 ký tự"));
        }
        if (!otpService.isOtpVerifiedForEmail(email.trim())) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Vui lòng xác thực OTP trước khi đặt lại mật khẩu"));
        }
        Optional<TaiKhoan> tk = taiKhoanRepository.findByEmail(email.trim());
        if (tk.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Không tìm thấy tài khoản với email này"));
        }
        TaiKhoan taiKhoan = tk.get();
        taiKhoan.setMatkhau(passwordEncoder.encode(matKhauMoi));
        taiKhoan.setSoLanDangNhapSai(0);
        taiKhoan.setKhoaDen(null);
        taiKhoan.setNgayCapNhat(LocalDateTime.now());
        taiKhoanRepository.save(taiKhoan);
        return ResponseEntity.ok(Map.of("success", true, "message", "Đặt lại mật khẩu thành công"));
    }
}
