-- =========================================================
-- DỮ LIỆU MẪU (chạy sau khi Hibernate tạo bảng)
-- Sử dụng IF NOT EXISTS để không trùng lặp sau mỗi lần restart
-- =========================================================

-- =========================================================
-- VAI TRÒ (4 vai trò)
-- =========================================================
IF NOT EXISTS (SELECT 1 FROM vai_tro WHERE ma_vai_tro = N'ADMIN')
BEGIN INSERT INTO vai_tro (ma_vai_tro, ten_vai_tro, mo_ta, trang_thai, ngay_tao) VALUES (N'ADMIN', N'Quản trị viên', N'Toàn quyền hệ thống', 1, GETDATE()) END;
IF NOT EXISTS (SELECT 1 FROM vai_tro WHERE ma_vai_tro = N'GV')
BEGIN INSERT INTO vai_tro (ma_vai_tro, ten_vai_tro, mo_ta, trang_thai, ngay_tao) VALUES (N'GV', N'Giáo viên', N'Quản lý lớp học và điểm danh', 1, GETDATE()) END;
IF NOT EXISTS (SELECT 1 FROM vai_tro WHERE ma_vai_tro = N'HV')
BEGIN INSERT INTO vai_tro (ma_vai_tro, ten_vai_tro, mo_ta, trang_thai, ngay_tao) VALUES (N'HV', N'Học viên', N'Xem lịch học và kết quả', 1, GETDATE()) END;
IF NOT EXISTS (SELECT 1 FROM vai_tro WHERE ma_vai_tro = N'NV')
BEGIN INSERT INTO vai_tro (ma_vai_tro, ten_vai_tro, mo_ta, trang_thai, ngay_tao) VALUES (N'NV', N'Nhân viên', N'Nhân viên trung tâm hỗ trợ', 1, GETDATE()) END;

-- =========================================================
-- TÀI KHOẢN (4 tài khoản mẫu, mật khẩu plaintext sẽ được
-- DataInitializer tự động mã hoá BCrypt khi chạy)
-- =========================================================
IF NOT EXISTS (SELECT 1 FROM tai_khoan WHERE ten_dang_nhap = N'admin')
BEGIN
    INSERT INTO tai_khoan (ten_dang_nhap, mat_khau, ho_ten, email, so_dien_thoai, vai_tro_id, trang_thai, so_lan_dang_nhap_sai, ngay_tao)
    VALUES (N'admin', N'123456', N'Nguyễn Quản Trị', N'admin@gmail.com', N'0901112223',
            (SELECT id FROM vai_tro WHERE ma_vai_tro = N'ADMIN'), N'ACTIVE', 0, GETDATE())
END;
IF NOT EXISTS (SELECT 1 FROM tai_khoan WHERE ten_dang_nhap = N'giaovien1')
BEGIN
    INSERT INTO tai_khoan (ten_dang_nhap, mat_khau, ho_ten, email, so_dien_thoai, vai_tro_id, trang_thai, so_lan_dang_nhap_sai, ngay_tao)
    VALUES (N'giaovien1', N'123456', N'Trần Văn Giáo', N'gv1@gmail.com', N'0904445556',
            (SELECT id FROM vai_tro WHERE ma_vai_tro = N'GV'), N'ACTIVE', 0, GETDATE())
END;
IF NOT EXISTS (SELECT 1 FROM tai_khoan WHERE ten_dang_nhap = N'hocvien1')
BEGIN
    INSERT INTO tai_khoan (ten_dang_nhap, mat_khau, ho_ten, email, so_dien_thoai, vai_tro_id, trang_thai, so_lan_dang_nhap_sai, ngay_tao)
    VALUES (N'hocvien1', N'123456', N'Lê Văn Học', N'hv1@gmail.com', N'0907778889',
            (SELECT id FROM vai_tro WHERE ma_vai_tro = N'HV'), N'ACTIVE', 0, GETDATE())
END;
IF NOT EXISTS (SELECT 1 FROM tai_khoan WHERE ten_dang_nhap = N'nhanvien1')
BEGIN
    INSERT INTO tai_khoan (ten_dang_nhap, mat_khau, ho_ten, email, so_dien_thoai, vai_tro_id, trang_thai, so_lan_dang_nhap_sai, ngay_tao)
    VALUES (N'nhanvien1', N'123456', N'Lê Thị Nhân', N'nv1@gmail.com', N'0908889990',
            (SELECT id FROM vai_tro WHERE ma_vai_tro = N'NV'), N'ACTIVE', 0, GETDATE())
END;

