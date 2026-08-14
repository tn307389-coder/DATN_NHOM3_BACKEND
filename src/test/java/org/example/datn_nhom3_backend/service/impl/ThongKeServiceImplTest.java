package org.example.datn_nhom3_backend.service.impl;

import org.example.datn_nhom3_backend.dto.DashboardStatsDto;
import org.example.datn_nhom3_backend.entity.DangKyKhoaHoc;
import org.example.datn_nhom3_backend.entity.KhoaHoc;
import org.example.datn_nhom3_backend.repository.DangKyKhoaHocRepository;
import org.example.datn_nhom3_backend.repository.HocVienRepository;
import org.example.datn_nhom3_backend.repository.GiaoVienRepository;
import org.example.datn_nhom3_backend.repository.KhoaHocRepository;
import org.example.datn_nhom3_backend.repository.ThanhToanRepository;
import org.example.datn_nhom3_backend.repository.ThiSatHachRepository;
import org.example.datn_nhom3_backend.repository.LopHocRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ThongKeServiceImplTest {

    @Mock private HocVienRepository hocVienRepository;
    @Mock private GiaoVienRepository giaoVienRepository;
    @Mock private KhoaHocRepository khoaHocRepository;
    @Mock private ThanhToanRepository thanhToanRepository;
    @Mock private ThiSatHachRepository thiSatHachRepository;
    @Mock private LopHocRepository lopHocRepository;
    @Mock private DangKyKhoaHocRepository dangKyKhoaHocRepository;

    @InjectMocks private ThongKeServiceImpl service;

    @Test
    void getHocVienTheoKhoa_ShouldCountFromDangKy() {
        KhoaHoc kh = new KhoaHoc();
        kh.setMakh(1);
        kh.setTenkhoahoc("Khóa A");
        when(khoaHocRepository.findAll()).thenReturn(List.of(kh));
        when(dangKyKhoaHocRepository.countGroupByKhoaHoc())
                .thenReturn(List.<Object[]>of(new Object[]{1, 5L}));

        List<DashboardStatsDto.HocVienTheoKhoa> result = service.getHocVienTheoKhoa();

        assertEquals(1, result.size());
        assertEquals(5L, result.get(0).getSoLuong());
        assertEquals("Khóa A", result.get(0).getTenKhoaHoc());
    }

    @Test
    void getSoHocVienDangKyTrongKhoang_ShouldCountByDate() {
        LocalDate start = LocalDate.of(2026, 1, 1);
        LocalDate end = LocalDate.of(2026, 1, 31);
        DangKyKhoaHoc dk = new DangKyKhoaHoc();
        dk.setNgaydangky(LocalDate.of(2026, 1, 15));
        when(dangKyKhoaHocRepository.findAll()).thenReturn(List.of(dk, new DangKyKhoaHoc()));

        Long count = service.getSoHocVienDangKyTrongKhoang(start, end);

        assertEquals(1L, count);
    }
}
