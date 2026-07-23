package org.example.datn_nhom3_backend.controller;

import jakarta.persistence.Id;
import org.example.datn_nhom3_backend.entity.TinTuc;
import org.example.datn_nhom3_backend.exception.ResourceNotFoundException;
import org.example.datn_nhom3_backend.service.TinTucService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tin-tuc")
@CrossOrigin(origins = "http://localhost:5173")
public class TinTucController {

    private final TinTucService service;

    public TinTucController(TinTucService service) {
        this.service = service;
    }

    @GetMapping
    public List<TinTuc> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TinTuc> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dữ liệu với ID: " + id));
    }

    @PostMapping
    public TinTuc create(@RequestBody TinTuc data) {
        return service.save(data);
    }

    @PutMapping("/{id}")
    public TinTuc update(@PathVariable Integer id, @RequestBody TinTuc data) throws IllegalAccessException {
        setEntityId(data, id);
        return service.save(data);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    private void setEntityId(TinTuc data, Integer id) throws IllegalAccessException {
        for (Field field : data.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                field.set(data, id);
                return;
            }
        }
    }
}
