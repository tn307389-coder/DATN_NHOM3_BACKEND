package org.example.datn_nhom3_backend.controller;
import jakarta.persistence.Id;
import org.example.datn_nhom3_backend.annotation.LogAction;
import org.example.datn_nhom3_backend.entity.*;
import org.example.datn_nhom3_backend.exception.ResourceNotFoundException;
import org.example.datn_nhom3_backend.repository.*;
import org.example.datn_nhom3_backend.service.HocVienService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/hoc-vien")
@CrossOrigin(origins = "http://localhost:5173")
public class HocVienController {
    private final HocVienService service;
    private final TaiKhoanRepository taiKhoanRepository;
    private final LichHocRepository lichHocRepository;
    private final LichThiRepository lichThiRepository;
    private final DangKyKhoaHocRepository dangKyKhoaHocRepository;
    public HocVienController(HocVienService service,
                             TaiKhoanRepository taiKhoanRepository,
                             LichHocRepository lichHocRepository,
                             LichThiRepository lichThiRepository,
                             DangKyKhoaHocRepository dangKyKhoaHocRepository) {
        this.service = service;
        this.taiKhoanRepository = taiKhoanRepository;
        this.lichHocRepository = lichHocRepository;
        this.lichThiRepository = lichThiRepository;
        this.dangKyKhoaHocRepository = dangKyKhoaHocRepository;
    }

