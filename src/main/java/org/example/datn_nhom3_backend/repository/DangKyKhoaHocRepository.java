package org.example.datn_nhom3_backend.repository;
import org.example.datn_nhom3_backend.entity.DangKyKhoaHoc;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface DangKyKhoaHocRepository extends JpaRepository<DangKyKhoaHoc, Integer> {
    long countByKhoaHoc_Makh(Integer makh);

    @EntityGraph(attributePaths = {"khoaHoc", "hocVien"})
    List<DangKyKhoaHoc> findByHocVien_Mahv(Integer mahv);

    @Query("SELECT dk.khoaHoc.makh, COUNT(dk) FROM DangKyKhoaHoc dk GROUP BY dk.khoaHoc.makh")
    List<Object[]> countGroupByKhoaHoc();
}