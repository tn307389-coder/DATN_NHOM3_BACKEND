package org.example.datn_nhom3_backend.service.impl;
import org.example.datn_nhom3_backend.entity.BangDiemThuongXuyen;
import org.example.datn_nhom3_backend.repository.BangDiemThuongXuyenRepository;
import org.example.datn_nhom3_backend.service.BangDiemThuongXuyenService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class BangDiemThuongXuyenServiceImpl implements BangDiemThuongXuyenService {
    private final BangDiemThuongXuyenRepository repository;
    public BangDiemThuongXuyenServiceImpl(BangDiemThuongXuyenRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<BangDiemThuongXuyen> getAll() {
        return repository.findAll();
    }
    @Override
    public Optional<BangDiemThuongXuyen> getById(Integer id) {
        return repository.findById(id);
    }
    @Override
    public BangDiemThuongXuyen save(BangDiemThuongXuyen data) {
        return repository.save(data);
    }
    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}