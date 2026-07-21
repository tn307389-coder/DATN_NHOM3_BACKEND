package org.example.datn_nhom3_backend.repository;

import org.example.datn_nhom3_backend.entity.HocVien;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class HocVienRepositoryTest {

    @Autowired
    private HocVienRepository hocVienRepository;

    @Test
    void findByCccd_WhenExists_ShouldReturnHocVien() {
        HocVien hv = new HocVien();
        hv.setHoten("Nguyễn Test");
        hv.setCccd("123456789099");
        hv.setSodienthoai("0900000000");
        hv.setEmail("test@example.com");
        hv.setNgaysinh(LocalDate.of(2000, 1, 1));
        hv.setGioitinh("Nam");
        hv.setDiachi("Hà Nội");
        hv.setNgaydangky(LocalDate.now());
        hocVienRepository.save(hv);

        Optional<HocVien> found = hocVienRepository.findByCccd("123456789099");

        assertTrue(found.isPresent());
        assertEquals("Nguyễn Test", found.get().getHoten());
    }

    @Test
    void findByCccd_WhenNotExists_ShouldReturnEmpty() {
        Optional<HocVien> found = hocVienRepository.findByCccd("000000000000");

        assertTrue(found.isEmpty());
    }

    @Test
    void save_ShouldPersistHocVien() {
        HocVien hv = new HocVien();
        hv.setHoten("Lê Thị Test");
        hv.setCccd("987654321099");
        hv.setSodienthoai("0911111111");
        hv.setEmail("test2@example.com");
        hv.setNgaysinh(LocalDate.of(1997, 6, 15));
        hv.setGioitinh("Nữ");
        hv.setDiachi("TP.HCM");
        hv.setNgaydangky(LocalDate.now());

        HocVien saved = hocVienRepository.save(hv);

        assertNotNull(saved.getMahv());
        assertEquals("987654321099", saved.getCccd());
    }

    @Test
    void delete_ShouldRemoveEntity() {
        HocVien hv = new HocVien();
        hv.setHoten("Xóa Test");
        hv.setCccd("111111111111");
        hv.setNgaydangky(LocalDate.now());
        HocVien saved = hocVienRepository.save(hv);

        hocVienRepository.deleteById(saved.getMahv());

        assertTrue(hocVienRepository.findById(saved.getMahv()).isEmpty());
    }
}