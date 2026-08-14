package org.example.datn_nhom3_backend.controller;
import org.example.datn_nhom3_backend.annotation.LogAction;
import jakarta.persistence.Id;
import org.example.datn_nhom3_backend.entity.ChuongTrinhHoc;
import org.example.datn_nhom3_backend.exception.ResourceNotFoundException;
import org.example.datn_nhom3_backend.service.ChuongTrinhHocService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/chuong-trinh-hoc")
@CrossOrigin(origins = "http://localhost:5173")
public class ChuongTrinhHocController {
    private final ChuongTrinhHocService service;
    public ChuongTrinhHocController(ChuongTrinhHocService service) {
        this.service = service;
    }
    @GetMapping
    public List<ChuongTrinhHoc> getAll() {
        return service.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ChuongTrinhHoc> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dữ liệu với ID: " + id));
    }
    @LogAction(action = "Xử lý chương trình học", table = "chuong_trinh_hoc")
    @PostMapping
    public ChuongTrinhHoc create(@RequestBody ChuongTrinhHoc data) {
        return service.save(data);
    }
    @LogAction(action = "Xử lý chương trình học", table = "chuong_trinh_hoc")
    @PutMapping("/{id}")
    public ChuongTrinhHoc update(@PathVariable Integer id, @RequestBody ChuongTrinhHoc data) throws IllegalAccessException {
        setEntityId(data, id);
        return service.save(data);
    }
    @LogAction(action = "Xử lý chương trình học", table = "chuong_trinh_hoc")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
    private void setEntityId(ChuongTrinhHoc data, Integer id) throws IllegalAccessException {
        for (Field field : data.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                field.set(data, id);
                return;
            }
        }
    }
}