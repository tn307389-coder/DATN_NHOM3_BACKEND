package org.example.datn_nhom3_backend.controller;

import org.example.datn_nhom3_backend.entity.HoSoHocVien;
import org.example.datn_nhom3_backend.entity.HocVien;
import org.example.datn_nhom3_backend.entity.TaiKhoan;
import org.example.datn_nhom3_backend.entity.VaiTro;
import org.example.datn_nhom3_backend.repository.HoSoHocVienRepository;
import org.example.datn_nhom3_backend.repository.HocVienRepository;
import org.example.datn_nhom3_backend.repository.NhatKyHeThongRepository;
import org.example.datn_nhom3_backend.repository.TaiKhoanRepository;
import org.example.datn_nhom3_backend.repository.VaiTroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class HoSoCuaToiIT {

    private static final String USERNAME = "hocvien_it";
    private static final String CCCD = "0245555668";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;
    @Autowired
    private VaiTroRepository vaiTroRepository;
    @Autowired
    private HocVienRepository hocVienRepository;
    @Autowired
    private HoSoHocVienRepository hoSoHocVienRepository;
    @Autowired
    private NhatKyHeThongRepository nhatKyHeThongRepository;

    private HoSoHocVien hoSo;

    @BeforeEach
    void setUp() {
        nhatKyHeThongRepository.deleteAll();
        hoSoHocVienRepository.deleteAll();
        taiKhoanRepository.deleteAll();
        hocVienRepository.deleteAll();

        VaiTro hvRole = vaiTroRepository.findByMaVaiTro("HV").orElseGet(() -> {
            VaiTro v = new VaiTro();
            v.setMaVaiTro("HV");
            v.setTenVaiTro("Học viên");
            return vaiTroRepository.save(v);
        });

        HocVien hocVien = new HocVien();
        hocVien.setHoten("Lê Nhật Phát");
        hocVien.setCccd(CCCD);
        hocVien.setSodienthoai("0866557176");
        hocVien.setEmail("phat_hoso@example.com");
        hocVien.setNgaysinh(LocalDate.of(2000, 5, 20));
        hocVien.setGioitinh("Nam");
        hocVien.setDiachi("TP.HCM");
        hocVien.setNgaydangky(LocalDate.of(2026, 8, 1));
        hocVien = hocVienRepository.save(hocVien);

        TaiKhoan tk = new TaiKhoan();
        tk.setTendangnhap(USERNAME);
        tk.setMatkhau("encrypted");
        tk.setHoten("Lê Nhật Phát");
        tk.setEmail("phat_hoso@example.com");
        tk.setCccd(CCCD);
        tk.setVaitro(hvRole);
        tk.setTrangthai("ACTIVE");
        taiKhoanRepository.save(tk);

        HoSoHocVien hoSoNew = new HoSoHocVien();
        hoSoNew.setHocVien(hocVien);
        hoSoNew.setNgaydangky(LocalDate.of(2026, 8, 1));
        hoSoNew.setTinhtrang("Đang xử lý");
        hoSoNew.setDaChinhSua(false);
        hoSo = hoSoHocVienRepository.save(hoSoNew);
    }

    @Test
    @WithMockUser(username = USERNAME, roles = "HV")
    void getMe_CoHoSo_TraVe200() throws Exception {
        mockMvc.perform(get("/api/ho-so-hoc-vien/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mahs").value(hoSo.getMahs()))
                .andExpect(jsonPath("$.hocVien.cccd").value(CCCD))
                .andExpect(jsonPath("$.tinhtrang").value("Đang xử lý"))
                .andExpect(jsonPath("$.daChinhSua").value(false));
    }

    @Test
    @WithMockUser(username = USERNAME, roles = "HV")
    void putMe_CapNhatMotLan_ThanhCong() throws Exception {
        String json = """
                {"hocVien":{"hoten":"Lê Nhật Phát Mới","ngaysinh":"2000-05-20","gioitinh":"Nam",
                 "sodienthoai":"0999999999","email":"phat_hoso@example.com","diachi":"Hà Nội"},
                 "anhCanhCuoc":"/api/files/anh1.jpg","fileHoSo":"/api/files/hoso1.pdf"}
                """;
        mockMvc.perform(put("/api/ho-so-hoc-vien/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.daChinhSua").value(true))
                .andExpect(jsonPath("$.hocVien.hoten").value("Lê Nhật Phát Mới"))
                .andExpect(jsonPath("$.hocVien.diachi").value("Hà Nội"))
                .andExpect(jsonPath("$.anhCanhCuoc").value("/api/files/anh1.jpg"))
                .andExpect(jsonPath("$.fileHoSo").value("/api/files/hoso1.pdf"));
    }

    @Test
    @WithMockUser(username = USERNAME, roles = "HV")
    void putMe_LanThuHai_BiKhoa409() throws Exception {
        String lan1 = """
                {"hocVien":{"hoten":"Lê Nhật Phát Sửa","ngaysinh":"2000-05-20","gioitinh":"Nam"}}
                """;
        mockMvc.perform(put("/api/ho-so-hoc-vien/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(lan1))
                .andExpect(status().isOk());

        String lan2 = """
                {"hocVien":{"hoten":"Lê Nhật Phát Sửa Lần 2"}}
                """;
        mockMvc.perform(put("/api/ho-so-hoc-vien/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(lan2))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    @WithMockUser(username = USERNAME, roles = "HV")
    void getMe_ChuaCoHoSo_TraVe404() throws Exception {
        hoSoHocVienRepository.deleteAll();
        mockMvc.perform(get("/api/ho-so-hoc-vien/me"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = USERNAME, roles = "HV")
    void putMe_ChuaCoHoSo_TraVe404() throws Exception {
        hoSoHocVienRepository.deleteAll();
        mockMvc.perform(put("/api/ho-so-hoc-vien/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"hocVien":{"hoten":"Lê Nhật Phát"}}
                                """))
                .andExpect(status().isNotFound());
    }
}
