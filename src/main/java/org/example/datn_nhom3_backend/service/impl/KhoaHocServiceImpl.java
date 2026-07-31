package org.example.datn_nhom3_backend.service.impl;
import org.example.datn_nhom3_backend.dto.KhoaHocDto;
import org.example.datn_nhom3_backend.entity.KhoaHoc;
import org.example.datn_nhom3_backend.repository.DangKyKhoaHocRepository;
import org.example.datn_nhom3_backend.repository.KhoaHocRepository;
import org.example.datn_nhom3_backend.service.KhoaHocService;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.Optional;
@Service
public class KhoaHocServiceImpl implements KhoaHocService {
    private final KhoaHocRepository repository;
    private final DangKyKhoaHocRepository dangKyKhoaHocRepository;
    public KhoaHocServiceImpl(KhoaHocRepository repository,
                              DangKyKhoaHocRepository dangKyKhoaHocRepository) {
        this.repository = repository;
        this.dangKyKhoaHocRepository = dangKyKhoaHocRepository;
    }
    @Override
    public List<KhoaHocDto> getAllWithCount() {
        List<KhoaHoc> list = repository.findAll();
        List<KhoaHocDto> result = new ArrayList<>();

        Map<Integer, Long> countMap = new HashMap<>();
        List<Object[]> grouped = dangKyKhoaHocRepository.countGroupByKhoaHoc();
        for (Object[] row : grouped) {
            countMap.put(((Number) row[0]).intValue(), ((Number) row[1]).longValue());
        }

        for (KhoaHoc kh : list) {
            KhoaHocDto dto = new KhoaHocDto();
            dto.setMakh(kh.getMakh());
            dto.setTenkhoahoc(kh.getTenkhoahoc());
            dto.setNgaybatdau(kh.getNgaybatdau());
            dto.setNgayketthuc(kh.getNgayketthuc());
            dto.setTrangthai(kh.getTrangthai());
            if (kh.getChuongTrinhHoc() != null) {
                dto.setMacth(kh.getChuongTrinhHoc().getMacth());
                dto.setTencth(kh.getChuongTrinhHoc().getTenchuongtrinh());
                dto.setHangBang(kh.getChuongTrinhHoc().getHangbang());
            }
            dto.setSoLuongHocVien(countMap.getOrDefault(kh.getMakh(), 0L));
            result.add(dto);
        }
        return result;
    }
    @Override
    public List<KhoaHoc> getAll() { return repository.findAll(); }
    @Override
    public Optional<KhoaHoc> getById(Integer id) { return repository.findById(id); }
    @Override
    public KhoaHoc save(KhoaHoc data) { return repository.save(data); }
    @Override
    public void delete(Integer id) { repository.deleteById(id); }
}