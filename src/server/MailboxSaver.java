package server;
import java.io.*;
import java.time.LocalDateTime;
import java.util.List;

public class MailboxSaver {
    public static void save(String from, List<String> toList, String body) {
        try {
            File dir = new File("mailbox");
            if (!dir.exists()) dir.mkdirs();

            File f = new File(dir, "email_" + System.currentTimeMillis() + ".txt");
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
                bw.write("Từ: " + (from == null ? "" : from) + "\r\n");
                bw.write("Đến: " + String.join(", ", toList) + "\r\n");
                bw.write("Ngày: " + LocalDateTime.now().toString() + "\r\n");
                bw.write("\r\n");
                bw.write(body);
            }
            System.out.println("[>] Đã lưu email vào " + f.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Lỗi khi lưu email: " + e.getMessage());
        }
    }
}