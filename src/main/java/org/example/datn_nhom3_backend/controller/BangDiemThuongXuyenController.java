package org.example.datn_nhom3_backend.controller;
import org.example.datn_nhom3_backend.annotation.LogAction;
import jakarta.persistence.Id;
import org.example.datn_nhom3_backend.entity.BangDiemThuongXuyen;
import org.example.datn_nhom3_backend.exception.ResourceNotFoundException;
import org.example.datn_nhom3_backend.service.BangDiemThuongXuyenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/bang-diem-thuong-xuyen")
@CrossOrigin(origins = "http://localhost:5173")
public class BangDiemThuongXuyenController {
    private final BangDiemThuongXuyenService service;
    public BangDiemThuongXuyenController(BangDiemThuongXuyenService service) {
        this.service = service;
    }
    @GetMapping
    public List<BangDiemThuongXuyen> getAll() {
        return service.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<BangDiemThuongXuyen> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dữ liệu với ID: " + id));
    }
    @LogAction(action = "Xử lý bảng điểm thường xuyên", table = "bang_diem_thuong_xuyen")
    @PostMapping
    public BangDiemThuongXuyen create(@RequestBody BangDiemThuongXuyen data) {
        return service.save(data);
    }
    @LogAction(action = "Xử lý bảng điểm thường xuyên", table = "bang_diem_thuong_xuyen")
    @PutMapping("/{id}")
    public BangDiemThuongXuyen update(@PathVariable Integer id, @RequestBody BangDiemThuongXuyen data) throws IllegalAccessException {
        setEntityId(data, id);
        return service.save(data);
    }
    @LogAction(action = "Xử lý bảng điểm thường xuyên", table = "bang_diem_thuong_xuyen")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
    private void setEntityId(BangDiemThuongXuyen data, Integer id) throws IllegalAccessException {
        for (Field field : data.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                field.set(data, id);
                return;
            }
        }
    }
}