<h2 align="center">
    <a href="https://dainam.edu.vn/vi/khoa-cong-nghe-thong-tin">
    🎓 Faculty of Information Technology (DaiNam University)
    </a>
</h2>
<h2 align="center">
   GỬI EMAIL MÔ PHỎNG SMTP QUA SOCKET
</h2>
<div align="center">
    <p align="center">
        <img src="docs/aiotlab_logo.png" alt="AIoTLab Logo" width="170"/>
        <img src="docs/fitdnu_logo.png" alt="AIoTLab Logo" width="180"/>
        <img src="docs/dnu_logo.png" alt="DaiNam University Logo" width="200"/>
    </p>

[![AIoTLab](https://img.shields.io/badge/AIoTLab-green?style=for-the-badge)](https://www.facebook.com/DNUAIoTLab)
[![Faculty of Information Technology](https://img.shields.io/badge/Faculty%20of%20Information%20Technology-blue?style=for-the-badge)](https://dainam.edu.vn/vi/khoa-cong-nghe-thong-tin)
[![DaiNam University](https://img.shields.io/badge/DaiNam%20University-orange?style=for-the-badge)](https://dainam.edu.vn)

</div>


## 📖 1. Giới thiệu

Đề tài: Mô phỏng gửi email qua SMTP bằng Socket (Java)

Hệ thống mô phỏng quá trình gửi email qua giao thức SMTP (Simple Mail Transfer Protocol).

Người dùng nhập thông tin email qua giao diện Swing, client gửi lệnh SMTP qua TCP socket đến server, server sẽ lưu email thành file .txt trong thư mục mailbox/.



📌 2. Công nghệ sử dụng

Trong quá trình xây dựng hệ thống mô phỏng gửi email qua giao thức SMTP bằng Socket, nhóm sử dụng các công nghệ chính sau:

⸻

2.1. Ngôn ngữ lập trình Java

Java là một ngôn ngữ lập trình hướng đối tượng, đa nền tảng, được phát triển bởi Sun Microsystems (nay thuộc Oracle). Java nổi bật nhờ nguyên lý “Write Once, Run Anywhere”, tức là chương trình viết một lần có thể chạy trên nhiều hệ điều hành khác nhau nhờ Java Virtual Machine (JVM).

Trong đề tài này, Java được lựa chọn vì:

 • Hỗ trợ mạnh mẽ các thư viện Socket, cho phép lập trình mạng dễ dàng.
 
 • Có API I/O (Input/Output) phong phú để đọc/ghi dữ liệu từ client và server.
 
 • Cộng đồng lớn, nhiều tài liệu tham khảo.
 
 • Khả năng chạy ổn định trên nhiều hệ điều hành (Windows, Linux, macOS).

Java giúp việc xây dựng mô hình Client – Server trở nên trực quan, dễ hiểu, đồng thời đảm bảo chương trình có thể tái sử dụng và mở rộng.

⸻

2.2. Socket trong Java

Socket là điểm cuối (endpoint) trong quá trình giao tiếp giữa hai tiến trình qua mạng. Trong Java, gói java.net cung cấp các lớp quan trọng:

 • ServerSocket: Dùng để tạo máy chủ, lắng nghe yêu cầu từ client.
 
 • Socket: Dùng để tạo kết nối từ phía client đến server.
 
 • Các phương thức đọc/ghi (InputStream, OutputStream) cho phép trao đổi dữ liệu qua kết nối.

Trong hệ thống này:

 • Server mở cổng 2525, chờ client kết nối.
 
 • Client kết nối qua Socket và gửi các lệnh theo chuẩn SMTP (HELO, MAIL FROM, RCPT TO, DATA…).
 
 • Server phản hồi bằng các mã trạng thái (220, 250, 354, 221…) như một máy chủ SMTP thực tế.

Việc sử dụng TCP Socket đảm bảo:

 • Kết nối tin cậy: Dữ liệu gửi đi không bị mất hoặc sai thứ tự.
 
 • Giao tiếp hai chiều: Client có thể gửi lệnh, server phản hồi ngay lập tức.
 
 • Đồng bộ hóa: Thích hợp cho mô phỏng giao thức SMTP vốn cần phản hồi tuần tự.


2.3. Java I/O (Input/Output)

Trong ứng dụng mạng, dữ liệu trao đổi đều ở dạng chuỗi ký tự. Java cung cấp hệ thống I/O Streams mạnh mẽ để xử lý:

 • InputStreamReader + BufferedReader: đọc dữ liệu từ client.
 
 • OutputStreamWriter + BufferedWriter: gửi dữ liệu từ server đến client.
 
 • FileWriter + BufferedWriter: ghi nội dung email xuống file .txt.

Ưu điểm khi dùng I/O trong Java:

 • Dễ dàng thao tác với dữ liệu dạng text.
 
 • Hỗ trợ buffer (bộ đệm), giúp tăng tốc độ xử lý.
 
 • Có thể kết hợp nhiều lớp I/O để đạt hiệu suất và tính linh hoạt. 
 
Trong hệ thống SMTP mô phỏng, I/O đóng vai trò quan trọng để:

 1. Gửi lệnh từ client đến server.
    
 2. Nhận phản hồi từ server.
    
 3. Lưu email thành file trong thư mục mailbox/.

⸻

2.4. Mô hình Client – Server

Mô hình Client – Server là kiến trúc phổ biến trong lập trình mạng.

 • Client: Gửi yêu cầu (request).
 
 • Server: Xử lý yêu cầu và trả về phản hồi (response).

Trong bài toán này:

 • Client đóng vai trò phần mềm gửi email.
 
 • Server đóng vai trò máy chủ SMTP giả lập.
 
 • Sau khi nhận đủ dữ liệu, server sẽ lưu email thành file để thay cho việc gửi ra Internet.

Việc sử dụng mô hình Client – Server giúp hệ thống dễ dàng mô phỏng cách mà các phần mềm email (Outlook, Gmail, Thunderbird…) giao tiếp với máy chủ SMTP thật ngoài Internet.

⸻

2.5. IDE: Eclipse / IntelliJ IDEA

Để lập trình và chạy ứng dụng, nhóm sử dụng IDE (Integrated Development Environment):

 • Eclipse: miễn phí, phổ biến trong cộng đồng Java.
 
 • IntelliJ IDEA: giao diện hiện đại, hỗ trợ tính năng thông minh (code completion, debug).

Lợi ích của việc dùng IDE:

 • Quản lý project dễ dàng.
 
 • Hỗ trợ chạy và debug nhanh.
 
 • Tích hợp console để quan sát log giao tiếp Client – Server.

 ## 📖 3. Các hình ảnh chức năng

Trong phần này, hệ thống được minh họa bằng các hình ảnh chụp từ quá trình chạy chương trình. Các hình này giúp làm rõ cách thức giao tiếp giữa SMTP Client và SMTP Server, cũng như kết quả lưu trữ email trên server.

⸻

3.1. Giao tiếp Client ↔ Server (Console log)

Khi chương trình được chạy, phía Client sẽ gửi các lệnh theo chuẩn SMTP đến Server thông qua kết nối TCP Socket. Đồng thời, Server sẽ phản hồi bằng các mã trạng thái.

 • Console phía Client hiển thị:
 
<img width="484" height="139" alt="image" src="https://github.com/user-attachments/assets/45cff8fa-91a3-466c-9e13-0087313ee716" />

• Console phía Server hiển thị:

<img width="615" height="260" alt="image" src="https://github.com/user-attachments/assets/67b3e0dc-930f-4542-a42d-8186527356c1" />


## 📞 5. Liên hệ
- Email: thankfwong23@gmail.com  
- SĐT: 0383 609 685 


© 2025 AIoTLab, Faculty of Information Technology, DaiNam University. All rights reserved.

---
