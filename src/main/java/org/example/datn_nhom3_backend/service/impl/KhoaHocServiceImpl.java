package org.example.datn_nhom3_backend.service.impl;
import org.example.datn_nhom3_backend.entity.KhoaHoc;
import org.example.datn_nhom3_backend.repository.KhoaHocRepository;
import org.example.datn_nhom3_backend.service.KhoaHocService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class KhoaHocServiceImpl implements KhoaHocService {
    private final KhoaHocRepository repository;
    public KhoaHocServiceImpl(KhoaHocRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<KhoaHoc> getAll() {
        return repository.findAll();
    }
    @Override
    public Optional<KhoaHoc> getById(Integer id) {
        return repository.findById(id);
    }
    @Override
    public KhoaHoc save(KhoaHoc data) {
        return repository.save(data);
    }
    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}