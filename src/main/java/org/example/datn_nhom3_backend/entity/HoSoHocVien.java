package org.example.datn_nhom3_backend.entity;
import jakarta.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name = "ho_so_hoc_vien")
public class HoSoHocVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer mahs;
    @ManyToOne
    @JoinColumn(name = "mahv")
    private HocVien hocVien;
    @Column(name = "ngaydangky")
    private LocalDate ngaydangky;
    @Column(name = "trang_thai_duyet")
    private String tinhtrang;
    @Column(name = "ghichu")
    private String ghichu;
    @Column(name = "anh_canh_caan")
    private String anhCanhCan;
    @Column(name = "file_ho_so")
    private String fileHoSo;
    public Integer getMahs() {
        return mahs;
    }
    public void setMahs(Integer mahs) {
        this.mahs = mahs;
    }
    public HocVien getHocVien() {
        return hocVien;
    }
    public void setHocVien(HocVien hocVien) {
        this.hocVien = hocVien;
    }
    public LocalDate getNgaydangky() {
        return ngaydangky;
    }
    public void setNgaydangky(LocalDate ngaydangky) {
        this.ngaydangky = ngaydangky;
    }
    public String getTinhtrang() {
        return tinhtrang;
    }
    public void setTinhtrang(String tinhtrang) {
        this.tinhtrang = tinhtrang;
    }
    public String getGhichu() {
        return ghichu;
    }
    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }
    public String getAnhCanhCan() {
        return anhCanhCan;
    }
    public void setAnhCanhCan(String anhCanhCan) {
        this.anhCanhCan = anhCanhCan;
    }
    public String getFileHoSo() {
        return fileHoSo;
    }
    public void setFileHoSo(String fileHoSo) {
        this.fileHoSo = fileHoSo;
    }
}
