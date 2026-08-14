package org.example.datn_nhom3_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.datn_nhom3_backend.entity.TaiKhoan;
import org.example.datn_nhom3_backend.entity.VaiTro;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthControllerIT {

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

    @BeforeEach
    void setUp() {
        taiKhoanRepository.deleteAll();
        VaiTro role = vaiTroRepository.findByMaVaiTro("ADMIN").orElseGet(() -> {
            VaiTro v = new VaiTro();
            v.setMaVaiTro("ADMIN");
            v.setTenVaiTro("Quan tri vien");
            return vaiTroRepository.save(v);
        });
        TaiKhoan tk = new TaiKhoan();
        tk.setTendangnhap("admin");
        tk.setMatkhau(passwordEncoder.encode("admin123"));
        tk.setHoten("Admin Test");
        tk.setEmail("admin@test.com");
        tk.setVaitro(role);
        tk.setTrangthai("ACTIVE");
        taiKhoanRepository.save(tk);
    }

    @Test
    void login_WithValidCredentials_ShouldReturnToken() throws Exception {
        Map<String, String> body = Map.of("tendangnhap", "admin", "matkhau", "admin123");
        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.token").exists());
    }

    @Test
    void login_WithWrongPassword_ShouldReturnUnauthorized() throws Exception {
        Map<String, String> body = Map.of("tendangnhap", "admin", "matkhau", "wrongpass");
        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false));
    }
}
