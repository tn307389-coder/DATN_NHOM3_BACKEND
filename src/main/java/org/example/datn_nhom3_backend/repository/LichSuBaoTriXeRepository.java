package org.example.datn_nhom3_backend.repository;

import org.example.datn_nhom3_backend.entity.LichSuBaoTriXe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface LichSuBaoTriXeRepository extends JpaRepository<LichSuBaoTriXe, Integer> {

    List<LichSuBaoTriXe> findByXeMaxeOrderByNgayBaoTriDesc(Integer maxe);

    List<LichSuBaoTriXe> findByTrangThaiOrderByNgayBaoTriDesc(String trangThai);

    List<LichSuBaoTriXe> findByNgayBaoTriBetweenOrderByNgayBaoTriDesc(
            LocalDate tuNgay, LocalDate denNgay);

    @Query("SELECT ls FROM LichSuBaoTriXe ls " +
           "WHERE ls.xe.maxe = :maxe " +
           "AND (:loaiBaoTri IS NULL OR ls.loaiBaoTri = :loaiBaoTri) " +
           "ORDER BY ls.ngayBaoTri DESC")
    List<LichSuBaoTriXe> findByXeAndLoaiBaoTri(@Param("maxe") Integer maxe,
                                                @Param("loaiBaoTri") String loaiBaoTri);
}
