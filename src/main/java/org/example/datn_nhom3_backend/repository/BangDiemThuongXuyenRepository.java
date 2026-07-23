package org.example.datn_nhom3_backend.repository;
import org.example.datn_nhom3_backend.entity.BangDiemThuongXuyen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface BangDiemThuongXuyenRepository extends JpaRepository<BangDiemThuongXuyen, Integer> {
}