package org.example.datn_nhom3_backend.controller;

import org.example.datn_nhom3_backend.entity.AnhChoDuyet;
import org.example.datn_nhom3_backend.entity.TaiKhoan;
import org.example.datn_nhom3_backend.repository.AnhChoDuyetRepository;
import org.example.datn_nhom3_backend.repository.TaiKhoanRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/anh-cho-duyet")
@CrossOrigin(origins = "http://localhost:5173")
public class AnhChoDuyetController {

    private final AnhChoDuyetRepository repository;
    private final TaiKhoanRepository taiKhoanRepository;

    @Value("${file.avatar-dir:D://DATN_NHOM3//DATN_NHOM3_FRONEND//public//avatars}")
    private String avatarDir;

    public AnhChoDuyetController(AnhChoDuyetRepository repository, TaiKhoanRepository taiKhoanRepository) {
        this.repository = repository;
        this.taiKhoanRepository = taiKhoanRepository;
    }

    private Integer currentMatk() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth != null ? auth.getName() : null;
        if (username == null || "anonymousUser".equals(username)) return null;
        return taiKhoanRepository.findByTendangnhap(username)
                .map(TaiKhoan::getMatk).orElse(null);
    }

    // Học viên/GV/NV/ADMIN upload ảnh -> tạo bản ghi CHỜ DUYỆT (chưa lưu vào tai_khoan)
    @PostMapping
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        Integer matk = currentMatk();
        if (matk == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Chưa đăng nhập"));
        try {
            Path dir = Paths.get(avatarDir);
            if (!Files.exists(dir)) Files.createDirectories(dir);
            String ext = "";
            String original = file.getOriginalFilename();
            if (original != null && original.contains(".")) ext = original.substring(original.lastIndexOf("."));
            String fileName = UUID.randomUUID().toString() + ext;
            Files.copy(file.getInputStream(), dir.resolve(fileName), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

            AnhChoDuyet a = new AnhChoDuyet();
            TaiKhoan tk = taiKhoanRepository.findById(matk).orElse(null);
            if (tk == null) return ResponseEntity.status(400).body(Map.of("success", false, "message", "Tài khoản không tồn tại"));
            a.setTaiKhoan(tk);
            a.setUrl("/avatars/" + fileName);
            a.setTrangthai("CHO_DUYET");
            a.setNgaytao(java.time.LocalDateTime.now());
            return ResponseEntity.ok(repository.save(a));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of("success", false, "message", "Lưu ảnh thất bại: " + e.getMessage()));
        }
    }

    // ADMIN/NV: danh sách ảnh chờ duyệt (mặc định CHO_DUYET)
    @GetMapping
    public List<AnhChoDuyet> list(@RequestParam(value = "trangthai", defaultValue = "CHO_DUYET") String trangthai) {
        return repository.findByTrangthai(trangthai);
    }

    // User: lấy ảnh chờ duyệt của chính mình (mới nhất)
    @GetMapping("/cua-toi")
    public ResponseEntity<?> cuaToi() {
        Integer matk = currentMatk();
        if (matk == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Chưa đăng nhập"));
        return repository.findByTaiKhoan_Matk(matk).stream()
                .filter(a -> !"DA_DUYET".equals(a.getTrangthai()))
                .reduce((a, b) -> b)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.ok(null));
    }

    // ADMIN/NV: duyệt -> cập nhật ảnh đại diện của tài khoản
    @PutMapping("/{id}/duyet")
    public ResponseEntity<?> duyet(@PathVariable Integer id) {
        return repository.findById(id).map(a -> {
            TaiKhoan tk = taiKhoanRepository.findById(a.getTaiKhoan().getMatk()).orElse(null);
            if (tk != null) {
                tk.setAnh(a.getUrl());
                taiKhoanRepository.save(tk);
            }
            a.setTrangthai("DA_DUYET");
            return ResponseEntity.ok(repository.save(a));
        }).orElse(ResponseEntity.notFound().build());
    }

    // ADMIN/NV: từ chối -> đánh dấu và xóa file
    @PutMapping("/{id}/tu-choi")
    public ResponseEntity<?> tuChoi(@PathVariable Integer id) {
        return repository.findById(id).map(a -> {
            a.setTrangthai("TU_CHOI");
            // xóa file vật lý
            try {
                String fileName = a.getUrl() != null ? a.getUrl().replace("/avatars/", "") : "";
                if (fileName != null && !fileName.isBlank()) {
                    Files.deleteIfExists(Paths.get(avatarDir).resolve(fileName));
                }
            } catch (IOException ignored) {}
            return ResponseEntity.ok(repository.save(a));
        }).orElse(ResponseEntity.notFound().build());
    }
}
