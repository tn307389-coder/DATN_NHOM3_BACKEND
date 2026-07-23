package org.example.datn_nhom3_backend.service.impl;
import org.example.datn_nhom3_backend.dto.BatchDiemDanhRequest;
import org.example.datn_nhom3_backend.entity.DiemDanh;
import org.example.datn_nhom3_backend.entity.HocVien;
import org.example.datn_nhom3_backend.entity.LichHoc;
import org.example.datn_nhom3_backend.repository.DiemDanhRepository;
import org.example.datn_nhom3_backend.repository.HocVienRepository;
import org.example.datn_nhom3_backend.repository.LichHocRepository;
import org.example.datn_nhom3_backend.service.DiemDanhService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class DiemDanhServiceImpl implements DiemDanhService {
    private final DiemDanhRepository repository;
    private final LichHocRepository lichHocRepository;
    private final HocVienRepository hocVienRepository;
    public DiemDanhServiceImpl(DiemDanhRepository repository,
                               LichHocRepository lichHocRepository,
                               HocVienRepository hocVienRepository) {
        this.repository = repository;
        this.lichHocRepository = lichHocRepository;
        this.hocVienRepository = hocVienRepository;
    }
    @Override
    public List<DiemDanh> getAll() { return repository.findAll(); }
    @Override
    public Optional<DiemDanh> getById(Integer id) { return repository.findById(id); }
    @Override
    public DiemDanh save(DiemDanh data) { return repository.save(data); }
    @Override
    public void delete(Integer id) { repository.deleteById(id); }
    @Override
    public List<DiemDanh> getByLichAndDate(Integer malich, LocalDate ngaydiemdanh) {
        return repository.findByLichHoc_MalichAndNgaydiemdanh(malich, ngaydiemdanh);
    }
    @Override
    @Transactional
    public void saveBatch(BatchDiemDanhRequest request) {
        repository.deleteByLichHoc_MalichAndNgaydiemdanh(request.getMalich(), request.getNgaydiemdanh());
        LichHoc lichHoc = lichHocRepository.findById(request.getMalich()).orElse(null);
        if (lichHoc == null) return;
        List<DiemDanh> list = new ArrayList<>();
        for (BatchDiemDanhRequest.DiemDanhItem item : request.getDanhSach()) {
            HocVien hv = hocVienRepository.findById(item.getMahv()).orElse(null);
            if (hv == null) continue;
            DiemDanh dd = new DiemDanh();
            dd.setLichHoc(lichHoc);
            dd.setHocVien(hv);
            dd.setNgaydiemdanh(request.getNgaydiemdanh());
            dd.setTrangthai(item.getTrangthai());
            dd.setGhichu(item.getGhichu());
            list.add(dd);
        }
        repository.saveAll(list);
    }
}