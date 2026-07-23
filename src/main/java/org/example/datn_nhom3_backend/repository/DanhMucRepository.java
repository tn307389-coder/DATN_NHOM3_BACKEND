package org.example.datn_nhom3_backend.repository;

import org.example.datn_nhom3_backend.entity.DanhMuc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DanhMucRepository extends JpaRepository<DanhMuc, Integer> {
    List<DanhMuc> findByNhomOrderByThuTu(String nhom);
}
