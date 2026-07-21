package org.example.datn_nhom3_backend.service.impl;
import org.example.datn_nhom3_backend.entity.LichThi;
import org.example.datn_nhom3_backend.repository.LichThiRepository;
import org.example.datn_nhom3_backend.service.LichThiService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class LichThiServiceImpl implements LichThiService {
    private final LichThiRepository repository;
    public LichThiServiceImpl(LichThiRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<LichThi> getAll() {
        return repository.findAll();
    }
    @Override
    public Optional<LichThi> getById(Integer id) {
        return repository.findById(id);
    }
    @Override
    public LichThi save(LichThi data) {
        return repository.save(data);
    }
    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}