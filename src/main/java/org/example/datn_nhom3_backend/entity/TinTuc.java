package org.example.datn_nhom3_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tin_tuc")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TinTuc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "tieu_de")
    private String tieude;

    @Column(name = "hinh_anh")
    private String hinhanh;

    @Column(name = "mo_ta_ngan")
    private String motangan;

    @Column(name = "noi_dung", columnDefinition = "NVARCHAR(MAX)")
    private String noidung;

    @Column(name = "ngay_dang")
    private LocalDateTime ngaydang;

    @Column(name = "tac_gia")
    private String tacgia;

    @Column(name = "trang_thai")
    private String trangthai;
}