-- =========================================================
-- HỌC VIÊN
-- =========================================================
IF NOT EXISTS (SELECT 1 FROM hoc_vien WHERE cccd = N'123456789001')
BEGIN
    INSERT INTO hoc_vien (hoten, ngaysinh, gioitinh, cccd, sodienthoai, email, diachi, ngaydangky)
    VALUES (N'Nguyễn Văn A', '2000-01-01', N'Nam', N'123456789001', N'0911223344', N'vana@gmail.com', N'Quận 1, TP.HCM', '2026-01-10')
END;
IF NOT EXISTS (SELECT 1 FROM hoc_vien WHERE cccd = N'123456789002')
BEGIN
    INSERT INTO hoc_vien (hoten, ngaysinh, gioitinh, cccd, sodienthoai, email, diachi, ngaydangky)
    VALUES (N'Trần Thị B', '2002-05-15', N'Nữ', N'123456789002', N'0922334455', N'thib@gmail.com', N'Quận 3, TP.HCM', '2026-01-12')
END;
IF NOT EXISTS (SELECT 1 FROM hoc_vien WHERE cccd = N'123456789003')
BEGIN
    INSERT INTO hoc_vien (hoten, ngaysinh, gioitinh, cccd, sodienthoai, email, diachi, ngaydangky)
    VALUES (N'Phạm Văn C', '1998-11-20', N'Nam', N'123456789003', N'0933445566', N'vanc@gmail.com', N'Quận 7, TP.HCM', '2026-01-15')
END;

-- =========================================================
-- GIÁO VIÊN
-- =========================================================
IF NOT EXISTS (SELECT 1 FROM giao_vien WHERE sodienthoai = N'0944556677')
BEGIN
    INSERT INTO giao_vien (hoten, ngaysinh, gioitinh, sodienthoai, email, diachi, hangday)
    VALUES (N'Nguyễn Văn Thầy', '1980-03-10', N'Nam', N'0944556677', N'thaynguyen@gmail.com', N'Quận 4, TP.HCM', N'Hạng 1')
END;
IF NOT EXISTS (SELECT 1 FROM giao_vien WHERE sodienthoai = N'0955667788')
BEGIN
    INSERT INTO giao_vien (hoten, ngaysinh, gioitinh, sodienthoai, email, diachi, hangday)
    VALUES (N'Lê Thị Cô', '1985-07-22', N'Nữ', N'0955667788', N'cole@gmail.com', N'Quận 5, TP.HCM', N'Hạng 2')
END;

-- =========================================================
-- CHƯƠNG TRÌNH HỌC
-- =========================================================
IF NOT EXISTS (SELECT 1 FROM chuong_trinh_hoc WHERE hangbang = N'B2')
BEGIN
    INSERT INTO chuong_trinh_hoc (tenchuongtrinh, hangbang, hocphi, sobuoilythuyet, sobuoithuchanh)
    VALUES (N'Đào tạo lái xe hạng B2', N'B2', 15000000, 20, 40)
END;
IF NOT EXISTS (SELECT 1 FROM chuong_trinh_hoc WHERE hangbang = N'C')
BEGIN
    INSERT INTO chuong_trinh_hoc (tenchuongtrinh, hangbang, hocphi, sobuoilythuyet, sobuoithuchanh)
    VALUES (N'Đào tạo lái xe hạng C', N'C', 20000000, 25, 50)
END;

-- =========================================================
-- KHOÁ HỌC
-- =========================================================
IF NOT EXISTS (SELECT 1 FROM khoa_hoc WHERE tenkhoahoc = N'Khóa B2 - Tháng 7/2026')
BEGIN
    INSERT INTO khoa_hoc (tenkhoahoc, macth, ngaybatdau, ngayketthuc, trangthai)
    VALUES (N'Khóa B2 - Tháng 7/2026', (SELECT macth FROM chuong_trinh_hoc WHERE hangbang = N'B2'), '2026-07-01', '2026-12-31', N'OPEN')
END;
IF NOT EXISTS (SELECT 1 FROM khoa_hoc WHERE tenkhoahoc = N'Khóa C - Tháng 7/2026')
BEGIN
    INSERT INTO khoa_hoc (tenkhoahoc, macth, ngaybatdau, ngayketthuc, trangthai)
    VALUES (N'Khóa C - Tháng 7/2026', (SELECT macth FROM chuong_trinh_hoc WHERE hangbang = N'C'), '2026-07-01', '2026-12-31', N'OPEN')
END;

