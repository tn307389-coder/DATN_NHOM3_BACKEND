package org.example.datn_nhom3_backend.service;
import org.example.datn_nhom3_backend.entity.MonHoc;
import java.util.List;
import java.util.Optional;
public interface MonHocService {
    List<MonHoc> getAll();
    Optional<MonHoc> getById(Integer id);
    MonHoc save(MonHoc data);
    void delete(Integer id);
}