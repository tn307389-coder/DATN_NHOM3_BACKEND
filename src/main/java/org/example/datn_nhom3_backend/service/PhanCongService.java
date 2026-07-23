package org.example.datn_nhom3_backend.service;
import org.example.datn_nhom3_backend.entity.PhanCong;
import java.util.List;
import java.util.Optional;
public interface PhanCongService {
    List<PhanCong> getAll();
    Optional<PhanCong> getById(Integer id);
    PhanCong save(PhanCong data);
    void delete(Integer id);
}