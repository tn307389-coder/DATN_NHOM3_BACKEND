package org.example.datn_nhom3_backend.service;

import org.example.datn_nhom3_backend.dto.DashboardStatsDto;
import java.util.List;

public interface ThongKeService {
    DashboardStatsDto getDashboardStats();
    List<DashboardStatsDto.DoanhThuTheoThang> getDoanhThuTheoThang(int soThang);
    List<DashboardStatsDto.ThongKeGiaoVien> getThongKeGiaoVien();
    List<DashboardStatsDto.HocVienTheoKhoa> getHocVienTheoKhoa();
    Double getTongDoanhThu();
    Double getDoanhThuTheoKhoangThoiGian(java.time.LocalDate tuNgay, java.time.LocalDate denNgay);
    Long getSoHocVienDangKyTrongKhoang(java.time.LocalDate tuNgay, java.time.LocalDate denNgay);
    Double getTyLeDauThi();
}