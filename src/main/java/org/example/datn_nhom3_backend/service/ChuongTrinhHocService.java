package org.example.datn_nhom3_backend.service;
import org.example.datn_nhom3_backend.entity.ChuongTrinhHoc;
import java.util.List;
import java.util.Optional;
public interface ChuongTrinhHocService {
    List<ChuongTrinhHoc> getAll();
    Optional<ChuongTrinhHoc> getById(Integer id);
    ChuongTrinhHoc save(ChuongTrinhHoc data);
    void delete(Integer id);
}