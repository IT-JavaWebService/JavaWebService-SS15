Phần 1 - Phân tích logic
1. Sự khác biệt cơ bản về cơ chế bảo vệ CSRF
   Ứng dụng Web truyền thống (Session-based): * Cơ chế: Trình duyệt tự động đính kèm Cookie (chứa JSESSIONID) vào mọi request gửi tới server, bất kể request đó xuất phát từ đâu (ngay cả từ một trang web độc hại của hacker).

Bảo vệ CSRF: Spring Security bắt buộc phải sử dụng CSRF Token (Synchronizer Token Pattern). Server tạo ra một token ngẫu nhiên, giấu vào Form/Header. Khi client gửi request (POST/PUT/DELETE), server sẽ so sánh token gửi lên với token lưu trên Session. Nếu khớp mới cho qua.

Ứng dụng REST API (Stateless/Token-based như Mobile App):

Cơ chế: Client di động không sử dụng Cookie để duy trì trạng thái đăng nhập. Thay vào đó, sau khi đăng nhập thành công, Mobile App sẽ lưu Token (ví dụ: JWT) vào bộ nhớ an toàn của thiết bị (Secure Storage) và chủ động đính kèm vào Header (ví dụ: Authorization: Bearer <token>) trong mỗi request.

Bảo vệ CSRF: Vì thiết bị di động / REST client không tự động đính kèm thông tin xác thực giống như cơ chế Cookie của trình duyệt, nên các cuộc tấn công CSRF không thể xảy ra trên luồng này.

2. Tại sao vô hiệu hóa CSRF "mù quáng" lại nguy hiểm?
   Nếu bạn tắt hoàn toàn CSRF (.csrf(csrf -> csrf.disable())) cho một ứng dụng Web truyền thống vẫn đang sử dụng Session/Cookie để xác thực, bạn sẽ mở toang cánh cửa cho hacker.

Hacker có thể lừa người dùng (đang đăng nhập hệ thống của bạn) click vào một link độc hại. Trang web của hacker sẽ chạy ngầm một đoạn mã gửi request đổi mật khẩu hoặc chuyển tiền tới server của bạn. Vì trình duyệt tự động "hiến dâng" Cookie Session, server sẽ tưởng đó là request hợp lệ của người dùng và thực thi lệnh. Kết quả là hệ thống bị hack hoàn toàn.

Kết luận: Chỉ vô hiệu hóa CSRF khi hệ thống của bạn hoàn toàn Stateless (Xác thực qua Header Token, không dùng Cookie trên trình duyệt).