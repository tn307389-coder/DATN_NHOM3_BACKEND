package org.example.datn_nhom3_backend.repository;

import org.example.datn_nhom3_backend.entity.HoSoHocVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HoSoHocVienRepository extends JpaRepository<HoSoHocVien, Integer> {

    Optional<HoSoHocVien> findByHocVien_Mahv(Integer mahv);

    long countByHocVien_Mahv(Integer mahv);

    @Modifying
    @Query("DELETE FROM HoSoHocVien h WHERE h.hocVien.mahv = :mahv")
    void deleteByHocVien_Mahv(@Param("mahv") Integer mahv);
}
