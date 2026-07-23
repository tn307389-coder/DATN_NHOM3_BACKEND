package org.example.datn_nhom3_backend.service;

import org.example.datn_nhom3_backend.entity.HocVien;
import org.example.datn_nhom3_backend.repository.HocVienRepository;
import org.example.datn_nhom3_backend.service.HocVienService;
import org.example.datn_nhom3_backend.service.impl.HocVienServiceImpl;
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
class HocVienServiceTest {

    @Mock
    private HocVienRepository repository;

    @InjectMocks
    private HocVienServiceImpl hocVienService;

    private HocVien hocVien;

    @BeforeEach
    void setUp() {
        hocVien = new HocVien();
        hocVien.setMahv(1);
        hocVien.setHoten("Nguyễn Văn A");
        hocVien.setCccd("123456789012");
        hocVien.setSodienthoai("0901234567");
        hocVien.setEmail("nguyenvana@example.com");
        hocVien.setNgaysinh(LocalDate.of(2000, 1, 1));
        hocVien.setGioitinh("Nam");
        hocVien.setDiachi("Hà Nội");
        hocVien.setNgaydangky(LocalDate.now());
    }

    @Test
    void getAll_ShouldReturnAllHocVien() {
        when(repository.findAll()).thenReturn(List.of(hocVien));

        List<HocVien> result = hocVienService.getAll();

        assertEquals(1, result.size());
        assertEquals("Nguyễn Văn A", result.get(0).getHoten());
        verify(repository).findAll();
    }

    @Test
    void getById_WhenExists_ShouldReturnHocVien() {
        when(repository.findById(1)).thenReturn(Optional.of(hocVien));

        Optional<HocVien> result = hocVienService.getById(1);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getMahv());
        verify(repository).findById(1);
    }

    @Test
    void getById_WhenNotExists_ShouldReturnEmpty() {
        when(repository.findById(999)).thenReturn(Optional.empty());

        Optional<HocVien> result = hocVienService.getById(999);

        assertTrue(result.isEmpty());
        verify(repository).findById(999);
    }

    @Test
    void findByCccd_WhenExists_ShouldReturnHocVien() {
        when(repository.findByCccd("123456789012")).thenReturn(Optional.of(hocVien));

        Optional<HocVien> result = hocVienService.findByCccd("123456789012");

        assertTrue(result.isPresent());
        assertEquals("123456789012", result.get().getCccd());
        verify(repository).findByCccd("123456789012");
    }

    @Test
    void findByCccd_WhenNotExists_ShouldReturnEmpty() {
        when(repository.findByCccd("999999999999")).thenReturn(Optional.empty());

        Optional<HocVien> result = hocVienService.findByCccd("999999999999");

        assertTrue(result.isEmpty());
        verify(repository).findByCccd("999999999999");
    }

    @Test
    void save_ShouldReturnSavedHocVien() {
        when(repository.save(hocVien)).thenReturn(hocVien);

        HocVien result = hocVienService.save(hocVien);

        assertNotNull(result);
        assertEquals(1, result.getMahv());
        verify(repository).save(hocVien);
    }

    @Test
    void delete_ShouldCallRepository() {
        hocVienService.delete(1);

        verify(repository).deleteById(1);
    }
}