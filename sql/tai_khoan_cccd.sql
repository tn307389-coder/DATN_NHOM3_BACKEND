-- Thêm cột cccd vào tai_khoan để liên kết tài khoản học viên với hồ sơ hoc_vien
IF NOT EXISTS (
  SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS
  WHERE TABLE_NAME = 'tai_khoan' AND COLUMN_NAME = 'cccd'
)
BEGIN
  ALTER TABLE tai_khoan ADD cccd NVARCHAR(20);
END
GO

-- Liên kết tài khoản học viên mẫu với hồ sơ học viên (theo CCCD)
UPDATE tai_khoan
SET cccd = N'123456789001'
WHERE ten_dang_nhap = N'hocvien1' AND cccd IS NULL;
GO
