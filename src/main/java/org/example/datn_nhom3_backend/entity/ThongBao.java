package org.example.datn_nhom3_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "thong_bao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ThongBao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer matb;

    @Column(name = "tieu_de")
    private String tieude;

    @Column(name = "noi_dung")
    private String noidung;

    @Column(name = "ngay_tao")
    private LocalDateTime ngaytao;

    @Column(name = "doituong")
    private String doituong;
}
