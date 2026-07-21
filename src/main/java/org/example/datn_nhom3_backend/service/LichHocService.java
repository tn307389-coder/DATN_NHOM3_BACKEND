package org.example.datn_nhom3_backend.service;
import org.example.datn_nhom3_backend.entity.LichHoc;
import java.util.List;
import java.util.Optional;
public interface LichHocService {
    List<LichHoc> getAll();
    Optional<LichHoc> getById(Integer id);
    LichHoc save(LichHoc data);
    void delete(Integer id);
}