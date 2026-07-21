package org.example.datn_nhom3_backend.service;
import org.example.datn_nhom3_backend.entity.BangDiemThuongXuyen;
import java.util.List;
import java.util.Optional;
public interface BangDiemThuongXuyenService {
    List<BangDiemThuongXuyen> getAll();
    Optional<BangDiemThuongXuyen> getById(Integer id);
    BangDiemThuongXuyen save(BangDiemThuongXuyen data);
    void delete(Integer id);
}