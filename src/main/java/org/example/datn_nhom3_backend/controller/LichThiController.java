package org.example.datn_nhom3_backend.controller;
import org.example.datn_nhom3_backend.annotation.LogAction;
import jakarta.persistence.Id;
import org.example.datn_nhom3_backend.entity.LichThi;
import org.example.datn_nhom3_backend.exception.ResourceNotFoundException;
import org.example.datn_nhom3_backend.service.LichThiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/lich-thi")
@CrossOrigin(origins = "http://localhost:5173")
public class LichThiController {
    private final LichThiService service;
    public LichThiController(LichThiService service) {
        this.service = service;
    }
    @GetMapping
    public List<LichThi> getAll() {
        return service.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<LichThi> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dữ liệu với ID: " + id));
    }
    @LogAction(action = "Xử lý lịch thi", table = "lich_thi")
    @PostMapping
    public LichThi create(@RequestBody LichThi data) {
        return service.save(data);
    }
    @LogAction(action = "Xử lý lịch thi", table = "lich_thi")
    @PutMapping("/{id}")
    public LichThi update(@PathVariable Integer id, @RequestBody LichThi data) throws IllegalAccessException {
        setEntityId(data, id);
        return service.save(data);
    }
    @LogAction(action = "Xử lý lịch thi", table = "lich_thi")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
    private void setEntityId(LichThi data, Integer id) throws IllegalAccessException {
        for (Field field : data.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                field.set(data, id);
                return;
            }
        }
    }
}