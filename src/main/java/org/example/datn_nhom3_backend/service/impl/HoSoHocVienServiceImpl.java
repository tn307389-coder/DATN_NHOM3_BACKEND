package org.example.datn_nhom3_backend.service.impl;
import org.example.datn_nhom3_backend.entity.HoSoHocVien;
import org.example.datn_nhom3_backend.repository.HoSoHocVienRepository;
import org.example.datn_nhom3_backend.service.HoSoHocVienService;
import org.example.datn_nhom3_backend.service.TaiKhoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class HoSoHocVienServiceImpl implements HoSoHocVienService {
    private static final Logger log = LoggerFactory.getLogger(HoSoHocVienServiceImpl.class);
    private final HoSoHocVienRepository repository;
    private final TaiKhoanService taiKhoanService;
    public HoSoHocVienServiceImpl(HoSoHocVienRepository repository,
                                  TaiKhoanService taiKhoanService) {
        this.repository = repository;
        this.taiKhoanService = taiKhoanService;
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
        boolean taoMoi = data.getMahs() == null;
        // Giữ nguyên cờ "đã chỉnh sửa" nếu payload không gửi (tránh reset khi admin sửa hồ sơ)
        if (!taoMoi && data.getDaChinhSua() == null) {
            repository.findById(data.getMahs()).ifPresent(old ->
                    data.setDaChinhSua(old.getDaChinhSua()));
        }
        HoSoHocVien saved = repository.save(data);
        // Có hồ sơ học viên mới -> tự cấp tài khoản học viên + gửi email
        if (taoMoi && saved.getHocVien() != null) {
            try {
                taiKhoanService.capTaiKhoanHocVien(saved.getHocVien());
            } catch (Exception e) {
                log.warn("Tự cấp tài khoản học viên khi tạo hồ sơ {} thất bại: {}", saved.getMahs(), e.getMessage());
            }
        }
        return saved;
    }
    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}