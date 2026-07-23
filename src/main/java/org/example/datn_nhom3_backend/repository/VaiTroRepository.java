package org.example.datn_nhom3_backend.repository;

import org.example.datn_nhom3_backend.entity.VaiTro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VaiTroRepository extends JpaRepository<VaiTro, Integer> {
    Optional<VaiTro> findByMaVaiTro(String maVaiTro);
}
