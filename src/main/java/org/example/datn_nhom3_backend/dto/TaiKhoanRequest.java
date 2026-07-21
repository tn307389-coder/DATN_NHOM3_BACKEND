package org.example.datn_nhom3_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaiKhoanRequest {
    private String tendangnhap;
    private String matkhau;
    private String hoten;
    private String email;
    private String soDienThoai;
    private String anh;
    private String maVaiTro;
    private Boolean trangthai;
}
