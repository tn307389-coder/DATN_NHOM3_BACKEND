package org.example.datn_nhom3_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "thi_sat_hach")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ThiSatHach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mathi;
    @ManyToOne
    @JoinColumn(name = "mahv")
    private HocVien hocVien;
    @ManyToOne
    @JoinColumn(name = "malichthi")
    private LichThi lichThi;
    private LocalDate ngaythi;
    private String ketqua;
    private String ghichu;
    public Integer getMathi() {
        return mathi;
    }
    public void setMathi(Integer mathi) {
        this.mathi = mathi;
    }
    public HocVien getHocVien() {
        return hocVien;
    }
    public void setHocVien(HocVien hocVien) {
        this.hocVien = hocVien;
    }
    public LichThi getLichThi() {
        return lichThi;
    }
    public void setLichThi(LichThi lichThi) {
        this.lichThi = lichThi;
    }
    public LocalDate getNgaythi() {
        return ngaythi;
    }
    public void setNgaythi(LocalDate ngaythi) {
        this.ngaythi = ngaythi;
    }
    public String getKetqua() {
        return ketqua;
    }
    public void setKetqua(String ketqua) {
        this.ketqua = ketqua;
    }
    public String getGhichu() {
        return ghichu;
    }
    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }
}
