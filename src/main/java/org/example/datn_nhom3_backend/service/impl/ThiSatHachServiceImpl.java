package org.example.datn_nhom3_backend.service.impl;

import org.example.datn_nhom3_backend.entity.ThiSatHach;
import org.example.datn_nhom3_backend.repository.ThiSatHachRepository;
import org.example.datn_nhom3_backend.service.ThiSatHachService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThiSatHachServiceImpl implements ThiSatHachService {

    private final ThiSatHachRepository repository;

    public ThiSatHachServiceImpl(ThiSatHachRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ThiSatHach> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ThiSatHach> getById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public ThiSatHach save(ThiSatHach thiSatHach) {
        return repository.save(thiSatHach);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}