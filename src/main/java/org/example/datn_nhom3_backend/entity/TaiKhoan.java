package org.example.datn_nhom3_backend.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Table(name = "tai_khoan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaiKhoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer matk;

    @Column(name = "ten_dang_nhap", unique = true, nullable = false, length = 50, columnDefinition = "NVARCHAR(50)")
    private String tendangnhap;

    @Column(name = "mat_khau", nullable = false, length = 100)
    private String matkhau;

    @Column(name = "ho_ten", nullable = false, length = 120, columnDefinition = "NVARCHAR(120)")
    private String hoten;

    @Column(unique = true, length = 150, columnDefinition = "NVARCHAR(150)")
    private String email;

    @Column(name = "so_dien_thoai", length = 15, columnDefinition = "NVARCHAR(15)")
    private String soDienThoai;

    @Column(name = "anh", length = 255)
    private String anh;

    @Column(name = "cccd", length = 20, columnDefinition = "NVARCHAR(20)")
    private String cccd;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vai_tro_id", nullable = false)
    private VaiTro vaitro;

    @Column(name = "trang_thai", nullable = false, length = 20)
    private String trangthai = "ACTIVE";

    @Column(name = "so_lan_dang_nhap_sai", nullable = false)
    private Integer soLanDangNhapSai = 0;

    @Column(name = "khoa_den")
    private LocalDateTime khoaDen;

    @Column(name = "lan_dang_nhap_cuoi")
    private LocalDateTime lanDangNhapCuoi;

    @Column(name = "ngay_tao", nullable = false)
    private LocalDateTime ngaytao = LocalDateTime.now();

    @Column(name = "ngay_cap_nhat")
    private LocalDateTime ngayCapNhat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nguoi_tao_id")
    private TaiKhoan nguoiTao;
}
