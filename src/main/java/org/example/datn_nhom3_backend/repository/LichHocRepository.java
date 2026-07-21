package org.example.datn_nhom3_backend.repository;
import org.example.datn_nhom3_backend.entity.LichHoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface LichHocRepository extends JpaRepository<LichHoc, Integer> {

    @Query(value = "SELECT lh.* FROM lich_hoc lh " +
           "JOIN lop_hoc lop ON lh.malop = lop.malop " +
           "JOIN khoa_hoc kh ON lop.makh = kh.makh " +
           "JOIN dang_ky_khoa_hoc dk ON dk.makh = kh.makh AND dk.mahv = :mahv " +
           "WHERE (:tuNgay IS NULL OR lh.ngayhoc >= :tuNgay) " +
           "AND (:denNgay IS NULL OR lh.ngayhoc <= :denNgay) " +
           "ORDER BY lh.ngayhoc ASC", nativeQuery = true)
    List<LichHoc> findByHocVien(@Param("mahv") Integer mahv,
                                 @Param("tuNgay") LocalDate tuNgay,
                                 @Param("denNgay") LocalDate denNgay);

    @Query(value = "SELECT lh.* FROM lich_hoc lh " +
           "JOIN lop_hoc lop ON lh.malop = lop.malop " +
           "JOIN khoa_hoc kh ON lop.makh = kh.makh " +
           "JOIN dang_ky_khoa_hoc dk ON dk.makh = kh.makh AND dk.mahv = :mahv " +
           "WHERE lh.ngayhoc >= :ngayHienTai " +
           "ORDER BY lh.ngayhoc ASC", nativeQuery = true)
    List<LichHoc> findUpcomingByHocVien(@Param("mahv") Integer mahv,
                                         @Param("ngayHienTai") LocalDate ngayHienTai);

    @Query(value = "SELECT lh.* FROM lich_hoc lh " +
           "WHERE lh.magv = :magv " +
           "AND (:tuNgay IS NULL OR lh.ngayhoc >= :tuNgay) " +
           "AND (:denNgay IS NULL OR lh.ngayhoc <= :denNgay) " +
           "ORDER BY lh.ngayhoc ASC", nativeQuery = true)
    List<LichHoc> findByGiaoVien(@Param("magv") Integer magv,
                                  @Param("tuNgay") LocalDate tuNgay,
                                  @Param("denNgay") LocalDate denNgay);
}