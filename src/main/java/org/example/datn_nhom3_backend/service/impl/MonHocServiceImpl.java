package org.example.datn_nhom3_backend.service.impl;
import org.example.datn_nhom3_backend.entity.MonHoc;
import org.example.datn_nhom3_backend.repository.MonHocRepository;
import org.example.datn_nhom3_backend.service.MonHocService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class MonHocServiceImpl implements MonHocService {
    private final MonHocRepository repository;
    public MonHocServiceImpl(MonHocRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<MonHoc> getAll() {
        return repository.findAll();
    }
    @Override
    public Optional<MonHoc> getById(Integer id) {
        return repository.findById(id);
    }
    @Override
    public MonHoc save(MonHoc data) {
        return repository.save(data);
    }
    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}