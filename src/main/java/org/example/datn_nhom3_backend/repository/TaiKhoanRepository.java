package org.example.datn_nhom3_backend.repository;

import org.example.datn_nhom3_backend.entity.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaiKhoanRepository extends JpaRepository<TaiKhoan,Integer>, JpaSpecificationExecutor<TaiKhoan> {

    @Query("SELECT DISTINCT t FROM TaiKhoan t LEFT JOIN FETCH t.vaitro")
    List<TaiKhoan> findAllWithVaiTro();

    @Query("SELECT t FROM TaiKhoan t LEFT JOIN FETCH t.vaitro WHERE t.tendangnhap = :tendangnhap")
    Optional<TaiKhoan> findByTendangnhap(String tendangnhap);

    @Query("SELECT t FROM TaiKhoan t LEFT JOIN FETCH t.vaitro WHERE t.email = :email")
    Optional<TaiKhoan> findByEmail(String email);

    @Query("SELECT t FROM TaiKhoan t LEFT JOIN FETCH t.vaitro WHERE t.googleId = :googleId")
    Optional<TaiKhoan> findByGoogleId(String googleId);

    @Query("SELECT t FROM TaiKhoan t LEFT JOIN FETCH t.vaitro WHERE t.cccd = :cccd")
    Optional<TaiKhoan> findByCccd(String cccd);

}