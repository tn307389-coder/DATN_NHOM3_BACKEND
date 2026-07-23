package org.example.datn_nhom3_backend.service;
import org.example.datn_nhom3_backend.entity.CaThi;
import java.util.List;
import java.util.Optional;
public interface CaThiService {
    List<CaThi> getAll();
    Optional<CaThi> getById(Integer id);
    CaThi save(CaThi data);
    void delete(Integer id);
}