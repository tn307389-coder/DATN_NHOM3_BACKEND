package org.example.datn_nhom3_backend.service.impl;

import org.example.datn_nhom3_backend.entity.DangKyKhoaHoc;
import org.example.datn_nhom3_backend.entity.HoSoHocVien;
import org.example.datn_nhom3_backend.entity.HocVien;
import org.example.datn_nhom3_backend.repository.DangKyKhoaHocRepository;
import org.example.datn_nhom3_backend.repository.HoSoHocVienRepository;
import org.example.datn_nhom3_backend.repository.ThanhToanRepository;
import org.example.datn_nhom3_backend.service.DangKyKhoaHocService;
import org.example.datn_nhom3_backend.service.EmailService;
import org.example.datn_nhom3_backend.service.TaiKhoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DangKyKhoaHocServiceImpl implements DangKyKhoaHocService {

    private static final Logger log = LoggerFactory.getLogger(DangKyKhoaHocServiceImpl.class);

    private final DangKyKhoaHocRepository repository;
    private final HoSoHocVienRepository hoSoHocVienRepository;
    private final ThanhToanRepository thanhToanRepository;
    private final EmailService emailService;
    private final TaiKhoanService taiKhoanService;

    public DangKyKhoaHocServiceImpl(DangKyKhoaHocRepository repository,
                                    HoSoHocVienRepository hoSoHocVienRepository,
                                    ThanhToanRepository thanhToanRepository,
                                    EmailService emailService,
                                    TaiKhoanService taiKhoanService) {
        this.repository = repository;
        this.hoSoHocVienRepository = hoSoHocVienRepository;
        this.thanhToanRepository = thanhToanRepository;
        this.emailService = emailService;
        this.taiKhoanService = taiKhoanService;
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
        // Lấy bản ghi cũ trước khi merge (trạng thái + email để so sánh khi gửi thông báo)
        DangKyKhoaHoc old = data.getMadk() != null
                ? repository.findById(data.getMadk()).orElse(null) : null;
        DangKyKhoaHoc saved = repository.save(data);
        String trangThai = saved.getTrangthai();
        boolean daThayDoiTrangThai = old == null || !Objects.equals(old.getTrangthai(), trangThai);
        if (trangThai == null) {
            return saved;
        }
        String trangThaiLower = trangThai.trim().toLowerCase();
        if (trangThaiLower.contains("đã duyệt")) {
            // Khi Admin duyệt đăng ký, tự động tạo hồ sơ học viên (CHỜ DUYỆT) nếu chưa có.
            if (saved.getHocVien() != null && saved.getHocVien().getMahv() != null) {
                ensureHoSoHocVien(saved.getHocVien());
            }
            if (daThayDoiTrangThai) {
                guiEmailThongBao(saved, old, true);
            }
        } else if ("đã hủy".equals(trangThaiLower) && daThayDoiTrangThai) {
            guiEmailThongBao(saved, old, false);
        }
        return saved;
    }

    private void guiEmailThongBao(DangKyKhoaHoc saved, DangKyKhoaHoc old, boolean duyet) {
        HocVien hv = saved.getHocVien() != null && saved.getHocVien().getEmail() != null
                ? saved.getHocVien()
                : (old != null ? old.getHocVien() : null);
        if (hv == null || hv.getEmail() == null || hv.getEmail().isBlank()) {
            return;
        }
        String tenKhoaHoc = saved.getKhoaHoc() != null
                ? saved.getKhoaHoc().getTenkhoahoc()
                : (old != null && old.getKhoaHoc() != null ? old.getKhoaHoc().getTenkhoahoc() : null);
        try {
            if (duyet) {
                emailService.sendRegistrationApproved(hv.getEmail(), hv.getHoten(), tenKhoaHoc);
            } else {
                emailService.sendRegistrationRejected(hv.getEmail(), hv.getHoten(), tenKhoaHoc);
            }
        } catch (Exception e) {
            // Email lỗi không làm hỏng thao tác duyệt/từ chối
            log.warn("Gửi email {} đăng ký {} thất bại: {}", duyet ? "duyệt" : "từ chối",
                    saved.getMadk(), e.getMessage());
        }
    }

    private void ensureHoSoHocVien(HocVien hv) {
        if (hoSoHocVienRepository.findByHocVien_Mahv(hv.getMahv()).isPresent()) {
            return;
        }
        HoSoHocVien hoSo = new HoSoHocVien();
        hoSo.setHocVien(hv);
        hoSo.setNgaydangky(hv.getNgaydangky());
        hoSo.setTinhtrang("Đang xử lý");
        hoSo.setGhichu("Tự động tạo khi duyệt đăng ký");
        hoSo.setDaChinhSua(false);
        hoSoHocVienRepository.save(hoSo);
        // Có hồ sơ học viên -> tự cấp tài khoản học viên + gửi email (lỗi không làm hỏng thao tác duyệt)
        try {
            taiKhoanService.capTaiKhoanHocVien(hv);
        } catch (Exception e) {
            log.warn("Tự cấp tài khoản học viên (hồ sơ {}) thất bại: {}", hoSo.getMahs(), e.getMessage());
        }
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        // Xóa thanh toán liên quan trước (FK_TT_DKKH) rồi mới xóa đăng ký
        thanhToanRepository.deleteByDangKyKhoaHoc_Madk(id);
        repository.deleteById(id);
    }
}