-- =========================================================
-- LỚP HỌC
-- =========================================================
IF NOT EXISTS (SELECT 1 FROM lop_hoc WHERE tenlop = N'Lớp B2_01')
BEGIN
    INSERT INTO lop_hoc (tenlop, makh, magv, soluong, trangthai)
    VALUES (N'Lớp B2_01',
            (SELECT makh FROM khoa_hoc WHERE tenkhoahoc = N'Khóa B2 - Tháng 7/2026'),
            (SELECT magv FROM giao_vien WHERE hoten = N'Nguyễn Văn Thầy'), 20, N'ACTIVE')
END;
IF NOT EXISTS (SELECT 1 FROM lop_hoc WHERE tenlop = N'Lớp C_01')
BEGIN
    INSERT INTO lop_hoc (tenlop, makh, magv, soluong, trangthai)
    VALUES (N'Lớp C_01',
            (SELECT makh FROM khoa_hoc WHERE tenkhoahoc = N'Khóa C - Tháng 7/2026'),
            (SELECT magv FROM giao_vien WHERE hoten = N'Lê Thị Cô'), 15, N'ACTIVE')
END;

-- =========================================================
-- PHÒNG HỌC
-- =========================================================
IF NOT EXISTS (SELECT 1 FROM phong_hoc WHERE tenphong = N'Phòng Lý Thuyết 1')
BEGIN INSERT INTO phong_hoc (tenphong, succhua, trangthai) VALUES (N'Phòng Lý Thuyết 1', 30, N'AVAILABLE') END;
IF NOT EXISTS (SELECT 1 FROM phong_hoc WHERE tenphong = N'Phòng Lý Thuyết 2')
BEGIN INSERT INTO phong_hoc (tenphong, succhua, trangthai) VALUES (N'Phòng Lý Thuyết 2', 30, N'AVAILABLE') END;
IF NOT EXISTS (SELECT 1 FROM phong_hoc WHERE tenphong = N'Sân Tập Lái 1')
BEGIN INSERT INTO phong_hoc (tenphong, succhua, trangthai) VALUES (N'Sân Tập Lái 1', 10, N'AVAILABLE') END;

-- =========================================================
-- CA HỌC
-- =========================================================
IF NOT EXISTS (SELECT 1 FROM ca_hoc WHERE tencahoc = N'Ca Sáng')
BEGIN INSERT INTO ca_hoc (tencahoc, giobatdau, gioketthuc) VALUES (N'Ca Sáng', '07:00:00', '11:00:00') END;
IF NOT EXISTS (SELECT 1 FROM ca_hoc WHERE tencahoc = N'Ca Chiều')
BEGIN INSERT INTO ca_hoc (tencahoc, giobatdau, gioketthuc) VALUES (N'Ca Chiều', '13:00:00', '17:00:00') END;

-- =========================================================
-- MÔN HỌC
-- =========================================================
IF NOT EXISTS (SELECT 1 FROM mon_hoc WHERE tenmonhoc = N'Luật Giao Thông Đường Bộ')
BEGIN INSERT INTO mon_hoc (tenmonhoc, so_tiet) VALUES (N'Luật Giao Thông Đường Bộ', 40) END;
IF NOT EXISTS (SELECT 1 FROM mon_hoc WHERE tenmonhoc = N'Kỹ thuật lái xe cơ bản')
BEGIN INSERT INTO mon_hoc (tenmonhoc, so_tiet) VALUES (N'Kỹ thuật lái xe cơ bản', 60) END;
IF NOT EXISTS (SELECT 1 FROM mon_hoc WHERE tenmonhoc = N'Đạo đức người lái xe')
BEGIN INSERT INTO mon_hoc (tenmonhoc, so_tiet) VALUES (N'Đạo đức người lái xe', 10) END;

-- =========================================================
-- LỊCH HỌC
-- =========================================================
IF NOT EXISTS (SELECT 1 FROM lich_hoc WHERE ngayhoc = '2026-07-15' AND malop = (SELECT malop FROM lop_hoc WHERE tenlop = N'Lớp B2_01'))
BEGIN
    INSERT INTO lich_hoc (malop, mamh, magv, maphong, macahoc, ngayhoc, ghichu)
    VALUES ((SELECT malop FROM lop_hoc WHERE tenlop = N'Lớp B2_01'),
            (SELECT mamh FROM mon_hoc WHERE tenmonhoc = N'Luật Giao Thông Đường Bộ'),
            (SELECT magv FROM giao_vien WHERE hoten = N'Nguyễn Văn Thầy'),
            (SELECT maphong FROM phong_hoc WHERE tenphong = N'Phòng Lý Thuyết 1'),
            (SELECT macahoc FROM ca_hoc WHERE tencahoc = N'Ca Sáng'),
            '2026-07-15', N'Học lý thuyết chương 1')
