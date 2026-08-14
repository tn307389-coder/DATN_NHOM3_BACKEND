package org.example.datn_nhom3_backend.controller;

import org.example.datn_nhom3_backend.dto.EmailNotificationRequest;
import org.example.datn_nhom3_backend.entity.DangKyKhoaHoc;
import org.example.datn_nhom3_backend.entity.HocVien;
import org.example.datn_nhom3_backend.entity.KhoaHoc;
import org.example.datn_nhom3_backend.repository.DangKyKhoaHocRepository;
import org.example.datn_nhom3_backend.repository.KhoaHocRepository;
import org.example.datn_nhom3_backend.service.EmailService;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/email")
@CrossOrigin(origins = "http://localhost:5173")
public class EmailController {

    private final EmailService emailService;
    private final DangKyKhoaHocRepository dkRepository;
    private final KhoaHocRepository khoaHocRepository;

    public EmailController(EmailService emailService,
                           DangKyKhoaHocRepository dkRepository,
                           KhoaHocRepository khoaHocRepository) {
        this.emailService = emailService;
        this.dkRepository = dkRepository;
        this.khoaHocRepository = khoaHocRepository;
    }

    // Gửi thông báo kỳ đóng học phần tới email các học viên
    // makh = null -> tất cả khóa học đang hoạt động; makh != null -> lọc theo khóa học
    @PostMapping("/thong-bao-ky-dong")
    public Map<String, Object> thongBaoKyDong(@RequestBody EmailNotificationRequest req) {
        String hanChot = req.getHanChot();
        String noiDung = req.getNoiDung();

        if ((noiDung == null || noiDung.isBlank()) && (hanChot == null || hanChot.isBlank())) {
            return Map.of("success", false, "message", "Vui lòng nhập hạn chốt hoặc nội dung thông báo");
        }

        // Lọc các đăng ký khóa học theo makh (nếu có)
        List<DangKyKhoaHoc> dks = dkRepository.findAll().stream()
                .filter(dk -> !dk.getHocVien().getEmail().isBlank())
                .filter(dk -> !"ĐÃ HỦY".equalsIgnoreCase(dk.getTrangthai())
                        && !"Đã hủy".equalsIgnoreCase(dk.getTrangthai())
                        && !"HOÀN THÀNH".equalsIgnoreCase(dk.getTrangthai())
                        && !"Hoàn thành".equalsIgnoreCase(dk.getTrangthai()))
                .filter(dk -> req.getMakh() == null || (dk.getKhoaHoc() != null && dk.getKhoaHoc().getMakh().equals(req.getMakh())))
                .collect(Collectors.toList());

        if (dks.isEmpty()) {
            return Map.of("success", false, "message", "Không có học viên nào để gửi thông báo");
        }

        // Gom theo email (mỗi học viên nhận 1 email, gom các khóa học của họ)
        Map<String, HocVien> hvByEmail = new LinkedHashMap<>();
        Map<String, List<String>> khoaHocByEmail = new LinkedHashMap<>();
        for (DangKyKhoaHoc dk : dks) {
            HocVien hv = dk.getHocVien();
            String email = hv.getEmail().trim();
            hvByEmail.putIfAbsent(email, hv);
            String tenKhoaHoc = dk.getKhoaHoc() != null ? dk.getKhoaHoc().getTenkhoahoc() : "khóa học";
            khoaHocByEmail.computeIfAbsent(email, k -> new ArrayList<>()).add(tenKhoaHoc);
        }

        int sent = 0;
        List<String> failed = new ArrayList<>();
        for (Map.Entry<String, HocVien> entry : hvByEmail.entrySet()) {
            String email = entry.getKey();
            HocVien hv = entry.getValue();
            String tenKhoaHoc = String.join(", ", khoaHocByEmail.get(email));
            try {
                emailService.sendSemesterNotification(email, hv.getHoten(), tenKhoaHoc, hanChot, noiDung);
                sent++;
            } catch (Exception e) {
                failed.add(email + " (" + e.getMessage() + ")");
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("sent", sent);
        result.put("failedCount", failed.size());
        result.put("failed", failed);
        result.put("recipients", new ArrayList<>(hvByEmail.keySet()));
        return result;
    }

    // Xem trước danh sách học viên nhận thông báo (makh = null -> tất cả)
    @GetMapping("/thong-bao-ky-dong/preview")
    public Map<String, Object> preview(@RequestParam(required = false) Integer makh) {
        List<DangKyKhoaHoc> dks = dkRepository.findAll().stream()
                .filter(dk -> dk.getHocVien() != null && dk.getHocVien().getEmail() != null && !dk.getHocVien().getEmail().isBlank())
                .filter(dk -> !"ĐÃ HỦY".equalsIgnoreCase(dk.getTrangthai())
                        && !"Đã hủy".equalsIgnoreCase(dk.getTrangthai())
                        && !"HOÀN THÀNH".equalsIgnoreCase(dk.getTrangthai())
                        && !"Hoàn thành".equalsIgnoreCase(dk.getTrangthai()))
                .filter(dk -> makh == null || (dk.getKhoaHoc() != null && dk.getKhoaHoc().getMakh().equals(makh)))
                .collect(Collectors.toList());

        Map<String, String> hvByEmail = new LinkedHashMap<>();
        for (DangKyKhoaHoc dk : dks) {
            String email = dk.getHocVien().getEmail().trim();
            hvByEmail.putIfAbsent(email, dk.getHocVien().getHoten());
        }

        List<Map<String, String>> recipients = hvByEmail.entrySet().stream()
                .map(e -> Map.of("email", e.getKey(), "hoten", e.getValue()))
                .collect(Collectors.toList());

        return Map.of("success", true, "count", recipients.size(), "recipients", recipients);
    }
}
