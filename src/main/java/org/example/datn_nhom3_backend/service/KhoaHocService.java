package org.example.datn_nhom3_backend.service;
import org.example.datn_nhom3_backend.entity.KhoaHoc;
import java.util.List;
import java.util.Optional;
public interface KhoaHocService {
    List<KhoaHoc> getAll();
    Optional<KhoaHoc> getById(Integer id);
    KhoaHoc save(KhoaHoc data);
    void delete(Integer id);
}