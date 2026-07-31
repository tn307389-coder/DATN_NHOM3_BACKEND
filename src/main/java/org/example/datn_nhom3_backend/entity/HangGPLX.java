package org.example.datn_nhom3_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hang_gplx")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HangGPLX {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ma_hang", unique = true, length = 30)
    private String maHang;

    @Column(name = "ten_hang")
    private String tenHang;

    @Column(name = "mo_ta")
    private String moTa;
}