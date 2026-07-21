package org.example.datn_nhom3_backend.controller;

import org.example.datn_nhom3_backend.config.JwtUtil;
import org.example.datn_nhom3_backend.entity.TaiKhoan;
import org.example.datn_nhom3_backend.repository.TaiKhoanRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final TaiKhoanRepository taiKhoanRepository;

    public LoginController(AuthenticationManager authenticationManager,
                           JwtUtil jwtUtil,
                           TaiKhoanRepository taiKhoanRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.taiKhoanRepository = taiKhoanRepository;
    }

    @PostMapping("/login")
    public Object login(@RequestBody Map<String, String> request) {
        try {
            String tendangnhap = request.get("tendangnhap");
            String matkhau = request.get("matkhau");

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(tendangnhap, matkhau));

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails);

            Optional<TaiKhoan> taiKhoan = taiKhoanRepository.findByTendangnhap(tendangnhap);

            Map<String, Object> data = new java.util.HashMap<>();
            data.put("token", token);
            data.put("tendangnhap", tendangnhap);
            if (taiKhoan.isPresent()) {
                TaiKhoan tk = taiKhoan.get();
                data.put("hoten", tk.getHoten());
                data.put("vaitro", tk.getVaitro() != null ? tk.getVaitro().getTenVaiTro() : null);
                data.put("maVaiTro", tk.getVaitro() != null ? tk.getVaitro().getMaVaiTro() : null);
                data.put("cccd", tk.getCccd());
            }

            return Map.of(
                    "success", true,
                    "message", "Đăng nhập thành công",
                    "data", data
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "success", false,
                    "message", "Sai tên đăng nhập hoặc mật khẩu"
            ));
        }
    }

    @PostMapping("/logout")
    public Object logout() {
        return Map.of(
                "success", true,
                "message", "Đăng xuất thành công"
        );
    }
}
