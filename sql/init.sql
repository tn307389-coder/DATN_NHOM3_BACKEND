CREATE DATABASE duantotnghiep01;
GO
USE duantotnghiep01;
GO

-- =========================================================
-- PHẦN 1: TẠO CÁC BẢNG (Theo thứ tự để không lỗi khóa ngoại)
-- =========================================================

-- Bảng VaiTro
CREATE TABLE vai_tro (
    id INT PRIMARY KEY IDENTITY(1,1),
    ma_vai_tro NVARCHAR(30) UNIQUE NOT NULL,
    ten_vai_tro NVARCHAR(100) UNIQUE NOT NULL, 1
    mo_ta NVARCHAR(255),
    trang_thai BIT NOT NULL DEFAULT 1,
    ngay_tao DATETIME2 NOT NULL DEFAULT GETDATE(),
    ngay_cap_nhat DATETIME2
);

-- Bảng TaiKhoan
CREATE TABLE tai_khoan (
    id INT PRIMARY KEY IDENTITY(1,1),
    ten_dang_nhap NVARCHAR(50) UNIQUE NOT NULL,
    mat_khau NVARCHAR(100) NOT NULL,
    ho_ten NVARCHAR(120) NOT NULL,
    email NVARCHAR(150) UNIQUE,
    so_dien_thoai NVARCHAR(15),
    vai_tro_id INT NOT NULL,
    trang_thai NVARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    so_lan_dang_nhap_sai INT NOT NULL DEFAULT 0,
    khoa_den DATETIME2,
    lan_dang_nhap_cuoi DATETIME2,
    ngay_tao DATETIME2 NOT NULL DEFAULT GETDATE(),
    ngay_cap_nhat DATETIME2,
    nguoi_tao_id INT,
    CONSTRAINT FK_TaiKhoan_VaiTro FOREIGN KEY (vai_tro_id) REFERENCES vai_tro(id),
    CONSTRAINT FK_TaiKhoan_NguoiTao FOREIGN KEY (nguoi_tao_id) REFERENCES tai_khoan(id)
);

-- Bảng HocVien
CREATE TABLE hoc_vien (
    mahv INT PRIMARY KEY IDENTITY(1,1),
    hoten NVARCHAR(100),
    ngaysinh DATE,
    gioitinh NVARCHAR(10),
    cccd NVARCHAR(20),
    sodienthoai NVARCHAR(15),
    email NVARCHAR(100),
    diachi NVARCHAR(255),
    ngaydangky DATE
);

-- Bảng GiaoVien
CREATE TABLE giao_vien (
    magv INT PRIMARY KEY IDENTITY(1,1),
    hoten NVARCHAR(100),
    ngaysinh DATE,
    gioitinh NVARCHAR(10),
    sodienthoai NVARCHAR(15),
    email NVARCHAR(100),
    diachi NVARCHAR(255),
    hangday NVARCHAR(50)
);

-- Bảng ChuongTrinhHoc
CREATE TABLE chuong_trinh_hoc (
    macth INT PRIMARY KEY IDENTITY(1,1),
    tenchuongtrinh NVARCHAR(200),
    hangbang NVARCHAR(50),
    hocphi DECIMAL(18,2),
    sobuoilythuyet INT,
    sobuoithuchanh INT
);

-- Bảng KhoaHoc
CREATE TABLE khoa_hoc (
    makh INT PRIMARY KEY IDENTITY(1,1),
    tenkhoahoc NVARCHAR(200),
    macth INT,
    ngaybatdau DATE,
    ngayketthuc DATE,
    trangthai NVARCHAR(50),
    CONSTRAINT FK_KhoaHoc_CTH FOREIGN KEY (macth) REFERENCES chuong_trinh_hoc(macth)
);

-- Bảng LopHoc
CREATE TABLE lop_hoc (
    malop INT PRIMARY KEY IDENTITY(1,1),
    tenlop NVARCHAR(100),
    makh INT,
    magv INT,
    soluong INT,
    trangthai NVARCHAR(50),
    CONSTRAINT FK_LopHoc_KH FOREIGN KEY (makh) REFERENCES khoa_hoc(makh),
    CONSTRAINT FK_LopHoc_GV FOREIGN KEY (magv) REFERENCES giao_vien(magv)
);

-- Bảng PhongHoc
CREATE TABLE phong_hoc (
    maphong INT PRIMARY KEY IDENTITY(1,1),
    tenphong NVARCHAR(100),
    succhua INT,
    trangthai NVARCHAR(50)
);

