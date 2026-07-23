package org.example.datn_nhom3_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "ca_thi")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CaThi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer macathi;

    @Column(name = "ten_ca")
    private String tencathi;

    @Column(name = "gio_bat_dau")
    private LocalTime giobatdau;

    @Column(name = "gio_ket_thuc")
    private LocalTime gioketthuc;
}