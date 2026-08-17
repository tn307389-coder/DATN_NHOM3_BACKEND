package org.example.datn_nhom3_backend.repository;
import org.example.datn_nhom3_backend.entity.DiemDanh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
@Repository
public interface DiemDanhRepository extends JpaRepository<DiemDanh, Integer> {
    List<DiemDanh> findByLichHoc_MalichAndNgaydiemdanh(Integer malich, LocalDate ngaydiemdanh);
    void deleteByLichHoc_MalichAndNgaydiemdanh(Integer malich, LocalDate ngaydiemdanh);

    long countByHocVien_Mahv(Integer mahv);

    @Modifying
    @Query("DELETE FROM DiemDanh d WHERE d.hocVien.mahv = :mahv")
    void deleteByHocVien_Mahv(@Param("mahv") Integer mahv);
}