-- Bảng CaHoc (Sửa theo chuẩn Code Java)
CREATE TABLE ca_hoc (
    macahoc INT PRIMARY KEY IDENTITY(1,1),
    tencahoc NVARCHAR(100),
    giobatdau TIME,
    gioketthuc TIME
);

-- Bảng MonHoc
CREATE TABLE mon_hoc (
    mamh INT PRIMARY KEY IDENTITY(1,1),
    tenmonhoc NVARCHAR(200),
    so_tiet INT
);

-- Bảng LichHoc (Sửa theo chuẩn Code Java)
CREATE TABLE lich_hoc (
    malich INT PRIMARY KEY IDENTITY(1,1),
    malop INT,
    mamh INT,
    magv INT,
    maphong INT,
    macahoc INT,
    ngayhoc DATE,
    ghichu NVARCHAR(255),
    CONSTRAINT FK_LichHoc_Lop FOREIGN KEY (malop) REFERENCES lop_hoc(malop),
    CONSTRAINT FK_LichHoc_Mon FOREIGN KEY (mamh) REFERENCES mon_hoc(mamh),
    CONSTRAINT FK_LichHoc_GV FOREIGN KEY (magv) REFERENCES giao_vien(magv),
    CONSTRAINT FK_LichHoc_Phong FOREIGN KEY (maphong) REFERENCES phong_hoc(maphong),
    CONSTRAINT FK_LichHoc_Ca FOREIGN KEY (macahoc) REFERENCES ca_hoc(macahoc)
);

-- Bảng Xe (Sửa theo chuẩn Code Java)
CREATE TABLE xe (
    maxe INT PRIMARY KEY IDENTITY(1,1),
    bienso NVARCHAR(20) UNIQUE NOT NULL,
    loaixe NVARCHAR(50),
    hangxe NVARCHAR(100),
    namsanxuat INT,
    trangthai NVARCHAR(50)
);

-- Bảng XeTapLai (Sửa theo chuẩn Code Java)
CREATE TABLE xe_tap_lai (
    maxetl INT PRIMARY KEY IDENTITY(1,1),
    maxe INT,
    hangbang NVARCHAR(50),
    ngaydangkiem DATE,
    handangkiem DATE,
    ghichu NVARCHAR(255),
    CONSTRAINT FK_XTL_Xe FOREIGN KEY (maxe) REFERENCES xe(maxe)
);

-- Các bảng bổ trợ khác
CREATE TABLE dang_ky_khoa_hoc (
    madk INT PRIMARY KEY IDENTITY(1,1),
    mahv INT,
    makh INT,
    ngaydangky DATE,
    trangthai NVARCHAR(50),
    CONSTRAINT FK_DKKH_HV FOREIGN KEY (mahv) REFERENCES hoc_vien(mahv),
    CONSTRAINT FK_DKKH_KH FOREIGN KEY (makh) REFERENCES khoa_hoc(makh)
);

CREATE TABLE thanh_toan (
    matt INT PRIMARY KEY IDENTITY(1,1),
    madk INT,
    mahv INT,
    sotien DECIMAL(18,2),
    ngaythanhtoan DATE,
    phuongthuc NVARCHAR(50),
    trangthai NVARCHAR(50),
    CONSTRAINT FK_TT_DKKH FOREIGN KEY (madk) REFERENCES dang_ky_khoa_hoc(madk),
    CONSTRAINT FK_TT_HV FOREIGN KEY (mahv) REFERENCES hoc_vien(mahv)
);

CREATE TABLE thong_bao (
    id INT PRIMARY KEY IDENTITY(1,1),
    tieu_de NVARCHAR(200),
    noi_dung NVARCHAR(MAX),
    ngay_tao DATETIME2 DEFAULT GETDATE(),
    doituong NVARCHAR(50)
);

CREATE TABLE nhat_ky_he_thong (
    id INT PRIMARY KEY IDENTITY(1,1),
    tai_khoan_id INT,
    hanh_dong NVARCHAR(255),
    chi_tiet NVARCHAR(MAX),
    ngay_thuc_hien DATETIME2 DEFAULT GETDATE(),
    ip NVARCHAR(50),
    CONSTRAINT FK_NKT_TK FOREIGN KEY (tai_khoan_id) REFERENCES tai_khoan(id)
);

CREATE TABLE hang_gplx (
    id INT PRIMARY KEY IDENTITY(1,1),
    ma_hang NVARCHAR(30) UNIQUE NOT NULL,
    ten_hang NVARCHAR(100) NOT NULL,
    mo_ta NVARCHAR(255)
);

