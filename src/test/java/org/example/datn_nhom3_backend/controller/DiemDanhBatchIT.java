package org.example.datn_nhom3_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.datn_nhom3_backend.entity.HocVien;
import org.example.datn_nhom3_backend.entity.LichHoc;
import org.example.datn_nhom3_backend.entity.TaiKhoan;
import org.example.datn_nhom3_backend.entity.VaiTro;
import org.example.datn_nhom3_backend.repository.DiemDanhRepository;
import org.example.datn_nhom3_backend.repository.HocVienRepository;
import org.example.datn_nhom3_backend.repository.LichHocRepository;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DiemDanhBatchIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HocVienRepository hocVienRepository;
    @Autowired
    private LichHocRepository lichHocRepository;
    @Autowired
    private DiemDanhRepository diemDanhRepository;
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;
    @Autowired
    private VaiTroRepository vaiTroRepository;
    @Autowired
    private NhatKyHeThongRepository nhatKyHeThongRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private HocVien hvA;
    private HocVien hvB;
    private LichHoc lichHoc;
    private String adminToken;

    @BeforeEach
    void setUp() throws Exception {
        nhatKyHeThongRepository.deleteAll();
        diemDanhRepository.deleteAll();
        hocVienRepository.deleteAll();
        lichHocRepository.deleteAll();
        taiKhoanRepository.deleteAll();

        VaiTro adminRole = vaiTroRepository.findByMaVaiTro("ADMIN").orElseGet(() -> {
            VaiTro v = new VaiTro();
            v.setMaVaiTro("ADMIN");
            v.setTenVaiTro("Quản trị viên");
            return vaiTroRepository.save(v);
        });
        TaiKhoan admin = new TaiKhoan();
        admin.setTendangnhap("admin_dd");
        admin.setMatkhau(passwordEncoder.encode("admin123"));
        admin.setHoten("Admin Điểm Danh");
        admin.setEmail("admin_dd@test.com");
        admin.setVaitro(adminRole);
        admin.setTrangthai("ACTIVE");
        taiKhoanRepository.save(admin);
        adminToken = login("admin_dd", "admin123");

        hvA = new HocVien();
        hvA.setHoten("Học viên A");
        hvA.setCccd("111111111111");
        hvA.setSodienthoai("0900000001");
        hvA.setEmail("a@test.com");
        hvA.setNgaysinh(LocalDate.of(2000, 1, 1));
        hvA = hocVienRepository.save(hvA);

        hvB = new HocVien();
        hvB.setHoten("Học viên B");
        hvB.setCccd("222222222222");
        hvB.setSodienthoai("0900000002");
        hvB.setEmail("b@test.com");
        hvB.setNgaysinh(LocalDate.of(2000, 2, 2));
        hvB = hocVienRepository.save(hvB);

        LichHoc lich = new LichHoc();
        lich.setNgayhoc(LocalDate.of(2026, 8, 15));
        lichHoc = lichHocRepository.save(lich);
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
    void saveBatch_CoHocVienHopLe_LuuDiemDanh() throws Exception {
        Map<String, Object> body = Map.of(
                "malich", lichHoc.getMalich(),
                "ngaydiemdanh", "2026-08-15",
                "danhSach", List.of(
                        Map.of("mahv", hvA.getMahv(), "trangthai", "Có mặt", "ghichu", ""),
                        Map.of("mahv", hvB.getMahv(), "trangthai", "Vắng mặt", "ghichu", "Xin phép")
                ));

        mockMvc.perform(post("/api/diem-danh/batch")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

        mockMvc.perform(get("/api/diem-danh/by-lich")
                        .header("Authorization", "Bearer " + adminToken)
                        .param("malich", String.valueOf(lichHoc.getMalich()))
                        .param("ngay", "2026-08-15"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void saveBatch_GoiLai2Lan_GhiDeKhongTrungLap() throws Exception {
        Map<String, Object> body = Map.of(
                "malich", lichHoc.getMalich(),
                "ngaydiemdanh", "2026-08-15",
                "danhSach", List.of(
                        Map.of("mahv", hvA.getMahv(), "trangthai", "Có mặt", "ghichu", ""),
                        Map.of("mahv", hvB.getMahv(), "trangthai", "Vắng mặt", "ghichu", "")));

        for (int i = 0; i < 2; i++) {
            mockMvc.perform(post("/api/diem-danh/batch")
                            .header("Authorization", "Bearer " + adminToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isOk());
        }

        mockMvc.perform(get("/api/diem-danh/by-lich")
                        .header("Authorization", "Bearer " + adminToken)
                        .param("malich", String.valueOf(lichHoc.getMalich()))
                        .param("ngay", "2026-08-15"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void saveBatch_KhongAuth_Tra401() throws Exception {
        Map<String, Object> body = Map.of(
                "malich", lichHoc.getMalich(),
                "ngaydiemdanh", "2026-08-15",
                "danhSach", List.of());

        mockMvc.perform(post("/api/diem-danh/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isUnauthorized());
    }
}
