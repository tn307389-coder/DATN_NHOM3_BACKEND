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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RoleBasedAccessIT {

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

    private String hvToken;
    private String adminToken;

    @BeforeEach
    void setUp() throws Exception {
        nhatKyHeThongRepository.deleteAll();
        taiKhoanRepository.deleteAll();

        VaiTro hvRole = vaiTroRepository.findByMaVaiTro("HV").orElseGet(() -> {
            VaiTro v = new VaiTro();
            v.setMaVaiTro("HV");
            v.setTenVaiTro("Học viên");
            return vaiTroRepository.save(v);
        });
        VaiTro adminRole = vaiTroRepository.findByMaVaiTro("ADMIN").orElseGet(() -> {
            VaiTro v = new VaiTro();
            v.setMaVaiTro("ADMIN");
            v.setTenVaiTro("Quản trị viên");
            return vaiTroRepository.save(v);
        });

        TaiKhoan hv = new TaiKhoan();
        hv.setTendangnhap("hv_a");
        hv.setMatkhau(passwordEncoder.encode("pass123"));
        hv.setHoten("HV A");
        hv.setEmail("hva@test.com");
        hv.setVaitro(hvRole);
        hv.setTrangthai("ACTIVE");
        taiKhoanRepository.save(hv);

        TaiKhoan admin = new TaiKhoan();
        admin.setTendangnhap("admin_a");
        admin.setMatkhau(passwordEncoder.encode("admin123"));
        admin.setHoten("Admin A");
        admin.setEmail("admina@test.com");
        admin.setVaitro(adminRole);
        admin.setTrangthai("ACTIVE");
        taiKhoanRepository.save(admin);

        hvToken = login("hv_a", "pass123");
        adminToken = login("admin_a", "admin123");
    }

    private String login(String username, String password) throws Exception {
        String resp = mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("tendangnhap", username, "matkhau", password))))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readTree(resp).path("data").path("token").asText();
    }

    @Test
    void hv_KhongDuocXemDanhSachTaiKhoan_Forbidden() throws Exception {
        mockMvc.perform(get("/api/tai-khoan")
                        .header("Authorization", "Bearer " + hvToken))
                .andExpect(status().isForbidden());
    }

    @Test
    void admin_DuocXemDanhSachTaiKhoan() throws Exception {
        mockMvc.perform(get("/api/tai-khoan")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk());
    }

    @Test
    void hv_KhongDuocGhiHocVien_Forbidden() throws Exception {
        mockMvc.perform(post("/api/hoc-vien")
                        .header("Authorization", "Bearer " + hvToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"hoten\":\"X\",\"cccd\":\"123\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    void admin_DuocGhiHocVien() throws Exception {
        mockMvc.perform(post("/api/hoc-vien")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"hoten\":\"Nguyễn Văn A\",\"cccd\":\"333444555666\",\"sodienthoai\":\"0987654321\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void chuaDangNhap_TruyCapApiPrivate_401() throws Exception {
        mockMvc.perform(get("/api/tai-khoan"))
                .andExpect(status().isUnauthorized());
    }
}
