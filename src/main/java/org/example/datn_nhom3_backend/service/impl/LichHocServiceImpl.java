package org.example.datn_nhom3_backend.service.impl;
import org.example.datn_nhom3_backend.entity.LichHoc;
import org.example.datn_nhom3_backend.repository.LichHocRepository;
import org.example.datn_nhom3_backend.service.LichHocService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class LichHocServiceImpl implements LichHocService {
    private final LichHocRepository repository;
    public LichHocServiceImpl(LichHocRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<LichHoc> getAll() {
        return repository.findAll();
    }
    @Override
    public Optional<LichHoc> getById(Integer id) {
        return repository.findById(id);
    }
    @Override
    public LichHoc save(LichHoc data) {
        return repository.save(data);
    }
    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}