package org.example.datn_nhom3_backend.service.impl;
import org.example.datn_nhom3_backend.entity.CaHoc;
import org.example.datn_nhom3_backend.repository.CaHocRepository;
import org.example.datn_nhom3_backend.service.CaHocService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class CaHocServiceImpl implements CaHocService {
    private final CaHocRepository repository;
    public CaHocServiceImpl(CaHocRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<CaHoc> getAll() {
        return repository.findAll();
    }
    @Override
    public Optional<CaHoc> getById(Integer id) {
        return repository.findById(id);
    }
    @Override
    public CaHoc save(CaHoc data) {
        return repository.save(data);
    }
    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}