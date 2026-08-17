package org.example.datn_nhom3_backend.service.impl;

import org.example.datn_nhom3_backend.entity.HocVien;
import org.example.datn_nhom3_backend.repository.*;
import org.example.datn_nhom3_backend.service.HocVienService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class HocVienServiceImpl implements HocVienService {

    private final HocVienRepository repository;
    private final ThanhToanRepository thanhToanRepository;
    private final TraGPLXRepository traGPLXRepository;
    private final KetQuaThiRepository ketQuaThiRepository;
    private final ThiSatHachRepository thiSatHachRepository;
    private final DangKyKhoaHocRepository dangKyKhoaHocRepository;
    private final DiemDanhRepository diemDanhRepository;
    private final PhanCongRepository phanCongRepository;
    private final BangDiemThuongXuyenRepository bangDiemThuongXuyenRepository;
    private final HoSoHocVienRepository hoSoHocVienRepository;

    public HocVienServiceImpl(HocVienRepository repository,
                              ThanhToanRepository thanhToanRepository,
                              TraGPLXRepository traGPLXRepository,
                              KetQuaThiRepository ketQuaThiRepository,
                              ThiSatHachRepository thiSatHachRepository,
                              DangKyKhoaHocRepository dangKyKhoaHocRepository,
                              DiemDanhRepository diemDanhRepository,
                              PhanCongRepository phanCongRepository,
                              BangDiemThuongXuyenRepository bangDiemThuongXuyenRepository,
                              HoSoHocVienRepository hoSoHocVienRepository) {
        this.repository = repository;
        this.thanhToanRepository = thanhToanRepository;
        this.traGPLXRepository = traGPLXRepository;
        this.ketQuaThiRepository = ketQuaThiRepository;
        this.thiSatHachRepository = thiSatHachRepository;
        this.dangKyKhoaHocRepository = dangKyKhoaHocRepository;
        this.diemDanhRepository = diemDanhRepository;
        this.phanCongRepository = phanCongRepository;
        this.bangDiemThuongXuyenRepository = bangDiemThuongXuyenRepository;
        this.hoSoHocVienRepository = hoSoHocVienRepository;
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
    @Transactional
    public void delete(Integer id) {
        // Xóa dữ liệu liên quan theo đúng thứ tự khóa ngoại trước khi xóa học viên
        thanhToanRepository.deleteByHocVien_Mahv(id);
        traGPLXRepository.deleteByHocVien_Mahv(id);
        ketQuaThiRepository.deleteByHocVien_Mahv(id);
        thiSatHachRepository.deleteByHocVien_Mahv(id);
        dangKyKhoaHocRepository.deleteByHocVien_Mahv(id);
        diemDanhRepository.deleteByHocVien_Mahv(id);
        phanCongRepository.deleteByHocVien_Mahv(id);
        bangDiemThuongXuyenRepository.deleteByHocVien_Mahv(id);
        hoSoHocVienRepository.deleteByHocVien_Mahv(id);
        repository.deleteById(id);
    }

    @Override
    public java.util.Map<String, Long> demDuLieuLienQuan(Integer id) {
        java.util.Map<String, Long> counts = new java.util.LinkedHashMap<>();
        counts.put("dangKyKhoaHoc", dangKyKhoaHocRepository.countByHocVien_Mahv(id));
        counts.put("thanhToan", thanhToanRepository.countByHocVien_Mahv(id));
        counts.put("hoSoHocVien", hoSoHocVienRepository.countByHocVien_Mahv(id));
        counts.put("diemDanh", diemDanhRepository.countByHocVien_Mahv(id));
        counts.put("phanCong", phanCongRepository.countByHocVien_Mahv(id));
        counts.put("bangDiemThuongXuyen", bangDiemThuongXuyenRepository.countByHocVien_Mahv(id));
        counts.put("thiSatHach", thiSatHachRepository.countByHocVien_Mahv(id));
        counts.put("ketQuaThi", ketQuaThiRepository.countByHocVien_Mahv(id));
        counts.put("traGPLX", traGPLXRepository.countByHocVien_Mahv(id));
        return counts;
    }
}
