IF OBJECT_ID('tin_tuc', 'U') IS NULL
BEGIN
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
END
GO

IF NOT EXISTS (SELECT 1 FROM tin_tuc)
BEGIN
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
END
GO
