package org.example.datn_nhom3_backend.service;
import org.example.datn_nhom3_backend.entity.KetQuaThi;
import java.util.List;
import java.util.Optional;
public interface KetQuaThiService {
    List<KetQuaThi> getAll();
    Optional<KetQuaThi> getById(Integer id);
    KetQuaThi save(KetQuaThi data);
    void delete(Integer id);
}