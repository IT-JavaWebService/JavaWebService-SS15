Phần 1 - Phân tích logic
Nguyên nhân gốc rễ: Trong cấu hình cũ, bạn đang sử dụng chuỗi logic sau:

Java
.requestMatchers("/").permitAll()
.anyRequest().authenticated()
-Khi tài khoản user (hoặc bất kỳ tài khoản nào đã đăng nhập thành công) gửi request đến /admin/orders:

-Spring Security sẽ kiểm tra từ trên xuống dưới. Đường dẫn /admin/orders không khớp với /.

-Hệ thống chuyển xuống quy tắc tiếp theo: .anyRequest().authenticated(). Quy tắc này hiểu là: "Bất kỳ request nào khác chỉ cần xác thực thành công (đã đăng nhập) là được qua".

-Vì tài khoản user đã đăng nhập hợp lệ, Spring Security sẽ "mở cửa" cho vào luôn mà không hề kiểm tra xem user đó nắm giữ vai trò (Role) gì.

-Chốt lại: Hệ thống mới chỉ làm chứng năng Xác thực (Authentication - Bạn là ai?) chứ chưa hề làm chức năng Phân quyền (Authorization - Bạn có quyền làm gì?) cho các đường dẫn admin.