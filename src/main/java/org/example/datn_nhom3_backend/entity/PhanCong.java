package org.example.datn_nhom3_backend.entity;
import jakarta.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name = "phan_cong")
public class PhanCong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer mapc;
    @ManyToOne
    @JoinColumn(name = "mahv")
    private HocVien hocVien;
    @ManyToOne
    @JoinColumn(name = "magv")
    private GiaoVien giaoVien;
    @ManyToOne
    @JoinColumn(name = "maxetl")
    private XeTapLai xeTapLai;
    @Column(name = "ngay_phan_cong")
    private LocalDate ngayphancong;
    @Column(name = "ghichu")
    private String ghichu;
    public Integer getMapc() {
        return mapc;
    }
    public void setMapc(Integer mapc) {
        this.mapc = mapc;
    }
    public HocVien getHocVien() {
        return hocVien;
    }
    public void setHocVien(HocVien hocVien) {
        this.hocVien = hocVien;
    }
    public GiaoVien getGiaoVien() {
        return giaoVien;
    }
    public void setGiaoVien(GiaoVien giaoVien) {
        this.giaoVien = giaoVien;
    }
    public XeTapLai getXeTapLai() {
        return xeTapLai;
    }
    public void setXeTapLai(XeTapLai xeTapLai) {
        this.xeTapLai = xeTapLai;
    }
    public LocalDate getNgayphancong() {
        return ngayphancong;
    }
    public void setNgayphancong(LocalDate ngayphancong) {
        this.ngayphancong = ngayphancong;
    }
    public String getGhichu() {
        return ghichu;
    }
    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }
}