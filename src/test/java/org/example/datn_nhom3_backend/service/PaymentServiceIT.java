package org.example.datn_nhom3_backend.service;

import org.example.datn_nhom3_backend.entity.ChuongTrinhHoc;
import org.example.datn_nhom3_backend.entity.DangKyKhoaHoc;
import org.example.datn_nhom3_backend.entity.HocVien;
import org.example.datn_nhom3_backend.entity.KhoaHoc;
import org.example.datn_nhom3_backend.entity.ThanhToan;
import org.example.datn_nhom3_backend.repository.ChuongTrinhHocRepository;
import org.example.datn_nhom3_backend.repository.DangKyKhoaHocRepository;
import org.example.datn_nhom3_backend.repository.HocVienRepository;
import org.example.datn_nhom3_backend.repository.KhoaHocRepository;
import org.example.datn_nhom3_backend.repository.ThanhToanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class PaymentServiceIT {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private HocVienRepository hocVienRepository;
    @Autowired
    private KhoaHocRepository khoaHocRepository;
    @Autowired
    private DangKyKhoaHocRepository dangKyKhoaHocRepository;
    @Autowired
    private ThanhToanRepository thanhToanRepository;
    @Autowired
    private ChuongTrinhHocRepository chuongTrinhHocRepository;

    private HocVien hocVien;
    private DangKyKhoaHoc dangKy;

    @BeforeEach
    void setUp() {
        thanhToanRepository.deleteAll();
        dangKyKhoaHocRepository.deleteAll();
        khoaHocRepository.deleteAll();
        hocVienRepository.deleteAll();
        chuongTrinhHocRepository.deleteAll();

        hocVien = new HocVien();
        hocVien.setHoten("Nguyen Van Pay");
        hocVien.setCccd("999000111222");
        hocVien.setEmail("pay@test.com");
        hocVien.setNgaysinh(LocalDate.of(2000, 1, 1));
        hocVien = hocVienRepository.save(hocVien);

        ChuongTrinhHoc cth = new ChuongTrinhHoc();
        cth.setTenchuongtrinh("CTH B2");
        cth.setHangbang("B2");
        cth.setHocphi(15000000.0);
        cth = chuongTrinhHocRepository.save(cth);

        KhoaHoc kh = new KhoaHoc();
        kh.setTenkhoahoc("Khoa B2 Test");
        kh.setChuongTrinhHoc(cth);
        kh.setTrangthai("DANG_MO");
        kh = khoaHocRepository.save(kh);

        dangKy = new DangKyKhoaHoc();
        dangKy.setHocVien(hocVien);
        dangKy.setKhoaHoc(kh);
        dangKy.setNgaydangky(LocalDate.now());
        dangKy.setTrangthai("CHO_DUYET");
        dangKy = dangKyKhoaHocRepository.save(dangKy);
    }

    @Test
    void khoiTaoPayment_ShouldCreateWithQr() {
        ThanhToan tt = paymentService.khoiTaoPayment(dangKy.getMadk(), hocVien);
        assertNotNull(tt.getMatt());
        assertEquals("CHUA_THANH_TOAN", tt.getTrangthai());
        assertEquals("QR_CODE", tt.getPhuongthuc());
        assertNotNull(tt.getQrData());
        assertTrue(tt.getQrData().startsWith("DRIVEHUB:"));
        assertNotNull(tt.getTransactionRef());
    }

    @Test
    void khoiTaoPayment_ShouldBeIdempotent() {
        ThanhToan first = paymentService.khoiTaoPayment(dangKy.getMadk(), hocVien);
        ThanhToan second = paymentService.khoiTaoPayment(dangKy.getMadk(), hocVien);
        assertEquals(first.getMatt(), second.getMatt());
        assertEquals(1, thanhToanRepository.count());
    }

    @Test
    void xacNhanPayment_ShouldUpdateStatus() {
        ThanhToan tt = paymentService.khoiTaoPayment(dangKy.getMadk(), hocVien);
        ThanhToan confirmed = paymentService.xacNhanPayment(tt.getMatt());
        assertEquals("DA_THANH_TOAN", confirmed.getTrangthai());
    }

    @Test
    void generateQrBase64_ShouldReturnBase64Png() throws Exception {
        String base64 = paymentService.generateQrBase64("DRIVEHUB:1|ref|15000000|B2", 200, 200);
        assertNotNull(base64);
        assertTrue(base64.length() > 100);
    }
}
