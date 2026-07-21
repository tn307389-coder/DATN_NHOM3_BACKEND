package org.example.datn_nhom3_backend.service.impl;
import org.example.datn_nhom3_backend.entity.HoSoHocVien;
import org.example.datn_nhom3_backend.repository.HoSoHocVienRepository;
import org.example.datn_nhom3_backend.service.HoSoHocVienService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class HoSoHocVienServiceImpl implements HoSoHocVienService {
    private final HoSoHocVienRepository repository;
    public HoSoHocVienServiceImpl(HoSoHocVienRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<HoSoHocVien> getAll() {
        return repository.findAll();
    }
    @Override
    public Optional<HoSoHocVien> getById(Integer id) {
        return repository.findById(id);
    }
    @Override
    public HoSoHocVien save(HoSoHocVien data) {
        return repository.save(data);
    }
    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}