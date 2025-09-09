package server;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    private String from;
    private List<String> recipients = new ArrayList<>();
    private StringBuilder data = new StringBuilder();
    private boolean inDataMode = false;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    private void sendResponse(BufferedWriter writer, String resp) throws IOException {
        writer.write(resp + "\r\n");
        writer.flush();
        System.out.println("S->C [" + socket.getRemoteSocketAddress() + "]: " + resp);
    }

    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            sendResponse(writer, "220 Dịch vụ SMTP mô phỏng bằng Java đã sẵn sàng");

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("C->S [" + socket.getRemoteSocketAddress() + "]: " + line);

                if (inDataMode) {
                    if (line.equals(".")) {
                        inDataMode = false;
                        MailboxSaver.save(from, recipients, data.toString());
                        sendResponse(writer, "250 OK: đã đưa vào hàng đợi giả lập");
                        data.setLength(0);
                    } else {
                        if (line.startsWith(".")) {
                            line = line.substring(1);
                        }
                        data.append(line).append("\r\n");
                    }
                } else {
                    if (line.toUpperCase().startsWith("HELO")) {
                        sendResponse(writer, "250 Xin chào");
                    } else if (line.toUpperCase().startsWith("MAIL FROM:")) {
                        from = line.substring(10).trim();
                        sendResponse(writer, "250 OK");
                    } else if (line.toUpperCase().startsWith("RCPT TO:")) {
                        if (from == null) {
                            sendResponse(writer, "503 Cần MAIL FROM trước");
                        } else {
                            String rcpt = line.substring(8).trim();
                            recipients.add(rcpt);
                            sendResponse(writer, "250 OK");
                        }
                    } else if (line.equalsIgnoreCase("DATA")) {
                        if (from == null || recipients.isEmpty()) {
                            sendResponse(writer, "503 Sai thứ tự lệnh");
                        } else {
                            inDataMode = true;
                            sendResponse(writer, "354 Kết thúc phần dữ liệu bằng <CR><LF>.<CR><LF>");
                        }
                    } else if (line.equalsIgnoreCase("QUIT")) {
                        sendResponse(writer, "221 Tạm biệt");
                        break;
                    } else {
                        sendResponse(writer, "502 Lệnh chưa được hỗ trợ");
                    }
                }
            }
        } catch (IOException ex) {
            System.err.println("Lỗi khi xử lý kết nối: " + ex.getMessage());
        } finally {
            try {
                socket.close();
                System.out.println("[-] Đóng kết nối: " + socket.getRemoteSocketAddress());
            } catch (IOException ignored) {
            }
        }
    }
}