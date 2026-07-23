package org.example.datn_nhom3_backend.service.impl;

import org.example.datn_nhom3_backend.dto.ThongKeHocVienGiaoVien;
import org.example.datn_nhom3_backend.entity.GiaoVien;
import org.example.datn_nhom3_backend.entity.HocVien;
import org.example.datn_nhom3_backend.exception.ResourceNotFoundException;
import org.example.datn_nhom3_backend.repository.GiaoVienRepository;
import org.example.datn_nhom3_backend.repository.LopHocRepository;
import org.example.datn_nhom3_backend.repository.PhanCongRepository;
import org.example.datn_nhom3_backend.service.GiaoVienService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GiaoVienServiceImpl implements GiaoVienService {
    private final GiaoVienRepository repository;
    private final LopHocRepository lopHocRepository;
    private final PhanCongRepository phanCongRepository;

    public GiaoVienServiceImpl(GiaoVienRepository repository,
                               LopHocRepository lopHocRepository,
                               PhanCongRepository phanCongRepository) {
        this.repository = repository;
        this.lopHocRepository = lopHocRepository;
        this.phanCongRepository = phanCongRepository;
    }

    @Override
    public List<GiaoVien> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<GiaoVien> getById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public GiaoVien save(GiaoVien data) {
        return repository.save(data);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public ThongKeHocVienGiaoVien thongKeHocVien(Integer magv) {
        GiaoVien giaoVien = repository.findById(magv)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giáo viên với ID: " + magv));

        List<HocVien> hocVienQuaLop = mapRawToHocVien(lopHocRepository.findRawHocVienByGiaoVien(magv));
        List<HocVien> hocVienQuaPhanCong = phanCongRepository.findHocVienByGiaoVien(magv);

        Map<Integer, ThongKeHocVienGiaoVien.HocVienTomTat> hocVienMap = new LinkedHashMap<>();

        for (HocVien hv : hocVienQuaLop) {
            hocVienMap.put(hv.getMahv(),
                    new ThongKeHocVienGiaoVien.HocVienTomTat(
                            hv.getMahv(), hv.getHoten(), hv.getSodienthoai(), "Lớp học"));
        }
        for (HocVien hv : hocVienQuaPhanCong) {
            hocVienMap.computeIfAbsent(hv.getMahv(),
                    k -> new ThongKeHocVienGiaoVien.HocVienTomTat(
                            hv.getMahv(), hv.getHoten(), hv.getSodienthoai(), "Phân công"));
        }

        List<ThongKeHocVienGiaoVien.HocVienTomTat> danhSach = new ArrayList<>(hocVienMap.values());

        return new ThongKeHocVienGiaoVien(
                giaoVien.getMagv(),
                giaoVien.getHoten(),
                hocVienQuaLop.size(),
                hocVienQuaPhanCong.size(),
                danhSach.size(),
                danhSach);
    }

    private List<HocVien> mapRawToHocVien(List<Object[]> raw) {
        List<HocVien> list = new ArrayList<>();
        for (Object[] row : raw) {
            HocVien hv = new HocVien();
            hv.setMahv((Integer) row[0]);
            hv.setHoten((String) row[1]);
            if (row[2] != null) hv.setNgaysinh(LocalDate.parse(row[2].toString()));
            hv.setGioitinh((String) row[3]);
            hv.setCccd((String) row[4]);
            hv.setSodienthoai((String) row[5]);
            hv.setEmail((String) row[6]);
            hv.setDiachi((String) row[7]);
            if (row[8] != null) hv.setNgaydangky(LocalDate.parse(row[8].toString()));
            list.add(hv);
        }
        return list;
    }
}