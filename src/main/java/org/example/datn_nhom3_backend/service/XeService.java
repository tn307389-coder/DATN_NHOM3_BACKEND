package org.example.datn_nhom3_backend.service;
import org.example.datn_nhom3_backend.entity.Xe;
import java.util.List;
import java.util.Optional;
public interface XeService {
    List<Xe> getAll();
    Optional<Xe> getById(Integer id);
    Xe save(Xe data);
    void delete(Integer id);
}