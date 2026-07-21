package org.example.datn_nhom3_backend.service;
import org.example.datn_nhom3_backend.entity.HangGPLX;
import java.util.List;
import java.util.Optional;
public interface HangGPLXService {
    List<HangGPLX> getAll();
    Optional<HangGPLX> getById(Integer id);
    HangGPLX save(HangGPLX data);
    void delete(Integer id);
}