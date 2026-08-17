package org.example.datn_nhom3_backend.service;
import org.example.datn_nhom3_backend.entity.HocVien;
import java.util.List;
import java.util.Map;
import java.util.Optional;
public interface HocVienService {
    List<HocVien> getAll();
    Optional<HocVien> getById(Integer id);
    Optional<HocVien> findByCccd(String cccd);
    HocVien save(HocVien data);
    void delete(Integer id);
    Map<String, Long> demDuLieuLienQuan(Integer id);
}