package org.example.datn_nhom3_backend.service.impl;
import org.example.datn_nhom3_backend.entity.PhongHoc;
import org.example.datn_nhom3_backend.repository.PhongHocRepository;
import org.example.datn_nhom3_backend.service.PhongHocService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class PhongHocServiceImpl implements PhongHocService {
    private final PhongHocRepository repository;
    public PhongHocServiceImpl(PhongHocRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<PhongHoc> getAll() {
        return repository.findAll();
    }
    @Override
    public Optional<PhongHoc> getById(Integer id) {
        return repository.findById(id);
    }
    @Override
    public PhongHoc save(PhongHoc data) {
        return repository.save(data);
    }
    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}