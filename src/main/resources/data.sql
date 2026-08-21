SET QUOTED_IDENTIFIER ON
SET ANSI_NULLS ON
GO
IF NOT EXISTS (SELECT 1 FROM vai_tro WHERE ma_vai_tro = N'ADMIN') INSERT INTO vai_tro (ma_vai_tro, ten_vai_tro, mo_ta, trang_thai, ngay_tao) VALUES (N'ADMIN', N'Quản trị viên', N'Toàn quyền hệ thống', 1, GETDATE())
IF NOT EXISTS (SELECT 1 FROM vai_tro WHERE ma_vai_tro = N'GV') INSERT INTO vai_tro (ma_vai_tro, ten_vai_tro, mo_ta, trang_thai, ngay_tao) VALUES (N'GV', N'Giáo viên', N'Quản lý lớp học và điểm danh', 1, GETDATE())
IF NOT EXISTS (SELECT 1 FROM vai_tro WHERE ma_vai_tro = N'HV') INSERT INTO vai_tro (ma_vai_tro, ten_vai_tro, mo_ta, trang_thai, ngay_tao) VALUES (N'HV', N'Học viên', N'Xem lịch học và kết quả', 1, GETDATE())
IF NOT EXISTS (SELECT 1 FROM vai_tro WHERE ma_vai_tro = N'NV') INSERT INTO vai_tro (ma_vai_tro, ten_vai_tro, mo_ta, trang_thai, ngay_tao) VALUES (N'NV', N'Nhân viên', N'Nhân viên trung tâm hỗ trợ', 1, GETDATE())
GO
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'vai-tro' AND ma = N'ADMIN') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'vai-tro', N'ADMIN', N'Quản trị viên', 1)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'vai-tro' AND ma = N'NV') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'vai-tro', N'NV', N'Nhân viên', 2)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'vai-tro' AND ma = N'GV') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'vai-tro', N'GV', N'Giáo viên', 3)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'vai-tro' AND ma = N'HV') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'vai-tro', N'HV', N'Học viên', 4)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'hang-bang' AND ma = N'A1') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'hang-bang', N'A1', N'Hạng A1', 1)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'hang-bang' AND ma = N'A2') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'hang-bang', N'A2', N'Hạng A2', 2)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'hang-bang' AND ma = N'B1') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'hang-bang', N'B1', N'Hạng B1', 3)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'hang-bang' AND ma = N'B2') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'hang-bang', N'B2', N'Hạng B2', 4)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'hang-bang' AND ma = N'C') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'hang-bang', N'C', N'Hạng C', 5)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'hang-bang' AND ma = N'D') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'hang-bang', N'D', N'Hạng D', 6)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'hang-bang' AND ma = N'E') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'hang-bang', N'E', N'Hạng E', 7)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'loai-mon' AND ma = N'LY_THUYET') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'loai-mon', N'LY_THUYET', N'Lý thuyết', 1)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'loai-mon' AND ma = N'THUC_HANH') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'loai-mon', N'THUC_HANH', N'Thực hành', 2)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'loai-mon' AND ma = N'MO_PHONG') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'loai-mon', N'MO_PHONG', N'Mô phỏng', 3)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'trang-thai-lop' AND ma = N'DANG_HOC') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'trang-thai-lop', N'DANG_HOC', N'Đang học', 1)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'trang-thai-lop' AND ma = N'DA_KET_THUC') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'trang-thai-lop', N'DA_KET_THUC', N'Đã kết thúc', 2)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'trang-thai-lop' AND ma = N'TAM_DUNG') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'trang-thai-lop', N'TAM_DUNG', N'Tạm dừng', 3)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'trang-thai-phong' AND ma = N'DANG_SU_DUNG') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'trang-thai-phong', N'DANG_SU_DUNG', N'Đang sử dụng', 1)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'trang-thai-phong' AND ma = N'TRONG') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'trang-thai-phong', N'TRONG', N'Trống', 2)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'trang-thai-phong' AND ma = N'BAO_TRI') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'trang-thai-phong', N'BAO_TRI', N'Bảo trì', 3)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'trang-thai-phong-thi' AND ma = N'HOAT_DONG') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'trang-thai-phong-thi', N'HOAT_DONG', N'Hoạt động', 1)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'trang-thai-phong-thi' AND ma = N'BAO_TRI') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'trang-thai-phong-thi', N'BAO_TRI', N'Bảo trì', 2)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'trang-thai-phong-thi' AND ma = N'NGUNG_SU_DUNG') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'trang-thai-phong-thi', N'NGUNG_SU_DUNG', N'Ngừng sử dụng', 3)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'trang-thai-xe' AND ma = N'DANG_SU_DUNG') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'trang-thai-xe', N'DANG_SU_DUNG', N'Đang sử dụng', 1)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'trang-thai-xe' AND ma = N'TRONG') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'trang-thai-xe', N'TRONG', N'Trống', 2)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'trang-thai-xe' AND ma = N'BAO_TRI') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'trang-thai-xe', N'BAO_TRI', N'Bảo trì', 3)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'pt-thanh-toan' AND ma = N'TIEN_MAT') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'pt-thanh-toan', N'TIEN_MAT', N'Tiền mặt', 1)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'pt-thanh-toan' AND ma = N'CHUYEN_KHOAN') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'pt-thanh-toan', N'CHUYEN_KHOAN', N'Chuyển khoản', 2)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'pt-thanh-toan' AND ma = N'VI_DIEN_TU') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'pt-thanh-toan', N'VI_DIEN_TU', N'Ví điện tử', 3)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'pt-thanh-toan' AND ma = N'QR_CODE') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'pt-thanh-toan', N'QR_CODE', N'Quét mã QR', 4)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'trang-thai-tt' AND ma = N'DA_THANH_TOAN') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'trang-thai-tt', N'DA_THANH_TOAN', N'Đã thanh toán', 1)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'trang-thai-tt' AND ma = N'CHUA_THANH_TOAN') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'trang-thai-tt', N'CHUA_THANH_TOAN', N'Chưa thanh toán', 2)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'trang-thai-tt' AND ma = N'THANH_TOAN_MOT_PHAN') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'trang-thai-tt', N'THANH_TOAN_MOT_PHAN', N'Thanh toán một phần', 3)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'trang-thai-dk' AND ma = N'CHO_DUYET') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'trang-thai-dk', N'CHO_DUYET', N'Chờ duyệt', 1)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'trang-thai-dk' AND ma = N'DA_DUYET') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'trang-thai-dk', N'DA_DUYET', N'Đã duyệt', 2)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'trang-thai-dk' AND ma = N'DANG_HOC') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'trang-thai-dk', N'DANG_HOC', N'Đang học', 3)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'trang-thai-dk' AND ma = N'HOAN_THANH') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'trang-thai-dk', N'HOAN_THANH', N'Hoàn thành', 4)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'trang-thai-dk' AND ma = N'DA_HUY') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'trang-thai-dk', N'DA_HUY', N'Đã hủy', 5)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'doi-tuong-tb' AND ma = N'TAT_CA') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'doi-tuong-tb', N'TAT_CA', N'Tất cả', 1)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'doi-tuong-tb' AND ma = N'HOC_VIEN') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'doi-tuong-tb', N'HOC_VIEN', N'Học viên', 2)
IF NOT EXISTS (SELECT 1 FROM danh_muc WHERE nhom = N'doi-tuong-tb' AND ma = N'GIAO_VIEN') INSERT INTO danh_muc (nhom, ma, ten, thu_tu) VALUES (N'doi-tuong-tb', N'GIAO_VIEN', N'Giáo viên', 3)
GO
IF NOT EXISTS (SELECT 1 FROM tai_khoan WHERE ten_dang_nhap = N'admin') INSERT INTO tai_khoan (ten_dang_nhap, mat_khau, ho_ten, email, so_dien_thoai, vai_tro_id, trang_thai, so_lan_dang_nhap_sai, ngay_tao) VALUES (N'admin', N'123456', N'Nguyễn Quản Trị', N'admin@gmail.com', N'0901112223', (SELECT id FROM vai_tro WHERE ma_vai_tro = N'ADMIN'), N'ACTIVE', 0, GETDATE())
IF NOT EXISTS (SELECT 1 FROM tai_khoan WHERE ten_dang_nhap = N'giaovien1') INSERT INTO tai_khoan (ten_dang_nhap, mat_khau, ho_ten, email, so_dien_thoai, vai_tro_id, trang_thai, so_lan_dang_nhap_sai, ngay_tao) VALUES (N'giaovien1', N'123456', N'Trần Văn Giáo', N'gv1@gmail.com', N'0904445556', (SELECT id FROM vai_tro WHERE ma_vai_tro = N'GV'), N'ACTIVE', 0, GETDATE())
IF NOT EXISTS (SELECT 1 FROM tai_khoan WHERE ten_dang_nhap = N'hocvien1') INSERT INTO tai_khoan (ten_dang_nhap, mat_khau, ho_ten, email, so_dien_thoai, vai_tro_id, trang_thai, so_lan_dang_nhap_sai, ngay_tao) VALUES (N'hocvien1', N'123456', N'Lê Văn Học', N'hv1@gmail.com', N'0907778889', (SELECT id FROM vai_tro WHERE ma_vai_tro = N'HV'), N'ACTIVE', 0, GETDATE())
IF NOT EXISTS (SELECT 1 FROM tai_khoan WHERE ten_dang_nhap = N'nhanvien1') INSERT INTO tai_khoan (ten_dang_nhap, mat_khau, ho_ten, email, so_dien_thoai, vai_tro_id, trang_thai, so_lan_dang_nhap_sai, ngay_tao) VALUES (N'nhanvien1', N'123456', N'Lê Thị Nhân', N'nv1@gmail.com', N'0908889990', (SELECT id FROM vai_tro WHERE ma_vai_tro = N'NV'), N'ACTIVE', 0, GETDATE())
GO
IF NOT EXISTS (SELECT 1 FROM hoc_vien WHERE hoten = N'Nguyễn Văn A' AND cccd = N'123456789001') INSERT INTO hoc_vien (hoten, ngaysinh, gioitinh, cccd, sodienthoai, email, diachi, ngaydangky) VALUES (N'Nguyễn Văn A', '2000-01-01', N'Nam', N'123456789001', N'0911223344', N'vana@gmail.com', N'Quận 1, TP.HCM', '2026-01-10')
IF NOT EXISTS (SELECT 1 FROM hoc_vien WHERE hoten = N'Trần Thị B' AND cccd = N'123456789002') INSERT INTO hoc_vien (hoten, ngaysinh, gioitinh, cccd, sodienthoai, email, diachi, ngaydangky) VALUES (N'Trần Thị B', '2002-05-15', N'Nữ', N'123456789002', N'0922334455', N'thib@gmail.com', N'Quận 3, TP.HCM', '2026-01-12')
IF NOT EXISTS (SELECT 1 FROM hoc_vien WHERE hoten = N'Phạm Văn C' AND cccd = N'123456789003') INSERT INTO hoc_vien (hoten, ngaysinh, gioitinh, cccd, sodienthoai, email, diachi, ngaydangky) VALUES (N'Phạm Văn C', '1998-11-20', N'Nam', N'123456789003', N'0933445566', N'vanc@gmail.com', N'Quận 7, TP.HCM', '2026-01-15')
GO
IF NOT EXISTS (SELECT 1 FROM giao_vien WHERE sodienthoai = N'0944556677') INSERT INTO giao_vien (hoten, ngaysinh, gioitinh, sodienthoai, email, diachi, hangday) VALUES (N'Nguyễn Văn Thầy', '1980-03-10', N'Nam', N'0944556677', N'thaynguyen@gmail.com', N'Quận 4, TP.HCM', N'Hạng 1')
IF NOT EXISTS (SELECT 1 FROM giao_vien WHERE sodienthoai = N'0955667788') INSERT INTO giao_vien (hoten, ngaysinh, gioitinh, sodienthoai, email, diachi, hangday) VALUES (N'Lê Thị Cô', '1985-07-22', N'Nữ', N'0955667788', N'cole@gmail.com', N'Quận 5, TP.HCM', N'Hạng 2')
GO
IF NOT EXISTS (SELECT 1 FROM chuong_trinh_hoc WHERE tenchuongtrinh = N'Đào tạo lái xe hạng B2') INSERT INTO chuong_trinh_hoc (tenchuongtrinh, hangbang, hocphi, sobuoilythuyet, sobuoithuchanh) VALUES (N'Đào tạo lái xe hạng B2', N'B2', 15000000, 20, 40)
IF NOT EXISTS (SELECT 1 FROM chuong_trinh_hoc WHERE tenchuongtrinh = N'Đào tạo lái xe hạng C') INSERT INTO chuong_trinh_hoc (tenchuongtrinh, hangbang, hocphi, sobuoilythuyet, sobuoithuchanh) VALUES (N'Đào tạo lái xe hạng C', N'C', 20000000, 25, 50)
GO
IF NOT EXISTS (SELECT 1 FROM khoa_hoc WHERE tenkhoahoc = N'Khóa B2 - Tháng 7/2026') INSERT INTO khoa_hoc (tenkhoahoc, macth, ngaybatdau, ngayketthuc, trangthai) VALUES (N'Khóa B2 - Tháng 7/2026', (SELECT macth FROM chuong_trinh_hoc WHERE hangbang = N'B2'), '2026-07-01', '2026-12-31', N'OPEN')
IF NOT EXISTS (SELECT 1 FROM khoa_hoc WHERE tenkhoahoc = N'Khóa C - Tháng 7/2026') INSERT INTO khoa_hoc (tenkhoahoc, macth, ngaybatdau, ngayketthuc, trangthai) VALUES (N'Khóa C - Tháng 7/2026', (SELECT macth FROM chuong_trinh_hoc WHERE hangbang = N'C'), '2026-07-01', '2026-12-31', N'OPEN')
GO
IF NOT EXISTS (SELECT 1 FROM lop_hoc WHERE tenlop = N'Lớp B2_01') INSERT INTO lop_hoc (tenlop, makh, magv, soluong, trangthai) VALUES (N'Lớp B2_01', (SELECT makh FROM khoa_hoc WHERE tenkhoahoc = N'Khóa B2 - Tháng 7/2026'), (SELECT magv FROM giao_vien WHERE hoten = N'Nguyễn Văn Thầy'), 20, N'ACTIVE')
IF NOT EXISTS (SELECT 1 FROM lop_hoc WHERE tenlop = N'Lớp C_01') INSERT INTO lop_hoc (tenlop, makh, magv, soluong, trangthai) VALUES (N'Lớp C_01', (SELECT makh FROM khoa_hoc WHERE tenkhoahoc = N'Khóa C - Tháng 7/2026'), (SELECT magv FROM giao_vien WHERE hoten = N'Lê Thị Cô'), 15, N'ACTIVE')
GO
IF NOT EXISTS (SELECT 1 FROM phong_hoc WHERE tenphong = N'Phòng Lý Thuyết 1') INSERT INTO phong_hoc (tenphong, succhua, trangthai) VALUES (N'Phòng Lý Thuyết 1', 30, N'AVAILABLE')
IF NOT EXISTS (SELECT 1 FROM phong_hoc WHERE tenphong = N'Phòng Lý Thuyết 2') INSERT INTO phong_hoc (tenphong, succhua, trangthai) VALUES (N'Phòng Lý Thuyết 2', 30, N'AVAILABLE')
IF NOT EXISTS (SELECT 1 FROM phong_hoc WHERE tenphong = N'Sân Tập Lái 1') INSERT INTO phong_hoc (tenphong, succhua, trangthai) VALUES (N'Sân Tập Lái 1', 10, N'AVAILABLE')
GO
IF NOT EXISTS (SELECT 1 FROM ca_hoc WHERE tencahoc = N'Ca Sáng') INSERT INTO ca_hoc (tencahoc, giobatdau, gioketthuc) VALUES (N'Ca Sáng', '07:00:00', '11:00:00')
IF NOT EXISTS (SELECT 1 FROM ca_hoc WHERE tencahoc = N'Ca Chiều') INSERT INTO ca_hoc (tencahoc, giobatdau, gioketthuc) VALUES (N'Ca Chiều', '13:00:00', '17:00:00')
GO
IF NOT EXISTS (SELECT 1 FROM mon_hoc WHERE tenmonhoc = N'Luật Giao Thông Đường Bộ') INSERT INTO mon_hoc (tenmonhoc, so_tiet) VALUES (N'Luật Giao Thông Đường Bộ', 40)
IF NOT EXISTS (SELECT 1 FROM mon_hoc WHERE tenmonhoc = N'Kỹ thuật lái xe cơ bản') INSERT INTO mon_hoc (tenmonhoc, so_tiet) VALUES (N'Kỹ thuật lái xe cơ bản', 60)
IF NOT EXISTS (SELECT 1 FROM mon_hoc WHERE tenmonhoc = N'Đạo đức người lái xe') INSERT INTO mon_hoc (tenmonhoc, so_tiet) VALUES (N'Đạo đức người lái xe', 10)
GO
IF NOT EXISTS (SELECT 1 FROM lich_hoc WHERE ghichu = N'Học lý thuyết chương 1') INSERT INTO lich_hoc (malop, mamh, magv, maphong, macahoc, ngayhoc, ghichu) VALUES ((SELECT malop FROM lop_hoc WHERE tenlop = N'Lớp B2_01'), (SELECT mamh FROM mon_hoc WHERE tenmonhoc = N'Luật Giao Thông Đường Bộ'), (SELECT magv FROM giao_vien WHERE hoten = N'Nguyễn Văn Thầy'), (SELECT maphong FROM phong_hoc WHERE tenphong = N'Phòng Lý Thuyết 1'), (SELECT macahoc FROM ca_hoc WHERE tencahoc = N'Ca Sáng'), '2026-07-15', N'Học lý thuyết chương 1')
GO
IF NOT EXISTS (SELECT 1 FROM xe WHERE bienso = N'51A-123.45') INSERT INTO xe (bienso, loaixe, hangxe, namsanxuat, trangthai) VALUES (N'51A-123.45', N'Xe tập lái B2', N'Toyota', 2022, N'AVAILABLE')
IF NOT EXISTS (SELECT 1 FROM xe WHERE bienso = N'51A-678.90') INSERT INTO xe (bienso, loaixe, hangxe, namsanxuat, trangthai) VALUES (N'51A-678.90', N'Xe tập lái C', N'Hyundai', 2021, N'AVAILABLE')
GO
IF NOT EXISTS (SELECT 1 FROM xe_tap_lai WHERE maxe = (SELECT maxe FROM xe WHERE bienso = N'51A-123.45')) INSERT INTO xe_tap_lai (maxe, hangbang, ngaydangkiem, handangkiem, ghichu) VALUES ((SELECT maxe FROM xe WHERE bienso = N'51A-123.45'), N'B2', '2026-01-01', '2027-01-01', N'Xe mới')
GO
IF NOT EXISTS (SELECT 1 FROM thong_bao WHERE tieu_de = N'Thông báo nghỉ lễ') INSERT INTO thong_bao (tieu_de, noi_dung, doituong) VALUES (N'Thông báo nghỉ lễ', N'Trung tâm nghỉ lễ từ ngày 2/9 đến hết 3/9', N'TẤT CẢ')
GO
IF NOT EXISTS (SELECT 1 FROM ho_so_hoc_vien WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A') AND ghichu = N'Hồ sơ đầy đủ') INSERT INTO ho_so_hoc_vien (mahv, ngaydangky, trang_thai_duyet, ghichu, anh_canh_cuoc, file_ho_so) VALUES ((SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A'), '2026-01-10', N'ĐÃ DUYỆT', N'Hồ sơ đầy đủ', N'anh_a.jpg', N'hoso_a.pdf')
IF NOT EXISTS (SELECT 1 FROM ho_so_hoc_vien WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Trần Thị B') AND ghichu = N'Hồ sơ thiếu CCCD') INSERT INTO ho_so_hoc_vien (mahv, ngaydangky, trang_thai_duyet, ghichu, anh_canh_cuoc, file_ho_so) VALUES ((SELECT mahv FROM hoc_vien WHERE hoten = N'Trần Thị B'), '2026-01-12', N'CHỜ DUYỆT', N'Hồ sơ thiếu CCCD', N'anh_b.jpg', N'hoso_b.pdf')
GO
IF NOT EXISTS (SELECT 1 FROM diem_danh WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A') AND malich = (SELECT malich FROM lich_hoc WHERE ghichu = N'Học lý thuyết chương 1')) INSERT INTO diem_danh (mahv, malich, ngaydiemdanh, trangthai, ghichu) VALUES ((SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A'), (SELECT malich FROM lich_hoc WHERE ghichu = N'Học lý thuyết chương 1'), '2026-07-15', N'CÓ_MẶT', N'Đi học đầy đủ')
GO
IF NOT EXISTS (SELECT 1 FROM phan_cong WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A') AND magv = (SELECT magv FROM giao_vien WHERE hoten = N'Nguyễn Văn Thầy')) INSERT INTO phan_cong (mahv, magv, maxe, ngay_phan_cong, ghichu) VALUES ((SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A'), (SELECT magv FROM giao_vien WHERE hoten = N'Nguyễn Văn Thầy'), (SELECT maxe FROM xe WHERE bienso = N'51A-123.45'), '2026-07-16', N'Phân công thực hành')
GO
IF NOT EXISTS (SELECT 1 FROM bang_diem_thuong_xuyen WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A') AND malop = (SELECT malop FROM lop_hoc WHERE tenlop = N'Lớp B2_01') AND mamh = (SELECT mamh FROM mon_hoc WHERE tenmonhoc = N'Luật Giao Thông Đường Bộ')) INSERT INTO bang_diem_thuong_xuyen (mahv, malop, mamh, diem, ngay_cham, ghichu) VALUES ((SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A'), (SELECT malop FROM lop_hoc WHERE tenlop = N'Lớp B2_01'), (SELECT mamh FROM mon_hoc WHERE tenmonhoc = N'Luật Giao Thông Đường Bộ'), 8.5, '2026-07-20', N'Điểm kiểm tra 1')
GO
IF NOT EXISTS (SELECT 1 FROM phong_thi WHERE tenphong = N'Phòng Thi 1') INSERT INTO phong_thi (tenphong, succhua, diadiem, trangthai) VALUES (N'Phòng Thi 1', 20, N'Tầng 2, Cơ sở chính', N'HOAT_DONG')
IF NOT EXISTS (SELECT 1 FROM phong_thi WHERE tenphong = N'Phòng Thi 2') INSERT INTO phong_thi (tenphong, succhua, diadiem, trangthai) VALUES (N'Phòng Thi 2', 20, N'Tầng 2, Cơ sở chính', N'HOAT_DONG')
GO
IF NOT EXISTS (SELECT 1 FROM thi_sat_hach WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A')) INSERT INTO thi_sat_hach (mahv, malichthi, ketqua, ngaythi, ghichu) VALUES ((SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A'), NULL, N'ĐẬU', '2026-08-01', N'Thi sát hạch lần 1')
GO
IF NOT EXISTS (SELECT 1 FROM tra_gplx WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A') AND sogplx = N'GPLX-B2-001') INSERT INTO tra_gplx (mahv, mathi, sogplx, hanggplx, ngaycap, ngayhethan, trangthai) VALUES ((SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A'), (SELECT mathi FROM thi_sat_hach WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A')), N'GPLX-B2-001', N'B2', '2026-08-05', '2032-08-05', N'DA_CAP')
GO
IF NOT EXISTS (SELECT 1 FROM dang_ky_khoa_hoc WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A')) INSERT INTO dang_ky_khoa_hoc (mahv, makh, ngaydangky, trangthai) VALUES ((SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A'), (SELECT makh FROM khoa_hoc WHERE tenkhoahoc = N'Khóa B2 - Tháng 7/2026'), '2026-01-10', N'ĐÃ ĐĂNG KÝ')
GO
IF NOT EXISTS (SELECT 1 FROM dang_ky_khoa_hoc WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Trần Thị B')) INSERT INTO dang_ky_khoa_hoc (mahv, makh, ngaydangky, trangthai) VALUES ((SELECT mahv FROM hoc_vien WHERE hoten = N'Trần Thị B'), (SELECT makh FROM khoa_hoc WHERE tenkhoahoc = N'Khóa B2 - Tháng 7/2026'), '2026-01-12', N'ĐÃ ĐĂNG KÝ')
GO
IF NOT EXISTS (SELECT 1 FROM dang_ky_khoa_hoc WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Phạm Văn C')) INSERT INTO dang_ky_khoa_hoc (mahv, makh, ngaydangky, trangthai) VALUES ((SELECT mahv FROM hoc_vien WHERE hoten = N'Phạm Văn C'), (SELECT makh FROM khoa_hoc WHERE tenkhoahoc = N'Khóa B2 - Tháng 7/2026'), '2026-01-15', N'CHỜ DUYỆT')
GO
IF NOT EXISTS (SELECT 1 FROM thanh_toan WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A')) INSERT INTO thanh_toan (mahv, madk, sotien, phuongthuc, trangthai, ngaythanhtoan) VALUES ((SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A'), (SELECT madk FROM dang_ky_khoa_hoc WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A')), 15000000, N'TIỀN MẶT', N'ĐÃ THANH TOÁN', '2026-07-02')
GO
IF NOT EXISTS (SELECT 1 FROM tin_tuc WHERE tieu_de = N'Khai giảng khóa học tháng 7/2026') INSERT INTO tin_tuc (tieu_de, mo_ta_ngan, noi_dung, hinh_anh, tac_gia, trang_thai, ngay_dang) VALUES (N'Khai giảng khóa học tháng 7/2026', N'Thông báo khai giảng các khóa B2, C', N'Trung tâm DriveHub chính thức khai giảng khóa học tháng 7/2026 với nhiều ưu đãi.', N'/tin-tuc-khai-giang.jpg', N'DriveHub', N'ĐÃ ĐĂNG', '2026-06-01')
GO
IF NOT EXISTS (SELECT 1 FROM nhat_ky_he_thong WHERE tai_khoan_id = (SELECT id FROM tai_khoan WHERE ten_dang_nhap = N'admin') AND hanh_dong = N'ĐĂNG NHẬP') INSERT INTO nhat_ky_he_thong (tai_khoan_id, hanh_dong, chi_tiet, ngay_thuc_hien, ip) VALUES ((SELECT id FROM tai_khoan WHERE ten_dang_nhap = N'admin'), N'ĐĂNG NHẬP', N'Đăng nhập thành công', GETDATE(), N'127.0.0.1')
GO
-- Lien ket tai khoan giao vien (giaovien1) voi ho so giao_vien (Nguyen Van Thay) qua CCCD
UPDATE tai_khoan SET cccd = N'079123456001' WHERE ten_dang_nhap = N'giaovien1' AND cccd IS NULL;
UPDATE giao_vien SET cccd = N'079123456001' WHERE hoten = N'Nguyễn Văn Thầy' AND cccd IS NULL;
UPDATE tai_khoan SET ho_ten = N'Nguyễn Văn Thầy' WHERE ten_dang_nhap = N'giaovien1' AND cccd = N'079123456001';
GO