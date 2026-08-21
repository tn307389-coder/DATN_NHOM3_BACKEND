package org.example.datn_nhom3_backend.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.example.datn_nhom3_backend.entity.DangKyKhoaHoc;
import org.example.datn_nhom3_backend.entity.HocVien;
import org.example.datn_nhom3_backend.entity.ThanhToan;
import org.example.datn_nhom3_backend.exception.ResourceNotFoundException;
import org.example.datn_nhom3_backend.repository.DangKyKhoaHocRepository;
import org.example.datn_nhom3_backend.repository.ThanhToanRepository;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    private final ThanhToanRepository thanhToanRepository;
    private final DangKyKhoaHocRepository dangKyKhoaHocRepository;

    public PaymentService(ThanhToanRepository thanhToanRepository,
                          DangKyKhoaHocRepository dangKyKhoaHocRepository) {
        this.thanhToanRepository = thanhToanRepository;
        this.dangKyKhoaHocRepository = dangKyKhoaHocRepository;
    }

    public ThanhToan khoiTaoPayment(Integer madk, HocVien hocVien) {
        DangKyKhoaHoc dk = dangKyKhoaHocRepository.findById(madk)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đăng ký khóa học"));

        if (hocVien == null) {
            throw new ResourceNotFoundException("Thiếu thông tin học viên");
        }
        if (!dk.getHocVien().getMahv().equals(hocVien.getMahv())) {
            throw new ResourceNotFoundException("Đăng ký khóa học không thuộc về học viên hiện tại");
        }

        ThanhToan existing = findExistingQrPayment(madk);
        if (existing != null) {
            return existing;
        }

        Double hocPhi = dk.getKhoaHoc() != null
                && dk.getKhoaHoc().getChuongTrinhHoc() != null
                ? dk.getKhoaHoc().getChuongTrinhHoc().getHocphi()
                : 0.0;

        String transactionRef = UUID.randomUUID().toString();
        String tenKhoaHoc = dk.getKhoaHoc() != null ? dk.getKhoaHoc().getTenkhoahoc() : "";

        ThanhToan tt = new ThanhToan();
        tt.setHocVien(dk.getHocVien());
        tt.setDangKyKhoaHoc(dk);
        tt.setSotien(hocPhi);
        tt.setNgaythanhtoan(LocalDate.now());
        tt.setPhuongthuc("QR_CODE");
        tt.setTrangthai("CHUA_THANH_TOAN");
        tt.setTransactionRef(transactionRef);
        tt.setQrData(buildQrPayload(dk, transactionRef, hocPhi, tenKhoaHoc));

        ThanhToan saved = thanhToanRepository.save(tt);
        saved.setPaymentUrl("http://localhost:5173/thanh-toan/" + saved.getMatt());
        return thanhToanRepository.save(saved);
    }

    public ThanhToan getPayment(Integer matt) {
        return thanhToanRepository.findById(matt)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy thanh toán"));
    }

    public ThanhToan xacNhanPayment(Integer matt) {
        ThanhToan tt = getPayment(matt);
        tt.setTrangthai("DA_THANH_TOAN");
        tt.setNgaythanhtoan(LocalDate.now());
        return thanhToanRepository.save(tt);
    }

    public String generateQrBase64(String payload, int width, int height) throws WriterException, IOException {
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(payload, BarcodeFormat.QR_CODE, width, height);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    private ThanhToan findExistingQrPayment(Integer madk) {
        List<ThanhToan> all = thanhToanRepository.findAll();
        for (ThanhToan t : all) {
            if (t.getDangKyKhoaHoc() != null
                    && t.getDangKyKhoaHoc().getMadk().equals(madk)
                    && "QR_CODE".equals(t.getPhuongthuc())) {
                return t;
            }
        }
        return null;
    }

    private String buildQrPayload(DangKyKhoaHoc dk, String transactionRef, Double hocPhi, String tenKhoaHoc) {
        return "DRIVEHUB:" + dk.getMadk() + "|" + transactionRef + "|" + hocPhi + "|" + tenKhoaHoc;
    }
}
