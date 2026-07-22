package org.example.datn_nhom3_backend.dto;

import java.time.LocalDate;
import java.util.List;

public class BatchDiemDanhRequest {
    private Integer malich;
    private LocalDate ngaydiemdanh;
    private List<DiemDanhItem> danhSach;

    public Integer getMalich() { return malich; }
    public void setMalich(Integer malich) { this.malich = malich; }
    public LocalDate getNgaydiemdanh() { return ngaydiemdanh; }
    public void setNgaydiemdanh(LocalDate ngaydiemdanh) { this.ngaydiemdanh = ngaydiemdanh; }
    public List<DiemDanhItem> getDanhSach() { return danhSach; }
    public void setDanhSach(List<DiemDanhItem> danhSach) { this.danhSach = danhSach; }

    public static class DiemDanhItem {
        private Integer mahv;
        private String trangthai;
        private String ghichu;

        public Integer getMahv() { return mahv; }
        public void setMahv(Integer mahv) { this.mahv = mahv; }
        public String getTrangthai() { return trangthai; }
        public void setTrangthai(String trangthai) { this.trangthai = trangthai; }
        public String getGhichu() { return ghichu; }
        public void setGhichu(String ghichu) { this.ghichu = ghichu; }
    }
}
