Phần 1 - Phân tích logic
Để xây dựng một API RESTful chuẩn hóa, chúng ta sử dụng "bộ ba nguyên tử" của Spring Web:

@RestController: Đây là sự kết hợp của @Controller và @ResponseBody. Nó thông báo cho Spring biết rằng class này không dùng để render các trang giao diện HTML truyền thống (Thymeleaf, JSP). Thay vào đó, tất cả dữ liệu trả về từ các method sẽ được tự động chuyển đổi (serialize) thành định dạng JSON/XML thông qua các HttpMessageConverter và ghi thẳng vào body của HTTP Response.

@RequestBody: Annotation này đóng vai trò là bộ giải mã dữ liệu. Khi Mobile Client gửi lên một chuỗi JSON trong body của HTTP Request, @RequestBody sẽ chặn lại, tự động phân tích và chuyển đổi (deserialize) chuỗi JSON đó thành một đối tượng Java (DTO - Data Transfer Object) tương ứng để lập trình viên dễ dàng xử lý.

ResponseEntity: Đại diện cho toàn bộ phản hồi HTTP (bao gồm Status Code, Headers, và Body). Trong REST API, việc trả về đúng HTTP Status Code mang tính sống còn để phía Client (Mobile/Web) biết chính xác chuyện gì đang xảy ra. ResponseEntity cho phép chúng ta tùy biến linh hoạt các mã trạng thái như 201 Created khi đăng ký thành công, 409 Conflict khi trùng tên, hay 401 Unauthorized khi sai mật khẩu.