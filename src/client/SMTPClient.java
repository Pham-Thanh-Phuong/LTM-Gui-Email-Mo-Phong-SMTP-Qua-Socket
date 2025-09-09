package client;
import java.util.List;
import java.io.*;
import java.net.Socket;


public class SMTPClient {
    private String server;
    private int port;

    public SMTPClient(String server, int port) {
        this.server = server;
        this.port = port;
    }

    public void sendEmail(String from, List<String> toList, String subject, String body) {
        try (Socket socket = new Socket(server, port);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            String line = reader.readLine();
            if (line != null) System.out.println("S->C: " + line);

            sendLine(writer, "HELO client.vi-du.com");
            System.out.println("S->C: " + reader.readLine());

            sendLine(writer, "MAIL FROM:<" + from + ">");
            System.out.println("S->C: " + reader.readLine());

            for (String rcpt : toList) {
                sendLine(writer, "RCPT TO:<" + rcpt + ">");
                System.out.println("S->C: " + reader.readLine());
            }

            sendLine(writer, "DATA");
            System.out.println("S->C: " + reader.readLine());

            sendLine(writer, "Chủ đề: " + subject);
            sendLine(writer, "Từ: " + from);
            sendLine(writer, "Đến: " + String.join(", ", toList));
            sendLine(writer, "");
            for (String l : body.split("\n")) {
                if (l.startsWith(".")) {
                    l = "." + l;
                }
                sendLine(writer, l);
            }
            sendLine(writer, ".");
            System.out.println("S->C: " + reader.readLine());

            sendLine(writer, "QUIT");
            System.out.println("S->C: " + reader.readLine());

        } catch (IOException e) {
            System.err.println("Lỗi client: " + e.getMessage());
        }
    }

    private void sendLine(BufferedWriter writer, String line) throws IOException {
        writer.write(line + "\r\n");
        writer.flush();
        System.out.println("C->S: " + line);
    }

    public static void main(String[] args) {
        SMTPClient client = new SMTPClient("localhost", 2525);
        client.sendEmail(
                "alice@example.com",
                List.of("bob@example.com"),
                "Thư thử nghiệm từ Client Java",
                "Xin chào Bob,\nĐây là thư thử nghiệm gửi tới máy chủ SMTP mô phỏng.\n.\nThân ái,\nAlice"
        );
    }
}