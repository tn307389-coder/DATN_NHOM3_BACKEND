package org.example.datn_nhom3_backend.dto;

import java.time.LocalDate;

public class KhoaHocDto {
    private Integer makh;
    private String tenkhoahoc;
    private Integer macth;
    private String tencth;
    private String hangBang;
    private LocalDate ngaybatdau;
    private LocalDate ngayketthuc;
    private String trangthai;
    private long soLuongHocVien;

    public Integer getMakh() { return makh; }
    public void setMakh(Integer makh) { this.makh = makh; }
    public String getTenkhoahoc() { return tenkhoahoc; }
    public void setTenkhoahoc(String tenkhoahoc) { this.tenkhoahoc = tenkhoahoc; }
    public Integer getMacth() { return macth; }
    public void setMacth(Integer macth) { this.macth = macth; }
    public String getTencth() { return tencth; }
    public void setTencth(String tencth) { this.tencth = tencth; }
    public String getHangBang() { return hangBang; }
    public void setHangBang(String hangBang) { this.hangBang = hangBang; }
    public LocalDate getNgaybatdau() { return ngaybatdau; }
    public void setNgaybatdau(LocalDate ngaybatdau) { this.ngaybatdau = ngaybatdau; }
    public LocalDate getNgayketthuc() { return ngayketthuc; }
    public void setNgayketthuc(LocalDate ngayketthuc) { this.ngayketthuc = ngayketthuc; }
    public String getTrangthai() { return trangthai; }
    public void setTrangthai(String trangthai) { this.trangthai = trangthai; }
    public long getSoLuongHocVien() { return soLuongHocVien; }
    public void setSoLuongHocVien(long soLuongHocVien) { this.soLuongHocVien = soLuongHocVien; }
}