CREATE TABLE tra_gplx (
    id INT PRIMARY KEY IDENTITY(1,1),
    mahv INT,
    mathi INT,
    sogplx NVARCHAR(50),
    hanggplx NVARCHAR(20),
    ngaycap DATE,
    ngayhethan DATE,
    trangthai NVARCHAR(50),
    CONSTRAINT FK_TraGPLX_HV FOREIGN KEY (mahv) REFERENCES hoc_vien(mahv)
);

CREATE TABLE phong_thi (
    maphongthi INT PRIMARY KEY IDENTITY(1,1),
    tenphong NVARCHAR(100),
    succhua INT,
    diadiem NVARCHAR(255),
    trangthai NVARCHAR(50)
);

CREATE TABLE ca_thi (
    macathi INT PRIMARY KEY IDENTITY(1,1),
    ten_ca NVARCHAR(50),
    gio_bat_dau TIME,
    gio_ket_thuc TIME
);

CREATE TABLE lich_thi (
    malichthi INT PRIMARY KEY IDENTITY(1,1),
    maphongthi INT,
    macathi INT,
    ngaythi DATE,
    ghichu NVARCHAR(255),
    CONSTRAINT FK_LT_Phong FOREIGN KEY (maphongthi) REFERENCES phong_thi(maphongthi),
    CONSTRAINT FK_LT_Ca FOREIGN KEY (macathi) REFERENCES ca_thi(macathi)
);

CREATE TABLE thi_sat_hach (
    mathi INT PRIMARY KEY IDENTITY(1,1),
    mahv INT,
    malichthi INT,
    ketqua NVARCHAR(50),
    ngaythi DATE,
    ghichu NVARCHAR(255),
    CONSTRAINT FK_TSH_HV FOREIGN KEY (mahv) REFERENCES hoc_vien(mahv),
    CONSTRAINT FK_TSH_Lich FOREIGN KEY (malichthi) REFERENCES lich_thi(malichthi)
);

CREATE TABLE ket_qua_thi (
    id INT PRIMARY KEY IDENTITY(1,1),
    mathi INT,
    diem DECIMAL(5,2),
    ketqua NVARCHAR(50),
    ghichu NVARCHAR(255),
    CONSTRAINT FK_KQT_TSH FOREIGN KEY (mathi) REFERENCES thi_sat_hach(mathi)
);

CREATE TABLE ho_so_hoc_vien (
    id INT PRIMARY KEY IDENTITY(1,1),
    mahv INT,
    ngaydangky DATE,
    trang_thai_duyet NVARCHAR(50),
    ghichu NVARCHAR(255),
    anh_canh_caan NVARCHAR(255),
    file_ho_so NVARCHAR(255),
    CONSTRAINT FK_HSHV_HV FOREIGN KEY (mahv) REFERENCES hoc_vien(mahv)
);

CREATE TABLE bang_diem_thuong_xuyen (
    id INT PRIMARY KEY IDENTITY(1,1),
    mahv INT,
    malop INT,
    mamh INT,
    diem DECIMAL(4,2),
    ngay_cham DATE,
    ghichu NVARCHAR(255),
    CONSTRAINT FK_BDTX_HV FOREIGN KEY (mahv) REFERENCES hoc_vien(mahv),
    CONSTRAINT FK_BDTX_Lop FOREIGN KEY (malop) REFERENCES lop_hoc(malop),
    CONSTRAINT FK_BDTX_MH FOREIGN KEY (mamh) REFERENCES mon_hoc(mamh)
);

CREATE TABLE phan_cong (
    id INT PRIMARY KEY IDENTITY(1,1),
    mahv INT,
    magv INT,
    maxetl INT,
    ngay_phan_cong DATE,
    ghichu NVARCHAR(255),
    CONSTRAINT FK_PC_HV FOREIGN KEY (mahv) REFERENCES hoc_vien(mahv),
    CONSTRAINT FK_PC_GV FOREIGN KEY (magv) REFERENCES giao_vien(magv),
    CONSTRAINT FK_PC_XTL FOREIGN KEY (maxetl) REFERENCES xe_tap_lai(maxetl)
);

CREATE TABLE diem_danh (
    madd INT PRIMARY KEY IDENTITY(1,1),
    mahv INT,
    malich INT,
    ngaydiemdanh DATE,
    trangthai NVARCHAR(50),
    ghichu NVARCHAR(255),
    CONSTRAINT FK_DD_HV FOREIGN KEY (mahv) REFERENCES hoc_vien(mahv),
    CONSTRAINT FK_DD_Lich FOREIGN KEY (malich) REFERENCES lich_hoc(malich)
);

