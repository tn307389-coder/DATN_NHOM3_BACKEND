package org.example.datn_nhom3_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.datn_nhom3_backend.entity.TaiKhoan;
import org.example.datn_nhom3_backend.entity.VaiTro;
import org.example.datn_nhom3_backend.repository.NhatKyHeThongRepository;
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

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class LoginSecurityIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;
    @Autowired
    private VaiTroRepository vaiTroRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private NhatKyHeThongRepository nhatKyHeThongRepository;

    @BeforeEach
    void setUp() {
        nhatKyHeThongRepository.deleteAll();
        taiKhoanRepository.deleteAll();
        VaiTro role = vaiTroRepository.findByMaVaiTro("HV").orElseGet(() -> {
            VaiTro v = new VaiTro();
            v.setMaVaiTro("HV");
            v.setTenVaiTro("Học viên");
            return vaiTroRepository.save(v);
        });
        TaiKhoan tk = new TaiKhoan();
        tk.setTendangnhap("hv01");
        tk.setMatkhau(passwordEncoder.encode("pass123"));
        tk.setHoten("Học Viên Test");
        tk.setEmail("hv01@test.com");
        tk.setVaitro(role);
        tk.setTrangthai("ACTIVE");
        taiKhoanRepository.save(tk);
    }

    @Test
    void login_SaiMatKhau5Lan_PhaiKhoaTaiKhoan() throws Exception {
        Map<String, String> body = Map.of("tendangnhap", "hv01", "matkhau", "sai-mat-khau");
        // 4 lần đầu: 401
        for (int i = 0; i < 4; i++) {
            mockMvc.perform(post("/api/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isUnauthorized());
        }
        // Lần thứ 5: khóa tài khoản -> 423
        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isLocked())
                .andExpect(jsonPath("$.message").exists());
        // Ngay cả đúng mật khẩu cũng bị chặn khi đang khóa
        Map<String, String> dung = Map.of("tendangnhap", "hv01", "matkhau", "pass123");
        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dung)))
                .andExpect(status().isLocked());
    }

    @Test
    void login_DungMatKhau_ResetSoLanSai() throws Exception {
        Map<String, String> sai = Map.of("tendangnhap", "hv01", "matkhau", "sai-mat-khau");
        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sai)))
                .andExpect(status().isUnauthorized());

        Map<String, String> dung = Map.of("tendangnhap", "hv01", "matkhau", "pass123");
        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dung)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.token").exists())
                .andExpect(jsonPath("$.data.refreshToken").exists());
    }

    @Test
    void refreshToken_VoiTokenHopLe_TraTokenMoi() throws Exception {
        // Đăng nhập lấy refresh token
        Map<String, String> loginBody = Map.of("tendangnhap", "hv01", "matkhau", "pass123");
        String loginResp = mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginBody)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String refresh = objectMapper.readTree(loginResp).path("data").path("refreshToken").asText();

        mockMvc.perform(post("/api/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("refreshToken", refresh))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.token").exists())
                .andExpect(jsonPath("$.data.refreshToken").exists());
    }

    @Test
    void refreshToken_VoiTokenKhongHopLe_Tra401() throws Exception {
        mockMvc.perform(post("/api/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("refreshToken", "token-gia"))))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void doiMatKhau_DungMatKhauCu_TraThanhCong() throws Exception {
        // Đăng nhập lấy token
        Map<String, String> loginBody = Map.of("tendangnhap", "hv01", "matkhau", "pass123");
        String loginResp = mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginBody)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String token = objectMapper.readTree(loginResp).path("data").path("token").asText();

        Map<String, String> body = Map.of("matKhauCu", "pass123", "matKhauMoi", "matkhau-moi-123");
        mockMvc.perform(put("/api/tai-khoan/me/doi-mat-khau")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

        // Đăng nhập với mật khẩu mới thành công
        Map<String, String> newLogin = Map.of("tendangnhap", "hv01", "matkhau", "matkhau-moi-123");
        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newLogin)))
                .andExpect(status().isOk());
    }

    @Test
    void doiMatKhau_SaiMatKhauCu_TraLoi400() throws Exception {
        Map<String, String> loginBody = Map.of("tendangnhap", "hv01", "matkhau", "pass123");
        String loginResp = mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginBody)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String token = objectMapper.readTree(loginResp).path("data").path("token").asText();

        Map<String, String> body = Map.of("matKhauCu", "sai-cu", "matKhauMoi", "matkhau-moi-123");
        mockMvc.perform(put("/api/tai-khoan/me/doi-mat-khau")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }
}
