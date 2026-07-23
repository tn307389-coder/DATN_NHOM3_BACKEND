package org.example.datn_nhom3_backend.service.impl;
import org.example.datn_nhom3_backend.entity.HocVien;
import org.example.datn_nhom3_backend.repository.HocVienRepository;
import org.example.datn_nhom3_backend.service.HocVienService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class HocVienServiceImpl implements HocVienService {
    private final HocVienRepository repository;
    public HocVienServiceImpl(HocVienRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<HocVien> getAll() {
        return repository.findAll();
    }
    @Override
    public Optional<HocVien> getById(Integer id) {
        return repository.findById(id);
    }
    @Override
    public Optional<HocVien> findByCccd(String cccd) {
        return repository.findByCccd(cccd);
    }
    @Override
    public HocVien save(HocVien data) {
        return repository.save(data);
    }
    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}