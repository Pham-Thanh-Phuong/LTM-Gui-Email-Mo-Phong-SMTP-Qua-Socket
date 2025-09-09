package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SMTPServer {
    private int port;

    public SMTPServer(int port) {
        this.port = port;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("[*] Máy chủ SMTP mô phỏng đơn giản đã khởi động tại cổng " + port);
            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("[+] Chấp nhận kết nối từ " + client.getRemoteSocketAddress());
                new Thread(new ClientHandler(client)).start();
            }
        } catch (IOException e) {
            System.err.println("Lỗi máy chủ: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SMTPServer server = new SMTPServer(2525);
        server.start();
    }
}