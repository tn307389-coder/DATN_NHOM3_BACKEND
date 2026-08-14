package org.example.datn_nhom3_backend.config;

import org.example.datn_nhom3_backend.entity.DanhMuc;
import org.example.datn_nhom3_backend.entity.TaiKhoan;
import org.example.datn_nhom3_backend.entity.VaiTro;
import org.example.datn_nhom3_backend.repository.DanhMucRepository;
import org.example.datn_nhom3_backend.repository.TaiKhoanRepository;
import org.example.datn_nhom3_backend.repository.VaiTroRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DataInitializer implements ApplicationRunner {

    private final TaiKhoanRepository taiKhoanRepository;
    private final VaiTroRepository vaiTroRepository;
    private final DanhMucRepository danhMucRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.seed.enabled:true}")
    private boolean seedEnabled;

    public DataInitializer(TaiKhoanRepository taiKhoanRepository,
                           VaiTroRepository vaiTroRepository,
                           DanhMucRepository danhMucRepository,
                           PasswordEncoder passwordEncoder) {
        this.taiKhoanRepository = taiKhoanRepository;
        this.vaiTroRepository = vaiTroRepository;
        this.danhMucRepository = danhMucRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (!seedEnabled) {
            return;
        }
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

        // Danh mục dùng chung
        seedDanhMuc();

        // Nếu chưa có tài khoản nào -> tạo mẫu (dùng khi chạy backend trên DB trống)
        if (taiKhoanRepository.count() == 0) {
            seedAccount("admin", "admin123", "Nguyễn Quản Trị", "ADMIN");
            seedAccount("giaovien", "giangvien123", "Trần Văn Giáo", "GV");
            seedAccount("nhanvien", "nhanvien123", "Lê Thị Nhân", "NV");
        }

        // Di chuyển mật khẩu plaintext (từ file SQL cũ) sang BCrypt một lần
        migratePlaintextPasswords();
    }

    private void seedDanhMuc() {
        if (danhMucRepository.count() > 0) return;
        String[][] data = {
            {"vai-tro", "ADMIN", "Quản trị viên"}, {"vai-tro", "NV", "Nhân viên"},
            {"vai-tro", "GV", "Giáo viên"}, {"vai-tro", "HV", "Học viên"},
            {"hang-bang", "A1", "Hạng A1"}, {"hang-bang", "A2", "Hạng A2"},
            {"hang-bang", "B1", "Hạng B1"}, {"hang-bang", "B2", "Hạng B2"},
            {"hang-bang", "C", "Hạng C"}, {"hang-bang", "D", "Hạng D"},
            {"hang-bang", "E", "Hạng E"},
            {"loai-mon", "LY_THUYET", "Lý thuyết"}, {"loai-mon", "THUC_HANH", "Thực hành"},
            {"loai-mon", "MO_PHONG", "Mô phỏng"},
            {"trang-thai-lop", "DANG_HOC", "Đang học"}, {"trang-thai-lop", "DA_KET_THUC", "Đã kết thúc"},
            {"trang-thai-lop", "TAM_DUNG", "Tạm dừng"},
            {"trang-thai-phong", "DANG_SU_DUNG", "Đang sử dụng"}, {"trang-thai-phong", "TRONG", "Trống"},
            {"trang-thai-phong", "BAO_TRI", "Bảo trì"},
            {"trang-thai-phong-thi", "HOAT_DONG", "Hoạt động"}, {"trang-thai-phong-thi", "BAO_TRI", "Bảo trì"},
            {"trang-thai-phong-thi", "NGUNG_SU_DUNG", "Ngừng sử dụng"},
            {"trang-thai-xe", "DANG_SU_DUNG", "Đang sử dụng"}, {"trang-thai-xe", "TRONG", "Trống"},
            {"trang-thai-xe", "BAO_TRI", "Bảo trì"},
            {"pt-thanh-toan", "TIEN_MAT", "Tiền mặt"}, {"pt-thanh-toan", "CHUYEN_KHOAN", "Chuyển khoản"},
            {"pt-thanh-toan", "VI_DIEN_TU", "Ví điện tử"}, {"pt-thanh-toan", "QR_CODE", "Quét mã QR"},
            {"trang-thai-tt", "DA_THANH_TOAN", "Đã thanh toán"}, {"trang-thai-tt", "CHUA_THANH_TOAN", "Chưa thanh toán"},
            {"trang-thai-tt", "THANH_TOAN_MOT_PHAN", "Thanh toán một phần"},
            {"trang-thai-dk", "CHO_DUYET", "Chờ duyệt"}, {"trang-thai-dk", "DA_DUYET", "Đã duyệt"},
            {"trang-thai-dk", "DANG_HOC", "Đang học"}, {"trang-thai-dk", "HOAN_THANH", "Hoàn thành"},
            {"trang-thai-dk", "DA_HUY", "Đã hủy"},
            {"doi-tuong-tb", "TAT_CA", "Tất cả"}, {"doi-tuong-tb", "HOC_VIEN", "Học viên"},
            {"doi-tuong-tb", "GIAO_VIEN", "Giáo viên"},
        };
        int order = 1;
        for (String[] row : data) {
            DanhMuc dm = new DanhMuc();
            dm.setNhom(row[0]); dm.setMa(row[1]); dm.setTen(row[2]); dm.setThuTu(order++);
            danhMucRepository.save(dm);
        }
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
