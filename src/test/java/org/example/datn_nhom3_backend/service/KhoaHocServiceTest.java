package org.example.datn_nhom3_backend.service;

import org.example.datn_nhom3_backend.entity.KhoaHoc;
import org.example.datn_nhom3_backend.repository.KhoaHocRepository;
import org.example.datn_nhom3_backend.service.impl.KhoaHocServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KhoaHocServiceTest {

    @Mock
    private KhoaHocRepository repository;

    @InjectMocks
    private KhoaHocServiceImpl khoaHocService;

    private KhoaHoc khoaHoc;

    @BeforeEach
    void setUp() {
        khoaHoc = new KhoaHoc();
        khoaHoc.setMakh(1);
        khoaHoc.setTenkhoahoc("Khóa học B2");
        khoaHoc.setNgaybatdau(LocalDate.of(2026, 1, 15));
        khoaHoc.setNgayketthuc(LocalDate.of(2026, 6, 30));
        khoaHoc.setTrangthai("DANG_MO");
    }

    @Test
    void getAll_ShouldReturnAll() {
        when(repository.findAll()).thenReturn(List.of(khoaHoc));

        List<KhoaHoc> result = khoaHocService.getAll();

        assertEquals(1, result.size());
        assertEquals("Khóa học B2", result.get(0).getTenkhoahoc());
    }

    @Test
    void getById_WhenExists_ShouldReturn() {
        when(repository.findById(1)).thenReturn(Optional.of(khoaHoc));

        Optional<KhoaHoc> result = khoaHocService.getById(1);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getMakh());
    }

    @Test
    void getById_WhenNotExists_ShouldReturnEmpty() {
        when(repository.findById(999)).thenReturn(Optional.empty());

        Optional<KhoaHoc> result = khoaHocService.getById(999);

        assertTrue(result.isEmpty());
    }

    @Test
    void save_ShouldReturnSaved() {
        when(repository.save(khoaHoc)).thenReturn(khoaHoc);

        KhoaHoc result = khoaHocService.save(khoaHoc);

        assertNotNull(result);
        assertEquals("DANG_MO", result.getTrangthai());
    }

    @Test
    void delete_ShouldCallRepository() {
        khoaHocService.delete(1);

        verify(repository).deleteById(1);
    }
}