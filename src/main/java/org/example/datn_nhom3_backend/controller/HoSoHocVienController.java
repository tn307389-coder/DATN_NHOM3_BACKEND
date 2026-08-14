package org.example.datn_nhom3_backend.controller;
import org.example.datn_nhom3_backend.annotation.LogAction;
import jakarta.persistence.Id;
import org.example.datn_nhom3_backend.entity.HoSoHocVien;
import org.example.datn_nhom3_backend.exception.ResourceNotFoundException;
import org.example.datn_nhom3_backend.service.HoSoHocVienService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/ho-so-hoc-vien")
@CrossOrigin(origins = "http://localhost:5173")
public class HoSoHocVienController {
    private final HoSoHocVienService service;
    public HoSoHocVienController(HoSoHocVienService service) {
        this.service = service;
    }
    @GetMapping
    public List<HoSoHocVien> getAll() {
        return service.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<HoSoHocVien> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dữ liệu với ID: " + id));
    }
    @LogAction(action = "Xử lý hồ sơ học viên", table = "ho_so_hoc_vien")
    @PostMapping
    public HoSoHocVien create(@RequestBody HoSoHocVien data) {
        return service.save(data);
    }
    @LogAction(action = "Xử lý hồ sơ học viên", table = "ho_so_hoc_vien")
    @PutMapping("/{id}")
    public HoSoHocVien update(@PathVariable Integer id, @RequestBody HoSoHocVien data) throws IllegalAccessException {
        setEntityId(data, id);
        return service.save(data);
    }
    @LogAction(action = "Xử lý hồ sơ học viên", table = "ho_so_hoc_vien")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
    private void setEntityId(HoSoHocVien data, Integer id) throws IllegalAccessException {
        for (Field field : data.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                field.set(data, id);
                return;
            }
        }
    }
}