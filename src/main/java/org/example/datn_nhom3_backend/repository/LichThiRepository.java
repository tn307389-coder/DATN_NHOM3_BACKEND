package org.example.datn_nhom3_backend.repository;
import org.example.datn_nhom3_backend.entity.LichThi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface LichThiRepository extends JpaRepository<LichThi, Integer> {

    @Query(value = "SELECT lt.* FROM lich_thi lt " +
           "JOIN thi_sat_hach tsh ON lt.malichthi = tsh.malichthi " +
           "WHERE tsh.mahv = :mahv " +
           "AND (:tuNgay IS NULL OR lt.ngaythi >= :tuNgay) " +
           "AND (:denNgay IS NULL OR lt.ngaythi <= :denNgay) " +
           "ORDER BY lt.ngaythi ASC", nativeQuery = true)
    List<LichThi> findByHocVien(@Param("mahv") Integer mahv,
                                @Param("tuNgay") LocalDate tuNgay,
                                @Param("denNgay") LocalDate denNgay);

    @Query(value = "SELECT lt.* FROM lich_thi lt " +
           "JOIN thi_sat_hach tsh ON lt.malichthi = tsh.malichthi " +
           "WHERE tsh.mahv = :mahv " +
           "AND lt.ngaythi >= :ngayHienTai " +
           "ORDER BY lt.ngaythi ASC", nativeQuery = true)
    List<LichThi> findUpcomingByHocVien(@Param("mahv") Integer mahv,
                                        @Param("ngayHienTai") LocalDate ngayHienTai);
}