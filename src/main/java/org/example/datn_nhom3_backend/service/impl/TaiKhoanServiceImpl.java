package org.example.datn_nhom3_backend.service.impl;

import org.example.datn_nhom3_backend.dto.TaiKhoanRequest;
import org.example.datn_nhom3_backend.entity.HocVien;
import org.example.datn_nhom3_backend.entity.TaiKhoan;
import org.example.datn_nhom3_backend.entity.VaiTro;
import org.example.datn_nhom3_backend.exception.ResourceNotFoundException;
import org.example.datn_nhom3_backend.repository.TaiKhoanRepository;
import org.example.datn_nhom3_backend.repository.VaiTroRepository;
import org.example.datn_nhom3_backend.service.EmailService;
import org.example.datn_nhom3_backend.service.TaiKhoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
public class TaiKhoanServiceImpl implements TaiKhoanService {

    private static final Logger log = LoggerFactory.getLogger(TaiKhoanServiceImpl.class);
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String PASSWORD_CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxyz23456789";

    private final TaiKhoanRepository repository;
    private final VaiTroRepository vaiTroRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public TaiKhoanServiceImpl(TaiKhoanRepository repository,
                               VaiTroRepository vaiTroRepository,
                               PasswordEncoder passwordEncoder,
                               EmailService emailService) {
        this.repository = repository;
        this.vaiTroRepository = vaiTroRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public List<TaiKhoan> getAll() {
        return repository.findAllWithVaiTro();
    }

    @Override
    public Page<TaiKhoan> getAll(Specification<TaiKhoan> spec, Pageable pageable) {
        return repository.findAll(spec, pageable);
    }

    @Override
    public Optional<TaiKhoan> getById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public TaiKhoan save(TaiKhoan taiKhoan) {
        return repository.save(taiKhoan);
    }

    @Override
    public TaiKhoan createAccount(TaiKhoanRequest request) {
        VaiTro vaiTro = vaiTroRepository.findByMaVaiTro(request.getMaVaiTro())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy vai trò: " + request.getMaVaiTro()));

        TaiKhoan taiKhoan = new TaiKhoan();
        taiKhoan.setTendangnhap(request.getTendangnhap());
        taiKhoan.setMatkhau(passwordEncoder.encode(request.getMatkhau()));
        taiKhoan.setHoten(request.getHoten());
        taiKhoan.setEmail(request.getEmail());
        taiKhoan.setSoDienThoai(request.getSoDienThoai());
        taiKhoan.setVaitro(vaiTro);
        taiKhoan.setTrangthai(request.getTrangthai() != null && request.getTrangthai() ? "ACTIVE" : "INACTIVE");
        return repository.save(taiKhoan);
    }

    @Override
    public TaiKhoan updateAccount(Integer id, TaiKhoanRequest request) {
        TaiKhoan taiKhoan = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tài khoản với ID: " + id));

        VaiTro vaiTro = vaiTroRepository.findByMaVaiTro(request.getMaVaiTro())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy vai trò: " + request.getMaVaiTro()));

        taiKhoan.setTendangnhap(request.getTendangnhap());
        if (request.getMatkhau() != null && !request.getMatkhau().isBlank()) {
            taiKhoan.setMatkhau(passwordEncoder.encode(request.getMatkhau()));
        }
        taiKhoan.setHoten(request.getHoten());
        taiKhoan.setEmail(request.getEmail());
        taiKhoan.setSoDienThoai(request.getSoDienThoai());
        taiKhoan.setVaitro(vaiTro);
        taiKhoan.setTrangthai(request.getTrangthai() != null && request.getTrangthai() ? "ACTIVE" : "INACTIVE");
        return repository.save(taiKhoan);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<TaiKhoan> login(String tendangnhap, String matkhau) {
        Optional<TaiKhoan> tk = repository.findByTendangnhap(tendangnhap);
        if (tk.isPresent() && passwordEncoder.matches(matkhau, tk.get().getMatkhau())) {
            return tk;
        }
        return Optional.empty();
    }

    @Override
    public Optional<TaiKhoan> findByTendangnhap(String tendangnhap) {
        return repository.findByTendangnhap(tendangnhap);
    }

    @Override
    public TaiKhoan updateCurrentUser(String tendangnhap, TaiKhoanRequest request) {
        TaiKhoan taiKhoan = repository.findByTendangnhap(tendangnhap)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tài khoản: " + tendangnhap));
        if (request.getHoten() != null) taiKhoan.setHoten(request.getHoten());
        if (request.getEmail() != null) taiKhoan.setEmail(request.getEmail());
        if (request.getSoDienThoai() != null) taiKhoan.setSoDienThoai(request.getSoDienThoai());
        if (request.getAnh() != null) taiKhoan.setAnh(request.getAnh());
        return repository.save(taiKhoan);
    }

    @Override
    public boolean doiMatKhau(String tendangnhap, String matKhauCu, String matKhauMoi) {
        TaiKhoan taiKhoan = repository.findByTendangnhap(tendangnhap)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tài khoản: " + tendangnhap));
        if (matKhauCu == null || matKhauCu.isBlank() || matKhauMoi == null || matKhauMoi.isBlank()) {
            throw new IllegalArgumentException("Mật khẩu cũ và mật khẩu mới không được để trống");
        }
        if (!passwordEncoder.matches(matKhauCu, taiKhoan.getMatkhau())) {
            return false;
        }
        taiKhoan.setMatkhau(passwordEncoder.encode(matKhauMoi));
        repository.save(taiKhoan);
        return true;
    }

    @Override
    public TaiKhoan capTaiKhoanHocVien(HocVien hocVien) {
        if (hocVien == null || hocVien.getCccd() == null || hocVien.getCccd().isBlank()) {
            log.warn("Không cấp tài khoản học viên: thiếu CCCD");
            return null;
        }
        // Đã có tài khoản theo CCCD hoặc email -> không cấp trùng
        if (repository.findByCccd(hocVien.getCccd()).isPresent()) {
            return null;
        }
        if (hocVien.getEmail() != null && !hocVien.getEmail().isBlank()
                && repository.findByEmail(hocVien.getEmail()).isPresent()) {
            return null;
        }
        VaiTro vaiTro = vaiTroRepository.findByMaVaiTro("HV").orElse(null);
        if (vaiTro == null) {
            log.warn("Không cấp tài khoản học viên: không tìm thấy vai trò HV");
            return null;
        }

        String matKhau = taoMatKhauNgauNhien();
        String tenDangNhap = taoTenDangNhap(hocVien.getCccd());
        TaiKhoan taiKhoan = new TaiKhoan();
        taiKhoan.setTendangnhap(tenDangNhap);
        taiKhoan.setMatkhau(passwordEncoder.encode(matKhau));
        taiKhoan.setHoten(hocVien.getHoten());
        taiKhoan.setEmail(hocVien.getEmail());
        taiKhoan.setSoDienThoai(hocVien.getSodienthoai());
        taiKhoan.setCccd(hocVien.getCccd());
        taiKhoan.setVaitro(vaiTro);
        taiKhoan.setTrangthai("ACTIVE");
        TaiKhoan saved = repository.save(taiKhoan);

        if (hocVien.getEmail() != null && !hocVien.getEmail().isBlank()) {
            try {
                emailService.sendAccountCreatedEmail(hocVien.getEmail(), hocVien.getHoten(), tenDangNhap, matKhau);
            } catch (Exception e) {
                log.warn("Gửi email cấp tài khoản thất bại cho {}: {}", hocVien.getEmail(), e.getMessage());
            }
        }
        return saved;
    }

    private String taoMatKhauNgauNhien() {
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            sb.append(PASSWORD_CHARS.charAt(RANDOM.nextInt(PASSWORD_CHARS.length())));
        }
        return sb.toString();
    }

    private String taoTenDangNhap(String cccd) {
        String base = "hocvien" + cccd;
        String candidate = base;
        int i = 1;
        while (repository.findByTendangnhap(candidate).isPresent()) {
            candidate = base + i;
            i++;
        }
        return candidate;
    }
}
