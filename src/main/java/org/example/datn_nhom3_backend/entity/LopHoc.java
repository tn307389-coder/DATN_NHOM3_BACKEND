package org.example.datn_nhom3_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lop_hoc")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LopHoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer malop;

    private String tenlop;

    @ManyToOne
    @JoinColumn(name = "makh")
    private KhoaHoc khoaHoc;

    @ManyToOne
    @JoinColumn(name = "magv")
    private GiaoVien giaoVien;

    private Integer soluong;

    private String trangthai;
}