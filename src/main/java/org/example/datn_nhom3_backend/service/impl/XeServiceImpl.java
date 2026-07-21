package org.example.datn_nhom3_backend.service.impl;
import org.example.datn_nhom3_backend.entity.Xe;
import org.example.datn_nhom3_backend.repository.XeRepository;
import org.example.datn_nhom3_backend.service.XeService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class XeServiceImpl implements XeService {
    private final XeRepository repository;
    public XeServiceImpl(XeRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<Xe> getAll() {
        return repository.findAll();
    }
    @Override
    public Optional<Xe> getById(Integer id) {
        return repository.findById(id);
    }
    @Override
    public Xe save(Xe data) {
        return repository.save(data);
    }
    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}