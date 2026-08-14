package org.example.datn_nhom3_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaiKhoanRequest {
    @NotBlank(message = "Tên đăng nhập không được để trống")
    private String tendangnhap;

    private String matkhau;

    @NotBlank(message = "Họ tên không được để trống")
    private String hoten;

    @Email(message = "Email không hợp lệ")
    private String email;

    private String soDienThoai;
    private String anh;

    @NotBlank(message = "Vai trò không được để trống")
    private String maVaiTro;

    private Boolean trangthai;
}