END;

-- =========================================================
-- XE
-- =========================================================
IF NOT EXISTS (SELECT 1 FROM xe WHERE bienso = N'51A-123.45')
BEGIN INSERT INTO xe (bienso, loaixe, hangxe, namsanxuat, trangthai) VALUES (N'51A-123.45', N'Xe tập lái B2', N'Toyota', 2022, N'AVAILABLE') END;
IF NOT EXISTS (SELECT 1 FROM xe WHERE bienso = N'51A-678.90')
BEGIN INSERT INTO xe (bienso, loaixe, hangxe, namsanxuat, trangthai) VALUES (N'51A-678.90', N'Xe tập lái C', N'Hyundai', 2021, N'AVAILABLE') END;

-- =========================================================
-- XE TẬP LÁI
-- =========================================================
IF NOT EXISTS (SELECT 1 FROM xe_tap_lai WHERE maxe = (SELECT maxe FROM xe WHERE bienso = N'51A-123.45'))
BEGIN
    INSERT INTO xe_tap_lai (maxe, hangbang, ngaydangkiem, handangkiem, ghichu)
    VALUES ((SELECT maxe FROM xe WHERE bienso = N'51A-123.45'), N'B2', '2026-01-01', '2027-01-01', N'Xe mới')
END;

-- =========================================================
-- ĐĂNG KÝ KHOÁ HỌC
-- =========================================================
IF NOT EXISTS (SELECT 1 FROM dang_ky_khoa_hoc WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A') AND makh = (SELECT makh FROM khoa_hoc WHERE tenkhoahoc = N'Khóa B2 - Tháng 7/2026'))
BEGIN
    INSERT INTO dang_ky_khoa_hoc (mahv, makh, ngaydangky, trangthai)
    VALUES ((SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A'), (SELECT makh FROM khoa_hoc WHERE tenkhoahoc = N'Khóa B2 - Tháng 7/2026'), '2026-01-10', N'ĐÃ ĐĂNG KÝ')
END;
IF NOT EXISTS (SELECT 1 FROM dang_ky_khoa_hoc WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Trần Thị B'))
BEGIN
    INSERT INTO dang_ky_khoa_hoc (mahv, makh, ngaydangky, trangthai)
    VALUES ((SELECT mahv FROM hoc_vien WHERE hoten = N'Trần Thị B'), (SELECT makh FROM khoa_hoc WHERE tenkhoahoc = N'Khóa B2 - Tháng 7/2026'), '2026-01-12', N'ĐÃ ĐĂNG KÝ')
END;
IF NOT EXISTS (SELECT 1 FROM dang_ky_khoa_hoc WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Phạm Văn C'))
BEGIN
    INSERT INTO dang_ky_khoa_hoc (mahv, makh, ngaydangky, trangthai)
    VALUES ((SELECT mahv FROM hoc_vien WHERE hoten = N'Phạm Văn C'), (SELECT makh FROM khoa_hoc WHERE tenkhoahoc = N'Khóa C - Tháng 7/2026'), '2026-01-15', N'ĐANG XỬ LÝ')
END;

-- =========================================================
-- THANH TOÁN
-- =========================================================
IF NOT EXISTS (SELECT 1 FROM thanh_toan WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A') AND sotien = 15000000 AND ngaythanhtoan = '2026-01-11')
BEGIN
    INSERT INTO thanh_toan (madk, mahv, sotien, ngaythanhtoan, phuongthuc, trangthai)
    VALUES ((SELECT madk FROM dang_ky_khoa_hoc WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A') AND makh = (SELECT makh FROM khoa_hoc WHERE tenkhoahoc = N'Khóa B2 - Tháng 7/2026')), (SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A'), 15000000, '2026-01-11', N'CHUYỂN KHOẢN', N'ĐÃ THANH TOÁN')
END;
IF NOT EXISTS (SELECT 1 FROM thanh_toan WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Trần Thị B') AND sotien = 15000000)
BEGIN
    INSERT INTO thanh_toan (madk, mahv, sotien, ngaythanhtoan, phuongthuc, trangthai)
    VALUES ((SELECT madk FROM dang_ky_khoa_hoc WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Trần Thị B') AND makh = (SELECT makh FROM khoa_hoc WHERE tenkhoahoc = N'Khóa B2 - Tháng 7/2026')), (SELECT mahv FROM hoc_vien WHERE hoten = N'Trần Thị B'), 15000000, '2026-01-13', N'TIỀN MẶT', N'ĐÃ THANH TOÁN')
END;
IF NOT EXISTS (SELECT 1 FROM thanh_toan WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Phạm Văn C') AND sotien = 20000000)
BEGIN
    INSERT INTO thanh_toan (madk, mahv, sotien, ngaythanhtoan, phuongthuc, trangthai)
    VALUES ((SELECT madk FROM dang_ky_khoa_hoc WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Phạm Văn C') AND makh = (SELECT makh FROM khoa_hoc WHERE tenkhoahoc = N'Khóa C - Tháng 7/2026')), (SELECT mahv FROM hoc_vien WHERE hoten = N'Phạm Văn C'), 20000000, '2026-02-15', N'TIỀN MẶT', N'ĐÃ THANH TOÁN')
END;

-- =========================================================
-- PHÂN CÔNG
-- =========================================================
IF NOT EXISTS (SELECT 1 FROM phan_cong WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A'))
BEGIN
    INSERT INTO phan_cong (mahv, magv, maxetl, ngay_phan_cong, ghichu)
    VALUES ((SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A'), (SELECT magv FROM giao_vien WHERE hoten = N'Nguyễn Văn Thầy'), (SELECT maxetl FROM xe_tap_lai WHERE maxe = (SELECT maxe FROM xe WHERE bienso = N'51A-123.45')), '2026-07-10', N'Xe tập lái Toyota')
END;
IF NOT EXISTS (SELECT 1 FROM phan_cong WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Phạm Văn C'))
BEGIN
    INSERT INTO phan_cong (mahv, magv, maxetl, ngay_phan_cong, ghichu)
    VALUES ((SELECT mahv FROM hoc_vien WHERE hoten = N'Phạm Văn C'), (SELECT magv FROM giao_vien WHERE hoten = N'Lê Thị Cô'), (SELECT maxetl FROM xe_tap_lai WHERE maxe = (SELECT maxe FROM xe WHERE bienso = N'51A-123.45')), '2026-07-11', N'Xe tập lái Toyota')
END;

-- =========================================================
-- ĐIỂM DANH
-- =========================================================
IF NOT EXISTS (SELECT 1 FROM diem_danh WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A') AND ngaydiemdanh = '2026-07-15')
BEGIN
    INSERT INTO diem_danh (mahv, malich, ngaydiemdanh, trangthai, ghichu)
    VALUES ((SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A'), 1, '2026-07-15', N'CÓ MẶT', N'')
END;
IF NOT EXISTS (SELECT 1 FROM diem_danh WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Trần Thị B') AND ngaydiemdanh = '2026-07-15')
BEGIN
    INSERT INTO diem_danh (mahv, malich, ngaydiemdanh, trangthai, ghichu)
    VALUES ((SELECT mahv FROM hoc_vien WHERE hoten = N'Trần Thị B'), 1, '2026-07-15', N'VẮNG', N'Nghỉ ốm')
END;

-- =========================================================
-- BẢNG ĐIỂM THƯỜNG XUYÊN
-- =========================================================
IF NOT EXISTS (SELECT 1 FROM bang_diem_thuong_xuyen WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A') AND mamh = (SELECT mamh FROM mon_hoc WHERE tenmonhoc = N'Luật Giao Thông Đường Bộ'))
BEGIN
    INSERT INTO bang_diem_thuong_xuyen (mahv, malop, mamh, diem, ngay_cham, ghichu)
    VALUES ((SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A'),
            (SELECT malop FROM lop_hoc WHERE tenlop = N'Lớp B2_01'),
            (SELECT mamh FROM mon_hoc WHERE tenmonhoc = N'Luật Giao Thông Đường Bộ'), 8.5, '2026-07-20', N'Tốt')
END;
IF NOT EXISTS (SELECT 1 FROM bang_diem_thuong_xuyen WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A') AND mamh = (SELECT mamh FROM mon_hoc WHERE tenmonhoc = N'Kỹ thuật lái xe cơ bản'))
BEGIN
    INSERT INTO bang_diem_thuong_xuyen (mahv, malop, mamh, diem, ngay_cham, ghichu)
    VALUES ((SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A'),
            (SELECT malop FROM lop_hoc WHERE tenlop = N'Lớp B2_01'),
            (SELECT mamh FROM mon_hoc WHERE tenmonhoc = N'Kỹ thuật lái xe cơ bản'), 9.0, '2026-07-22', N'Rất tốt')
END;

-- =========================================================
-- PHÒNG THI
-- =========================================================
IF NOT EXISTS (SELECT 1 FROM phong_thi WHERE tenphong = N'Phòng Thi Sát Hạch 1')
BEGIN INSERT INTO phong_thi (tenphong, succhua, diadiem, trangthai) VALUES (N'Phòng Thi Sát Hạch 1', 40, N'Số 123 Đường Lê Lợi, Quận 1', N'AVAILABLE') END;
IF NOT EXISTS (SELECT 1 FROM phong_thi WHERE tenphong = N'Phòng Thi Sát Hạch 2')
BEGIN INSERT INTO phong_thi (tenphong, succhua, diadiem, trangthai) VALUES (N'Phòng Thi Sát Hạch 2', 30, N'Số 456 Đường Nguyễn Huệ, Quận 1', N'AVAILABLE') END;

-- =========================================================
-- CA THI
-- =========================================================
IF NOT EXISTS (SELECT 1 FROM ca_thi WHERE ten_ca = N'Ca Thi Sáng')
BEGIN INSERT INTO ca_thi (ten_ca, gio_bat_dau, gio_ket_thuc) VALUES (N'Ca Thi Sáng', '07:30:00', '10:30:00') END;
IF NOT EXISTS (SELECT 1 FROM ca_thi WHERE ten_ca = N'Ca Thi Chiều')
BEGIN INSERT INTO ca_thi (ten_ca, gio_bat_dau, gio_ket_thuc) VALUES (N'Ca Thi Chiều', '13:30:00', '16:30:00') END;

-- =========================================================
-- THI SÁT HẠCH
-- =========================================================
IF NOT EXISTS (SELECT 1 FROM thi_sat_hach WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A'))
BEGIN
    INSERT INTO thi_sat_hach (mahv, malichthi, ketqua, ngaythi, ghichu)
    VALUES ((SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A'), NULL, N'ĐẬU', '2026-08-15', N'Thi đạt yêu cầu')
END;

-- =========================================================
-- KẾT QUẢ THI
-- =========================================================
IF NOT EXISTS (SELECT 1 FROM ket_qua_thi WHERE mathi = (SELECT mathi FROM thi_sat_hach WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A')))
BEGIN
    INSERT INTO ket_qua_thi (mathi, diem, ketqua, ghichu)
    VALUES ((SELECT mathi FROM thi_sat_hach WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A')), 9.0, N'ĐẬU', N'Kết quả thi sát hạch đạt')
END;

-- =========================================================
-- TRẢ GPLX
-- =========================================================
IF NOT EXISTS (SELECT 1 FROM tra_gplx WHERE sogplx = N'83B1-123456')
BEGIN
    INSERT INTO tra_gplx (mahv, mathi, sogplx, hanggplx, ngaycap, ngayhethan, trangthai)
    VALUES ((SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A'),
            (SELECT mathi FROM thi_sat_hach WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A')),
            N'83B1-123456', N'B2', '2026-08-20', '2032-08-20', N'ĐÃ TRẢ')
END;

-- =========================================================
-- HẠNG GPLX
-- =========================================================
IF NOT EXISTS (SELECT 1 FROM hang_gplx WHERE ten_hang = N'GPLX hạng A1')
BEGIN INSERT INTO hang_gplx (ten_hang, mo_ta) VALUES (N'GPLX hạng A1', N'Xe mô tô hai bánh dung tích từ 50cc đến dưới 175cc') END;
IF NOT EXISTS (SELECT 1 FROM hang_gplx WHERE ten_hang = N'GPLX hạng A2')
BEGIN INSERT INTO hang_gplx (ten_hang, mo_ta) VALUES (N'GPLX hạng A2', N'Xe mô tô hai bánh dung tích từ 175cc trở lên') END;
IF NOT EXISTS (SELECT 1 FROM hang_gplx WHERE ten_hang = N'GPLX hạng B1')
BEGIN INSERT INTO hang_gplx (ten_hang, mo_ta) VALUES (N'GPLX hạng B1', N'Ô tô số tự động chở người đến 9 chỗ') END;
IF NOT EXISTS (SELECT 1 FROM hang_gplx WHERE ten_hang = N'GPLX hạng B2')
BEGIN INSERT INTO hang_gplx (ten_hang, mo_ta) VALUES (N'GPLX hạng B2', N'Ô tô số tự động và số sàn chở người đến 9 chỗ') END;
IF NOT EXISTS (SELECT 1 FROM hang_gplx WHERE ten_hang = N'GPLX hạng C')
BEGIN INSERT INTO hang_gplx (ten_hang, mo_ta) VALUES (N'GPLX hạng C', N'Ô tô tải, ô tô chuyên dùng trọng tải từ 3.500kg trở lên') END;
IF NOT EXISTS (SELECT 1 FROM hang_gplx WHERE ten_hang = N'GPLX hạng D')
BEGIN INSERT INTO hang_gplx (ten_hang, mo_ta) VALUES (N'GPLX hạng D', N'Ô tô chở người từ 10 đến 30 chỗ') END;
IF NOT EXISTS (SELECT 1 FROM hang_gplx WHERE ten_hang = N'GPLX hạng E')
BEGIN INSERT INTO hang_gplx (ten_hang, mo_ta) VALUES (N'GPLX hạng E', N'Ô tô chở người trên 30 chỗ') END;
IF NOT EXISTS (SELECT 1 FROM hang_gplx WHERE ten_hang = N'GPLX hạng F')
BEGIN INSERT INTO hang_gplx (ten_hang, mo_ta) VALUES (N'GPLX hạng F', N'Kéo rơ moóc, sơ mi rơ moóc') END;

-- =========================================================
-- HỒ SƠ HỌC VIÊN
-- =========================================================
IF NOT EXISTS (SELECT 1 FROM ho_so_hoc_vien WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A'))
BEGIN
    INSERT INTO ho_so_hoc_vien (mahv, ngaydangky, trang_thai_duyet, ghichu, anh_canh_cuoc, file_ho_so)
    VALUES ((SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A'), '2026-01-10', N'ĐÃ DUYỆT', N'Hồ sơ đầy đủ', N'anh_a.jpg', N'hoso_a.pdf')
END;
IF NOT EXISTS (SELECT 1 FROM ho_so_hoc_vien WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Trần Thị B'))
BEGIN
    INSERT INTO ho_so_hoc_vien (mahv, ngaydangky, trang_thai_duyet, ghichu, anh_canh_cuoc, file_ho_so)
    VALUES ((SELECT mahv FROM hoc_vien WHERE hoten = N'Trần Thị B'), '2026-01-12', N'CHỜ DUYỆT', N'Thiếu ảnh căn cước', N'anh_b.jpg', N'hoso_b.pdf')
END;
IF NOT EXISTS (SELECT 1 FROM ho_so_hoc_vien WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Phạm Văn C'))
BEGIN
    INSERT INTO ho_so_hoc_vien (mahv, ngaydangky, trang_thai_duyet, ghichu, anh_canh_cuoc, file_ho_so)
    VALUES ((SELECT mahv FROM hoc_vien WHERE hoten = N'Phạm Văn C'), '2026-01-15', N'ĐÃ DUYỆT', N'Hồ sơ hợp lệ', N'anh_c.jpg', N'hoso_c.pdf')
END;

-- =========================================================
-- LỊCH THI
-- =========================================================
IF NOT EXISTS (SELECT 1 FROM lich_thi WHERE maphongthi = 1 AND ngaythi = '2026-08-15')
BEGIN
    INSERT INTO lich_thi (maphongthi, macathi, ngaythi, ghichu)
    VALUES (1, (SELECT macathi FROM ca_thi WHERE ten_ca = N'Ca Thi Sáng'), '2026-08-15', N'Thi lý thuyết và thực hành hạng B2')
END;
IF NOT EXISTS (SELECT 1 FROM lich_thi WHERE maphongthi = 2 AND ngaythi = '2026-08-15')
BEGIN
    INSERT INTO lich_thi (maphongthi, macathi, ngaythi, ghichu)
    VALUES (2, (SELECT macathi FROM ca_thi WHERE ten_ca = N'Ca Thi Chiều'), '2026-08-15', N'Thi sát hạch hạng C')
END;

-- =========================================================
-- THÔNG BÁO
-- =========================================================
IF NOT EXISTS (SELECT 1 FROM thong_bao WHERE tieu_de = N'Thông báo nghỉ lễ')
BEGIN
    INSERT INTO thong_bao (tieu_de, noi_dung, doituong)
    VALUES (N'Thông báo nghỉ lễ', N'Trung tâm nghỉ lễ từ ngày 2/9 đến hết 3/9', N'TẤT CẢ')
END;

-- =========================================================
-- TIN TỨC
-- =========================================================
IF NOT EXISTS (SELECT 1 FROM tin_tuc WHERE tieu_de LIKE N'DriveHub khai giảng%')
BEGIN
    INSERT INTO tin_tuc (tieu_de, hinh_anh, mo_ta_ngan, noi_dung, ngay_dang, tac_gia, trang_thai)
    VALUES (N'DriveHub khai giảng hàng nghìn khóa học lái xe B2 định kỳ mỗi tháng',
            N'/tin-tuc-b2.jpg',
            N'Chương trình đào tạo lái xe B2 chuẩn Bộ GTVT với lộ trình 3 tháng, xe đời mới và giáo viên giàu kinh nghiệm.',
            N'DriveHub khai giảng các khóa học lái xe hạng B2 liên tục đầu mỗi tháng. Học viên được tiếp cận giáo trình chuẩn Bộ GTVT, thực hành xe đời mới, giáo viên tận tâm. Lộ trình 3 tháng: lý thuyết, thi thử online, thực hành sa hình – đường trường.',
            '2026-07-01', N'DriveHub', N'ĐÃ ĐĂNG')
END;
IF NOT EXISTS (SELECT 1 FROM tin_tuc WHERE tieu_de LIKE N'Kinh nghiệm thi sát hạch%')
BEGIN
    INSERT INTO tin_tuc (tieu_de, hinh_anh, mo_ta_ngan, noi_dung, ngay_dang, tac_gia, trang_thai)
    VALUES (N'Kinh nghiệm thi sát hạch lý thuyết 600 câu đạt điểm tuyệt đối',
            N'/tin-tuc-ly-thuyet.jpg',
            N'Tổng hợp các mẹo ôn tập bộ 600 câu hỏi lý thuyết và cách làm bài thi sa hạch đạt kết quả cao.',
            N'Chia nhỏ đề mục theo chủ đề, kết hợp thi thử online mỗi ngày. Nắm vững 60 câu điểm liệt là ưu tiên hàng đầu. Giáo viên hướng dẫn phân tích sa hình và xử lý tình huống thực tế.',
            '2026-06-20', N'Giáo viên DriveHub', N'ĐÃ ĐĂNG')
END;
IF NOT EXISTS (SELECT 1 FROM tin_tuc WHERE tieu_de LIKE N'Cập nhật chính sách mới%')
BEGIN
    INSERT INTO tin_tuc (tieu_de, hinh_anh, mo_ta_ngan, noi_dung, ngay_dang, tac_gia, trang_thai)
    VALUES (N'Cập nhật chính sách mới về nâng hạng giấy phép lái xe năm 2026',
            N'/tin-tuc-nang-hang.jpg',
            N'Tổng hợp những thay đổi quan trọng trong quy định nâng hạng GPLX mà học viên cần lưu ý.',
            N'Bộ GTVT điều chỉnh điều kiện nâng hạng GPLX năm 2026. Độ tuổi và thời gian hành nghề là yếu tố quan trọng. DriveHub cập nhật lộ trình nâng hạng B2 lên C, A1 lên A2, D/E nâng cao.',
            '2026-06-05', N'DriveHub', N'ĐÃ ĐĂNG')
END;
IF NOT EXISTS (SELECT 1 FROM tin_tuc WHERE tieu_de LIKE N'Xe tập lái đời mới%')
BEGIN
    INSERT INTO tin_tuc (tieu_de, hinh_anh, mo_ta_ngan, noi_dung, ngay_dang, tac_gia, trang_thai)
    VALUES (N'Xe tập lái đời mới được đưa vào vận hành tại hệ thống sát hạch',
            N'/tin-tuc-xe.jpg',
            N'Trung tâm đầu tư thêm dàn xe tập lái hiện đại, trang bị đầy đủ cảm biến và camera hỗ trợ học viên.',
            N'DriveHub đưa vào vận hành xe tập lái đời mới, bảo trì định kỳ, bảo hiểm toàn diện. Mỗi xe có phanh phụ giáo viên, camera hành trình, cảm biến lùi giúp học viên tự tin khi thi sát hạch.',
            '2026-05-18', N'DriveHub', N'ĐÃ ĐĂNG')
END;

-- =========================================================
-- NHẬT KÝ HỆ THỐNG
-- =========================================================
IF NOT EXISTS (SELECT 1 FROM nhat_ky_he_thong WHERE tai_khoan_id = (SELECT id FROM tai_khoan WHERE ten_dang_nhap = N'admin') AND hanh_dong = N'ĐĂNG NHẬP')
BEGIN
    INSERT INTO nhat_ky_he_thong (tai_khoan_id, hanh_dong, chi_tiet, ngay_thuc_hien, ip)
    VALUES ((SELECT id FROM tai_khoan WHERE ten_dang_nhap = N'admin'), N'ĐĂNG NHẬP', N'Đăng nhập thành công', GETDATE(), N'127.0.0.1')
END;
