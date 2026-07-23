package org.example.datn_nhom3_backend.service;
import org.example.datn_nhom3_backend.entity.PhongThi;
import java.util.List;
import java.util.Optional;
public interface PhongThiService {
    List<PhongThi> getAll();
    Optional<PhongThi> getById(Integer id);
    PhongThi save(PhongThi data);
    void delete(Integer id);
}