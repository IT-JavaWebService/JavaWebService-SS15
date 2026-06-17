Phần 1 - Phân tích logic
Nguyên nhân gốc rễ: Mặc định, khi bạn chỉ khởi tạo dự án với Web dependency (spring-boot-starter-web), luồng đi của một HTTP Request sẽ cực kỳ đơn giản:

-Trình duyệt gửi request tới /products.

-DispatcherServlet của Spring Boot tiếp nhận request này.

-Nó tra cứu bản đồ định tuyến (Handler Mapping), tìm thấy @GetMapping("/products") trong ProductController và chuyển tiếp thẳng request vào đó xử lý, trả về dữ liệu.

Cốt lõi vấn đề: Vì hệ thống hoàn toàn thiếu vắng bộ lọc chặn đường (Servlet Filter), không có cơ chế nào đứng ra hỏi người dùng "Bạn là ai?" trước khi cho phép chạm vào dữ liệu Controller. Mọi endpoint lúc này mặc nhiên được coi là công khai hoàn toàn (Public).