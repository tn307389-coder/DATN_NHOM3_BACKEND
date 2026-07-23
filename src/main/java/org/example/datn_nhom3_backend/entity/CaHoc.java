package org.example.datn_nhom3_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "ca_hoc")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CaHoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer macahoc;

    private String tencahoc;

    private LocalTime giobatdau;

    private LocalTime gioketthuc;
}