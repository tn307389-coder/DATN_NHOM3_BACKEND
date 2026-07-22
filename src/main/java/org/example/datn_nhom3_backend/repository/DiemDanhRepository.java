package org.example.datn_nhom3_backend.repository;
import org.example.datn_nhom3_backend.entity.DiemDanh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
@Repository
public interface DiemDanhRepository extends JpaRepository<DiemDanh, Integer> {
    List<DiemDanh> findByLichHoc_MalichAndNgaydiemdanh(Integer malich, LocalDate ngaydiemdanh);
    void deleteByLichHoc_MalichAndNgaydiemdanh(Integer malich, LocalDate ngaydiemdanh);
}