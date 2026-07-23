package org.example.datn_nhom3_backend.controller;

import org.example.datn_nhom3_backend.dto.TaiKhoanRequest;
import org.example.datn_nhom3_backend.entity.TaiKhoan;
import org.example.datn_nhom3_backend.exception.ResourceNotFoundException;
import org.example.datn_nhom3_backend.service.TaiKhoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tai-khoan")
@CrossOrigin(origins = "http://localhost:5173")
public class TaiKhoanController {

    private final TaiKhoanService service;

    public TaiKhoanController(TaiKhoanService service) {
        this.service = service;
    }

    @GetMapping("/me")
    public ResponseEntity<TaiKhoan> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication != null ? authentication.getName() : null;
        if (username == null || "anonymousUser".equals(username)) {
            throw new ResourceNotFoundException("Chưa đăng nhập");
        }
        Optional<TaiKhoan> taiKhoan = service.findByTendangnhap(username);
        if (taiKhoan.isEmpty()) {
            throw new ResourceNotFoundException("Không tìm thấy tài khoản: " + username);
        }
        TaiKhoan tk = taiKhoan.get();
        tk.setMatkhau(null);
        return ResponseEntity.ok(tk);
    }

    @GetMapping
    public List<TaiKhoan> getAll() {
        return service.getAll();
    }

    @PutMapping("/me")
    public ResponseEntity<TaiKhoan> updateCurrentUser(@RequestBody TaiKhoanRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication != null ? authentication.getName() : null;
        if (username == null || "anonymousUser".equals(username)) {
            throw new ResourceNotFoundException("Chưa đăng nhập");
        }
        TaiKhoan tk = service.updateCurrentUser(username, request);
        tk.setMatkhau(null);
        return ResponseEntity.ok(tk);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaiKhoan> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tài khoản với ID: " + id));
    }

    @PostMapping
    public TaiKhoan create(@RequestBody TaiKhoanRequest request) {
        return service.createAccount(request);
    }

    @PutMapping("/{id}")
    public TaiKhoan update(@PathVariable Integer id, @RequestBody TaiKhoanRequest request) {
        return service.updateAccount(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
