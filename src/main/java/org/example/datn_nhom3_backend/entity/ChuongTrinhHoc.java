package org.example.datn_nhom3_backend.entity;
import jakarta.persistence.*;
@Entity
@Table(name = "chuong_trinh_hoc")
public class ChuongTrinhHoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer macth;
    private String tenchuongtrinh;
    private String hangbang;
    private Double hocphi;
    private Integer sobuoilythuyet;
    private Integer sobuoithuchanh;
    public Integer getMacth() {
        return macth;
    }
    public void setMacth(Integer macth) {
        this.macth = macth;
    }
    public String getTenchuongtrinh() {
        return tenchuongtrinh;
    }
    public void setTenchuongtrinh(String tenchuongtrinh) {
        this.tenchuongtrinh = tenchuongtrinh;
    }
    public String getHangbang() {
        return hangbang;
    }
    public void setHangbang(String hangbang) {
        this.hangbang = hangbang;
    }
    public Double getHocphi() {
        return hocphi;
    }
    public void setHocphi(Double hocphi) {
        this.hocphi = hocphi;
    }
    public Integer getSobuoilythuyet() {
        return sobuoilythuyet;
    }
    public void setSobuoilythuyet(Integer sobuoilythuyet) {
        this.sobuoilythuyet = sobuoilythuyet;
    }
    public Integer getSobuoithuchanh() {
        return sobuoithuchanh;
    }
    public void setSobuoithuchanh(Integer sobuoithuchanh) {
        this.sobuoithuchanh = sobuoithuchanh;
    }
}