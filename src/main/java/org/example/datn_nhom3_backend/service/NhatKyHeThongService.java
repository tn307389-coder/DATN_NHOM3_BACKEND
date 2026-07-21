package org.example.datn_nhom3_backend.service;
import org.example.datn_nhom3_backend.entity.NhatKyHeThong;
import java.util.List;
import java.util.Optional;
public interface NhatKyHeThongService {
    List<NhatKyHeThong> getAll();
    Optional<NhatKyHeThong> getById(Integer id);
    NhatKyHeThong save(NhatKyHeThong data);
    void delete(Integer id);
}