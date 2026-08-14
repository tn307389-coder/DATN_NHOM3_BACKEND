package org.example.datn_nhom3_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.datn_nhom3_backend.entity.OtpVerification;
import org.example.datn_nhom3_backend.entity.TaiKhoan;
import org.example.datn_nhom3_backend.entity.VaiTro;
import org.example.datn_nhom3_backend.repository.NhatKyHeThongRepository;
import org.example.datn_nhom3_backend.repository.OtpVerificationRepository;
import org.example.datn_nhom3_backend.repository.TaiKhoanRepository;
import org.example.datn_nhom3_backend.repository.VaiTroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class QuenMatKhauControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;
    @Autowired
    private VaiTroRepository vaiTroRepository;
    @Autowired
    private OtpVerificationRepository otpVerificationRepository;
    @Autowired
    private NhatKyHeThongRepository nhatKyHeThongRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String EMAIL = "hv.forgot@test.com";
    private static final String OTP_CODE = "123456";

    @BeforeEach
    void setUp() {
        nhatKyHeThongRepository.deleteAll();
        otpVerificationRepository.deleteAll();
        taiKhoanRepository.deleteAll();

        VaiTro role = vaiTroRepository.findByMaVaiTro("HV").orElseGet(() -> {
            VaiTro v = new VaiTro();
            v.setMaVaiTro("HV");
            v.setTenVaiTro("Học viên");
            return vaiTroRepository.save(v);
        });
        TaiKhoan tk = new TaiKhoan();
        tk.setTendangnhap("hv_forgot");
        tk.setMatkhau(passwordEncoder.encode("matkhau-cu-123"));
        tk.setHoten("Học viên Quên MK");
        tk.setEmail(EMAIL);
        tk.setVaitro(role);
        tk.setTrangthai("ACTIVE");
        taiKhoanRepository.save(tk);
    }

    // Chèn OTP hợp lệ trực tiếp vào DB (không phụ thuộc mail server thật)
    private void seedValidOtp() {
        OtpVerification ov = new OtpVerification();
        ov.setEmail(EMAIL);
        ov.setOtp(OTP_CODE);
        ov.setExpiry(LocalDateTime.now().plusMinutes(5));
        ov.setVerified(false);
        otpVerificationRepository.save(ov);
    }

    @Test
    void sendOtp_EmailKhongTonTai_VanTraSuccess() throws Exception {
        mockMvc.perform(post("/api/quen-mat-khau/send-otp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("email", "khong-ton-tai@test.com"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void verifyOtp_MaDung_TraThanhCong() throws Exception {
        seedValidOtp();
        mockMvc.perform(post("/api/quen-mat-khau/verify-otp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("email", EMAIL, "otp", OTP_CODE))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void verifyOtp_MaSai_TraThatBai() throws Exception {
        seedValidOtp();
        mockMvc.perform(post("/api/quen-mat-khau/verify-otp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("email", EMAIL, "otp", "000000"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void resetPassword_DaVerifyOtp_DoiMatKhauThanhCong() throws Exception {
        seedValidOtp();
        // Xác thực OTP trước
        mockMvc.perform(post("/api/quen-mat-khau/verify-otp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("email", EMAIL, "otp", OTP_CODE))))
                .andExpect(jsonPath("$.success").value(true));

        // Đặt lại mật khẩu
        mockMvc.perform(post("/api/quen-mat-khau/reset-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "email", EMAIL, "otp", OTP_CODE, "matKhauMoi", "matkhau-moi-456"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

        // Đăng nhập bằng mật khẩu mới thành công
        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("tendangnhap", "hv_forgot", "matkhau", "matkhau-moi-456"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.token").exists());
    }

    @Test
    void resetPassword_ChuaVerifyOtp_Tra400() throws Exception {
        mockMvc.perform(post("/api/quen-mat-khau/reset-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "email", EMAIL, "otp", OTP_CODE, "matKhauMoi", "matkhau-moi-456"))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void resetPassword_MatKhauNgan6KyTu_Tra400() throws Exception {
        seedValidOtp();
        mockMvc.perform(post("/api/quen-mat-khau/verify-otp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("email", EMAIL, "otp", OTP_CODE))))
                .andExpect(jsonPath("$.success").value(true));

        mockMvc.perform(post("/api/quen-mat-khau/reset-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "email", EMAIL, "otp", OTP_CODE, "matKhauMoi", "abc"))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }
}