CREATE TABLE tin_tuc (
    id INT PRIMARY KEY IDENTITY(1,1),
    tieu_de NVARCHAR(200) NOT NULL,
    hinh_anh NVARCHAR(255),
    mo_ta_ngan NVARCHAR(500),
    noi_dung NVARCHAR(MAX),
    ngay_dang DATETIME2 DEFAULT GETDATE(),
    tac_gia NVARCHAR(100),
    trang_thai NVARCHAR(30) DEFAULT N'ĐÃ ĐĂNG'
);

ALTER TABLE tra_gplx ADD CONSTRAINT FK_TraGPLX_TSH FOREIGN KEY (mathi) REFERENCES thi_sat_hach(mathi);
GO

-- =========================================================
-- PHẦN 2: CHÈN DỮ LIỆU MẪU (Sử dụng Subquery để an toàn)
-- =========================================================

INSERT INTO vai_tro (ma_vai_tro, ten_vai_tro, mo_ta) VALUES
(N'ADMIN', N'Quản trị viên', N'Toàn quyền hệ thống'),
(N'GV', N'Giáo viên', N'Quản lý lớp học và điểm danh'),
(N'HV', N'Học viên', N'Xem lịch học và kết quả'),
(N'NV', N'Nhân viên', N'Nhân viên trung tâm hỗ trợ');

INSERT INTO tai_khoan (ten_dang_nhap, mat_khau, ho_ten, email, so_dien_thoai, vai_tro_id, trang_thai) VALUES
(N'admin', N'123456', N'Nguyễn Quản Trị', N'admin@gmail.com', N'0901112223', (SELECT id FROM vai_tro WHERE ma_vai_tro = N'ADMIN'), N'ACTIVE'),
(N'giaovien1', N'123456', N'Trần Văn Giáo', N'gv1@gmail.com', N'0904445556', (SELECT id FROM vai_tro WHERE ma_vai_tro = N'GV'), N'ACTIVE'),
(N'hocvien1', N'123456', N'Lê Văn Học', N'hv1@gmail.com', N'0907778889', (SELECT id FROM vai_tro WHERE ma_vai_tro = N'HV'), N'ACTIVE'),
(N'nhanvien1', N'123456', N'Lê Thị Nhân', N'nv1@gmail.com', N'0908889990', (SELECT id FROM vai_tro WHERE ma_vai_tro = N'NV'), N'ACTIVE');

INSERT INTO hoc_vien (hoten, ngaysinh, gioitinh, cccd, sodienthoai, email, diachi, ngaydangky) VALUES
(N'Nguyễn Văn A', '2000-01-01', N'Nam', N'123456789001', N'0911223344', N'vana@gmail.com', N'Quận 1, TP.HCM', '2026-01-10'),
(N'Trần Thị B', '2002-05-15', N'Nữ', N'123456789002', N'0922334455', N'thib@gmail.com', N'Quận 3, TP.HCM', '2026-01-12'),
(N'Phạm Văn C', '1998-11-20', N'Nam', N'123456789003', N'0933445566', N'vanc@gmail.com', N'Quận 7, TP.HCM', '2026-01-15');

INSERT INTO giao_vien (hoten, ngaysinh, gioitinh, sodienthoai, email, diachi, hangday) VALUES
(N'Nguyễn Văn Thầy', '1980-03-10', N'Nam', N'0944556677', N'thaynguyen@gmail.com', N'Quận 4, TP.HCM', N'Hạng 1'),
(N'Lê Thị Cô', '1985-07-22', N'Nữ', N'0955667788', N'cole@gmail.com', N'Quận 5, TP.HCM', N'Hạng 2');

INSERT INTO chuong_trinh_hoc (tenchuongtrinh, hangbang, hocphi, sobuoilythuyet, sobuoithuchanh) VALUES
(N'Đào tạo lái xe hạng B2', N'B2', 15000000, 20, 40),
(N'Đào tạo lái xe hạng C', N'C', 20000000, 25, 50);

INSERT INTO khoa_hoc (tenkhoahoc, macth, ngaybatdau, ngayketthuc, trangthai) VALUES
(N'Khóa B2 - Tháng 7/2026', (SELECT macth FROM chuong_trinh_hoc WHERE hangbang = N'B2'), '2026-07-01', '2026-12-31', N'OPEN'),
(N'Khóa C - Tháng 7/2026', (SELECT macth FROM chuong_trinh_hoc WHERE hangbang = N'C'), '2026-07-01', '2026-12-31', N'OPEN');

