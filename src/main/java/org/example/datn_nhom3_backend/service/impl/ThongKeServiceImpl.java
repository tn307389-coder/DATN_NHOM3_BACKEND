package org.example.datn_nhom3_backend.service.impl;

import org.example.datn_nhom3_backend.dto.DashboardStatsDto;
import org.example.datn_nhom3_backend.entity.*;
import org.example.datn_nhom3_backend.repository.*;
import org.example.datn_nhom3_backend.service.ThongKeService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ThongKeServiceImpl implements ThongKeService {

    private final HocVienRepository hocVienRepository;
    private final GiaoVienRepository giaoVienRepository;
    private final KhoaHocRepository khoaHocRepository;
    private final ThanhToanRepository thanhToanRepository;
    private final ThiSatHachRepository thiSatHachRepository;
    private final LopHocRepository lopHocRepository;
    private final DangKyKhoaHocRepository dangKyKhoaHocRepository;

    public ThongKeServiceImpl(HocVienRepository hocVienRepository,
                              GiaoVienRepository giaoVienRepository,
                              KhoaHocRepository khoaHocRepository,
                              ThanhToanRepository thanhToanRepository,
                              ThiSatHachRepository thiSatHachRepository,
                              LopHocRepository lopHocRepository,
                              DangKyKhoaHocRepository dangKyKhoaHocRepository) {
        this.hocVienRepository = hocVienRepository;
        this.giaoVienRepository = giaoVienRepository;
        this.khoaHocRepository = khoaHocRepository;
        this.thanhToanRepository = thanhToanRepository;
        this.thiSatHachRepository = thiSatHachRepository;
        this.lopHocRepository = lopHocRepository;
        this.dangKyKhoaHocRepository = dangKyKhoaHocRepository;
    }

    @Override
    public DashboardStatsDto getDashboardStats() {
        LocalDate now = LocalDate.now();
        LocalDate firstDayOfMonth = now.withDayOfMonth(1);
        LocalDate firstDayLastMonth = firstDayOfMonth.minusMonths(1);
        LocalDate lastDayLastMonth = firstDayOfMonth.minusDays(1);

        Long tongHocVien = hocVienRepository.count();
        Long tongGiaoVien = giaoVienRepository.count();
        Long tongKhoaHocDangMo = khoaHocRepository.countByTrangthai("DANG_MO");
        if (tongKhoaHocDangMo == null) tongKhoaHocDangMo = 0L;

        Double tongDoanhThu = getTongDoanhThu();
        Double doanhThuThangNay = getDoanhThuTheoKhoangThoiGian(firstDayOfMonth, now);
        Double doanhThuThangTruoc = getDoanhThuTheoKhoangThoiGian(firstDayLastMonth, lastDayLastMonth);

        Long soHocVienMoiThangNay = getSoHocVienDangKyTrongKhoang(firstDayOfMonth, now);
        if (soHocVienMoiThangNay == null) soHocVienMoiThangNay = 0L;

        Long soHocVienDauKy = tongHocVien;
        Double tyLeDauThi = getTyLeDauThi();

        List<DashboardStatsDto.DoanhThuTheoThang> doanhThuTheoThangs = getDoanhThuTheoThang(6);
        List<DashboardStatsDto.ThongKeGiaoVien> thongKeGiaoViens = getThongKeGiaoVien();
        List<DashboardStatsDto.HocVienTheoKhoa> hocVienTheoKhoas = getHocVienTheoKhoa();

        return DashboardStatsDto.builder()
                .tongHocVien(tongHocVien)
                .tongGiaoVien(tongGiaoVien)
                .tongKhoaHocDangMo(tongKhoaHocDangMo)
                .tongDoanhThu(tongDoanhThu)
                .doanhThuThangNay(doanhThuThangNay)
                .doanhThuThangTruoc(doanhThuThangTruoc)
                .soHocVienDauKy(soHocVienDauKy)
                .soHocVienMoiThangNay(soHocVienMoiThangNay)
                .tyLeDauThi(tyLeDauThi)
                .doanhThuTheoThangs(doanhThuTheoThangs)
                .thongKeGiaoViens(thongKeGiaoViens)
                .hocVienTheoKhoas(hocVienTheoKhoas)
                .build();
    }

    @Override
    public Double getTongDoanhThu() {
        Double result = thanhToanRepository.sumAllSotien();
        return result != null ? result : 0.0;
    }

    @Override
    public Double getDoanhThuTheoKhoangThoiGian(LocalDate tuNgay, LocalDate denNgay) {
        Double result = thanhToanRepository.sumSotienByNgaythanhtoanBetween(tuNgay, denNgay);
        return result != null ? result : 0.0;
    }

    @Override
    public Long getSoHocVienDangKyTrongKhoang(LocalDate tuNgay, LocalDate denNgay) {
        return dangKyKhoaHocRepository.findAll().stream()
                .filter(dk -> dk.getNgaydangky() != null
                        && !dk.getNgaydangky().isBefore(tuNgay)
                        && !dk.getNgaydangky().isAfter(denNgay))
                .count();
    }

    @Override
    public Double getTyLeDauThi() {
        List<ThiSatHach> thiList = thiSatHachRepository.findAll();
        long total = thiList.size();
        if (total == 0) return 0.0;
        long dau = thiList.stream()
                .filter(t -> "Đậu".equals(t.getKetqua()) || (t.getKetqua() != null && t.getKetqua().toLowerCase().contains("đậu")))
                .count();
        return Math.round((double) dau / total * 10000) / 100.0;
    }

    @Override
    public List<DashboardStatsDto.DoanhThuTheoThang> getDoanhThuTheoThang(int soThang) {
        List<DashboardStatsDto.DoanhThuTheoThang> result = new ArrayList<>();
        LocalDate now = LocalDate.now();
        YearMonth current = YearMonth.from(now);

        for (int i = soThang - 1; i >= 0; i--) {
            YearMonth ym = current.minusMonths(i);
            LocalDate firstDay = ym.atDay(1);
            LocalDate lastDay = ym.atEndOfMonth();
            Double revenue = getDoanhThuTheoKhoangThoiGian(firstDay, lastDay);
            result.add(new DashboardStatsDto.DoanhThuTheoThang(
                    String.format("%02d/%d", ym.getMonthValue(), ym.getYear()),
                    revenue != null ? revenue : 0.0));
        }
        return result;
    }

    @Override
    public List<DashboardStatsDto.ThongKeGiaoVien> getThongKeGiaoVien() {
        return giaoVienRepository.findAll().stream().map(gv -> {
            Long soLopDay = lopHocRepository.countByGiaoVienMagv(gv.getMagv());
            return new DashboardStatsDto.ThongKeGiaoVien(
                    gv.getMagv(),
                    gv.getHoten(),
                    soLopDay != null ? soLopDay : 0L,
                    0L,
                    0.0
            );
        }).collect(Collectors.toList());
    }

    @Override
    public List<DashboardStatsDto.HocVienTheoKhoa> getHocVienTheoKhoa() {
        List<Object[]> counts = dangKyKhoaHocRepository.countGroupByKhoaHoc();
        Map<Integer, Long> countByMakh = counts.stream()
                .filter(row -> row[0] != null)
                .collect(Collectors.toMap(
                        row -> (Integer) row[0],
                        row -> ((Number) row[1]).longValue()));
        return khoaHocRepository.findAll().stream().map(kh -> {
            Long soLuong = countByMakh.getOrDefault(kh.getMakh(), 0L);
            return new DashboardStatsDto.HocVienTheoKhoa(
                    kh.getTenkhoahoc(),
                    soLuong
            );
        }).collect(Collectors.toList());
    }
}