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
## 📖 1. Giới thiệu hệ thống <br>
Đề tài này nhằm mô phỏng cơ chế hoạt động của SMTP (Simple Mail Transfer Protocol) – giao thức tiêu chuẩn để gửi email trên Internet.<br>
Thông qua việc sử dụng Socket trong Java, hệ thống cho phép:<br>
 • Một SMTP Server mô phỏng lắng nghe kết nối từ client.<br>
 • Một SMTP Client kết nối đến server và gửi email bằng các lệnh SMTP cơ bản: HELO, MAIL FROM, RCPT TO, DATA, QUIT.<br>
 • Server sau khi nhận email sẽ lưu lại vào thư mục mailbox/ dưới dạng file .txt để minh họa quá trình xử lý.<br>

👉 Mục tiêu:<br>
 • Giúp sinh viên hiểu rõ cơ chế truyền thông giữa client–server trong mô hình SMTP.<br>
 • Rèn luyện kỹ năng lập trình mạng với TCP Socket trong Java.<br>
 • Làm quen với cách triển khai một giao thức ứng dụng thực tế.<br>

## 🔧 2. Công nghệ sử dụng: [![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)](https://www.java.com/)
 • Ngôn ngữ lập trình.<br>
 • Thư viện sử dụng: Java Core (Socket, I/O, Collections).<br>
 • Mô hình lập trình: Client–Server qua TCP Socket.<br>
 • Eclipse hoặc IntelliJ IDEA.<br>
## 🚀 3. Các hình ảnh chức năng
3.1. Giao tiếp Client ↔ Server (console log)<br>
 • Client gửi lệnh đến server:<br>
 <img width="215" height="18" alt="image" src="https://github.com/user-attachments/assets/29ccb955-8d8e-47f0-879a-531219248664" /><br>
• Server phản hồi:<br>
<img width="136" height="18" alt="image" src="https://github.com/user-attachments/assets/5d4dc4b5-4d90-4b91-903b-78adcd803014" /><br>
3.2. Email được lưu trên Server<br>

Sau khi client gửi thành công, thư mục mailbox/ sinh ra file:<br>
<img width="167" height="23" alt="image" src="https://github.com/user-attachments/assets/80162433-2739-46c8-bbde-d57e46c2ba92" /><br>
Với nội dung: <br>
<img width="451" height="222" alt="image" src="https://github.com/user-attachments/assets/c24a0f85-0b40-4916-a66f-db982fd5513d" /><br>
3.3. Kiến trúc hệ thống <br>
[SMTP Client]  <--TCP-->  [SMTP Server]  -->  [Mailbox Saver -> file .txt] <br>


### [Khoá 16](./docs/projects/K16/README.md)

## 📝 4. Các bước cài đặt
4.1. Chuẩn bị <br>
- Cài đặt Eclipse IDE<br>
- Cài đặt thư mục theo cấu trúc:<br>
  <img width="216" height="167" alt="image" src="https://github.com/user-attachments/assets/1edc4596-ef4e-4157-a48a-41f29801d606" /> <br>
4.2. Chạy chương trình<br>
1. Chạy server trước<br>
 - Vào server/SMTPServer.java<br>
 - Run As → Java Application<br>
 - Console hiển thị: <br>
 <img width="463" height="18" alt="image" src="https://github.com/user-attachments/assets/9ebe92bc-c7c6-4485-ace4-b2b671305c93" /> <br>
 2. Chạy client<br>
 • Vào client/SMTPClient.java<br>
 • Run As → Java Application<br>
 • Console hiển thị log gửi email và phản hồi từ server.<br>
 3. Kiểm tra kết quả<br>
 • Trong thư mục project sẽ xuất hiện thư mục mailbox/<br>
 • Mỗi email được lưu dưới dạng file .txt.<br>


## 📞 5. Liên hệ
- Email: thankfwong23@gmail.com  
- SĐT: 0383 609 685 


© 2025 AIoTLab, Faculty of Information Technology, DaiNam University. All rights reserved.

---
