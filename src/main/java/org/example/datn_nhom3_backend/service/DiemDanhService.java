package org.example.datn_nhom3_backend.service;
import org.example.datn_nhom3_backend.entity.DiemDanh;
import java.util.List;
import java.util.Optional;
public interface DiemDanhService {
    List<DiemDanh> getAll();
    Optional<DiemDanh> getById(Integer id);
    DiemDanh save(DiemDanh data);
    void delete(Integer id);
}