package org.example.datn_nhom3_backend.service;

import org.example.datn_nhom3_backend.entity.OtpVerification;
import org.example.datn_nhom3_backend.repository.OtpVerificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class OtpService {

    private final OtpVerificationRepository repository;
    private final EmailService emailService;
    private static final SecureRandom RANDOM = new SecureRandom();

    public OtpService(OtpVerificationRepository repository, EmailService emailService) {
        this.repository = repository;
        this.emailService = emailService;
    }

    @Transactional
    public void sendOtp(String email) {
        repository.deleteByEmail(email);
        String otp = String.valueOf(100000 + RANDOM.nextInt(900000));
        OtpVerification ov = new OtpVerification();
        ov.setEmail(email);
        ov.setOtp(otp);
        ov.setExpiry(LocalDateTime.now().plusMinutes(5));
        repository.save(ov);
        emailService.sendOtpEmail(email, otp);
    }

    public boolean verifyOtp(String email, String otp) {
        var opt = repository.findTopByEmailAndVerifiedFalseOrderByCreatedAtDesc(email);
        if (opt.isEmpty()) return false;
        var ov = opt.get();
        if (ov.getExpiry().isBefore(LocalDateTime.now())) return false;
        if (!ov.getOtp().equals(otp)) return false;
        ov.setVerified(true);
        repository.save(ov);
        return true;
    }

    public boolean isEmailVerified(String email) {
        var opt = repository.findTopByEmailAndVerifiedFalseOrderByCreatedAtDesc(email);
        return opt.isEmpty();
    }

    // Kiểm tra email đã từng xác thực OTP thành công (bản ghi mới nhất là verified)
    public boolean isOtpVerifiedForEmail(String email) {
        var opt = repository.findTopByEmailOrderByCreatedAtDesc(email);
        return opt.isPresent() && opt.get().isVerified();
    }
}
