package org.example.datn_nhom3_backend.service;

import org.example.datn_nhom3_backend.entity.ThiSatHach;

import java.util.List;
import java.util.Optional;

public interface ThiSatHachService {

    List<ThiSatHach> getAll();

    Optional<ThiSatHach> getById(Integer id);

    ThiSatHach save(ThiSatHach thiSatHach);

    void delete(Integer id);

}