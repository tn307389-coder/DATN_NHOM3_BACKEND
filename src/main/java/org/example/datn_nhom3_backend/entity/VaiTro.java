package org.example.datn_nhom3_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vai_tro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaiTro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ma_vai_tro", unique = true, nullable = false, length = 30, columnDefinition = "NVARCHAR(30)")
    private String maVaiTro;

    @Column(name = "ten_vai_tro", unique = true, nullable = false, length = 100, columnDefinition = "NVARCHAR(100)")
    private String tenVaiTro;

    @Column(name = "mo_ta", length = 255, columnDefinition = "NVARCHAR(255)")
    private String moTa;

    @Column(name = "trang_thai", nullable = false)
    @Builder.Default
    private Boolean trangThai = true;

    @Column(name = "ngay_tao", nullable = false)
    @Builder.Default
    private LocalDateTime ngayTao = LocalDateTime.now();

    @Column(name = "ngay_cap_nhat")
    private LocalDateTime ngayCapNhat;
}
