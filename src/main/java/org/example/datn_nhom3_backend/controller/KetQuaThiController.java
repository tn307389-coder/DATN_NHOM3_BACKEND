package org.example.datn_nhom3_backend.controller;
import org.example.datn_nhom3_backend.entity.KetQuaThi;
import org.example.datn_nhom3_backend.exception.ResourceNotFoundException;
import org.example.datn_nhom3_backend.service.KetQuaThiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/ket-qua-thi")
@CrossOrigin(origins = "http://localhost:5173")
public class KetQuaThiController {
    private final KetQuaThiService service;
    public KetQuaThiController(KetQuaThiService service) {
        this.service = service;
    }
    @GetMapping
    public List<KetQuaThi> getAll() {
        return service.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<KetQuaThi> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dữ liệu với ID: " + id));
    }
    @PostMapping
    public KetQuaThi create(@RequestBody KetQuaThi data) {
        return service.save(data);
    }
    @PutMapping("/{id}")
    public KetQuaThi update(@PathVariable Integer id, @RequestBody KetQuaThi data) {
        data.setMakq(id);
        return service.save(data);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}