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
Đề tài xây dựng một **ứng dụng gửi và nhận email mô phỏng giao thức SMTP** thông qua **TCP Socket**.  
Hệ thống được phát triển bằng **Java (Swing)** và sử dụng **SQLite** để quản lý dữ liệu người dùng cũng như email.  

Ứng dụng bao gồm các chức năng chính:

- 🔑 **Đăng ký / Đăng nhập**: Người dùng có thể tạo tài khoản và đăng nhập hệ thống.  
- ✉️ **Gửi thư**: Gửi email đến người dùng khác thông qua cơ chế Socket mô phỏng SMTP.  
- 📥 **Hộp thư đến (Inbox)**: Hiển thị danh sách email đã nhận.  
- 📑 **Thư đã gửi (Sent)**: Hiển thị các email mà người dùng đã gửi đi.  
- 💾 **Lưu trữ email**: Mọi email sẽ được lưu trong **SQLite Database** và trên Server dưới dạng file `.txt`.  

Ứng dụng giúp hiểu rõ hơn về:
- Cách thức **Client ↔ Server** giao tiếp qua **TCP Socket**.  
- Nguyên lý hoạt động cơ bản của **SMTP (Simple Mail Transfer Protocol)**.  

## 📌 2. Công nghệ sử dụng

- Trong quá trình xây dựng hệ thống mô phỏng gửi email qua giao thức SMTP bằng Socket, nhóm sử dụng các công nghệ chính sau:

⸻

### 2.1. Ngôn ngữ lập trình Java <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" alt="Java Logo" width="50" height="50" />

### 2.2. Socket trong Java

Socket là điểm cuối (endpoint) trong quá trình giao tiếp giữa hai tiến trình qua mạng. Trong Java, gói java.net cung cấp các lớp quan trọng:

Trong hệ thống này:

     • Server mở cổng 2525, chờ client kết nối.
     
     • Client kết nối qua Socket và gửi các lệnh theo chuẩn SMTP (HELO, MAIL FROM, RCPT TO, DATA…).
     
     • Server phản hồi bằng các mã trạng thái (220, 250, 354, 221…) như một máy chủ SMTP thực tế.

### 2.3. Mô hình Client – Server

Mô hình Client – Server là kiến trúc phổ biến trong lập trình mạng.

     • Client: Gửi yêu cầu (request).
     
     • Server: Xử lý yêu cầu và trả về phản hồi (response).

Trong bài toán này:

     • Client đóng vai trò phần mềm gửi email.
     
     • Server đóng vai trò máy chủ SMTP giả lập.
     
     • Sau khi nhận đủ dữ liệu, server sẽ lưu email thành file để thay cho việc gửi ra Internet.

Việc sử dụng mô hình Client – Server giúp hệ thống dễ dàng mô phỏng cách mà các phần mềm email (Outlook, Gmail, Thunderbird…) giao tiếp với máy chủ SMTP thật ngoài Internet.

⸻

 ## 💻 3. Các hình ảnh chức năng

Trong phần này, hệ thống được minh họa bằng các hình ảnh chụp từ quá trình chạy chương trình. Các hình này giúp làm rõ cách thức giao tiếp giữa SMTP Client và SMTP Server, cũng như kết quả lưu trữ email trên server.

⸻

### 3.1. Giao tiếp Client ↔ Server (Console log)
- Khi client bấm **Gửi**, chương trình client (GUI) sẽ mở socket tới `localhost:2525` và gửi các lệnh SMTP dạng văn bản. Server phản hồi bằng các mã trạng thái SMTP (220/250/354/221...).

**Luồng mẫu (Client → Server)**:
```
S  220 SMTP Server Ready
C  HELO localhost
S  250 Hello
C  MAIL FROM:<alice>
S  250 OK
C  RCPT TO:<bob>
S  250 OK
C  SUBJECT: Test subject
S  250 OK
C  DATA
S  354 End data with <CR><LF>.<CR><LF>
C  [nội dung thư dòng 1]
C  [nội dung thư dòng 2]
C  .
S  250 Message accepted
C  QUIT
S  221 Bye
```

- **Console phía Client**: in các lệnh đã gửi và phản hồi nhận được.  
- **Console phía Server**: in các lệnh nhận được từ client và khi lưu email thành công sẽ in log (ví dụ “Message accepted” hoặc thông báo SQL thành công).

### 3.2. Email được lưu trong Database (`email_app.db`)
- Mã nguồn hiện tại **lưu email trực tiếp vào bảng `emails`** của SQLite (không lưu file `.txt` trong thư mục mailbox). Bảng `emails` có cấu trúc chính như sau (được tạo tự động khi ứng dụng khởi động):

```sql
CREATE TABLE IF NOT EXISTS users (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  username TEXT UNIQUE,
  password TEXT
);

CREATE TABLE IF NOT EXISTS emails (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  sender TEXT,
  receiver TEXT,
  subject TEXT,
  content TEXT,
  time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```
<p align="center"> <img width="863" height="465" alt="image" src="https://github.com/user-attachments/assets/e788e15a-f70d-4535-ae2e-338e7b8081fd" /> </p>
<p align="center"><i>Hình ảnh 1</i></p>

### 3.3. Giao diện & các chức năng liên quan
- Cấu trúc thư mục:
  
