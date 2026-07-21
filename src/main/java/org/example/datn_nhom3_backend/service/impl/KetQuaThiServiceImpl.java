package org.example.datn_nhom3_backend.service.impl;
import org.example.datn_nhom3_backend.entity.KetQuaThi;
import org.example.datn_nhom3_backend.repository.KetQuaThiRepository;
import org.example.datn_nhom3_backend.service.KetQuaThiService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class KetQuaThiServiceImpl implements KetQuaThiService {
    private final KetQuaThiRepository repository;
    public KetQuaThiServiceImpl(KetQuaThiRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<KetQuaThi> getAll() {
        return repository.findAll();
    }
    @Override
    public Optional<KetQuaThi> getById(Integer id) {
        return repository.findById(id);
    }
    @Override
    public KetQuaThi save(KetQuaThi data) {
        return repository.save(data);
    }
    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}