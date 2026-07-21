package org.example.datn_nhom3_backend.repository;
import org.example.datn_nhom3_backend.entity.HocVien;
import org.example.datn_nhom3_backend.entity.PhanCong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface PhanCongRepository extends JpaRepository<PhanCong, Integer> {

    @Query("SELECT DISTINCT pc.hocVien FROM PhanCong pc WHERE pc.giaoVien.magv = :magv")
    List<HocVien> findHocVienByGiaoVien(@Param("magv") Integer magv);
}