package org.example.datn_nhom3_backend.service.impl;
import org.example.datn_nhom3_backend.entity.ThanhToan;
import org.example.datn_nhom3_backend.repository.ThanhToanRepository;
import org.example.datn_nhom3_backend.service.ThanhToanService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class ThanhToanServiceImpl implements ThanhToanService {
    private final ThanhToanRepository repository;
    public ThanhToanServiceImpl(ThanhToanRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<ThanhToan> getAll() {
        return repository.findAll();
    }
    @Override
    public Optional<ThanhToan> getById(Integer id) {
        return repository.findById(id);
    }
    @Override
    public ThanhToan save(ThanhToan data) {
        return repository.save(data);
    }
    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}