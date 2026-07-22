package org.example.datn_nhom3_backend.repository;

import org.example.datn_nhom3_backend.entity.HocVien;
import org.example.datn_nhom3_backend.entity.LopHoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LopHocRepository extends JpaRepository<LopHoc, Integer> {

    Long countByGiaoVienMagv(Integer magv);

    @Query("SELECT lh FROM LopHoc lh WHERE lh.giaoVien.magv = :magv")
    List<LopHoc> findByGiaoVienMagv(Integer magv);

    @Query(value = "SELECT DISTINCT hv.mahv, hv.hoten, hv.ngaysinh, hv.gioitinh, hv.cccd, hv.sodienthoai, hv.email, hv.diachi, hv.ngaydangky FROM hoc_vien hv " +
           "JOIN dang_ky_khoa_hoc dk ON hv.mahv = dk.mahv " +
           "JOIN khoa_hoc kh ON dk.makh = kh.makh " +
           "JOIN lop_hoc lh ON kh.makh = lh.makh " +
           "WHERE lh.magv = :magv", nativeQuery = true)
    List<Object[]> findRawHocVienByGiaoVien(@Param("magv") Integer magv);
}