package org.example.datn_nhom3_backend.controller;
import jakarta.persistence.Id;
import org.example.datn_nhom3_backend.entity.DiemDanh;
import org.example.datn_nhom3_backend.exception.ResourceNotFoundException;
import org.example.datn_nhom3_backend.service.DiemDanhService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/diem-danh")
@CrossOrigin(origins = "http://localhost:5173")
public class DiemDanhController {
    private final DiemDanhService service;
    public DiemDanhController(DiemDanhService service) {
        this.service = service;
    }
    @GetMapping
    public List<DiemDanh> getAll() {
        return service.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<DiemDanh> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dữ liệu với ID: " + id));
    }
    @PostMapping
    public DiemDanh create(@RequestBody DiemDanh data) {
        return service.save(data);
    }
    @PutMapping("/{id}")
    public DiemDanh update(@PathVariable Integer id, @RequestBody DiemDanh data) throws IllegalAccessException {
        setEntityId(data, id);
        return service.save(data);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
    private void setEntityId(DiemDanh data, Integer id) throws IllegalAccessException {
        for (Field field : data.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                field.set(data, id);
                return;
            }
        }
    }
}