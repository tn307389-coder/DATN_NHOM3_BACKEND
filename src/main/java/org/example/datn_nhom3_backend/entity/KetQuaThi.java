package org.example.datn_nhom3_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ket_qua_thi")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KetQuaThi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer makq;

    @ManyToOne
    @JoinColumn(name = "mathi")
    private ThiSatHach thiSatHach;

    @Column(name = "diem")
    private Double diem;

    @Column(name = "ketqua")
    private String ketqua;

    @Column(name = "ghichu")
    private String ghichu;
}
