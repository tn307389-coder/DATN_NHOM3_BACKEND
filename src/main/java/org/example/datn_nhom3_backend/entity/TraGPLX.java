package org.example.datn_nhom3_backend.entity;
import jakarta.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name = "tra_gplx")
public class TraGPLX {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer magplx;
    @ManyToOne
    @JoinColumn(name = "mahv")
    private HocVien hocVien;
    @ManyToOne
    @JoinColumn(name = "mathi")
    private ThiSatHach thiSatHach;
    @Column(name = "sogplx")
    private String sogplx;
    @Column(name = "hanggplx")
    private String hanggplx;
    @Column(name = "ngaycap")
    private LocalDate ngaycap;
    @Column(name = "ngayhethan")
    private LocalDate ngayhethan;
    @Column(name = "trangthai")
    private String trangthai;
    public Integer getMagplx() {
        return magplx;
    }
    public void setMagplx(Integer magplx) {
        this.magplx = magplx;
    }
    public HocVien getHocVien() {
        return hocVien;
    }
    public void setHocVien(HocVien hocVien) {
        this.hocVien = hocVien;
    }
    public ThiSatHach getThiSatHach() {
        return thiSatHach;
    }
    public void setThiSatHach(ThiSatHach thiSatHach) {
        this.thiSatHach = thiSatHach;
    }
    public String getSogplx() {
        return sogplx;
    }
    public void setSogplx(String sogplx) {
        this.sogplx = sogplx;
    }
    public String getHanggplx() {
        return hanggplx;
    }
    public void setHanggplx(String hanggplx) {
        this.hanggplx = hanggplx;
    }
    public LocalDate getNgaycap() {
        return ngaycap;
    }
    public void setNgaycap(LocalDate ngaycap) {
        this.ngaycap = ngaycap;
    }
    public LocalDate getNgayhethan() {
        return ngayhethan;
    }
    public void setNgayhethan(LocalDate ngayhethan) {
        this.ngayhethan = ngayhethan;
    }
    public String getTrangthai() {
        return trangthai;
    }
    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }
}