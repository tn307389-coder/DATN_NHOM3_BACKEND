package org.example.datn_nhom3_backend.service;

import org.example.datn_nhom3_backend.entity.TraGPLX;

import java.util.List;
import java.util.Optional;

public interface TraGPLXService {

    List<TraGPLX> getAll();

    Optional<TraGPLX> getById(Integer id);

    TraGPLX save(TraGPLX traGPLX);

    void delete(Integer id);

}