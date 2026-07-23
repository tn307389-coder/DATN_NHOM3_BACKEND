package org.example.datn_nhom3_backend.service;
import org.example.datn_nhom3_backend.entity.HoSoHocVien;
import java.util.List;
import java.util.Optional;
public interface HoSoHocVienService {
    List<HoSoHocVien> getAll();
    Optional<HoSoHocVien> getById(Integer id);
    HoSoHocVien save(HoSoHocVien data);
    void delete(Integer id);
}