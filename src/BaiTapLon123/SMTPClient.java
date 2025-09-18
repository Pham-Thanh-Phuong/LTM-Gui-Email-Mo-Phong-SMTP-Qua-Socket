package BaiTapLon123;

import java.io.*;
import java.net.Socket;

public class SMTPClient {
    public static void sendMail(String from, String to, String subject, String content) throws Exception {
        try (
            Socket socket = new Socket("localhost", 2525);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            System.out.println("Server: " + in.readLine());

            out.println("HELO localhost");
            System.out.println("Server: " + in.readLine());

            out.println("MAIL FROM:" + from);
            System.out.println("Server: " + in.readLine());

            out.println("RCPT TO:" + to);
            System.out.println("Server: " + in.readLine());

            out.println("SUBJECT:" + subject);
            System.out.println("Server: " + in.readLine());

            out.println("DATA");
            System.out.println("Server: " + in.readLine());

            out.println(content);
            out.println("."); // Kết thúc nội dung

            System.out.println("Server: " + in.readLine());
        }
    }
}
