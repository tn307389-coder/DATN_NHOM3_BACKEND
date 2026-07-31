package org.example.datn_nhom3_backend.controller;
import jakarta.persistence.Id;
import org.example.datn_nhom3_backend.entity.ThongBao;
import org.example.datn_nhom3_backend.exception.ResourceNotFoundException;
import org.example.datn_nhom3_backend.service.ThongBaoRealtimeService;
import org.example.datn_nhom3_backend.service.ThongBaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/thong-bao")
@CrossOrigin(origins = "http://localhost:5173")
public class ThongBaoController {
    private final ThongBaoService service;
    private final ThongBaoRealtimeService realtimeService;
    public ThongBaoController(ThongBaoService service, ThongBaoRealtimeService realtimeService) {
        this.service = service;
        this.realtimeService = realtimeService;
    }
    @GetMapping
    public List<ThongBao> getAll() {
        return service.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ThongBao> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dữ liệu với ID: " + id));
    }
    @PostMapping
    public ThongBao create(@RequestBody ThongBao data) {
        ThongBao saved = service.save(data);
        realtimeService.notifyAdmins(saved.getTieude(), saved.getNoidung(), saved.getDoituong());
        return saved;
    }
    @PutMapping("/{id}")
    public ThongBao update(@PathVariable Integer id, @RequestBody ThongBao data) throws IllegalAccessException {
        setEntityId(data, id);
        return service.save(data);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
    private void setEntityId(ThongBao data, Integer id) throws IllegalAccessException {
        for (Field field : data.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                field.set(data, id);
                return;
            }
        }
    }
}