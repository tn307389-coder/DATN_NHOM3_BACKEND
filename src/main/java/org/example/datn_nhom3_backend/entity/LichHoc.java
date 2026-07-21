package org.example.datn_nhom3_backend.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
@Entity
@Table(name = "lich_hoc")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LichHoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer malich;
    @ManyToOne
    @JoinColumn(name = "malop")
    private LopHoc lopHoc;
    @ManyToOne
    @JoinColumn(name = "mamh")
    private MonHoc monHoc;
    @ManyToOne
    @JoinColumn(name = "magv")
    private GiaoVien giaoVien;
    @ManyToOne
    @JoinColumn(name = "maphong")
    private PhongHoc phongHoc;
    @ManyToOne
    @JoinColumn(name = "macahoc")
    private CaHoc caHoc;
    private LocalDate ngayhoc;
    private String ghichu;
}
