package org.example.datn_nhom3_backend.repository;
import org.example.datn_nhom3_backend.entity.KhoaHoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface KhoaHocRepository extends JpaRepository<KhoaHoc, Integer> {
    Long countByTrangthai(String trangthai);
}