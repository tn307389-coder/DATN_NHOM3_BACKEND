package org.example.datn_nhom3_backend.controller;

import org.example.datn_nhom3_backend.annotation.LogAction;
import org.example.datn_nhom3_backend.entity.ThiSatHach;
import org.example.datn_nhom3_backend.exception.ResourceNotFoundException;
import org.example.datn_nhom3_backend.service.ThiSatHachService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/thi-sat-hach")
@CrossOrigin(origins = "http://localhost:5173")
public class ThiSatHachController {

    private final ThiSatHachService service;

    public ThiSatHachController(ThiSatHachService service) {
        this.service = service;
    }

    @GetMapping
    public List<ThiSatHach> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThiSatHach> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dữ liệu với ID: " + id));
    }

    @PostMapping
    @LogAction(action = "Xử lý thi sát hạch", table = "thi_sat_hach")
    public ThiSatHach save(@RequestBody ThiSatHach thiSatHach) {
        return service.save(thiSatHach);
    }

    @PutMapping("/{id}")
    @LogAction(action = "Xử lý thi sát hạch", table = "thi_sat_hach")
    public ThiSatHach update(@PathVariable Integer id,
                             @RequestBody ThiSatHach thiSatHach) {
        thiSatHach.setMathi(id);
        return service.save(thiSatHach);
    }

    @DeleteMapping("/{id}")
    @LogAction(action = "Xử lý thi sát hạch", table = "thi_sat_hach")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}