<p align="center"> <img width="239" height="310" alt="image" src="https://github.com/user-attachments/assets/0b68a056-e014-4e1d-8843-d48a6041d4dd" /> </p>
<p align="center"><i>Hình ảnh 2</i></p>
- Giao diện đăng ký:

<p align="center"> <img width="387" height="244" alt="image" src="https://github.com/user-attachments/assets/e302f46f-fe40-45dc-8dcd-f6c50a1161e5" /> </p>
<p align="center"><i>Hình ảnh 3</i></p>
- Giao diện đăng nhập:

<p align="center"> <img width="385" height="292" alt="image" src="https://github.com/user-attachments/assets/ec6b96fa-b02c-4f13-bd59-e8a4b0eb6f31" /> </p>
<p align="center"><i>Hình ảnh 4</i></p>
- Giao diện gửi thư:

<p align="center"> <img width="791" height="596" alt="image" src="https://github.com/user-attachments/assets/9af6b2f2-463f-417b-aa06-4b6a5ac76b9c" /> </p>
<p align="center"><i>Hình ảnh 5</i></p>
- Giao diện hộp thư đến:

<p align="center"> <img width="786" height="590" alt="image" src="https://github.com/user-attachments/assets/a984cc6f-2a6d-45a3-a60e-cc461b9a10af" /> </p>
<p align="center"><i>Hình ảnh 6</i></p>
- Giao diện thư đã gửi:

<p align="center"> <img width="783" height="590" alt="image" src="https://github.com/user-attachments/assets/84b5f484-eb96-4a90-bcac-4bd05ec9fafd" /> </p>
<p align="center"><i>Hình ảnh 7</i></p>

## ⚙️ 4. Các bước cài đặt

    Phần này mô tả các bước chuẩn bị, cài đặt môi trường và chạy thử hệ thống SMTP mô phỏng bằng Java. 

⸻

### 4.1. Chuẩn bị
1. **Cài JDK** (JDK 8+) và kiểm tra:
   ```bash
   java -version
   ```

2. **IDE**: Eclipse.

3. **Thêm thư viện SQLite JDBC** :  
   - Nếu project chưa có `sqlite-jdbc` trên classpath, tải `sqlite-jdbc-*.jar`  và add vào Build Path trong Eclipse.  
   - (Trong trường hợp đã gửi sẵn `.class` thì chạy trực tiếp cũng OK nếu runtime có JDBC driver; nhưng tốt nhất để có thể mở DB từ mã nguồn, thêm jar này).

4. **Kiểm tra cấu trúc file**: trong thư mục project cần có:
```
/src/ (source files hoặc package .class)
App.class, LoginForm.class, RegisterForm.class, MainForm.class, Database.class, SMTPServer.class, SMTPClient.class, ...
email_app.db    <-- sẽ được tạo khi chạy lần đầu (nếu chưa có)
```

### 4.2. Khởi động hệ thống (thứ tự chạy)

#### 4.2.1. Chạy SMTP Server
1. Mở class `SMTPServer` (package `BaiTapLon123`).  
2. Run → **Run As → Java Application**.  
3. Console sẽ in (ví dụ):
```
SMTP Server đang chạy trên cổng 2525
```
> Lưu ý: Server phải chạy trước khi client cố gắng kết nối, nếu không client sẽ gặp `Connection refused`.

#### 4.2.2. Chạy Client (GUI)
1. Mở `App` (hoặc `LoginForm` nếu chạy trực tiếp).  
2. Run → **Run As → Java Application**.  
3. Trên giao diện: Đăng ký tài khoản (Register) → Đăng nhập (Login) → vào tab Soạn thư.  
4. Nhập **Người nhận**, **Chủ đề**, **Nội dung** → bấm **Gửi**. Client sẽ:
   - Gọi SQL `INSERT INTO emails(...)` để lưu email (client-side lưu trước).  
   - Mở socket tới server `localhost:2525`, gửi lệnh SMTP (HELO, MAIL FROM, RCPT TO, SUBJECT, DATA, content, `.`), nhận phản hồi, rồi đóng kết nối.

#### 4.2.3. Kiểm tra kết quả
- Mở `email_app.db` bằng **DB Browser for SQLite** (hoặc chạy `ViewDB` test class) để xem các bản ghi trong bảng `emails`.  
- Bạn có thể thấy các trường: `sender`, `receiver`, `subject`, `content`, `time`.
- Console server sẽ in log các lệnh client gửi và thông báo khi email được insert thành công.

### 4.3. Các lỗi thường gặp & cách xử lý nhanh
- **Connection refused**: Server chưa chạy → chạy `SMTPServer` trước.  
- **No such table: emails**: Database chưa được tạo hoặc class `Database` không được gọi trước khi insert → chạy App (Database static initializer sẽ tạo bảng). Có thể xóa file `email_app.db` và khởi động lại để tạo lại bảng mới.  
- **Tài khoản rỗng / đăng ký lỗi**: Đảm bảo `RegisterForm` không cho phép username hoặc password rỗng; kiểm tra bảng `users` có bản ghi rỗng không. 
- **Không thấy email trong Inbox**: Kiểm tra xem gửi tới đúng `receiver` (trùng chính xác username trong bảng `users`).

---

## 📞 5. Liên hệ
- 💌 Email: thankfwong23@gmail.com  
- ☎️ SĐT: 0383 609 685 


© 2025 AIoTLab, Faculty of Information Technology, DaiNam University. All rights reserved.

---
