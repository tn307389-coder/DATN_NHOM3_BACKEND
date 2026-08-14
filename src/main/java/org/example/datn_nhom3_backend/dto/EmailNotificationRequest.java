package org.example.datn_nhom3_backend.dto;

public class EmailNotificationRequest {
    private Integer makh;
    private String hanChot;
    private String noiDung;

    public Integer getMakh() { return makh; }
    public void setMakh(Integer makh) { this.makh = makh; }
    public String getHanChot() { return hanChot; }
    public void setHanChot(String hanChot) { this.hanChot = hanChot; }
    public String getNoiDung() { return noiDung; }
    public void setNoiDung(String noiDung) { this.noiDung = noiDung; }
}
