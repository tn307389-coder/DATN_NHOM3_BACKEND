package org.example.datn_nhom3_backend.repository;
import org.example.datn_nhom3_backend.entity.CaHoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CaHocRepository extends JpaRepository<CaHoc, Integer> {
}