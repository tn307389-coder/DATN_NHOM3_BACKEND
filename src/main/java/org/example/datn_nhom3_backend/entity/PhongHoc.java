package org.example.datn_nhom3_backend.entity;
import jakarta.persistence.*;
@Entity
@Table(name = "phong_hoc")
public class PhongHoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maphong;
    private String tenphong;
    private Integer succhua;
    private String trangthai;
    public Integer getMaphong() {
        return maphong;
    }
    public void setMaphong(Integer maphong) {
        this.maphong = maphong;
    }
    public String getTenphong() {
        return tenphong;
    }
    public void setTenphong(String tenphong) {
        this.tenphong = tenphong;
    }
    public Integer getSucchua() {
        return succhua;
    }
    public void setSucchua(Integer succhua) {
        this.succhua = succhua;
    }
    public String getTrangthai() {
        return trangthai;
    }
    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }
}