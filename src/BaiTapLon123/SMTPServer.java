package BaiTapLon123;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class SMTPServer {
    public static void main(String[] args) {
        int port = 2525; // port SMTP mô phỏng
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("SMTP Server đang chạy trên cổng " + port);

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
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            out.println("220 Welcome to Fake SMTP Server");

            String sender = null, receiver = null, subject = null, content = "";

            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("Client: " + line);

                if (line.startsWith("HELO")) {
                    out.println("250 Hello");
                } else if (line.startsWith("MAIL FROM:")) {
                    sender = line.substring(10).trim();
                    out.println("250 OK");
                } else if (line.startsWith("RCPT TO:")) {
                    receiver = line.substring(8).trim();
                    out.println("250 OK");
                } else if (line.startsWith("SUBJECT:")) {
                    subject = line.substring(8).trim();
                    out.println("250 OK");
                } else if (line.equals("DATA")) {
                    out.println("354 End data with <CR><LF>.<CR><LF>");
                } else if (line.equals(".")) {
                    // Lưu vào database
                    try {
                        Connection conn = Database.getConnection();
                        PreparedStatement ps = conn.prepareStatement(
                            "INSERT INTO emails(sender,receiver,subject,content) VALUES(?,?,?,?)"
                        );
                        ps.setString(1, sender);
                        ps.setString(2, receiver);
                        ps.setString(3, subject);
                        ps.setString(4, content);
                        ps.executeUpdate();
                        conn.close();
                        out.println("250 Message accepted");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        out.println("550 Database error");
                    }
                    break;
                } else {
                    content += line + "\n";
                }
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
