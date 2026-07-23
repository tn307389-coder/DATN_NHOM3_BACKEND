package org.example.datn_nhom3_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "diem_danh")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiemDanh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "madd")
    private Integer madd;

    @ManyToOne
    @JoinColumn(name = "mahv")
    private HocVien hocVien;

    @ManyToOne
    @JoinColumn(name = "malich")
    private LichHoc lichHoc;

    @Column(name = "ngaydiemdanh")
    private LocalDate ngaydiemdanh;

    @Column(name = "trangthai")
    private String trangthai;

    @Column(name = "ghichu")
    private String ghichu;
}
