package org.example.datn_nhom3_backend.service.impl;
import org.example.datn_nhom3_backend.entity.ChuongTrinhHoc;
import org.example.datn_nhom3_backend.repository.ChuongTrinhHocRepository;
import org.example.datn_nhom3_backend.service.ChuongTrinhHocService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class ChuongTrinhHocServiceImpl implements ChuongTrinhHocService {
    private final ChuongTrinhHocRepository repository;
    public ChuongTrinhHocServiceImpl(ChuongTrinhHocRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<ChuongTrinhHoc> getAll() {
        return repository.findAll();
    }
    @Override
    public Optional<ChuongTrinhHoc> getById(Integer id) {
        return repository.findById(id);
    }
    @Override
    public ChuongTrinhHoc save(ChuongTrinhHoc data) {
        return repository.save(data);
    }
    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}