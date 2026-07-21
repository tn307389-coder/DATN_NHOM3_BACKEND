package org.example.datn_nhom3_backend.repository;

import org.example.datn_nhom3_backend.entity.KhoaHoc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class KhoaHocRepositoryTest {

    @Autowired
    private KhoaHocRepository khoaHocRepository;

    @Test
    void countByTrangthai_ShouldReturnCorrectCount() {
        KhoaHoc kh1 = new KhoaHoc();
        kh1.setTenkhoahoc("Khóa B2");
        kh1.setTrangthai("DANG_MO");
        kh1.setNgaybatdau(LocalDate.now());
        khoaHocRepository.save(kh1);

        KhoaHoc kh2 = new KhoaHoc();
        kh2.setTenkhoahoc("Khóa C1");
        kh2.setTrangthai("DANG_MO");
        kh2.setNgaybatdau(LocalDate.now());
        khoaHocRepository.save(kh2);

        KhoaHoc kh3 = new KhoaHoc();
        kh3.setTenkhoahoc("Khóa Cũ");
        kh3.setTrangthai("DA_DONG");
        kh3.setNgaybatdau(LocalDate.now().minusMonths(6));
        khoaHocRepository.save(kh3);

        Long count = khoaHocRepository.countByTrangthai("DANG_MO");

        assertEquals(2L, count);
    }

    @Test
    void save_ShouldGenerateId() {
        KhoaHoc kh = new KhoaHoc();
        kh.setTenkhoahoc("Test Course");
        kh.setTrangthai("DANG_MO");

        KhoaHoc saved = khoaHocRepository.save(kh);

        assertNotNull(saved.getMakh());
    }
}