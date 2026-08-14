package org.example.datn_nhom3_backend.controller;

import jakarta.persistence.Id;
import org.example.datn_nhom3_backend.annotation.LogAction;
import org.example.datn_nhom3_backend.entity.LichSuBaoTriXe;
import org.example.datn_nhom3_backend.entity.Xe;
import org.example.datn_nhom3_backend.exception.ResourceNotFoundException;
import org.example.datn_nhom3_backend.repository.XeRepository;
import org.example.datn_nhom3_backend.service.LichSuBaoTriXeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lich-su-bao-tri-xe")
@CrossOrigin(origins = "http://localhost:5173")
public class LichSuBaoTriXeController {

    private final LichSuBaoTriXeService service;
    private final XeRepository xeRepository;

    public LichSuBaoTriXeController(LichSuBaoTriXeService service, XeRepository xeRepository) {
        this.service = service;
        this.xeRepository = xeRepository;
    }

    @GetMapping
    public List<LichSuBaoTriXe> getAll() {
        return service.getAll();
    }

    @GetMapping("/xe/{maxe}")
    public List<LichSuBaoTriXe> getByXe(@PathVariable Integer maxe) {
        return service.getByXe(maxe);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LichSuBaoTriXe> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dữ liệu với ID: " + id));
    }

    @PostMapping
    @LogAction(action = "Xử lý lịch sử bảo trì xe", table = "lich_su_bao_tri_xe")
    public LichSuBaoTriXe create(@RequestBody CreateBaoTriRequest request) {
        Xe xe = xeRepository.findById(request.getMaxe())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy xe: " + request.getMaxe()));

        LichSuBaoTriXe entity = LichSuBaoTriXe.builder()
                .xe(xe)
                .loaiBaoTri(request.getLoaiBaoTri())
                .ngayBaoTri(request.getNgayBaoTri())
                .soKmHienTai(request.getSoKmHienTai())
                .chiPhi(request.getChiPhi())
                .donViBaoTri(request.getDonViBaoTri())
                .trangThai(request.getTrangThai())
                .ngayBaoTriTiepTheo(request.getNgayBaoTriTiepTheo())
                .moTa(request.getMoTa())
                .nguoiTao(request.getNguoiTao())
                .build();

        return service.save(entity);
    }

    @PutMapping("/{id}")
    @LogAction(action = "Xử lý lịch sử bảo trì xe", table = "lich_su_bao_tri_xe")
    public LichSuBaoTriXe update(@PathVariable Integer id, @RequestBody UpdateBaoTriRequest request) {
        LichSuBaoTriXe entity = service.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dữ liệu: " + id));

        if (request.getLoaiBaoTri() != null) entity.setLoaiBaoTri(request.getLoaiBaoTri());
        if (request.getNgayBaoTri() != null) entity.setNgayBaoTri(request.getNgayBaoTri());
        entity.setSoKmHienTai(request.getSoKmHienTai());
        entity.setChiPhi(request.getChiPhi());
        entity.setDonViBaoTri(request.getDonViBaoTri());
        entity.setTrangThai(request.getTrangThai());
        entity.setNgayBaoTriTiepTheo(request.getNgayBaoTriTiepTheo());
        entity.setMoTa(request.getMoTa());
        entity.setNguoiTao(request.getNguoiTao());

        return service.save(entity);
    }

    @DeleteMapping("/{id}")
    @LogAction(action = "Xử lý lịch sử bảo trì xe", table = "lich_su_bao_tri_xe")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    // Request DTO cho tạo mới
    public static class CreateBaoTriRequest {
        private Integer maxe;
        private String loaiBaoTri;
        private LocalDate ngayBaoTri;
        private Integer soKmHienTai;
        private Double chiPhi;
        private String donViBaoTri;
        private String trangThai;
        private LocalDate ngayBaoTriTiepTheo;
        private String moTa;
        private String nguoiTao;

        // getters/setters
        public Integer getMaxe() { return maxe; }
        public void setMaxe(Integer maxe) { this.maxe = maxe; }
        public String getLoaiBaoTri() { return loaiBaoTri; }
        public void setLoaiBaoTri(String loaiBaoTri) { this.loaiBaoTri = loaiBaoTri; }
        public LocalDate getNgayBaoTri() { return ngayBaoTri; }
        public void setNgayBaoTri(LocalDate ngayBaoTri) { this.ngayBaoTri = ngayBaoTri; }
        public Integer getSoKmHienTai() { return soKmHienTai; }
        public void setSoKmHienTai(Integer soKmHienTai) { this.soKmHienTai = soKmHienTai; }
        public Double getChiPhi() { return chiPhi; }
        public void setChiPhi(Double chiPhi) { this.chiPhi = chiPhi; }
        public String getDonViBaoTri() { return donViBaoTri; }
        public void setDonViBaoTri(String donViBaoTri) { this.donViBaoTri = donViBaoTri; }
        public String getTrangThai() { return trangThai; }
        public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
        public LocalDate getNgayBaoTriTiepTheo() { return ngayBaoTriTiepTheo; }
        public void setNgayBaoTriTiepTheo(LocalDate ngayBaoTriTiepTheo) { this.ngayBaoTriTiepTheo = ngayBaoTriTiepTheo; }
        public String getMoTa() { return moTa; }
        public void setMoTa(String moTa) { this.moTa = moTa; }
        public String getNguoiTao() { return nguoiTao; }
        public void setNguoiTao(String nguoiTao) { this.nguoiTao = nguoiTao; }
    }

    // Request DTO cho cập nhật
    public static class UpdateBaoTriRequest {
        private String loaiBaoTri;
        private LocalDate ngayBaoTri;
        private Integer soKmHienTai;
        private Double chiPhi;
        private String donViBaoTri;
        private String trangThai;
        private LocalDate ngayBaoTriTiepTheo;
        private String moTa;
        private String nguoiTao;

        // getters/setters
        public String getLoaiBaoTri() { return loaiBaoTri; }
        public void setLoaiBaoTri(String loaiBaoTri) { this.loaiBaoTri = loaiBaoTri; }
        public LocalDate getNgayBaoTri() { return ngayBaoTri; }
        public void setNgayBaoTri(LocalDate ngayBaoTri) { this.ngayBaoTri = ngayBaoTri; }
        public Integer getSoKmHienTai() { return soKmHienTai; }
        public void setSoKmHienTai(Integer soKmHienTai) { this.soKmHienTai = soKmHienTai; }
        public Double getChiPhi() { return chiPhi; }
        public void setChiPhi(Double chiPhi) { this.chiPhi = chiPhi; }
        public String getDonViBaoTri() { return donViBaoTri; }
        public void setDonViBaoTri(String donViBaoTri) { this.donViBaoTri = donViBaoTri; }
        public String getTrangThai() { return trangThai; }
        public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
        public LocalDate getNgayBaoTriTiepTheo() { return ngayBaoTriTiepTheo; }
        public void setNgayBaoTriTiepTheo(LocalDate ngayBaoTriTiepTheo) { this.ngayBaoTriTiepTheo = ngayBaoTriTiepTheo; }
        public String getMoTa() { return moTa; }
        public void setMoTa(String moTa) { this.moTa = moTa; }
        public String getNguoiTao() { return nguoiTao; }
        public void setNguoiTao(String nguoiTao) { this.nguoiTao = nguoiTao; }
    }
}