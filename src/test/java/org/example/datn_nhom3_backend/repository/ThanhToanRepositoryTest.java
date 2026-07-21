package org.example.datn_nhom3_backend.repository;

import org.example.datn_nhom3_backend.entity.DangKyKhoaHoc;
import org.example.datn_nhom3_backend.entity.HocVien;
import org.example.datn_nhom3_backend.entity.KhoaHoc;
import org.example.datn_nhom3_backend.entity.ThanhToan;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ThanhToanRepositoryTest {

    @Autowired
    private ThanhToanRepository thanhToanRepository;
    @Autowired
    private HocVienRepository hocVienRepository;
    @Autowired
    private DangKyKhoaHocRepository dangKyKhoaHocRepository;
    @Autowired
    private KhoaHocRepository khoaHocRepository;

    @Test
    void countByNgaythanhtoanBetween_ShouldCountCorrectly() {
        HocVien hv = new HocVien();
        hv.setHoten("Test HV");
        hv.setCccd("111222333999");
        hv.setNgaydangky(LocalDate.now());
        hv = hocVienRepository.save(hv);

        KhoaHoc kh = new KhoaHoc();
        kh.setTenkhoahoc("Test Khoa");
        kh.setTrangthai("DANG_MO");
        kh = khoaHocRepository.save(kh);

        DangKyKhoaHoc dk = new DangKyKhoaHoc();
        dk.setHocVien(hv);
        dk.setKhoaHoc(kh);
        dk.setNgaydangky(LocalDate.now());
        dk.setTrangthai("DANG_KY");
        dk = dangKyKhoaHocRepository.save(dk);

        ThanhToan tt1 = new ThanhToan();
        tt1.setHocVien(hv);
        tt1.setDangKyKhoaHoc(dk);
        tt1.setSotien(1000000.0);
        tt1.setNgaythanhtoan(LocalDate.of(2026, 7, 15));
        tt1.setPhuongthuc("TIEN_MAT");
        tt1.setTrangthai("DA_THANH_TOAN");
        thanhToanRepository.save(tt1);

        ThanhToan tt2 = new ThanhToan();
        tt2.setHocVien(hv);
        tt2.setDangKyKhoaHoc(dk);
        tt2.setSotien(2000000.0);
        tt2.setNgaythanhtoan(LocalDate.of(2026, 7, 20));
        tt2.setPhuongthuc("CHUYEN_KHOAN");
        tt2.setTrangthai("DA_THANH_TOAN");
        thanhToanRepository.save(tt2);

        Long count = thanhToanRepository.countByNgaythanhtoanBetween(
                LocalDate.of(2026, 7, 1), LocalDate.of(2026, 7, 31));

        assertEquals(2L, count);
    }

    @Test
    void countByNgaythanhtoanBetween_WhenNoData_ShouldReturnZero() {
        Long count = thanhToanRepository.countByNgaythanhtoanBetween(
                LocalDate.of(2000, 1, 1), LocalDate.of(2000, 12, 31));

        assertEquals(0L, count);
    }
}