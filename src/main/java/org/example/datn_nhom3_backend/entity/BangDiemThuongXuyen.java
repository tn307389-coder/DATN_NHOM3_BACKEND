package org.example.datn_nhom3_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "bang_diem_thuong_xuyen")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BangDiemThuongXuyen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer mabd;

    @ManyToOne
    @JoinColumn(name = "mahv")
    private HocVien hocVien;

    @ManyToOne
    @JoinColumn(name = "mamh")
    private MonHoc monHoc;

    @Column(name = "diem")
    private Double diem;

    @Column(name = "ghichu")
    private String ghichu;

    @Column(name = "malop")
    private Integer malop;

    @Column(name = "ngay_cham")
    private LocalDate ngayCham;
}
