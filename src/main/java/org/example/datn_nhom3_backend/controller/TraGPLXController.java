package org.example.datn_nhom3_backend.controller;

import org.example.datn_nhom3_backend.annotation.LogAction;
import org.example.datn_nhom3_backend.entity.TraGPLX;
import org.example.datn_nhom3_backend.exception.ResourceNotFoundException;
import org.example.datn_nhom3_backend.service.TraGPLXService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tra-gplx")
@CrossOrigin(origins = "http://localhost:5173")
public class TraGPLXController {

    private final TraGPLXService service;

    public TraGPLXController(TraGPLXService service) {
        this.service = service;
    }

    @GetMapping
    public List<TraGPLX> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TraGPLX> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dữ liệu với ID: " + id));
    }

    @PostMapping
    @LogAction(action = "Xử lý trả GPLX", table = "tra_gplx")
    public TraGPLX save(@RequestBody TraGPLX traGPLX) {
        return service.save(traGPLX);
    }

    @PutMapping("/{id}")
    @LogAction(action = "Xử lý trả GPLX", table = "tra_gplx")
    public TraGPLX update(@PathVariable Integer id,
                          @RequestBody TraGPLX traGPLX) {
        traGPLX.setMagplx(id);
        return service.save(traGPLX);
    }

    @DeleteMapping("/{id}")
    @LogAction(action = "Xử lý trả GPLX", table = "tra_gplx")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}