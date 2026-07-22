package org.example.datn_nhom3_backend.repository;
import org.example.datn_nhom3_backend.entity.DangKyKhoaHoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface DangKyKhoaHocRepository extends JpaRepository<DangKyKhoaHoc, Integer> {
    long countByKhoaHoc_Makh(Integer makh);
}