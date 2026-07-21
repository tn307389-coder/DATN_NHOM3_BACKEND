package org.example.datn_nhom3_backend.service;
import org.example.datn_nhom3_backend.entity.LopHoc;
import java.util.List;
import java.util.Optional;
public interface LopHocService {
    List<LopHoc> getAll();
    Optional<LopHoc> getById(Integer id);
    LopHoc save(LopHoc data);
    void delete(Integer id);
}