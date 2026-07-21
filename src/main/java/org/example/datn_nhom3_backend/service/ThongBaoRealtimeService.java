package org.example.datn_nhom3_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class ThongBaoRealtimeService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * Gửi thông báo đến tất cả admin/staff
     */
    public void notifyAdmins(String tieuDe, String noiDung, String loai) {
        messagingTemplate.convertAndSend("/topic/thongbao", Map.of(
                "tieuDe", tieuDe,
                "noiDung", noiDung,
                "loai", loai,
                "thoiGian", LocalDateTime.now()
        ));
    }

    /**
     * Gửi thông báo đến học viên cụ thể
     */
    public void notifyHocVien(Integer mahv, String tieuDe, String noiDung) {
        messagingTemplate.convertAndSend("/user/" + mahv + "/queue/thongbao", Map.of(
                "tieuDe", tieuDe,
                "noiDung", noiDung,
                "thoiGian", LocalDateTime.now()
        ));
    }
}