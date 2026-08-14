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

    public void sendSemesterNotification(String to, String hoten, String tenKhoaHoc, String hanChot, String noiDung) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("tn307389@gmail.com");
        msg.setTo(to);
        msg.setSubject("[DriveHub] Thông báo kỳ đóng học phần - " + tenKhoaHoc);
        String body = "Kính gửi " + hoten + ",\n\n"
                + "Kỳ đóng học phần khóa " + tenKhoaHoc + " sắp kết thúc"
                + (hanChot != null && !hanChot.isBlank() ? " vào ngày " + hanChot : "")
                + ".\n\n"
                + (noiDung != null && !noiDung.isBlank()
                        ? noiDung + "\n\n"
                        : "Vui lòng hoàn thành học phí và hoàn tất các thủ tục trước thời hạn trên.\n\n")
                + "Trân trọng,\nDriveHub - Trung tâm đào tạo lái xe";
        msg.setText(body);
        mailSender.send(msg);
    }
}
