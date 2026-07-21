package org.example.datn_nhom3_backend.repository;
import org.example.datn_nhom3_backend.entity.ThanhToan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;

@Repository
public interface ThanhToanRepository extends JpaRepository<ThanhToan, Integer> {
    Long countByNgaythanhtoanBetween(LocalDate tu, LocalDate den);
}