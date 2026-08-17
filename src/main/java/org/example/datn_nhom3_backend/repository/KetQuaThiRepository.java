package org.example.datn_nhom3_backend.repository;

import org.example.datn_nhom3_backend.entity.KetQuaThi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KetQuaThiRepository extends JpaRepository<KetQuaThi, Integer> {

    long countByHocVien_Mahv(Integer mahv);

    @Modifying
    @Query("DELETE FROM KetQuaThi k WHERE k.hocVien.mahv = :mahv")
    void deleteByHocVien_Mahv(@Param("mahv") Integer mahv);
}
