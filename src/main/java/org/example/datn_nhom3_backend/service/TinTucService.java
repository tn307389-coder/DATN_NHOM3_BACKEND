package org.example.datn_nhom3_backend.service;

import org.example.datn_nhom3_backend.entity.TinTuc;

import java.util.List;
import java.util.Optional;

public interface TinTucService {
    List<TinTuc> getAll();
    Optional<TinTuc> getById(Integer id);
    TinTuc save(TinTuc data);
    void delete(Integer id);
}
