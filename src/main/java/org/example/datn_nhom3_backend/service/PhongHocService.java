package org.example.datn_nhom3_backend.service;
import org.example.datn_nhom3_backend.entity.PhongHoc;
import java.util.List;
import java.util.Optional;
public interface PhongHocService {
    List<PhongHoc> getAll();
    Optional<PhongHoc> getById(Integer id);
    PhongHoc save(PhongHoc data);
    void delete(Integer id);
}