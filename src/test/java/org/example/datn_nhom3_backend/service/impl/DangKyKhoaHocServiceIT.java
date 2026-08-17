package org.example.datn_nhom3_backend.service.impl;

import org.example.datn_nhom3_backend.entity.DangKyKhoaHoc;
import org.example.datn_nhom3_backend.entity.HoSoHocVien;
import org.example.datn_nhom3_backend.entity.HocVien;
import org.example.datn_nhom3_backend.entity.KhoaHoc;
import org.example.datn_nhom3_backend.entity.TaiKhoan;
import org.example.datn_nhom3_backend.entity.VaiTro;
import org.example.datn_nhom3_backend.repository.DangKyKhoaHocRepository;
import org.example.datn_nhom3_backend.repository.HoSoHocVienRepository;
import org.example.datn_nhom3_backend.repository.HocVienRepository;
import org.example.datn_nhom3_backend.repository.KhoaHocRepository;
import org.example.datn_nhom3_backend.repository.NhatKyHeThongRepository;
import org.example.datn_nhom3_backend.repository.TaiKhoanRepository;
import org.example.datn_nhom3_backend.repository.ThanhToanRepository;
import org.example.datn_nhom3_backend.repository.VaiTroRepository;
import org.example.datn_nhom3_backend.service.DangKyKhoaHocService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DangKyKhoaHocServiceIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private DangKyKhoaHocRepository dkRepository;
    @Autowired
    private HocVienRepository hocVienRepository;
    @Autowired
    private KhoaHocRepository khoaHocRepository;
    @Autowired
    private HoSoHocVienRepository hoSoHocVienRepository;
    @Autowired
    private NhatKyHeThongRepository nhatKyHeThongRepository;
    @Autowired
    private ThanhToanRepository thanhToanRepository;
    @Autowired
    private DangKyKhoaHocService dangKyKhoaHocService;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private VaiTroRepository vaiTroRepository;

    @BeforeEach
    void setUp() {
        nhatKyHeThongRepository.deleteAll();
        thanhToanRepository.deleteAll();
        dkRepository.deleteAll();
        hoSoHocVienRepository.deleteAll();
        hocVienRepository.deleteAll();
        khoaHocRepository.deleteAll();
    }

    private DangKyKhoaHoc taoDangKyChoTatCa(String trangthai) {
        HocVien hv = new HocVien();
        hv.setHoten("Học viên Test HS");
        hv.setCccd("111222333444");
        hv.setSodienthoai("0901234567");
        hv.setEmail("hs.test@test.com");
        hv.setNgaysinh(LocalDate.of(2000, 1, 1));
        hv.setNgaydangky(LocalDate.of(2026, 8, 1));
        hv = hocVienRepository.save(hv);

        KhoaHoc kh = new KhoaHoc();
        kh.setTenkhoahoc("Khóa B2 test");
        kh = khoaHocRepository.save(kh);

        DangKyKhoaHoc dk = new DangKyKhoaHoc();
        dk.setHocVien(hv);
        dk.setKhoaHoc(kh);
        dk.setNgaydangky(LocalDate.of(2026, 8, 1));
        dk.setTrangthai(trangthai);
        return dk;
    }

    @Test
    void khiDuyetTaoHoSoHocVien_CHO_DUYET() {
        DangKyKhoaHoc dk = taoDangKyChoTatCa("Đã duyệt");

        var saved = dangKyKhoaHocService.save(dk);

        // Khi duyệt (save với trạng thái đã duyệt) -> tự tạo hồ sơ
        Optional<HoSoHocVien> hoSo = hoSoHocVienRepository.findByHocVien_Mahv(saved.getHocVien().getMahv());
        assertTrue(hoSo.isPresent(), "Phải tự tạo hồ sơ học viên khi duyệt đăng ký");
        assertEquals("Đang xử lý", hoSo.get().getTinhtrang(), "Hồ sơ mới phải ở trạng thái Đang xử lý");
        assertEquals(saved.getHocVien().getMahv(), hoSo.get().getHocVien().getMahv());
    }

    @Test
    void khiChuaDuyet_KhongTaoHoSo() {
        DangKyKhoaHoc dk = taoDangKyChoTatCa("Chờ duyệt");
        var saved = dangKyKhoaHocService.save(dk);

        Optional<HoSoHocVien> hoSo = hoSoHocVienRepository.findByHocVien_Mahv(saved.getHocVien().getMahv());
        assertFalse(hoSo.isPresent(), "Chưa duyệt thì không tạo hồ sơ");
    }

    @Test
    void duyetLai_KhongTaoTrungHoSo() {
        DangKyKhoaHoc dk = taoDangKyChoTatCa("Đã duyệt");
        var saved = dangKyKhoaHocService.save(dk);

        // Duyệt lại (save lần 2) -> không tạo thêm hồ sơ
        dangKyKhoaHocService.save(saved);

        long count = hoSoHocVienRepository.count();
        assertEquals(1, count, "Chỉ tạo đúng 1 hồ sơ, không trùng lặp");
    }

    @Test
    void delete_CoThanhToanLienQuan_CascadeXoaThanhCong() {
        DangKyKhoaHoc dk = taoDangKyChoTatCa("Đã duyệt");
        var saved = dangKyKhoaHocService.save(dk);

        // Tạo thanh toán tham chiếu đăng ký này (FK_TT_DKKH)
        org.example.datn_nhom3_backend.entity.ThanhToan tt = new org.example.datn_nhom3_backend.entity.ThanhToan();
        tt.setHocVien(saved.getHocVien());
        tt.setDangKyKhoaHoc(saved);
        tt.setNgaythanhtoan(LocalDate.of(2026, 8, 10));
        tt.setSotien(1000000d);
        tt.setPhuongthuc("CHUYỂN KHOẢN");
        tt.setTrangthai("ĐÃ THANH TOÁN");
        thanhToanRepository.save(tt);

        // Xóa đăng ký phải xóa luôn thanh toán liên quan, không lỗi FK
        dangKyKhoaHocService.delete(saved.getMadk());

        assertEquals(0, thanhToanRepository.countByDangKyKhoaHoc_Madk(saved.getMadk()),
                "Thanh toán liên quan phải bị xóa theo cascade");
        assertTrue(dkRepository.findById(saved.getMadk()).isEmpty(), "Đăng ký phải bị xóa");
    }

    @Test
    void taoHoSo_TuDongCapTaiKhoanHocVien() {
        // Seed vai trò HV
        VaiTro hvRole = vaiTroRepository.findByMaVaiTro("HV").orElseGet(() -> {
            VaiTro v = new VaiTro();
            v.setMaVaiTro("HV");
            v.setTenVaiTro("Học viên");
            return vaiTroRepository.save(v);
        });
        assertNotNull(hvRole, "Phải có vai trò HV");

        HocVien hv = new HocVien();
        hv.setHoten("Học viên Cấp Tài Khoản");
        hv.setCccd("777888999000");
        hv.setSodienthoai("0900000000");
        hv.setEmail("cap-taikhoan-" + System.nanoTime() + "@test.com");
        hv.setNgaysinh(LocalDate.of(2000, 1, 1));
        hv.setNgaydangky(LocalDate.of(2026, 8, 1));
        hv = hocVienRepository.save(hv);

        KhoaHoc kh = new KhoaHoc();
        kh.setTenkhoahoc("Khóa B2 cấp tài khoản");
        kh = khoaHocRepository.save(kh);

        DangKyKhoaHoc dk = new DangKyKhoaHoc();
        dk.setHocVien(hv);
        dk.setKhoaHoc(kh);
        dk.setNgaydangky(LocalDate.of(2026, 8, 1));
        dk.setTrangthai("Đã duyệt");
        dangKyKhoaHocService.save(dk);

        // Khi tạo hồ sơ học viên -> tự cấp tài khoản học viên
        Optional<TaiKhoan> tk = taiKhoanRepository.findByCccd("777888999000");
        assertTrue(tk.isPresent(), "Phải tự cấp tài khoản học viên khi có hồ sơ");
        assertEquals("HV", tk.get().getVaitro().getMaVaiTro(), "Tài khoản phải có vai trò HV");
        assertTrue(tk.get().getTendangnhap().startsWith("hocvien"), "Tên đăng nhập phải bắt đầu bằng hocvien");

        // Hồ sơ học viên phải được tạo
        assertTrue(hoSoHocVienRepository.findByHocVien_Mahv(hv.getMahv()).isPresent(), "Phải tự tạo hồ sơ học viên");
    }
}
