package org.example.datn_nhom3_backend.service;
import org.example.datn_nhom3_backend.entity.LichThi;
import java.util.List;
import java.util.Optional;
public interface LichThiService {
    List<LichThi> getAll();
    Optional<LichThi> getById(Integer id);
    LichThi save(LichThi data);
    void delete(Integer id);
}