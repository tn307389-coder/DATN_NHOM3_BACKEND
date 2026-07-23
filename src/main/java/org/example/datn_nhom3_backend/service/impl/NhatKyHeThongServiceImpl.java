package org.example.datn_nhom3_backend.service.impl;
import org.example.datn_nhom3_backend.entity.NhatKyHeThong;
import org.example.datn_nhom3_backend.repository.NhatKyHeThongRepository;
import org.example.datn_nhom3_backend.service.NhatKyHeThongService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class NhatKyHeThongServiceImpl implements NhatKyHeThongService {
    private final NhatKyHeThongRepository repository;
    public NhatKyHeThongServiceImpl(NhatKyHeThongRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<NhatKyHeThong> getAll() {
        return repository.findAll();
    }
    @Override
    public Optional<NhatKyHeThong> getById(Integer id) {
        return repository.findById(id);
    }
    @Override
    public NhatKyHeThong save(NhatKyHeThong data) {
        return repository.save(data);
    }
    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}