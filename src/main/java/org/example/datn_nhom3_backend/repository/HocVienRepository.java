package org.example.datn_nhom3_backend.repository;
import org.example.datn_nhom3_backend.entity.HocVien;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;
import org.springframework.stereotype.Repository;
@Repository
public interface HocVienRepository extends JpaRepository<HocVien, Integer> {
    Optional<HocVien> findByCccd(String cccd);
    List<HocVien> findByEmail(String email);
}