package org.example.datn_nhom3_backend.service.impl;

import org.example.datn_nhom3_backend.dto.TaiKhoanRequest;
import org.example.datn_nhom3_backend.entity.TaiKhoan;
import org.example.datn_nhom3_backend.entity.VaiTro;
import org.example.datn_nhom3_backend.exception.ResourceNotFoundException;
import org.example.datn_nhom3_backend.repository.TaiKhoanRepository;
import org.example.datn_nhom3_backend.repository.VaiTroRepository;
import org.example.datn_nhom3_backend.service.TaiKhoanService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaiKhoanServiceImpl implements TaiKhoanService {

    private final TaiKhoanRepository repository;
    private final VaiTroRepository vaiTroRepository;
    private final PasswordEncoder passwordEncoder;

    public TaiKhoanServiceImpl(TaiKhoanRepository repository,
                               VaiTroRepository vaiTroRepository,
                               PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.vaiTroRepository = vaiTroRepository;
        this.passwordEncoder = passwordEncoder;
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
}
