package org.example.datn_nhom3_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Table(name = "anh_cho_duyet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnhChoDuyet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "matk")
    private TaiKhoan taiKhoan;

    @Column(name = "url")
    private String url;

    @Column(name = "trangthai")
    private String trangthai;

    @Column(name = "ghichu")
    private String ghichu;

    @Column(name = "ngaytao")
    private LocalDateTime ngaytao;
}
