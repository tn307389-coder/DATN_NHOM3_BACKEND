package org.example.datn_nhom3_backend.repository;
import org.example.datn_nhom3_backend.entity.HoSoHocVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface HoSoHocVienRepository extends JpaRepository<HoSoHocVien, Integer> {
}