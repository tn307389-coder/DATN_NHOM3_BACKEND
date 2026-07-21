package org.example.datn_nhom3_backend.service;
import org.example.datn_nhom3_backend.entity.CaHoc;
import java.util.List;
import java.util.Optional;
public interface CaHocService {
    List<CaHoc> getAll();
    Optional<CaHoc> getById(Integer id);
    CaHoc save(CaHoc data);
    void delete(Integer id);
}