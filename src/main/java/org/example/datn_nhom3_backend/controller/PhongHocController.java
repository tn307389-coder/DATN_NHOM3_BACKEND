package org.example.datn_nhom3_backend.controller;
import org.example.datn_nhom3_backend.annotation.LogAction;
import jakarta.persistence.Id;
import org.example.datn_nhom3_backend.entity.PhongHoc;
import org.example.datn_nhom3_backend.exception.ResourceNotFoundException;
import org.example.datn_nhom3_backend.service.PhongHocService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/phong-hoc")
@CrossOrigin(origins = "http://localhost:5173")
public class PhongHocController {
    private final PhongHocService service;
    public PhongHocController(PhongHocService service) {
        this.service = service;
    }
    @GetMapping
    public List<PhongHoc> getAll() {
        return service.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<PhongHoc> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dữ liệu với ID: " + id));
    }
    @LogAction(action = "Xử lý phòng học", table = "phong_hoc")
    @PostMapping
    public PhongHoc create(@RequestBody PhongHoc data) {
        return service.save(data);
    }
    @LogAction(action = "Xử lý phòng học", table = "phong_hoc")
    @PutMapping("/{id}")
    public PhongHoc update(@PathVariable Integer id, @RequestBody PhongHoc data) throws IllegalAccessException {
        setEntityId(data, id);
        return service.save(data);
    }
    @LogAction(action = "Xử lý phòng học", table = "phong_hoc")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
    private void setEntityId(PhongHoc data, Integer id) throws IllegalAccessException {
        for (Field field : data.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                field.set(data, id);
                return;
            }
        }
    }
}