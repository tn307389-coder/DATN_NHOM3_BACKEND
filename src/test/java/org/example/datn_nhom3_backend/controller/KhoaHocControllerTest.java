package org.example.datn_nhom3_backend.controller;

import org.example.datn_nhom3_backend.entity.KhoaHoc;
import org.example.datn_nhom3_backend.service.KhoaHocService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(roles = "ADMIN")
class KhoaHocControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAll_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/khoa-hoc"))
                .andExpect(status().isOk());
    }

    @Test
    void postCreate_ShouldCreate() throws Exception {
        String json = """
                {"tenkhoahoc":"Khóa học test B2","trangthai":"DANG_MO"}
                """;
        mockMvc.perform(post("/api/khoa-hoc")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void putUpdate_ShouldUpdate() throws Exception {
        String json = """
                {"tenkhoahoc":"Khóa học cập nhật","trangthai":"DA_DONG"}
                """;
        mockMvc.perform(put("/api/khoa-hoc/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }
}