package org.example.datn_nhom3_backend.service.impl;
import org.example.datn_nhom3_backend.entity.PhongThi;
import org.example.datn_nhom3_backend.repository.PhongThiRepository;
import org.example.datn_nhom3_backend.service.PhongThiService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class PhongThiServiceImpl implements PhongThiService {
    private final PhongThiRepository repository;
    public PhongThiServiceImpl(PhongThiRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<PhongThi> getAll() {
        return repository.findAll();
    }
    @Override
    public Optional<PhongThi> getById(Integer id) {
        return repository.findById(id);
    }
    @Override
    public PhongThi save(PhongThi data) {
        return repository.save(data);
    }
    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}