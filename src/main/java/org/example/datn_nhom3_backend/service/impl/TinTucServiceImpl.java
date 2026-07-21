package org.example.datn_nhom3_backend.service.impl;

import org.example.datn_nhom3_backend.entity.TinTuc;
import org.example.datn_nhom3_backend.repository.TinTucRepository;
import org.example.datn_nhom3_backend.service.TinTucService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TinTucServiceImpl implements TinTucService {

    private final TinTucRepository repository;

    public TinTucServiceImpl(TinTucRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TinTuc> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<TinTuc> getById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public TinTuc save(TinTuc data) {
        return repository.save(data);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
