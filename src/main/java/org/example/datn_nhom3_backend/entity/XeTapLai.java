package org.example.datn_nhom3_backend.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "xe_tap_lai")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class XeTapLai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maxetl;

    @ManyToOne
    @JoinColumn(name = "maxe")
    private Xe xe;

    private String hangbang;

    private LocalDate ngaydangkiem;

    private LocalDate handangkiem;

    private String ghichu;

    @Column(name = "ngay_tao")
    private LocalDateTime ngaytao = LocalDateTime.now();

    @Column(name = "ngay_cap_nhat")
    private LocalDateTime ngaycapnhat;
}