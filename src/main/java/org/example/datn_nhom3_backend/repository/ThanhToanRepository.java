package org.example.datn_nhom3_backend.repository;
import org.example.datn_nhom3_backend.entity.ThanhToan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ThanhToanRepository extends JpaRepository<ThanhToan, Integer> {
    List<ThanhToan> findByHocVien_Mahv(Integer mahv);

    long countByHocVien_Mahv(Integer mahv);

    @Modifying
    @Query("DELETE FROM ThanhToan t WHERE t.hocVien.mahv = :mahv")
    void deleteByHocVien_Mahv(@Param("mahv") Integer mahv);

    @Modifying
    @Query("DELETE FROM ThanhToan t WHERE t.dangKyKhoaHoc.madk = :madk")
    void deleteByDangKyKhoaHoc_Madk(@Param("madk") Integer madk);

    long countByDangKyKhoaHoc_Madk(Integer madk);

    Long countByNgaythanhtoanBetween(LocalDate tu, LocalDate den);

    @Query("SELECT COALESCE(SUM(t.sotien), 0) FROM ThanhToan t")
    Double sumAllSotien();

    @Query("SELECT COALESCE(SUM(t.sotien), 0) FROM ThanhToan t WHERE t.ngaythanhtoan BETWEEN ?1 AND ?2")
    Double sumSotienByNgaythanhtoanBetween(LocalDate tu, LocalDate den);
}