package org.example.datn_nhom3_backend.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
@Entity
@Table(name = "hoc_vien", indexes = {
    @Index(name = "idx_hoc_vien_cccd", columnList = "cccd"),
    @Index(name = "idx_hoc_vien_hoten", columnList = "hoten"),
    @Index(name = "idx_hoc_vien_sodienthoai", columnList = "sodienthoai")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HocVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mahv;

    @Column(columnDefinition = "NVARCHAR(100)")
    private String hoten;

    private LocalDate ngaysinh;

    @Column(columnDefinition = "NVARCHAR(10)")
    private String gioitinh;

    @Column(columnDefinition = "NVARCHAR(20)")
    private String cccd;

    @Column(columnDefinition = "NVARCHAR(15)")
    private String sodienthoai;

    @Column(columnDefinition = "NVARCHAR(100)")
    private String email;

    @Column(columnDefinition = "NVARCHAR(255)")
    private String diachi;

    private LocalDate ngaydangky;
}