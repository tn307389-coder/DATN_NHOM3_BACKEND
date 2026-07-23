package org.example.datn_nhom3_backend.controller;

import org.example.datn_nhom3_backend.dto.DashboardStatsDto;
import org.example.datn_nhom3_backend.service.ThongKeService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:5173")
public class ThongKeController {

    private final ThongKeService thongKeService;

    public ThongKeController(ThongKeService thongKeService) {
        this.thongKeService = thongKeService;
    }

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDto> getStats() {
        return ResponseEntity.ok(thongKeService.getDashboardStats());
    }

    @GetMapping("/doanh-thu-theo-thang")
    public ResponseEntity<List<DashboardStatsDto.DoanhThuTheoThang>> getDoanhThuTheoThang(
            @RequestParam(defaultValue = "6") int soThang) {
        return ResponseEntity.ok(thongKeService.getDoanhThuTheoThang(soThang));
    }

    @GetMapping("/thong-ke-giao-vien")
    public ResponseEntity<List<DashboardStatsDto.ThongKeGiaoVien>> getThongKeGiaoVien() {
        return ResponseEntity.ok(thongKeService.getThongKeGiaoVien());
    }

    @GetMapping("/hoc-vien-theo-khoa")
    public ResponseEntity<List<DashboardStatsDto.HocVienTheoKhoa>> getHocVienTheoKhoa() {
        return ResponseEntity.ok(thongKeService.getHocVienTheoKhoa());
    }

    @GetMapping("/doanh-thu")
    public ResponseEntity<Double> getDoanhThu(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tuNgay,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate denNgay) {
        return ResponseEntity.ok(thongKeService.getDoanhThuTheoKhoangThoiGian(tuNgay, denNgay));
    }

    @GetMapping("/ty-le-dau-thi")
    public ResponseEntity<Double> getTyLeDauThi() {
        return ResponseEntity.ok(thongKeService.getTyLeDauThi());
    }

    @GetMapping("/test-vn")
    public ResponseEntity<Map<String, String>> testVn() {
        return ResponseEntity.ok(Map.of(
            "tiengViet", "Quản trị viên Nguyễn Quản Trị Đăng nhập thành công",
            "english", "Hello World"
        ));
    }
}