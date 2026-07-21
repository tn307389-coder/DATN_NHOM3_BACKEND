package org.example.datn_nhom3_backend.controller;
import jakarta.persistence.Id;
import org.example.datn_nhom3_backend.entity.HangGPLX;
import org.example.datn_nhom3_backend.exception.ResourceNotFoundException;
import org.example.datn_nhom3_backend.service.HangGPLXService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/hang-gplx")
@CrossOrigin(origins = "http://localhost:5173")
public class HangGPLXController {
    private final HangGPLXService service;
    public HangGPLXController(HangGPLXService service) {
        this.service = service;
    }
    @GetMapping
    public List<HangGPLX> getAll() {
        return service.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<HangGPLX> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dữ liệu với ID: " + id));
    }
    @PostMapping
    public HangGPLX create(@RequestBody HangGPLX data) {
        return service.save(data);
    }
    @PutMapping("/{id}")
    public HangGPLX update(@PathVariable Integer id, @RequestBody HangGPLX data) throws IllegalAccessException {
        setEntityId(data, id);
        return service.save(data);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
    private void setEntityId(HangGPLX data, Integer id) throws IllegalAccessException {
        for (Field field : data.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                field.set(data, id);
                return;
            }
        }
    }
}