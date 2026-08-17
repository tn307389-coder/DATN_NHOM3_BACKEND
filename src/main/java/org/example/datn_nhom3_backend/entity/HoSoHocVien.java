package org.example.datn_nhom3_backend.entity;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;
@Entity
@Table(name = "ho_so_hoc_vien")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HoSoHocVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer mahs;
    @ManyToOne
    @JoinColumn(name = "mahv")
    private HocVien hocVien;
    @Column(name = "ngaydangky")
    private LocalDate ngaydangky;
    @Column(name = "trang_thai_duyet")
    private String tinhtrang;
    @Column(name = "ghichu")
    private String ghichu;
    @Column(name = "anh_canh_cuoc")
    private String anhCanhCuoc;
    @Column(name = "file_ho_so")
    private String fileHoSo;

    @Column(name = "da_chinh_sua")
    private Boolean daChinhSua;
}
