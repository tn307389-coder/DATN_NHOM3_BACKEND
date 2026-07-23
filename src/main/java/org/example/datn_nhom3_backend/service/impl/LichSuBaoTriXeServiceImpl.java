package org.example.datn_nhom3_backend.service.impl;

import org.example.datn_nhom3_backend.entity.LichSuBaoTriXe;
import org.example.datn_nhom3_backend.repository.LichSuBaoTriXeRepository;
import org.example.datn_nhom3_backend.service.LichSuBaoTriXeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class LichSuBaoTriXeServiceImpl implements LichSuBaoTriXeService {

    private final LichSuBaoTriXeRepository repository;

    public LichSuBaoTriXeServiceImpl(LichSuBaoTriXeRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LichSuBaoTriXe> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LichSuBaoTriXe> getById(Integer id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LichSuBaoTriXe> getByXe(Integer maxe) {
        return repository.findByXeMaxeOrderByNgayBaoTriDesc(maxe);
    }

    @Override
    @Transactional
    public LichSuBaoTriXe save(LichSuBaoTriXe data) {
        return repository.save(data);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
