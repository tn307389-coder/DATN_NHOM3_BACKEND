package org.example.datn_nhom3_backend.controller;

import org.example.datn_nhom3_backend.entity.DanhMuc;
import org.example.datn_nhom3_backend.repository.DanhMucRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/danh-muc")
@CrossOrigin(origins = "http://localhost:5173")
public class DanhMucController {
    private final DanhMucRepository repository;

    public DanhMucController(DanhMucRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{nhom}")
    public List<DanhMuc> getByNhom(@PathVariable String nhom) {
        return repository.findByNhomOrderByThuTu(nhom);
    }
}
