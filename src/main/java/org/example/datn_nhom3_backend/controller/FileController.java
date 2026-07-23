package org.example.datn_nhom3_backend.controller;

import org.example.datn_nhom3_backend.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "http://localhost:5173")
public class FileController {

    private final FileStorageService fileStorageService;

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
        Path path = Paths.get(uploadDir).resolve(fileName).normalize();
        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            return ResponseEntity.notFound().build();
        }
        Resource resource = fileStorageService.load(fileName);
        String contentType = "application/octet-stream";
        try {
            contentType = Files.probeContentType(path);
            if (contentType == null) contentType = "application/octet-stream";
        } catch (Exception ignored) {
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
