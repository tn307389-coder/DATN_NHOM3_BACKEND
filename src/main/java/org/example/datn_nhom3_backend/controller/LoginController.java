package org.example.datn_nhom3_backend.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.example.datn_nhom3_backend.config.JwtUtil;
import org.example.datn_nhom3_backend.entity.TaiKhoan;
import org.example.datn_nhom3_backend.entity.VaiTro;
import org.example.datn_nhom3_backend.repository.TaiKhoanRepository;
import org.example.datn_nhom3_backend.repository.VaiTroRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final TaiKhoanRepository taiKhoanRepository;
    private final VaiTroRepository vaiTroRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${google.client-id}")
    private String googleClientId;

    public LoginController(AuthenticationManager authenticationManager,
                           JwtUtil jwtUtil,
                           TaiKhoanRepository taiKhoanRepository,
                           VaiTroRepository vaiTroRepository,
                           PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.taiKhoanRepository = taiKhoanRepository;
        this.vaiTroRepository = vaiTroRepository;
        this.passwordEncoder = passwordEncoder;
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

    @PostMapping("/login/google")
    public Object loginGoogle(@RequestBody Map<String, String> request) {
        try {
            String idTokenString = request.get("idToken");
            if (idTokenString == null || idTokenString.isBlank()) {
                return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Thiếu idToken"));
            }

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(), new GsonFactory())
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("success", false, "message", "Token Google không hợp lệ"));
            }

            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();
            String name = (String) payload.get("name");
            String googleId = payload.getSubject();
            String picture = (String) payload.get("picture");

            // Tìm tài khoản theo googleId hoặc email
            Optional<TaiKhoan> existing = taiKhoanRepository.findByGoogleId(googleId);
            if (existing.isEmpty() && email != null) {
                existing = taiKhoanRepository.findByEmail(email);
            }

            TaiKhoan tk;
            if (existing.isPresent()) {
                tk = existing.get();
                tk.setGoogleId(googleId);
                taiKhoanRepository.save(tk);
            } else {
                // Tạo tài khoản mới
                VaiTro hvRole = vaiTroRepository.findByMaVaiTro("HV")
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy vai trò HV"));

                String username = (email != null) ? email.split("@")[0] : "user" + UUID.randomUUID().toString().substring(0, 6);
                // Đảm bảo username không trùng
                if (taiKhoanRepository.findByTendangnhap(username).isPresent()) {
                    username = username + "_" + UUID.randomUUID().toString().substring(0, 4);
                }

                tk = new TaiKhoan();
                tk.setTendangnhap(username);
                tk.setMatkhau(passwordEncoder.encode(UUID.randomUUID().toString()));
                tk.setHoten(name != null ? name : email);
                tk.setEmail(email);
                tk.setGoogleId(googleId);
                tk.setVaitro(hvRole);
                tk.setTrangthai("ACTIVE");
                taiKhoanRepository.save(tk);
            }

            // Tạo JWT token
            org.springframework.security.core.userdetails.User userDetails =
                    new org.springframework.security.core.userdetails.User(
                            tk.getTendangnhap(), tk.getMatkhau(),
                            java.util.List.of(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + tk.getVaitro().getMaVaiTro()))
                    );
            String token = jwtUtil.generateToken(userDetails);

            Map<String, Object> data = new java.util.HashMap<>();
            data.put("token", token);
            data.put("tendangnhap", tk.getTendangnhap());
            data.put("hoten", tk.getHoten());
            data.put("vaitro", tk.getVaitro() != null ? tk.getVaitro().getTenVaiTro() : null);
            data.put("maVaiTro", tk.getVaitro() != null ? tk.getVaitro().getMaVaiTro() : null);
            data.put("email", tk.getEmail());
            if (picture != null) data.put("anh", picture);

            return Map.of("success", true, "message", "Đăng nhập bằng Google thành công", "data", data);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("success", false, "message", "Lỗi xác thực Google: " + e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public Object logout() {
        return Map.of("success", true, "message", "Đăng xuất thành công");
    }
}
