package org.example.datn_nhom3_backend.service;

import org.example.datn_nhom3_backend.entity.LichSuBaoTriXe;
import org.example.datn_nhom3_backend.entity.Xe;
import org.example.datn_nhom3_backend.repository.LichSuBaoTriXeRepository;
import org.example.datn_nhom3_backend.service.impl.LichSuBaoTriXeServiceImpl;
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
class LichSuBaoTriXeServiceTest {

    @Mock
    private LichSuBaoTriXeRepository repository;

    @InjectMocks
    private LichSuBaoTriXeServiceImpl lichSuBaoTriXeService;

    private LichSuBaoTriXe baoTri;
    private Xe xe;

    @BeforeEach
    void setUp() {
        xe = new Xe();
        xe.setMaxe(1);
        xe.setBienso("51A-12345");
        xe.setLoaixe("Toyota");

        baoTri = new LichSuBaoTriXe();
        baoTri.setId(1);
        baoTri.setXe(xe);
        baoTri.setLoaiBaoTri("THAY_NHIEU");
        baoTri.setNgayBaoTri(LocalDate.of(2026, 3, 15));
        baoTri.setChiPhi(500000.0);
        baoTri.setDonViBaoTri("Garage ABC");
        baoTri.setTrangThai("HOAN_THANH");
    }

    @Test
    void getAll_ShouldReturnAll() {
        when(repository.findAll()).thenReturn(List.of(baoTri));

        List<LichSuBaoTriXe> result = lichSuBaoTriXeService.getAll();

        assertEquals(1, result.size());
    }

    @Test
    void getById_WhenExists_ShouldReturn() {
        when(repository.findById(1)).thenReturn(Optional.of(baoTri));

        Optional<LichSuBaoTriXe> result = lichSuBaoTriXeService.getById(1);

        assertTrue(result.isPresent());
        assertEquals(500000.0, result.get().getChiPhi());
    }

    @Test
    void getByXe_ShouldReturnList() {
        when(repository.findByXeMaxeOrderByNgayBaoTriDesc(1)).thenReturn(List.of(baoTri));

        List<LichSuBaoTriXe> result = lichSuBaoTriXeService.getByXe(1);

        assertEquals(1, result.size());
        assertEquals("51A-12345", result.get(0).getXe().getBienso());
    }

    @Test
    void save_ShouldReturnSaved() {
        when(repository.save(baoTri)).thenReturn(baoTri);

        LichSuBaoTriXe result = lichSuBaoTriXeService.save(baoTri);

        assertNotNull(result);
        verify(repository).save(baoTri);
    }

    @Test
    void delete_ShouldCallRepository() {
        lichSuBaoTriXeService.delete(1);

        verify(repository).deleteById(1);
    }
}