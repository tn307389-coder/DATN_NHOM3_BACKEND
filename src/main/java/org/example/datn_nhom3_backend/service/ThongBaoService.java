package org.example.datn_nhom3_backend.service;
import org.example.datn_nhom3_backend.entity.ThongBao;
import java.util.List;
import java.util.Optional;
public interface ThongBaoService {
    List<ThongBao> getAll();
    Optional<ThongBao> getById(Integer id);
    ThongBao save(ThongBao data);
    void delete(Integer id);
}