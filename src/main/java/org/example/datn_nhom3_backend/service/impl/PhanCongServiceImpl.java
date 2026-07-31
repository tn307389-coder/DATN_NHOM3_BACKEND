package org.example.datn_nhom3_backend.service.impl;
import org.example.datn_nhom3_backend.entity.PhanCong;
import org.example.datn_nhom3_backend.repository.PhanCongRepository;
import org.example.datn_nhom3_backend.service.PhanCongService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class PhanCongServiceImpl implements PhanCongService {
    private final PhanCongRepository repository;
    public PhanCongServiceImpl(PhanCongRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<PhanCong> getAll() {
        return repository.findAll();
    }
    @Override
    public Optional<PhanCong> getById(Integer id) {
        return repository.findById(id);
    }
    @Override
    public PhanCong save(PhanCong data) {
        if (data.getXe() != null && data.getXe().getMaxe() == null) {
            data.setXe(null);
        }
        if (data.getHocVien() != null && data.getHocVien().getMahv() == null) {
            data.setHocVien(null);
        }
        if (data.getGiaoVien() != null && data.getGiaoVien().getMagv() == null) {
            data.setGiaoVien(null);
        }
        return repository.save(data);
    }
    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}