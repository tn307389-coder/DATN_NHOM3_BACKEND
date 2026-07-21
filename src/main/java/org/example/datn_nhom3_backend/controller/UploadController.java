package org.example.datn_nhom3_backend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
@CrossOrigin(origins = "http://localhost:5173")
public class UploadController {

    @Value("${file.avatar-dir:D://DATN_NHOM3//DATN_NHOM3_FRONEND//public//avatars}")
    private String avatarDir;

    @PostMapping("/avatar")
    public java.util.Map<String, String> uploadAvatar(@RequestParam("file") MultipartFile file) {
        try {
            Path dir = Paths.get(avatarDir);
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }
            String ext = "";
            String original = file.getOriginalFilename();
            if (original != null && original.contains(".")) {
                ext = original.substring(original.lastIndexOf("."));
            }
            String fileName = UUID.randomUUID().toString() + ext;
            Files.copy(file.getInputStream(), dir.resolve(fileName), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            return java.util.Map.of("success", "true", "url", "/avatars/" + fileName);
        } catch (IOException e) {
            throw new RuntimeException("Không thể lưu ảnh: " + e.getMessage(), e);
        }
    }
}
