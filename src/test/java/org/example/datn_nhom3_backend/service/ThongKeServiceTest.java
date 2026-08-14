package org.example.datn_nhom3_backend.service;

import org.example.datn_nhom3_backend.entity.*;
import org.example.datn_nhom3_backend.repository.*;
import org.example.datn_nhom3_backend.service.impl.ThongKeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ThongKeServiceTest {

    @Mock
    private HocVienRepository hocVienRepository;
    @Mock
    private GiaoVienRepository giaoVienRepository;
    @Mock
    private KhoaHocRepository khoaHocRepository;
    @Mock
    private ThanhToanRepository thanhToanRepository;
    @Mock
    private ThiSatHachRepository thiSatHachRepository;
    @Mock
    private LopHocRepository lopHocRepository;
    @Mock
    private DangKyKhoaHocRepository dangKyKhoaHocRepository;

    @InjectMocks
    private ThongKeServiceImpl thongKeService;

    private ThanhToan thanhToan1;
    private ThanhToan thanhToan2;
    private ThiSatHach thiSatHachDau;
    private ThiSatHach thiSatHachRot;

    @BeforeEach
    void setUp() {
        thanhToan1 = new ThanhToan();
        thanhToan1.setSotien(1000000.0);
        thanhToan1.setNgaythanhtoan(LocalDate.now());
        thanhToan1.setPhuongthuc("TIEN_MAT");
        thanhToan1.setTrangthai("DA_THANH_TOAN");
        HocVien hv1 = new HocVien();
        hv1.setMahv(1);
        DangKyKhoaHoc dk = new DangKyKhoaHoc();
        dk.setMadk(1);
        thanhToan1.setHocVien(hv1);
        thanhToan1.setDangKyKhoaHoc(dk);

        thanhToan2 = new ThanhToan();
        thanhToan2.setSotien(2000000.0);
        thanhToan2.setNgaythanhtoan(LocalDate.now().minusMonths(2));
        thanhToan2.setPhuongthuc("CHUYEN_KHOAN");
        thanhToan2.setTrangthai("DA_THANH_TOAN");
        HocVien hv2 = new HocVien();
        hv2.setMahv(2);
        DangKyKhoaHoc dk2 = new DangKyKhoaHoc();
        dk2.setMadk(2);
        thanhToan2.setHocVien(hv2);
        thanhToan2.setDangKyKhoaHoc(dk2);

        thiSatHachDau = new ThiSatHach();
        thiSatHachDau.setKetqua("Đậu");
        thiSatHachRot = new ThiSatHach();
        thiSatHachRot.setKetqua("Rớt");
    }

    @Test
    void getTongDoanhThu_ShouldSumAllPayments() {
        when(thanhToanRepository.sumAllSotien()).thenReturn(3000000.0);

        Double result = thongKeService.getTongDoanhThu();

        assertEquals(3000000.0, result);
    }

    @Test
    void getTyLeDauThi_ShouldCalculateCorrectly() {
        when(thiSatHachRepository.findAll()).thenReturn(List.of(thiSatHachDau, thiSatHachDau, thiSatHachRot));

        Double result = thongKeService.getTyLeDauThi();

        assertEquals(66.67, result, 0.02);
    }

    @Test
    void getTyLeDauThi_WhenNoData_ShouldReturnZero() {
        when(thiSatHachRepository.findAll()).thenReturn(List.of());

        Double result = thongKeService.getTyLeDauThi();

        assertEquals(0.0, result);
    }

    @Test
    void getDoanhThuTheoKhoangThoiGian_ShouldFilterCorrectly() {
        when(thanhToanRepository.sumSotienByNgaythanhtoanBetween(any(), any())).thenReturn(1000000.0);

        Double result = thongKeService.getDoanhThuTheoKhoangThoiGian(
                LocalDate.now().minusDays(5), LocalDate.now().plusDays(1));

        assertEquals(1000000.0, result);
    }

    @Test
    void getThongKeGiaoVien_ShouldReturnList() {
        GiaoVien gv = new GiaoVien();
        gv.setMagv(1);
        gv.setHoten("Nguyễn Văn D");
        when(giaoVienRepository.findAll()).thenReturn(List.of(gv));
        when(lopHocRepository.countByGiaoVienMagv(1)).thenReturn(5L);

        var result = thongKeService.getThongKeGiaoVien();

        assertEquals(1, result.size());
        assertEquals("Nguyễn Văn D", result.get(0).getTenGiaoVien());
        assertEquals(5L, result.get(0).getSoLopDay().longValue());
    }
}