package org.example.datn_nhom3_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "dang_ky_khoa_hoc")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DangKyKhoaHoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer madk;

    @ManyToOne
    @JoinColumn(name = "mahv", nullable = false)
    private HocVien hocVien;

    @ManyToOne
    @JoinColumn(name = "makh", nullable = false)
    private KhoaHoc khoaHoc;

    @ManyToOne
    @JoinColumn(name = "hang_gplx_id")
    private HangGPLX hangGPLX;

    private LocalDate ngaydangky;

    private String trangthai;
}