package org.example.datn_nhom3_backend.service;
import org.example.datn_nhom3_backend.entity.DangKyKhoaHoc;
import java.util.List;
import java.util.Optional;
public interface DangKyKhoaHocService {
    List<DangKyKhoaHoc> getAll();
    Optional<DangKyKhoaHoc> getById(Integer id);
    DangKyKhoaHoc save(DangKyKhoaHoc data);
    void delete(Integer id);
}