package org.example.datn_nhom3_backend.controller;

import org.example.datn_nhom3_backend.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
@CrossOrigin(origins = "http://localhost:5173")
public class UploadController {

    @Value("${file.avatar-dir:D://DATN_NHOM3//DATN_NHOM3_FRONEND//public//avatars}")
    private String avatarDir;

    private final FileStorageService fileStorageService;

    public UploadController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/avatar")
    public Map<String, String> uploadAvatar(@RequestParam("file") MultipartFile file) {
        try {
            Path dir = Paths.get(avatarDir);
            if (!Files.exists(dir)) Files.createDirectories(dir);
            String ext = "";
            String original = file.getOriginalFilename();
            if (original != null && original.contains(".")) ext = original.substring(original.lastIndexOf("."));
            String fileName = UUID.randomUUID().toString() + ext;
            Files.copy(file.getInputStream(), dir.resolve(fileName), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            return Map.of("success", "true", "url", "/avatars/" + fileName);
        } catch (IOException e) {
            throw new RuntimeException("Không thể lưu ảnh: " + e.getMessage(), e);
        }
    }

    @PostMapping("/tin-tuc")
    public Map<String, Object> uploadTinTucImage(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.store(file);
        return Map.of("success", true, "url", "/api/files/" + fileName);
    }
}
