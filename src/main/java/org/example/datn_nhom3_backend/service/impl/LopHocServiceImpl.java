package org.example.datn_nhom3_backend.service.impl;
import org.example.datn_nhom3_backend.entity.LopHoc;
import org.example.datn_nhom3_backend.repository.LopHocRepository;
import org.example.datn_nhom3_backend.service.LopHocService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class LopHocServiceImpl implements LopHocService {
    private final LopHocRepository repository;
    public LopHocServiceImpl(LopHocRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<LopHoc> getAll() {
        return repository.findAll();
    }
    @Override
    public Optional<LopHoc> getById(Integer id) {
        return repository.findById(id);
    }
    @Override
    public LopHoc save(LopHoc data) {
        return repository.save(data);
    }
    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}