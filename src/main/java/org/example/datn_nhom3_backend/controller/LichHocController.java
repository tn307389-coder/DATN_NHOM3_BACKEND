package org.example.datn_nhom3_backend.controller;
import org.example.datn_nhom3_backend.annotation.LogAction;
import jakarta.persistence.Id;
import org.example.datn_nhom3_backend.entity.*;
import org.example.datn_nhom3_backend.exception.ResourceNotFoundException;
import org.example.datn_nhom3_backend.repository.*;
import org.example.datn_nhom3_backend.service.LichHocService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Field;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lich-hoc")
@CrossOrigin(origins = "http://localhost:5173")
public class LichHocController {
    private final LichHocService service;
    private final LopHocRepository lopHocRepository;
    private final MonHocRepository monHocRepository;
    private final GiaoVienRepository giaoVienRepository;
    private final PhongHocRepository phongHocRepository;
    private final CaHocRepository caHocRepository;

    public LichHocController(LichHocService service,
                             LopHocRepository lopHocRepository,
                             MonHocRepository monHocRepository,
                             GiaoVienRepository giaoVienRepository,
                             PhongHocRepository phongHocRepository,
                             CaHocRepository caHocRepository) {
        this.service = service;
        this.lopHocRepository = lopHocRepository;
        this.monHocRepository = monHocRepository;
        this.giaoVienRepository = giaoVienRepository;
        this.phongHocRepository = phongHocRepository;
        this.caHocRepository = caHocRepository;
    }

    @GetMapping
    public List<LichHoc> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LichHoc> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dữ liệu với ID: " + id));
    }

    @LogAction(action = "Xử lý lịch học", table = "lich_hoc")
    @PostMapping
    public LichHoc create(@RequestBody LichHoc data) {
        return service.save(data);
    }

    @LogAction(action = "Xử lý lịch học", table = "lich_hoc")
    @PostMapping("/hang-loat")
    public ResponseEntity<Map<String, Object>> createHangLoat(@RequestBody BatchScheduleRequest request) {
        LopHoc lopHoc = lopHocRepository.findById(request.getMalop())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy lớp học"));
        MonHoc monHoc = monHocRepository.findById(request.getMamh())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy môn học"));
        GiaoVien giaoVien = giaoVienRepository.findById(request.getMagv())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giáo viên"));
        PhongHoc phongHoc = phongHocRepository.findById(request.getMaphong())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phòng học"));
        CaHoc caHoc = caHocRepository.findById(request.getMacahoc())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy ca học"));

        LocalDate startDate = LocalDate.parse(request.getNgayBatDau());
        LocalDate endDate = LocalDate.parse(request.getNgayKetThuc());
        Set<DayOfWeek> selectedDays = request.getThuTrongTuan().stream()
                .map(DayOfWeek::of)
                .collect(Collectors.toSet());

        List<LichHoc> created = new ArrayList<>();
        LocalDate current = startDate.with(TemporalAdjusters.nextOrSame(
                selectedDays.stream().min(Comparator.naturalOrder()).orElse(DayOfWeek.MONDAY)));

        while (!current.isAfter(endDate)) {
            if (selectedDays.contains(current.getDayOfWeek())) {
                LichHoc lichHoc = LichHoc.builder()
                        .lopHoc(lopHoc)
                        .monHoc(monHoc)
                        .giaoVien(giaoVien)
                        .phongHoc(phongHoc)
                        .caHoc(caHoc)
                        .ngayhoc(current)
                        .ghichu(request.getGhichu() != null ? request.getGhichu() : "")
                        .build();
                created.add(service.save(lichHoc));
            }
            current = current.plusDays(1);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("soBuoiTao", created.size());
        response.put("danhSach", created);
        return ResponseEntity.ok(response);
    }

    @LogAction(action = "Xử lý lịch học", table = "lich_hoc")
    @PutMapping("/{id}")
    public LichHoc update(@PathVariable Integer id, @RequestBody LichHoc data) throws IllegalAccessException {
        setEntityId(data, id);
        return service.save(data);
    }

    @LogAction(action = "Xử lý lịch học", table = "lich_hoc")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    private void setEntityId(LichHoc data, Integer id) throws IllegalAccessException {
        for (Field field : data.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                field.set(data, id);
                return;
            }
        }
    }

    public static class BatchScheduleRequest {
        private Integer malop;
        private Integer mamh;
        private Integer magv;
        private Integer maphong;
        private Integer macahoc;
        private String ngayBatDau;
        private String ngayKetThuc;
        private List<Integer> thuTrongTuan;
        private String ghichu;

        public Integer getMalop() { return malop; }
        public void setMalop(Integer malop) { this.malop = malop; }
        public Integer getMamh() { return mamh; }
        public void setMamh(Integer mamh) { this.mamh = mamh; }
        public Integer getMagv() { return magv; }
        public void setMagv(Integer magv) { this.magv = magv; }
        public Integer getMaphong() { return maphong; }
        public void setMaphong(Integer maphong) { this.maphong = maphong; }
        public Integer getMacahoc() { return macahoc; }
        public void setMacahoc(Integer macahoc) { this.macahoc = macahoc; }
        public String getNgayBatDau() { return ngayBatDau; }
        public void setNgayBatDau(String ngayBatDau) { this.ngayBatDau = ngayBatDau; }
        public String getNgayKetThuc() { return ngayKetThuc; }
        public void setNgayKetThuc(String ngayKetThuc) { this.ngayKetThuc = ngayKetThuc; }
        public List<Integer> getThuTrongTuan() { return thuTrongTuan; }
        public void setThuTrongTuan(List<Integer> thuTrongTuan) { this.thuTrongTuan = thuTrongTuan; }
        public String getGhichu() { return ghichu; }
        public void setGhichu(String ghichu) { this.ghichu = ghichu; }
    }
}