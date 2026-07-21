package org.example.datn_nhom3_backend.repository;

import org.example.datn_nhom3_backend.entity.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaiKhoanRepository extends JpaRepository<TaiKhoan,Integer>, JpaSpecificationExecutor<TaiKhoan> {

    Optional<TaiKhoan> findByTendangnhap(String tendangnhap);

}