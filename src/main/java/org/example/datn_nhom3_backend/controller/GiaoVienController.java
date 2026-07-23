package org.example.datn_nhom3_backend.controller;
import jakarta.persistence.Id;
import org.example.datn_nhom3_backend.dto.ThongKeHocVienGiaoVien;
import org.example.datn_nhom3_backend.entity.*;
import org.example.datn_nhom3_backend.exception.ResourceNotFoundException;
import org.example.datn_nhom3_backend.repository.GiaoVienRepository;
import org.example.datn_nhom3_backend.repository.LichHocRepository;
import org.example.datn_nhom3_backend.repository.TaiKhoanRepository;
import org.example.datn_nhom3_backend.service.GiaoVienService;
import org.example.datn_nhom3_backend.service.HocVienService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/giao-vien")
@CrossOrigin(origins = "http://localhost:5173")
public class GiaoVienController {
    private final GiaoVienService service;
    private final GiaoVienRepository giaoVienRepository;
    private final LichHocRepository lichHocRepository;
    private final TaiKhoanRepository taiKhoanRepository;
    private final HocVienService hocVienService;

    public GiaoVienController(GiaoVienService service,
                              GiaoVienRepository giaoVienRepository,
                              LichHocRepository lichHocRepository,
                              TaiKhoanRepository taiKhoanRepository,
                              HocVienService hocVienService) {
        this.service = service;
        this.giaoVienRepository = giaoVienRepository;
        this.lichHocRepository = lichHocRepository;
        this.taiKhoanRepository = taiKhoanRepository;
        this.hocVienService = hocVienService;
    }

    @GetMapping("/me")
    public ResponseEntity<GiaoVien> getCurrentGiaoVien() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth != null ? auth.getName() : null;
        if (username == null) throw new ResourceNotFoundException("Chưa đăng nhập");
        TaiKhoan tk = taiKhoanRepository.findByTendangnhap(username)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tài khoản"));
        if (tk.getCccd() == null) throw new ResourceNotFoundException("Tài khoản chưa có CCCD");
        GiaoVien gv = giaoVienRepository.findByCccd(tk.getCccd())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giáo viên"));
        return ResponseEntity.ok(gv);
    }

    @GetMapping("/me/lich-day")
    public ResponseEntity<List<LichDayDto>> getLichDayCuaToi(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String tuNgay,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String denNgay) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth != null ? auth.getName() : null;
        if (username == null) throw new ResourceNotFoundException("Chưa đăng nhập");
        TaiKhoan tk = taiKhoanRepository.findByTendangnhap(username)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tài khoản"));
        if (tk.getCccd() == null) throw new ResourceNotFoundException("Tài khoản chưa có CCCD");
        GiaoVien gv = giaoVienRepository.findByCccd(tk.getCccd())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giáo viên"));

        LocalDate tu = tuNgay != null ? LocalDate.parse(tuNgay) : null;
        LocalDate den = denNgay != null ? LocalDate.parse(denNgay) : null;

        // Lấy lịch dạy từ bảng lich_hoc qua giaoVien.magv
        List<LichHoc> list = lichHocRepository.findByGiaoVien(gv.getMagv(), tu, den);

        List<LichDayDto> result = list.stream().map(lh -> {
            LichDayDto dto = new LichDayDto();
            dto.setMalich(lh.getMalich());
            dto.setNgayhoc(lh.getNgayhoc());
            dto.setTenlop(lh.getLopHoc() != null ? lh.getLopHoc().getTenlop() : null);
            dto.setTenmonhoc(lh.getMonHoc() != null ? lh.getMonHoc().getTenmonhoc() : null);
            dto.setTencahoc(lh.getCaHoc() != null ? lh.getCaHoc().getTencahoc() : null);
            dto.setGiobatdau(lh.getCaHoc() != null && lh.getCaHoc().getGiobatdau() != null
                    ? lh.getCaHoc().getGiobatdau().toString() : null);
            dto.setGioketthuc(lh.getCaHoc() != null && lh.getCaHoc().getGioketthuc() != null
                    ? lh.getCaHoc().getGioketthuc().toString() : null);
            dto.setTenphong(lh.getPhongHoc() != null ? lh.getPhongHoc().getTenphong() : null);
            dto.setGhichu(lh.getGhichu());
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @GetMapping
    public List<GiaoVien> getAll() {
        return service.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<GiaoVien> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dữ liệu với ID: " + id));
    }
    @GetMapping("/{id}/thong-ke-hoc-vien")
    public ThongKeHocVienGiaoVien thongKeHocVien(@PathVariable("id") Integer id) {
        return service.thongKeHocVien(id);
    }
    @PostMapping
    public GiaoVien create(@RequestBody GiaoVien data) {
        return service.save(data);
    }
    @PutMapping("/{id}")
    public GiaoVien update(@PathVariable Integer id, @RequestBody GiaoVien data) throws IllegalAccessException {
        setEntityId(data, id);
        return service.save(data);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
    private void setEntityId(GiaoVien data, Integer id) throws IllegalAccessException {
        for (Field field : data.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                field.set(data, id);
                return;
            }
        }
    }
}

class LichDayDto {
    private Integer malich;
    private LocalDate ngayhoc;
    private String tenlop;
    private String tenmonhoc;
    private String tencahoc;
    private String giobatdau;
    private String gioketthuc;
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
    public String getGiobatdau() { return giobatdau; }
    public void setGiobatdau(String giobatdau) { this.giobatdau = giobatdau; }
    public String getGioketthuc() { return gioketthuc; }
    public void setGioketthuc(String gioketthuc) { this.gioketthuc = gioketthuc; }
    public String getTenphong() { return tenphong; }
    public void setTenphong(String tenphong) { this.tenphong = tenphong; }
    public String getGhichu() { return ghichu; }
    public void setGhichu(String ghichu) { this.ghichu = ghichu; }
}