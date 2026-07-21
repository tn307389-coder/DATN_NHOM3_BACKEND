package org.example.datn_nhom3_backend.config;

import org.example.datn_nhom3_backend.entity.TaiKhoan;
import org.example.datn_nhom3_backend.repository.TaiKhoanRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final TaiKhoanRepository taiKhoanRepository;

    public CustomUserDetailsService(TaiKhoanRepository taiKhoanRepository) {
        this.taiKhoanRepository = taiKhoanRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String tendangnhap) throws UsernameNotFoundException {
        TaiKhoan tk = taiKhoanRepository.findByTendangnhap(tendangnhap)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy tài khoản: " + tendangnhap));

        String role = tk.getVaitro() != null ? tk.getVaitro().getMaVaiTro() : "UNKNOWN";
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));

        return User.builder()
                .username(tk.getTendangnhap())
                .password(tk.getMatkhau())
                .authorities(authorities)
                .disabled(!"ACTIVE".equals(tk.getTrangthai()))
                .build();
    }
}
