package org.example.datn_nhom3_backend.service;
import org.example.datn_nhom3_backend.entity.ThanhToan;
import java.util.List;
import java.util.Optional;
public interface ThanhToanService {
    List<ThanhToan> getAll();
    Optional<ThanhToan> getById(Integer id);
    ThanhToan save(ThanhToan data);
    void delete(Integer id);
}