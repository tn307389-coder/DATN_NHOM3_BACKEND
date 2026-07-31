package org.example.datn_nhom3_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ket_qua_thi")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KetQuaThi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer makq;

    @ManyToOne
    @JoinColumn(name = "mahv")
    private HocVien hocVien;

    @ManyToOne
    @JoinColumn(name = "malichthi")
    private LichThi lichThi;

    @Column(name = "diem")
    private Double diem;

    @Column(name = "ketqua")
    private String ketqua;

    @Column(name = "ghichu")
    private String ghichu;
}
