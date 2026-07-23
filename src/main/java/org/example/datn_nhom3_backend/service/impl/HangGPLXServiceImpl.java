package org.example.datn_nhom3_backend.service.impl;
import org.example.datn_nhom3_backend.entity.HangGPLX;
import org.example.datn_nhom3_backend.repository.HangGPLXRepository;
import org.example.datn_nhom3_backend.service.HangGPLXService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class HangGPLXServiceImpl implements HangGPLXService {
    private final HangGPLXRepository repository;
    public HangGPLXServiceImpl(HangGPLXRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<HangGPLX> getAll() {
        return repository.findAll();
    }
    @Override
    public Optional<HangGPLX> getById(Integer id) {
        return repository.findById(id);
    }
    @Override
    public HangGPLX save(HangGPLX data) {
        return repository.save(data);
    }
    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}