package org.example.datn_nhom3_backend.config;

import org.example.datn_nhom3_backend.entity.TaiKhoan;
import org.example.datn_nhom3_backend.entity.VaiTro;
import org.example.datn_nhom3_backend.repository.TaiKhoanRepository;
import org.example.datn_nhom3_backend.repository.VaiTroRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DataInitializer implements ApplicationRunner {

    private final TaiKhoanRepository taiKhoanRepository;
    private final VaiTroRepository vaiTroRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(TaiKhoanRepository taiKhoanRepository,
                           VaiTroRepository vaiTroRepository,
                           PasswordEncoder passwordEncoder) {
        this.taiKhoanRepository = taiKhoanRepository;
        this.vaiTroRepository = vaiTroRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        // Các mã vai trò chuẩn của hệ thống
        Map<String, String> roles = Map.of(
                "ADMIN", "Quản trị viên",
                "GV", "Giáo viên",
                "HV", "Học viên",
                "NV", "Nhân viên"
        );

        for (Map.Entry<String, String> entry : roles.entrySet()) {
            String ma = entry.getKey();
            String ten = entry.getValue();
            if (vaiTroRepository.findByMaVaiTro(ma).isEmpty()) {
                vaiTroRepository.save(VaiTro.builder()
                        .maVaiTro(ma)
                        .tenVaiTro(ten)
                        .moTa("Vai trò " + ten)
                        .trangThai(true)
                        .build());
            }
        }

        // Nếu chưa có tài khoản nào -> tạo mẫu (dùng khi chạy backend trên DB trống)
        if (taiKhoanRepository.count() == 0) {
            seedAccount("admin", "admin123", "Nguyễn Quản Trị", "ADMIN");
            seedAccount("giaovien", "giangvien123", "Trần Văn Giáo", "GV");
            seedAccount("nhanvien", "nhanvien123", "Lê Thị Nhân", "NV");
        }

        // Di chuyển mật khẩu plaintext (từ file SQL cũ) sang BCrypt một lần
        migratePlaintextPasswords();
    }

    private void seedAccount(String tendangnhap, String matkhau, String hoten, String maVaiTro) {
        if (taiKhoanRepository.findByTendangnhap(tendangnhap).isPresent()) {
            return;
        }
        VaiTro vaiTro = vaiTroRepository.findByMaVaiTro(maVaiTro).orElseThrow();
        TaiKhoan tk = new TaiKhoan();
        tk.setTendangnhap(tendangnhap);
        tk.setMatkhau(passwordEncoder.encode(matkhau));
        tk.setHoten(hoten);
        tk.setVaitro(vaiTro);
        tk.setTrangthai("ACTIVE");
        taiKhoanRepository.save(tk);
    }

    private void migratePlaintextPasswords() {
        List<TaiKhoan> all = taiKhoanRepository.findAll();
        for (TaiKhoan tk : all) {
            String mk = tk.getMatkhau();
            if (mk == null || !mk.startsWith("$2")) {
                tk.setMatkhau(passwordEncoder.encode(mk == null ? "123456" : mk));
                taiKhoanRepository.save(tk);
            }
        }
    }
}
