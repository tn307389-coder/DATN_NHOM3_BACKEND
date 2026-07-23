package org.example.datn_nhom3_backend.entity;
import jakarta.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name = "giao_vien")
public class GiaoVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer magv;
    private String hoten;
    private String cccd;
    private LocalDate ngaysinh;
    private String gioitinh;
    private String sodienthoai;
    private String email;
    private String diachi;
    private String hangday;
    public Integer getMagv() {
        return magv;
    }
    public void setMagv(Integer magv) {
        this.magv = magv;
    }
    public String getHoten() {
        return hoten;
    }
    public void setHoten(String hoten) {
        this.hoten = hoten;
    }
    public LocalDate getNgaysinh() {
        return ngaysinh;
    }
    public void setNgaysinh(LocalDate ngaysinh) {
        this.ngaysinh = ngaysinh;
    }
    public String getGioitinh() {
        return gioitinh;
    }
    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }
    public String getSodienthoai() {
        return sodienthoai;
    }
    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getDiachi() {
        return diachi;
    }
    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }
    public String getHangday() {
        return hangday;
    }
    public void setHangday(String hangday) {
        this.hangday = hangday;
    }
    public String getCccd() {
        return cccd;
    }
    public void setCccd(String cccd) {
        this.cccd = cccd;
    }
}