INSERT INTO lop_hoc (tenlop, makh, magv, soluong, trangthai) VALUES
(N'Lớp B2_01', (SELECT makh FROM khoa_hoc WHERE tenkhoahoc LIKE N'Khóa B2%'), (SELECT magv FROM giao_vien WHERE hoten = N'Nguyễn Văn Thầy'), 20, N'ACTIVE'),
(N'Lớp C_01', (SELECT makh FROM khoa_hoc WHERE tenkhoahoc LIKE N'Khóa C%'), (SELECT magv FROM giao_vien WHERE hoten = N'Lê Thị Cô'), 15, N'ACTIVE');

INSERT INTO phong_hoc (tenphong, succhua, trangthai) VALUES
(N'Phòng Lý Thuyết 1', 30, N'AVAILABLE'),
(N'Phòng Lý Thuyết 2', 30, N'AVAILABLE'),
(N'Sân Tập Lái 1', 10, N'AVAILABLE');

INSERT INTO ca_hoc (tencahoc, giobatdau, gioketthuc) VALUES
(N'Ca Sáng', '07:00:00', '11:00:00'),
(N'Ca Chiều', '13:00:00', '17:00:00');

INSERT INTO mon_hoc (tenmonhoc, so_tiet) VALUES
(N'Luật Giao Thông Đường Bộ', 40),
(N'Kỹ thuật lái xe cơ bản', 60),
(N'Đạo đức người lái xe', 10);

INSERT INTO lich_hoc (malop, mamh, magv, maphong, macahoc, ngayhoc, ghichu) VALUES
((SELECT malop FROM lop_hoc WHERE tenlop = N'Lớp B2_01'), (SELECT mamh FROM mon_hoc WHERE tenmonhoc = N'Luật Giao Thông Đường Bộ'), (SELECT magv FROM giao_vien WHERE hoten = N'Nguyễn Văn Thầy'), (SELECT maphong FROM phong_hoc WHERE tenphong = N'Phòng Lý Thuyết 1'), (SELECT macahoc FROM ca_hoc WHERE tencahoc = N'Ca Sáng'), '2026-07-15', N'Học lý thuyết chương 1');

INSERT INTO xe (bienso, loaixe, hangxe, namsanxuat, trangthai) VALUES
(N'51A-123.45', N'Xe tập lái B2', N'Toyota', 2022, N'AVAILABLE'),
(N'51A-678.90', N'Xe tập lái C', N'Hyundai', 2021, N'AVAILABLE');

INSERT INTO xe_tap_lai (maxe, hangbang, ngaydangkiem, handangkiem, ghichu) VALUES
((SELECT maxe FROM xe WHERE bienso = N'51A-123.45'), N'B2', '2026-01-01', '2027-01-01', N'Xe mới');

INSERT INTO thong_bao (tieu_de, noi_dung, doituong) VALUES
(N'Thông báo nghỉ lễ', N'Trung tâm nghỉ lễ từ ngày 2/9 đến hết 3/9', N'TẤT CẢ');

