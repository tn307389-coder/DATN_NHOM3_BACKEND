package org.example.datn_nhom3_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "lich_su_bao_tri_xe")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LichSuBaoTriXe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maxe", nullable = false)
    private Xe xe;

    @Column(name = "loai_bao_tri", nullable = false, length = 50)
    private String loaiBaoTri;

    @Column(name = "mo_ta", columnDefinition = "NVARCHAR(MAX)")
    private String moTa;

    @Column(name = "ngay_bao_tri", nullable = false)
    private LocalDate ngayBaoTri;

    @Column(name = "so_km_hien_tai")
    private Integer soKmHienTai;

    @Column(name = "chi_phi")
    private Double chiPhi;

    @Column(name = "don_vi_bao_tri", length = 200)
    private String donViBaoTri;

    @Column(name = "trang_thai", length = 30)
    private String trangThai;

    @Column(name = "ngay_bao_tri_tiep_theo")
    private LocalDate ngayBaoTriTiepTheo;

    @Column(name = "nguoi_tao", length = 100)
    private String nguoiTao;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao = LocalDateTime.now();
}
