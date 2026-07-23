package org.example.datn_nhom3_backend.service.impl;
import org.example.datn_nhom3_backend.entity.ThongBao;
import org.example.datn_nhom3_backend.repository.ThongBaoRepository;
import org.example.datn_nhom3_backend.service.ThongBaoService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class ThongBaoServiceImpl implements ThongBaoService {
    private final ThongBaoRepository repository;
    public ThongBaoServiceImpl(ThongBaoRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<ThongBao> getAll() {
        return repository.findAll();
    }
    @Override
    public Optional<ThongBao> getById(Integer id) {
        return repository.findById(id);
    }
    @Override
    public ThongBao save(ThongBao data) {
        return repository.save(data);
    }
    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}