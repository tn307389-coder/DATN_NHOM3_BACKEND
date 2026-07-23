package org.example.datn_nhom3_backend.repository;
import org.example.datn_nhom3_backend.entity.GiaoVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface GiaoVienRepository extends JpaRepository<GiaoVien, Integer> {
    Optional<GiaoVien> findByCccd(String cccd);
}