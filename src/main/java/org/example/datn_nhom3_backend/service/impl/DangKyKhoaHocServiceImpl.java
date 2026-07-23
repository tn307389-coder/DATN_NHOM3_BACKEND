package org.example.datn_nhom3_backend.service.impl;
import org.example.datn_nhom3_backend.entity.DangKyKhoaHoc;
import org.example.datn_nhom3_backend.repository.DangKyKhoaHocRepository;
import org.example.datn_nhom3_backend.service.DangKyKhoaHocService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class DangKyKhoaHocServiceImpl implements DangKyKhoaHocService {
    private final DangKyKhoaHocRepository repository;
    public DangKyKhoaHocServiceImpl(DangKyKhoaHocRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<DangKyKhoaHoc> getAll() {
        return repository.findAll();
    }
    @Override
    public Optional<DangKyKhoaHoc> getById(Integer id) {
        return repository.findById(id);
    }
    @Override
    public DangKyKhoaHoc save(DangKyKhoaHoc data) {
        return repository.save(data);
    }
    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}