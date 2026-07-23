package org.example.datn_nhom3_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DangKyKhoaHocPublicRequest {
    private String hoten;
    private LocalDate ngaysinh;
    private String gioitinh;
    private String sodienthoai;
    private String email;
    private String diachi;
    private Integer makh;
}
