package org.example.datn_nhom3_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DangKyKhoaHocPublicRequest {
    @NotBlank(message = "Họ tên không được để trống")
    private String hoten;

    private LocalDate ngaysinh;
    private String gioitinh;
    private String sodienthoai;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    private String diachi;

    @NotNull(message = "Vui lòng chọn khóa học")
    private Integer makh;

    private String maHang;
}
