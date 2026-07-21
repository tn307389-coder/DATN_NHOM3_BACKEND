package org.example.datn_nhom3_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "xe")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Xe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maxe;

    private String bienso;

    private String loaixe;

    private String hangxe;

    private Integer namsanxuat;

    private String trangthai;
}