    private HocVien getCurrentHocVienEntity() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth != null ? auth.getName() : null;
        if (username == null || "anonymousUser".equals(username))
            throw new ResourceNotFoundException("Chưa đăng nhập");
        TaiKhoan tk = taiKhoanRepository.findByTendangnhap(username)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tài khoản"));
        if (tk.getCccd() == null || tk.getCccd().isBlank())
            throw new ResourceNotFoundException("Tài khoản chưa liên kết với học viên");
        return service.findByCccd(tk.getCccd())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy hồ sơ học viên"));
    }

    @GetMapping("/me")
    public ResponseEntity<HocVien> getCurrentHocVien() {
        return ResponseEntity.ok(getCurrentHocVienEntity());
    }

    @GetMapping("/me/lich-hoc")
    public ResponseEntity<List<LichHocDto>> getLichHocCuaToi(
            @RequestParam(required = false) String tuNgay,
            @RequestParam(required = false) String denNgay) {
        HocVien hv = getCurrentHocVienEntity();
        LocalDate tu = tuNgay != null ? LocalDate.parse(tuNgay) : null;
        LocalDate den = denNgay != null ? LocalDate.parse(denNgay) : null;
        List<LichHoc> list = lichHocRepository.findByHocVien(hv.getMahv(), tu, den);
        List<LichHocDto> result = list.stream().map(lh -> {
            LichHocDto dto = new LichHocDto();
            dto.setMalich(lh.getMalich());
            dto.setNgayhoc(lh.getNgayhoc());
            dto.setTenlop(lh.getLopHoc() != null ? lh.getLopHoc().getTenlop() : null);
            dto.setTenmonhoc(lh.getMonHoc() != null ? lh.getMonHoc().getTenmonhoc() : null);
            dto.setTencahoc(lh.getCaHoc() != null ? lh.getCaHoc().getTencahoc() : null);
            dto.setGiolhlucbatdau(lh.getCaHoc() != null && lh.getCaHoc().getGiobatdau() != null
                    ? lh.getCaHoc().getGiobatdau().toString() : null);
            dto.setGiolhlucketthuc(lh.getCaHoc() != null && lh.getCaHoc().getGioketthuc() != null
                    ? lh.getCaHoc().getGioketthuc().toString() : null);
            dto.setTenphong(lh.getPhongHoc() != null ? lh.getPhongHoc().getTenphong() : null);
            dto.setGhichu(lh.getGhichu());
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/me/lich-thi")
    public ResponseEntity<List<LichThiDto>> getLichThiCuaToi(
            @RequestParam(required = false) String tuNgay,
            @RequestParam(required = false) String denNgay) {
        HocVien hv = getCurrentHocVienEntity();
        LocalDate tu = tuNgay != null ? LocalDate.parse(tuNgay) : null;
        LocalDate den = denNgay != null ? LocalDate.parse(denNgay) : null;
        List<LichThi> list = lichThiRepository.findByHocVien(hv.getMahv(), tu, den);
        List<LichThiDto> result = list.stream().map(lt -> {
            LichThiDto dto = new LichThiDto();
            dto.setMalichthi(lt.getMalichthi());
            dto.setNgaythi(lt.getNgaythi());
            dto.setTencathi(lt.getCaThi() != null ? lt.getCaThi().getTencathi() : null);
            dto.setGiobatdau(lt.getCaThi() != null && lt.getCaThi().getGiobatdau() != null
                    ? lt.getCaThi().getGiobatdau().toString() : null);
            dto.setGioketthuc(lt.getCaThi() != null && lt.getCaThi().getGioketthuc() != null
                    ? lt.getCaThi().getGioketthuc().toString() : null);
            dto.setTenphongthi(lt.getPhongThi() != null ? lt.getPhongThi().getTenphong() : null);
            dto.setDiadiem(lt.getPhongThi() != null ? lt.getPhongThi().getDiadiem() : null);
            dto.setGhichu(lt.getGhichu());
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/me/khoa-hoc-dang-ky")
    public ResponseEntity<List<KhoaHocCuaToiDto>> getKhoaHocCuaToi() {
        HocVien hv = getCurrentHocVienEntity();
        List<DangKyKhoaHoc> list = dangKyKhoaHocRepository.findAll().stream()
                .filter(dk -> dk.getHocVien().getMahv().equals(hv.getMahv()))
                .collect(Collectors.toList());
        List<KhoaHocCuaToiDto> result = list.stream().map(dk -> {
            KhoaHocCuaToiDto dto = new KhoaHocCuaToiDto();
            dto.setMadk(dk.getMadk());
            dto.setMakh(dk.getKhoaHoc() != null ? dk.getKhoaHoc().getMakh() : null);
            dto.setTenKhoaHoc(dk.getKhoaHoc() != null ? dk.getKhoaHoc().getTenkhoahoc() : null);
            dto.setNgaydangky(dk.getNgaydangky());
            dto.setTrangthai(dk.getTrangthai());
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public List<HocVien> getAll() {
        return service.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<HocVien> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dữ liệu với ID: " + id));
    }
    @PostMapping
    @LogAction(action = "Tạo học viên", table = "hoc_vien")
    public HocVien create(@RequestBody HocVien data) {
        return service.save(data);
    }
    @PutMapping("/{id}")
    @LogAction(action = "Cập nhật học viên", table = "hoc_vien")
    public HocVien update(@PathVariable Integer id, @RequestBody HocVien data) throws IllegalAccessException {
        setEntityId(data, id);
        return service.save(data);
    }
    @GetMapping("/{id}/so-du-lieu-lien-quan")
    public java.util.Map<String, Long> soDuLieuLienQuan(@PathVariable Integer id) {
        return service.demDuLieuLienQuan(id);
    }

    @DeleteMapping("/{id}")
    @LogAction(action = "Xóa học viên", table = "hoc_vien")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
    private void setEntityId(HocVien data, Integer id) throws IllegalAccessException {
        for (Field field : data.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                field.set(data, id);
                return;
            }
        }
    }
}

class LichHocDto {
    private Integer malich;
    private LocalDate ngayhoc;
    private String tenlop;
    private String tenmonhoc;
    private String tencahoc;
    private String giolhlucbatdau;
    private String giolhlucketthuc;
    private String tenphong;
    private String ghichu;

    public Integer getMalich() { return malich; }
    public void setMalich(Integer malich) { this.malich = malich; }
    public LocalDate getNgayhoc() { return ngayhoc; }
    public void setNgayhoc(LocalDate ngayhoc) { this.ngayhoc = ngayhoc; }
    public String getTenlop() { return tenlop; }
    public void setTenlop(String tenlop) { this.tenlop = tenlop; }
    public String getTenmonhoc() { return tenmonhoc; }
    public void setTenmonhoc(String tenmonhoc) { this.tenmonhoc = tenmonhoc; }
    public String getTencahoc() { return tencahoc; }
    public void setTencahoc(String tencahoc) { this.tencahoc = tencahoc; }
    public String getGiolhlucbatdau() { return giolhlucbatdau; }
    public void setGiolhlucbatdau(String giolhlucbatdau) { this.giolhlucbatdau = giolhlucbatdau; }
    public String getGiolhlucketthuc() { return giolhlucketthuc; }
    public void setGiolhlucketthuc(String giolhlucketthuc) { this.giolhlucketthuc = giolhlucketthuc; }
    public String getTenphong() { return tenphong; }
    public void setTenphong(String tenphong) { this.tenphong = tenphong; }
    public String getGhichu() { return ghichu; }
    public void setGhichu(String ghichu) { this.ghichu = ghichu; }
}

class LichThiDto {
    private Integer malichthi;
    private LocalDate ngaythi;
    private String tencathi;
    private String giobatdau;
    private String gioketthuc;
    private String tenphongthi;
    private String diadiem;
    private String ghichu;

    public Integer getMalichthi() { return malichthi; }
    public void setMalichthi(Integer malichthi) { this.malichthi = malichthi; }
    public LocalDate getNgaythi() { return ngaythi; }
    public void setNgaythi(LocalDate ngaythi) { this.ngaythi = ngaythi; }
    public String getTencathi() { return tencathi; }
    public void setTencathi(String tencathi) { this.tencathi = tencathi; }
    public String getGiobatdau() { return giobatdau; }
    public void setGiobatdau(String giobatdau) { this.giobatdau = giobatdau; }
    public String getGioketthuc() { return gioketthuc; }
    public void setGioketthuc(String gioketthuc) { this.gioketthuc = gioketthuc; }
    public String getTenphongthi() { return tenphongthi; }
    public void setTenphongthi(String tenphongthi) { this.tenphongthi = tenphongthi; }
    public String getDiadiem() { return diadiem; }
    public void setDiadiem(String diadiem) { this.diadiem = diadiem; }
    public String getGhichu() { return ghichu; }
    public void setGhichu(String ghichu) { this.ghichu = ghichu; }
}

class KhoaHocCuaToiDto {
    private Integer madk;
    private Integer makh;
    private String tenKhoaHoc;
    private LocalDate ngaydangky;
    private String trangthai;

    public Integer getMadk() { return madk; }
    public void setMadk(Integer madk) { this.madk = madk; }
    public Integer getMakh() { return makh; }
    public void setMakh(Integer makh) { this.makh = makh; }
    public String getTenKhoaHoc() { return tenKhoaHoc; }
    public void setTenKhoaHoc(String tenKhoaHoc) { this.tenKhoaHoc = tenKhoaHoc; }
    public LocalDate getNgaydangky() { return ngaydangky; }
    public void setNgaydangky(LocalDate ngaydangky) { this.ngaydangky = ngaydangky; }
    public String getTrangthai() { return trangthai; }
    public void setTrangthai(String trangthai) { this.trangthai = trangthai; }
}