package org.example.datn_nhom3_backend.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardStatsDto {
    private Long tongHocVien;
    private Long tongGiaoVien;
    private Long tongKhoaHocDangMo;
    private Double tongDoanhThu;
    private Double doanhThuThangNay;
    private Double doanhThuThangTruoc;
    private Long soHocVienDauKy;
    private Long soHocVienMoiThangNay;
    private Double tyLeDauThi;
    private Long soCaThiSapDienRa;
    private List<DoanhThuTheoThang> doanhThuTheoThangs;
    private List<ThongKeGiaoVien> thongKeGiaoViens;
    private List<HocVienTheoKhoa> hocVienTheoKhoas;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DoanhThuTheoThang {
        private String thang;
        private Double soTien;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ThongKeGiaoVien {
        private Integer magv;
        private String tenGiaoVien;
        private Long soLopDay;
        private Long soHocVien;
        private Double tyLeDau;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HocVienTheoKhoa {
        private String tenKhoaHoc;
        private Long soLuong;
    }
}
