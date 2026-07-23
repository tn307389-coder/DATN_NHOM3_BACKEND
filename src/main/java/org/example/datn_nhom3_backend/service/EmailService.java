package org.example.datn_nhom3_backend.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOtpEmail(String to, String otp) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("tn307389@gmail.com");
        msg.setTo(to);
        msg.setSubject("Mã xác thực OTP - DriveHub");
        msg.setText("Chào bạn,\n\nMã OTP của bạn là: " + otp + "\n\nMã có hiệu lực trong 5 phút.\n\n-- DriveHub");
        mailSender.send(msg);
    }
}
