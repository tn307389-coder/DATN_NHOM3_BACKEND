package org.example.datn_nhom3_backend.dto;

import java.util.List;

public class ThongKeHocVienGiaoVien {
    private Integer magv;
    private String hotenGiaoVien;
    private long soHocVienQuaLop;
    private long soHocVienQuaPhanCong;
    private long tongSoHocVien;
    private List<HocVienTomTat> danhSachHocVien;

    public ThongKeHocVienGiaoVien() {
    }

    public ThongKeHocVienGiaoVien(
            Integer magv,
            String hotenGiaoVien,
            long soHocVienQuaLop,
            long soHocVienQuaPhanCong,
            long tongSoHocVien,
            List<HocVienTomTat> danhSachHocVien) {
        this.magv = magv;
        this.hotenGiaoVien = hotenGiaoVien;
        this.soHocVienQuaLop = soHocVienQuaLop;
        this.soHocVienQuaPhanCong = soHocVienQuaPhanCong;
        this.tongSoHocVien = tongSoHocVien;
        this.danhSachHocVien = danhSachHocVien;
    }

    public Integer getMagv() {
        return magv;
    }

    public void setMagv(Integer magv) {
        this.magv = magv;
    }

    public String getHotenGiaoVien() {
        return hotenGiaoVien;
    }

    public void setHotenGiaoVien(String hotenGiaoVien) {
        this.hotenGiaoVien = hotenGiaoVien;
    }

    public long getSoHocVienQuaLop() {
        return soHocVienQuaLop;
    }

    public void setSoHocVienQuaLop(long soHocVienQuaLop) {
        this.soHocVienQuaLop = soHocVienQuaLop;
    }

    public long getSoHocVienQuaPhanCong() {
        return soHocVienQuaPhanCong;
    }

    public void setSoHocVienQuaPhanCong(long soHocVienQuaPhanCong) {
        this.soHocVienQuaPhanCong = soHocVienQuaPhanCong;
    }

    public long getTongSoHocVien() {
        return tongSoHocVien;
    }

    public void setTongSoHocVien(long tongSoHocVien) {
        this.tongSoHocVien = tongSoHocVien;
    }

    public List<HocVienTomTat> getDanhSachHocVien() {
        return danhSachHocVien;
    }

    public void setDanhSachHocVien(List<HocVienTomTat> danhSachHocVien) {
        this.danhSachHocVien = danhSachHocVien;
    }

    public static class HocVienTomTat {
        private Integer mahv;
        private String hoten;
        private String sodienthoai;
        private String nguon;

        public HocVienTomTat() {
        }

        public HocVienTomTat(Integer mahv, String hoten, String sodienthoai, String nguon) {
            this.mahv = mahv;
            this.hoten = hoten;
            this.sodienthoai = sodienthoai;
            this.nguon = nguon;
        }

        public Integer getMahv() {
            return mahv;
        }

        public void setMahv(Integer mahv) {
            this.mahv = mahv;
        }

        public String getHoten() {
            return hoten;
        }

        public void setHoten(String hoten) {
            this.hoten = hoten;
        }

        public String getSodienthoai() {
            return sodienthoai;
        }

        public void setSodienthoai(String sodienthoai) {
            this.sodienthoai = sodienthoai;
        }

        public String getNguon() {
            return nguon;
        }

        public void setNguon(String nguon) {
            this.nguon = nguon;
        }
    }
}
