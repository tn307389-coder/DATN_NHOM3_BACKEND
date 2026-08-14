package org.example.datn_nhom3_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.datn_nhom3_backend.entity.NhatKyHeThong;
import org.example.datn_nhom3_backend.repository.NhatKyHeThongRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class LogActionAspectIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private NhatKyHeThongRepository nhatKyHeThongRepository;

    @Test
    @WithMockUser(roles = "ADMIN")
    void createHocVien_ShouldWriteAuditLog() throws Exception {
        nhatKyHeThongRepository.deleteAll();
        String json = """
                {"hoten":"Audit Test","cccd":"555111222333","sodienthoai":"0901112223",
                 "email":"audit@test.com","ngaysinh":"2001-02-02","gioitinh":"Nam","diachi":"HCM"}
                """;
        mockMvc.perform(post("/api/hoc-vien")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        assertTrue(nhatKyHeThongRepository.count() >= 1,
                "Nhật ký hệ thống phải ghi ít nhất 1 record sau khi tạo học viên");
    }
}
