package SMTP;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SmtpServer {
    public static void main(String[] args) {
        int port = 2525;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("📡 Máy chủ SMTP đang chạy, lắng nghe cổng " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ClientHandler(socket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {
            writer.write("220 Chào mừng đến với máy chủ SMTP tự tạo\r\n");
            writer.flush();

            StringBuilder data = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println("📥 Client: " + line);

                if (line.startsWith("HELO")) {
                    writer.write("250 Xin chào\r\n");
                } else if (line.startsWith("MAIL FROM:")) {
                    writer.write("250 OK\r\n");
                } else if (line.startsWith("RCPT TO:")) {
                    writer.write("250 OK\r\n");
                } else if (line.startsWith("DATA")) {
                    writer.write("354 Nhập nội dung email, kết thúc bằng dấu chấm '.'\r\n");
                } else if (line.equals(".")) {
                    saveEmail(data.toString());
                    writer.write("250 Email đã được lưu\r\n");
                    writer.flush();
                    break;
                } else if (line.equalsIgnoreCase("QUIT")) {
                    writer.write("221 Tạm biệt\r\n");
                    writer.flush();
                    break;
                } else {
                    data.append(line).append("\n");
                }
                writer.flush();
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveEmail(String content) {
        try {
            File dir = new File("mailbox");
            if (!dir.exists()) {
                dir.mkdir();
            }
            String fileName = "mail_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".txt";
            File file = new File(dir, fileName);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(content.trim() + "\n");
                writer.write("Thời gian: " + new Date() + "\n\n");
            }
            System.out.println("📂 Email đã được lưu vào " + file.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}	
            