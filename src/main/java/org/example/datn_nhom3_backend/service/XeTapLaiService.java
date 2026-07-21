package org.example.datn_nhom3_backend.service;
import org.example.datn_nhom3_backend.entity.XeTapLai;
import java.util.List;
import java.util.Optional;
public interface XeTapLaiService {
    List<XeTapLai> getAll();
    Optional<XeTapLai> getById(Integer id);
    XeTapLai save(XeTapLai data);
    void delete(Integer id);
}