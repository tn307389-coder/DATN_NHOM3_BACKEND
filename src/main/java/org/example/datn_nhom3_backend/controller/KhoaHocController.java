package org.example.datn_nhom3_backend.controller;
import jakarta.persistence.Id;
import org.example.datn_nhom3_backend.annotation.LogAction;
import org.example.datn_nhom3_backend.dto.KhoaHocDto;
import org.example.datn_nhom3_backend.entity.KhoaHoc;
import org.example.datn_nhom3_backend.exception.ResourceNotFoundException;
import org.example.datn_nhom3_backend.service.KhoaHocService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Field;
import java.util.List;
@RestController
@RequestMapping("/api/khoa-hoc")
@CrossOrigin(origins = "http://localhost:5173")
public class KhoaHocController {
    private final KhoaHocService service;
    public KhoaHocController(KhoaHocService service) {
        this.service = service;
    }
    @GetMapping
    public List<KhoaHocDto> getAll() {
        return service.getAllWithCount();
    }
    @GetMapping("/{id}")
    public ResponseEntity<KhoaHoc> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dữ liệu với ID: " + id));
    }
    @PostMapping
    @LogAction(action = "Tạo khóa học", table = "khoa_hoc")
    public KhoaHoc create(@RequestBody KhoaHoc data) {
        return service.save(data);
    }
    @PutMapping("/{id}")
    @LogAction(action = "Cập nhật khóa học", table = "khoa_hoc")
    public KhoaHoc update(@PathVariable Integer id, @RequestBody KhoaHoc data) throws IllegalAccessException {
        setEntityId(data, id);
        return service.save(data);
    }
    @DeleteMapping("/{id}")
    @LogAction(action = "Xóa khóa học", table = "khoa_hoc")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
    private void setEntityId(KhoaHoc data, Integer id) throws IllegalAccessException {
        for (Field field : data.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                field.set(data, id);
                return;
            }
        }
    }
}