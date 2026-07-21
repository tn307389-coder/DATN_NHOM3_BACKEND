-- Thêm cột cccd vào giao_vien để liên kết tài khoản giáo viên với hồ sơ giao_vien
IF NOT EXISTS (
  SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS
  WHERE TABLE_NAME = 'giao_vien' AND COLUMN_NAME = 'cccd'
)
BEGIN
  ALTER TABLE giao_vien ADD cccd NVARCHAR(20);
END
GO

-- Liên kết tài khoản giáo viên mẫu với hồ sơ giao_vien (theo CCCD)
UPDATE giao_vien SET cccd = N'GV123456789001' WHERE magv = 1 AND cccd IS NULL;
GO
UPDATE tai_khoan SET cccd = N'GV123456789001' WHERE ten_dang_nhap = N'giaovien1' AND cccd IS NULL;
GO
