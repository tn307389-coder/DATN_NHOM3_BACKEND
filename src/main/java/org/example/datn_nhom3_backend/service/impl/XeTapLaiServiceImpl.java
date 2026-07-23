package org.example.datn_nhom3_backend.service.impl;
import org.example.datn_nhom3_backend.entity.XeTapLai;
import org.example.datn_nhom3_backend.repository.XeTapLaiRepository;
import org.example.datn_nhom3_backend.service.XeTapLaiService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class XeTapLaiServiceImpl implements XeTapLaiService {
    private final XeTapLaiRepository repository;
    public XeTapLaiServiceImpl(XeTapLaiRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<XeTapLai> getAll() {
        return repository.findAll();
    }
    @Override
    public Optional<XeTapLai> getById(Integer id) {
        return repository.findById(id);
    }
    @Override
    public XeTapLai save(XeTapLai data) {
        return repository.save(data);
    }
    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}