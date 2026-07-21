package org.example.datn_nhom3_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "phong_thi")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhongThi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maphongthi;

    @Column(name = "tenphong")
    private String tenphong;

    @Column(name = "succhua")
    private Integer succhua;

    @Column(name = "trangthai")
    private String trangthai;

    @Column(name = "diadiem")
    private String diadiem;
}