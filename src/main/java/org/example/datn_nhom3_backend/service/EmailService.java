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

    public void sendRegistrationApproved(String to, String hoten, String tenKhoaHoc) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("tn307389@gmail.com");
        msg.setTo(to);
        msg.setSubject("[DriveHub] Đăng ký học đã được duyệt");
        String body = "Kính gửi " + hoten + ",\n\n"
                + "Bạn đã được duyệt đăng ký học ở trung tâm đào tạo lái xe DriveHub"
                + (tenKhoaHoc != null && !tenKhoaHoc.isBlank() ? " (khóa " + tenKhoaHoc + ")" : "")
                + ".\n\n"
                + "Trung tâm sẽ liên hệ với bạn trong thời gian sớm nhất để hoàn tất thủ tục nhập học.\n\n"
                + "Trân trọng,\nDriveHub - Trung tâm đào tạo lái xe";
        msg.setText(body);
        mailSender.send(msg);
    }

    public void sendRegistrationRejected(String to, String hoten, String tenKhoaHoc) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("tn307389@gmail.com");
        msg.setTo(to);
        msg.setSubject("[DriveHub] Thông báo đăng ký không hợp lệ");
        String body = "Kính gửi " + hoten + ",\n\n"
                + "Đăng ký khóa " + (tenKhoaHoc != null && !tenKhoaHoc.isBlank() ? tenKhoaHoc : "học")
                + " của bạn không hợp lệ và đã bị từ chối.\n\n"
                + "Vui lòng kiểm tra lại thông tin đăng ký hoặc liên hệ trung tâm đào tạo lái xe DriveHub để được hỗ trợ.\n\n"
                + "Trân trọng,\nDriveHub - Trung tâm đào tạo lái xe";
        msg.setText(body);
        mailSender.send(msg);
    }

    public void sendAccountCreatedEmail(String to, String hoten, String tendangnhap, String matkhau) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("tn307389@gmail.com");
        msg.setTo(to);
        msg.setSubject("[DriveHub] Tài khoản học viên của bạn");
        String body = "Kính gửi " + hoten + ",\n\n"
                + "Chúc mừng bạn đã đăng ký thành công tại trung tâm đào tạo lái xe DriveHub.\n\n"
                + "Tài khoản học viên của bạn đã được cấp:\n"
                + "  - Tên đăng nhập: " + tendangnhap + "\n"
                + "  - Mật khẩu: " + matkhau + "\n\n"
                + "Vui lòng đăng nhập hệ thống và đổi mật khẩu ngay sau lần đăng nhập đầu tiên.\n\n"
                + "Trân trọng,\nDriveHub - Trung tâm đào tạo lái xe";
        msg.setText(body);
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
