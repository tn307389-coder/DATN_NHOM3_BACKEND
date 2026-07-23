package org.example.datn_nhom3_backend.service;
import org.example.datn_nhom3_backend.dto.ThongKeHocVienGiaoVien;
import org.example.datn_nhom3_backend.entity.GiaoVien;
import java.util.List;
import java.util.Optional;
public interface GiaoVienService {
    List<GiaoVien> getAll();
    Optional<GiaoVien> getById(Integer id);
    GiaoVien save(GiaoVien data);
    void delete(Integer id);
    ThongKeHocVienGiaoVien thongKeHocVien(Integer magv);
}