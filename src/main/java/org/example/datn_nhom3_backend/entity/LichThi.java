package org.example.datn_nhom3_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "lich_thi")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LichThi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer malichthi;

    @Column(name = "ngaythi")
    private LocalDate ngaythi;

    @ManyToOne
    @JoinColumn(name = "macathi")
    private CaThi caThi;

    @ManyToOne
    @JoinColumn(name = "maphongthi")
    private PhongThi phongThi;

    @Column(name = "ghichu")
    private String ghichu;
}