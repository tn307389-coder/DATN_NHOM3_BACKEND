package org.example.datn_nhom3_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "khoa_hoc")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KhoaHoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer makh;

    private String tenkhoahoc;

    @ManyToOne
    @JoinColumn(name = "macth")
    private ChuongTrinhHoc chuongTrinhHoc;

    private LocalDate ngaybatdau;

    private LocalDate ngayketthuc;

    private String trangthai;
}