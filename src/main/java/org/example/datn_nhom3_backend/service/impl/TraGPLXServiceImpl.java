package org.example.datn_nhom3_backend.service.impl;

import org.example.datn_nhom3_backend.entity.TraGPLX;
import org.example.datn_nhom3_backend.repository.TraGPLXRepository;
import org.example.datn_nhom3_backend.service.TraGPLXService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TraGPLXServiceImpl implements TraGPLXService {

    private final TraGPLXRepository repository;

    public TraGPLXServiceImpl(TraGPLXRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TraGPLX> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<TraGPLX> getById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public TraGPLX save(TraGPLX traGPLX) {
        return repository.save(traGPLX);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}