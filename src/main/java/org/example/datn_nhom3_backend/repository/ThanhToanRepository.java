package org.example.datn_nhom3_backend.repository;
import org.example.datn_nhom3_backend.entity.ThanhToan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;

@Repository
public interface ThanhToanRepository extends JpaRepository<ThanhToan, Integer> {
    Long countByNgaythanhtoanBetween(LocalDate tu, LocalDate den);

    @Query("SELECT COALESCE(SUM(t.sotien), 0) FROM ThanhToan t")
    Double sumAllSotien();

    @Query("SELECT COALESCE(SUM(t.sotien), 0) FROM ThanhToan t WHERE t.ngaythanhtoan BETWEEN ?1 AND ?2")
    Double sumSotienByNgaythanhtoanBetween(LocalDate tu, LocalDate den);
}