package org.example.datn_nhom3_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "danh_muc")
public class DanhMuc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nhom", nullable = false, length = 50)
    private String nhom;

    @Column(name = "ma", nullable = false, length = 50)
    private String ma;

    @Column(name = "ten", nullable = false, length = 100)
    private String ten;

    @Column(name = "thu_tu")
    private Integer thuTu = 0;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNhom() { return nhom; }
    public void setNhom(String nhom) { this.nhom = nhom; }
    public String getMa() { return ma; }
    public void setMa(String ma) { this.ma = ma; }
    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }
    public Integer getThuTu() { return thuTu; }
    public void setThuTu(Integer thuTu) { this.thuTu = thuTu; }
}
