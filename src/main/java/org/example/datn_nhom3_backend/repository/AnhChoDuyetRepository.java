package org.example.datn_nhom3_backend.repository;

import org.example.datn_nhom3_backend.entity.AnhChoDuyet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnhChoDuyetRepository extends JpaRepository<AnhChoDuyet, Integer> {
    List<AnhChoDuyet> findByTrangthai(String trangthai);
    List<AnhChoDuyet> findByMatk(Integer matk);
}
