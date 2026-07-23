package org.example.datn_nhom3_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "nhat_ky_he_thong")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NhatKyHeThong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer mank;

    @ManyToOne
    @JoinColumn(name = "tai_khoan_id")
    private TaiKhoan taiKhoan;

    @Column(name = "hanh_dong")
    private String hanhdong;

    @Column(name = "chi_tiet")
    private String chiTiet;

    @Column(name = "ngay_thuc_hien")
    private LocalDateTime thoigian;

    @Column(name = "ip")
    private String ip;
}
