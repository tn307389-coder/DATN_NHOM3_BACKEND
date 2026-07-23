package org.example.datn_nhom3_backend.service;

import org.example.datn_nhom3_backend.entity.LichSuBaoTriXe;
import java.util.List;
import java.util.Optional;

public interface LichSuBaoTriXeService {
    List<LichSuBaoTriXe> getAll();
    Optional<LichSuBaoTriXe> getById(Integer id);
    List<LichSuBaoTriXe> getByXe(Integer maxe);
    LichSuBaoTriXe save(LichSuBaoTriXe data);
    void delete(Integer id);
}
