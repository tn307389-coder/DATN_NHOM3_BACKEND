package org.example.datn_nhom3_backend.controller;

import org.example.datn_nhom3_backend.entity.HocVien;
import org.example.datn_nhom3_backend.repository.HocVienRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(roles = "ADMIN")
class HocVienControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HocVienRepository hocVienRepository;

    private HocVien savedHocVien;

    @BeforeEach
    void setUp() {
        hocVienRepository.deleteAll();
        savedHocVien = new HocVien();
        savedHocVien.setHoten("Trần Thị B");
        savedHocVien.setCccd("987654321012");
        savedHocVien.setSodienthoai("0912345678");
        savedHocVien.setEmail("tranthib@example.com");
        savedHocVien.setNgaysinh(LocalDate.of(1999, 5, 15));
        savedHocVien.setGioitinh("Nữ");
        savedHocVien.setDiachi("TP.HCM");
        savedHocVien = hocVienRepository.save(savedHocVien);
    }

    @Test
    void getAll_ShouldReturnList() throws Exception {
        mockMvc.perform(get("/api/hoc-vien"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getById_WhenExists_ShouldReturnHocVien() throws Exception {
        mockMvc.perform(get("/api/hoc-vien/{id}", savedHocVien.getMahv()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hoten").value("Trần Thị B"));
    }

    @Test
    void getById_WhenNotExists_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/hoc-vien/{id}", 99999))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_ShouldSaveHocVien() throws Exception {
        String json = """
                {"hoten":"Lê Văn C","cccd":"111222333444","sodienthoai":"0923456789",
                 "email":"levanc@example.com","ngaysinh":"1998-03-20","gioitinh":"Nam","diachi":"Đà Nẵng"}
                """;

        mockMvc.perform(post("/api/hoc-vien")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void update_ShouldUpdateHocVien() throws Exception {
        String json = """
                {"hoten":"Trần Thị B Cập Nhật","sodienthoai":"0999999999"}
                """;

        mockMvc.perform(put("/api/hoc-vien/{id}", savedHocVien.getMahv())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }
}