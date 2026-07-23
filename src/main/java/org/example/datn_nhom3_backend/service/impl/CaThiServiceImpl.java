package org.example.datn_nhom3_backend.service.impl;
import org.example.datn_nhom3_backend.entity.CaThi;
import org.example.datn_nhom3_backend.repository.CaThiRepository;
import org.example.datn_nhom3_backend.service.CaThiService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class CaThiServiceImpl implements CaThiService {
    private final CaThiRepository repository;
    public CaThiServiceImpl(CaThiRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<CaThi> getAll() {
        return repository.findAll();
    }
    @Override
    public Optional<CaThi> getById(Integer id) {
        return repository.findById(id);
    }
    @Override
    public CaThi save(CaThi data) {
        return repository.save(data);
    }
    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}