package org.example.datn_nhom3_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.datn_nhom3_backend.entity.*;
import org.example.datn_nhom3_backend.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DangKyKhoaHocControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OtpVerificationRepository otpRepository;
    @Autowired
    private HocVienRepository hocVienRepository;
    @Autowired
    private KhoaHocRepository khoaHocRepository;

    private Integer makh;

    @BeforeEach
    void setUp() {
        otpRepository.deleteAll();
        hocVienRepository.deleteAll();
        khoaHocRepository.deleteAll();

        KhoaHoc kh = new KhoaHoc();
        kh.setTenkhoahoc("Khoa Test Dang Ky");
        kh.setTrangthai("DANG_MO");
        kh = khoaHocRepository.save(kh);
        makh = kh.getMakh();
    }

    @Test
    void publicRegister_WithoutVerifiedOtp_ShouldFail() throws Exception {
        OtpVerification ov = new OtpVerification();
        ov.setEmail("chuaverify@test.com");
        ov.setOtp("123456");
        ov.setExpiry(java.time.LocalDateTime.now().plusMinutes(5));
        ov.setVerified(false);
        otpRepository.save(ov);

        String json = """
                {"hoten":"Nguyen DK","ngaysinh":"2000-01-01","gioitinh":"Nam",
                 "sodienthoai":"0909998887","email":"chuaverify@test.com",
                 "diachi":"HCM","makh":%d}
                """.formatted(makh);
        mockMvc.perform(post("/api/dang-ky-khoa-hoc/public")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));
    }
}
