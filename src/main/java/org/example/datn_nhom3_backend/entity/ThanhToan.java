package org.example.datn_nhom3_backend.entity;
import jakarta.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name = "thanh_toan")
public class ThanhToan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer matt;
    @ManyToOne
    @JoinColumn(name = "mahv", nullable = false)
    private HocVien hocVien;
    
    @ManyToOne
    @JoinColumn(name = "madk", nullable = false)
    private DangKyKhoaHoc dangKyKhoaHoc;
    private LocalDate ngaythanhtoan;
    private Double sotien;
    private String phuongthuc;
    private String trangthai;
    public Integer getMatt() {
        return matt;
    }
    public void setMatt(Integer matt) {
        this.matt = matt;
    }
    public HocVien getHocVien() {
        return hocVien;
    }
    public void setHocVien(HocVien hocVien) {
        this.hocVien = hocVien;
    }
    public DangKyKhoaHoc getDangKyKhoaHoc() {
        return dangKyKhoaHoc;
    }
    public void setDangKyKhoaHoc(DangKyKhoaHoc dangKyKhoaHoc) {
        this.dangKyKhoaHoc = dangKyKhoaHoc;
    }
    public LocalDate getNgaythanhtoan() {
        return ngaythanhtoan;
    }
    public void setNgaythanhtoan(LocalDate ngaythanhtoan) {
        this.ngaythanhtoan = ngaythanhtoan;
    }
    public Double getSotien() {
        return sotien;
    }
    public void setSotien(Double sotien) {
        this.sotien = sotien;
    }
    public String getPhuongthuc() {
        return phuongthuc;
    }
    public void setPhuongthuc(String phuongthuc) {
        this.phuongthuc = phuongthuc;
    }
    public String getTrangthai() {
        return trangthai;
    }
    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }
}