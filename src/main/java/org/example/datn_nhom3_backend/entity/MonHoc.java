package org.example.datn_nhom3_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mon_hoc")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonHoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mamh;

    @Column(name = "tenmonhoc")
    private String tenmonhoc;

    @Column(name = "loai_mon_hoc")
    private String loaimonhoc;

    @Column(name = "so_tiet")
    private Integer sotiet;

    @Column(name = "ghi_chu")
    private String ghichu;
}