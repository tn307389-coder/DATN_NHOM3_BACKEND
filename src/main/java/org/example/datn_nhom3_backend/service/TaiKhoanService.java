package org.example.datn_nhom3_backend.service;

import org.example.datn_nhom3_backend.dto.TaiKhoanRequest;
import org.example.datn_nhom3_backend.entity.TaiKhoan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface TaiKhoanService {

    List<TaiKhoan> getAll();

    Page<TaiKhoan> getAll(Specification<TaiKhoan> spec, Pageable pageable);

    Optional<TaiKhoan> getById(Integer id);

    TaiKhoan save(TaiKhoan taiKhoan);

    TaiKhoan createAccount(TaiKhoanRequest request);

    TaiKhoan updateAccount(Integer id, TaiKhoanRequest request);

    void delete(Integer id);

    Optional<TaiKhoan> login(String tendangnhap, String matkhau);

    Optional<TaiKhoan> findByTendangnhap(String tendangnhap);

    TaiKhoan updateCurrentUser(String tendangnhap, TaiKhoanRequest request);
}