package org.example.datn_nhom3_backend.service.impl;
import org.example.datn_nhom3_backend.entity.DiemDanh;
import org.example.datn_nhom3_backend.repository.DiemDanhRepository;
import org.example.datn_nhom3_backend.service.DiemDanhService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class DiemDanhServiceImpl implements DiemDanhService {
    private final DiemDanhRepository repository;
    public DiemDanhServiceImpl(DiemDanhRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<DiemDanh> getAll() {
        return repository.findAll();
    }
    @Override
    public Optional<DiemDanh> getById(Integer id) {
        return repository.findById(id);
    }
    @Override
    public DiemDanh save(DiemDanh data) {
        return repository.save(data);
    }
    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}