INSERT INTO ho_so_hoc_vien (mahv, ngaydangky, trang_thai_duyet, ghichu, anh_canh_caan, file_ho_so) VALUES
((SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A'), '2026-01-10', N'ĐÃ DUYỆT', N'Hồ sơ đầy đủ', N'anh_a.jpg', N'hoso_a.pdf'),
((SELECT mahv FROM hoc_vien WHERE hoten = N'Trần Thị B'), '2026-01-12', N'CHỜ DUYỆT', N'Thiếu ảnh căn cước', N'anh_b.jpg', N'hoso_b.pdf'),
((SELECT mahv FROM hoc_vien WHERE hoten = N'Phạm Văn C'), '2026-01-15', N'ĐÃ DUYỆT', N'Hồ sơ hợp lệ', N'anh_c.jpg', N'hoso_c.pdf');

INSERT INTO diem_danh (mahv, malich, ngaydiemdanh, trangthai, ghichu) VALUES
((SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A'), 1, '2026-07-15', N'CÓ MẶT', N''),
((SELECT mahv FROM hoc_vien WHERE hoten = N'Trần Thị B'), 1, '2026-07-15', N'VẮNG', N'Nghỉ ốm');

INSERT INTO phan_cong (mahv, magv, maxetl, ngay_phan_cong, ghichu) VALUES
((SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A'), (SELECT magv FROM giao_vien WHERE hoten = N'Nguyễn Văn Thầy'), (SELECT maxetl FROM xe_tap_lai WHERE maxetl = 1), '2026-07-10', N'Xe tập lái Toyota'),
((SELECT mahv FROM hoc_vien WHERE hoten = N'Phạm Văn C'), (SELECT magv FROM giao_vien WHERE hoten = N'Lê Thị Cô'), (SELECT maxetl FROM xe_tap_lai WHERE maxetl = 1), '2026-07-11', N'Xe tập lái Toyota');

INSERT INTO bang_diem_thuong_xuyen (mahv, malop, mamh, diem, ngay_cham, ghichu) VALUES
((SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A'), (SELECT malop FROM lop_hoc WHERE tenlop = N'Lớp B2_01'), (SELECT mamh FROM mon_hoc WHERE tenmonhoc = N'Luật Giao Thông Đường Bộ'), 8.5, '2026-07-20', N'Tốt'),
((SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A'), (SELECT malop FROM lop_hoc WHERE tenlop = N'Lớp B2_01'), (SELECT mamh FROM mon_hoc WHERE tenmonhoc = N'Kỹ thuật lái xe cơ bản'), 9.0, '2026-07-22', N'Rất tốt');

INSERT INTO phong_thi (tenphong, succhua, diadiem, trangthai) VALUES
(N'Phòng Thi Sát Hạch 1', 40, N'Số 123 Đường Lê Lợi, Quận 1', N'AVAILABLE'),
(N'Phòng Thi Sát Hạch 2', 30, N'Số 456 Đường Nguyễn Huệ, Quận 1', N'AVAILABLE');

INSERT INTO thi_sat_hach (mahv, malichthi, ketqua, ngaythi, ghichu) VALUES
((SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A'), NULL, N'ĐẬU', '2026-08-15', N'Thi đạt yêu cầu');

INSERT INTO tra_gplx (mahv, mathi, sogplx, hanggplx, ngaycap, ngayhethan, trangthai) VALUES
((SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A'), (SELECT mathi FROM thi_sat_hach WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A')), N'83B1-123456', N'B2', '2026-08-20', '2032-08-20', N'ĐÃ TRẢ');

INSERT INTO ket_qua_thi (mathi, diem, ketqua, ghichu) VALUES
((SELECT mathi FROM thi_sat_hach WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A')), 9.0, N'ĐẬU', N'Kết quả thi sát hạch đạt');

INSERT INTO nhat_ky_he_thong (tai_khoan_id, hanh_dong, chi_tiet, ngay_thuc_hien, ip) VALUES
((SELECT id FROM tai_khoan WHERE ten_dang_nhap = N'admin'), N'ĐĂNG NHẬP', N'Đăng nhập thành công', GETDATE(), N'127.0.0.1');

INSERT INTO hang_gplx (ma_hang, ten_hang, mo_ta) VALUES
(N'A1', N'GPLX hạng A1', N'Xe mô tô hai bánh dung tích từ 50cc đến dưới 175cc'),
(N'A2', N'GPLX hạng A2', N'Xe mô tô hai bánh dung tích từ 175cc trở lên'),
(N'B1', N'GPLX hạng B1', N'Ô tô số tự động chở người đến 9 chỗ'),
(N'B2', N'GPLX hạng B2', N'Ô tô số tự động và số sàn chở người đến 9 chỗ'),
(N'C', N'GPLX hạng C', N'Ô tô tải, ô tô chuyên dùng trọng tải từ 3.500kg trở lên'),
(N'D', N'GPLX hạng D', N'Ô tô chở người từ 10 đến 30 chỗ'),
(N'E', N'GPLX hạng E', N'Ô tô chở người trên 30 chỗ'),
(N'F', N'GPLX hạng F', N'Kéo rơ moóc, sơ mi rơ moóc');

INSERT INTO ca_thi (ten_ca, gio_bat_dau, gio_ket_thuc) VALUES
(N'Ca Thi Sáng', '07:30:00', '10:30:00'),
(N'Ca Thi Chiều', '13:30:00', '16:30:00');

INSERT INTO dang_ky_khoa_hoc (mahv, makh, ngaydangky, trangthai) VALUES
((SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A'), (SELECT makh FROM khoa_hoc WHERE tenkhoahoc LIKE N'Khóa B2%'), '2026-01-10', N'ĐÃ ĐĂNG KÝ'),
((SELECT mahv FROM hoc_vien WHERE hoten = N'Trần Thị B'), (SELECT makh FROM khoa_hoc WHERE tenkhoahoc LIKE N'Khóa B2%'), '2026-01-12', N'ĐÃ ĐĂNG KÝ'),
((SELECT mahv FROM hoc_vien WHERE hoten = N'Phạm Văn C'), (SELECT makh FROM khoa_hoc WHERE tenkhoahoc LIKE N'Khóa C%'), '2026-01-15', N'ĐANG XỬ LÝ');

INSERT INTO thanh_toan (madk, mahv, sotien, ngaythanhtoan, phuongthuc, trangthai) VALUES
((SELECT madk FROM dang_ky_khoa_hoc WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A') AND makh = (SELECT makh FROM khoa_hoc WHERE tenkhoahoc LIKE N'Khóa B2%')), (SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A'), 15000000, '2026-01-11', N'CHUYỂN KHOẢN', N'ĐÃ THANH TOÁN'),
((SELECT madk FROM dang_ky_khoa_hoc WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Trần Thị B') AND makh = (SELECT makh FROM khoa_hoc WHERE tenkhoahoc LIKE N'Khóa B2%')), (SELECT mahv FROM hoc_vien WHERE hoten = N'Trần Thị B'), 15000000, '2026-01-13', N'TIỀN MẶT', N'ĐÃ THANH TOÁN'),
((SELECT madk FROM dang_ky_khoa_hoc WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A') AND makh = (SELECT makh FROM khoa_hoc WHERE tenkhoahoc LIKE N'Khóa B2%')), (SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A'), 5000000, '2026-02-10', N'CHUYỂN KHOẢN', N'ĐÃ THANH TOÁN'),
((SELECT madk FROM dang_ky_khoa_hoc WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Phạm Văn C') AND makh = (SELECT makh FROM khoa_hoc WHERE tenkhoahoc LIKE N'Khóa C%')), (SELECT mahv FROM hoc_vien WHERE hoten = N'Phạm Văn C'), 20000000, '2026-02-15', N'TIỀN MẶT', N'ĐÃ THANH TOÁN'),
((SELECT madk FROM dang_ky_khoa_hoc WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Trần Thị B') AND makh = (SELECT makh FROM khoa_hoc WHERE tenkhoahoc LIKE N'Khóa B2%')), (SELECT mahv FROM hoc_vien WHERE hoten = N'Trần Thị B'), 7500000, '2026-03-12', N'CHUYỂN KHOẢN', N'ĐÃ THANH TOÁN'),
((SELECT madk FROM dang_ky_khoa_hoc WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A') AND makh = (SELECT makh FROM khoa_hoc WHERE tenkhoahoc LIKE N'Khóa B2%')), (SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A'), 3000000, '2026-04-10', N'TIỀN MẶT', N'ĐÃ THANH TOÁN'),
((SELECT madk FROM dang_ky_khoa_hoc WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Phạm Văn C') AND makh = (SELECT makh FROM khoa_hoc WHERE tenkhoahoc LIKE N'Khóa C%')), (SELECT mahv FROM hoc_vien WHERE hoten = N'Phạm Văn C'), 10000000, '2026-05-20', N'CHUYỂN KHOẢN', N'ĐÃ THANH TOÁN'),
((SELECT madk FROM dang_ky_khoa_hoc WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Trần Thị B') AND makh = (SELECT makh FROM khoa_hoc WHERE tenkhoahoc LIKE N'Khóa B2%')), (SELECT mahv FROM hoc_vien WHERE hoten = N'Trần Thị B'), 8000000, '2026-06-15', N'CHUYỂN KHOẢN', N'ĐÃ THANH TOÁN'),
((SELECT madk FROM dang_ky_khoa_hoc WHERE mahv = (SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A') AND makh = (SELECT makh FROM khoa_hoc WHERE tenkhoahoc LIKE N'Khóa B2%')), (SELECT mahv FROM hoc_vien WHERE hoten = N'Nguyễn Văn A'), 12000000, '2026-07-10', N'TIỀN MẶT', N'ĐÃ THANH TOÁN');

INSERT INTO lich_thi (maphongthi, macathi, ngaythi, ghichu) VALUES
(1, (SELECT macathi FROM ca_thi WHERE ten_ca = N'Ca Thi Sáng'), '2026-08-15', N'Thi lý thuyết và thực hành hạng B2'),
(2, (SELECT macathi FROM ca_thi WHERE ten_ca = N'Ca Thi Chiều'), '2026-08-15', N'Thi sát hạch hạng C');

INSERT INTO tin_tuc (tieu_de, hinh_anh, mo_ta_ngan, noi_dung, ngay_dang, tac_gia, trang_thai) VALUES
(N'DriveHub khai giảng hàng nghìn khóa học lái xe B2 định kỳ mỗi tháng',
 N'/tin-tuc-b2.jpg',
 N'Chương trình đào tạo lái xe B2 chuẩn Bộ GTVT với lộ trình 3 tháng, xe đời mới và giáo viên giàu kinh nghiệm.',
 N'DriveHub chính thức thông báo khai giảng các khóa học lái xe hạng B2 liên tục vào đầu mỗi tháng. Học viên sẽ được tiếp cận giáo trình chuẩn Bộ Giao thông Vận tải, thực hành trên xe đời mới có gắn cảm biến hỗ trợ, cùng đội ngũ giáo viên tận tâm.

Lộ trình học kéo dài 3 tháng bao gồm lý thuyết luật giao thông, thi thử online và thực hành sa hình – đường trường. Đặc biệt, trung tâm hỗ trợ thủ tục thi và cấp bằng tận nơi, giúp học viên tiết kiệm thời gian.

Đăng ký sớm để nhận ưu đãi học phí hấp dẫn cùng lịch học linh hoạt các ca sáng – chiều – tối.',
 '2026-07-01', N'DriveHub', N'ĐÃ ĐĂNG'),

(N'Kinh nghiệm thi sát hạch lý thuyết 600 câu đạt điểm tuyệt đối',
 N'/tin-tuc-ly-thuyet.jpg',
 N'Tổng hợp các mẹo ôn tập bộ 600 câu hỏi lý thuyết và cách làm bài thi sa hạch đạt kết quả cao.',
 N'Kỳ thi lý thuyết với bộ 600 câu hỏi là nỗi lo của nhiều học viên mới. DriveHub chia sẻ bí quyết: chia nhỏ đề mục theo chủ đề (khái niệm, biển báo, sa hình, tình huống), kết hợp thi thử online mỗi ngày để ghi nhớ lâu hơn.

Nắm vững 60 câu điểm liệt là ưu tiên hàng đầu vì chỉ cần sai 1 câu điểm liệt sẽ trượt dù tổng điểm cao. Giáo viên sẽ hướng dẫn chi tiết cách phân tích sa hình và xử lý tình huống giao thông thực tế.

Kiên trì luyện tập mỗi ngày là chìa khóa để tự tin bước vào phòng thi.',
 '2026-06-20', N'Giáo viên DriveHub', N'ĐÃ ĐĂNG'),

(N'Cập nhật chính sách mới về nâng hạng giấy phép lái xe năm 2026',
 N'/tin-tuc-nang-hang.jpg',
 N'Tổng hợp những thay đổi quan trọng trong quy định nâng hạng GPLX mà học viên cần lưu ý.',
 N'Bộ Giao thông Vận tải đã có một số điều chỉnh về điều kiện nâng hạng giấy phép lái xe trong năm 2026. Theo đó, độ tuổi và thời gian hành nghề với hạng hiện tại là yếu tố quan trọng khi đăng ký nâng hạng.

DriveHub cập nhật chi tiết từng lộ trình nâng hạng: B2 lên C (đủ 21 tuổi, 3 năm lái xe), A1 lên A2, hay các hạng D/E nâng cao. Hồ sơ được đơn giản hóa, miễn giảm một số học phần nếu đủ điều kiện.

Học viên nên liên hệ tư vấn sớm để được kiểm tra hồ sơ và sắp xếp lịch học phù hợp.',
 '2026-06-05', N'DriveHub', N'ĐÃ ĐĂNG'),

(N'Xe tập lái đời mới được đưa vào vận hành tại hệ thống sát hạch',
 N'/tin-tuc-xe.jpg',
 N'Trung tâm đầu tư thêm dàn xe tập lái hiện đại, trang bị đầy đủ cảm biến và camera hỗ trợ học viên.',
 N'Nhằm nâng cao chất lượng đào tạo, DriveHub đưa vào vận hành thêm hàng chục xe tập lái đời mới, được bảo trì định kỳ và trang bị bảo hiểm toàn diện.

Mỗi xe đều có phanh phụ phía giáo viên, camera hành trình và cảm biến lùi, giúp học viên làm quen với công nghệ ô tô hiện đại. Việc thực hành trên xe tốt giúp học viên tự tin hơn khi thi sát hạch và khi tham gia giao thông thực tế.

Đây là minh chứng cho cam kết đầu tư cơ sở vật chất của trung tâm qua 15 năm hoạt động.',
 '2026-05-18', N'DriveHub', N'ĐÃ ĐĂNG');

GO
