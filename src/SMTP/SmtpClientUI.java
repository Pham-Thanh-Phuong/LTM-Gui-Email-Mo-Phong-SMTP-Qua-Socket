package SMTP;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

public class SmtpClientUI extends JFrame {
    private JTextField txtNguoiGui;
    private JTextField txtNguoiNhan;
    private JTextField txtChuDe;
    private JTextArea txtNoiDung;

    public SmtpClientUI() {
        setTitle("📧 SMTP Client - Mô phỏng gửi Email");
        setSize(500, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}

        // ===== Tiêu đề =====
        JLabel lblTitle = new JLabel("MÔ PHỎNG GỬI EMAIL QUA SMTP SOCKET", JLabel.CENTER);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTitle.setOpaque(true);
        lblTitle.setBackground(new Color(0, 102, 204));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(lblTitle, BorderLayout.NORTH);

        // ===== Form nhập =====
        JPanel panelForm = new JPanel(new GridLayout(3, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelForm.setBackground(new Color(230, 240, 255));

        txtNguoiGui = new JTextField();
        txtNguoiNhan = new JTextField();
        txtChuDe = new JTextField();

        panelForm.add(new JLabel("👤 Người gửi:"));
        panelForm.add(txtNguoiGui);
        panelForm.add(new JLabel("👤 Người nhận:"));
        panelForm.add(txtNguoiNhan);
        panelForm.add(new JLabel("📝 Chủ đề:"));
        panelForm.add(txtChuDe);

        // ===== Nội dung =====
        txtNoiDung = new JTextArea(8, 20);
        txtNoiDung.setLineWrap(true);
        txtNoiDung.setWrapStyleWord(true);
        txtNoiDung.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(txtNoiDung);
        scrollPane.setBorder(BorderFactory.createTitledBorder("✍ Nội dung"));

        // ===== Nút gửi =====
        JButton btnGui = new JButton("✉ Gửi Email");
        btnGui.setBackground(new Color(0, 153, 76));
        btnGui.setForeground(Color.WHITE);
        btnGui.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnGui.addActionListener(e -> guiEmail());

        JPanel panelButton = new JPanel();
        panelButton.add(btnGui);
        panelButton.setBackground(new Color(230, 240, 255));

        add(panelForm, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelButton, BorderLayout.SOUTH);
    }

    private void guiEmail() {
        String nguoiGui = txtNguoiGui.getText().trim();
        String nguoiNhan = txtNguoiNhan.getText().trim();
        String chuDe = txtChuDe.getText().trim();
        String noiDung = txtNoiDung.getText().trim();
        if (nguoiGui.isEmpty() || nguoiNhan.isEmpty() || chuDe.isEmpty() || noiDung.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠ Vui lòng nhập đầy đủ thông tin.");
            return;
        }

        try (Socket socket = new Socket("127.0.0.1", 2525);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            System.out.println(in.readLine()); 

            out.write("HELO client.local\r\n"); out.flush();
            in.readLine();

            out.write("MAIL FROM:<" + nguoiGui + ">\r\n"); out.flush();
            in.readLine();

            out.write("RCPT TO:<" + nguoiNhan + ">\r\n"); out.flush();
            in.readLine();

            out.write("DATA\r\n"); out.flush();
            in.readLine();

            // Gửi metadata + nội dung
            out.write("Người gửi: " + nguoiGui + "\r\n");
            out.write("Người nhận: " + nguoiNhan + "\r\n");
            out.write("Chủ đề: " + chuDe + "\r\n");
            out.write("\r\n");
            out.write(noiDung + "\r\n");
            out.write(".\r\n");
            out.flush();
            in.readLine();

            out.write("QUIT\r\n"); out.flush();
            in.readLine();

            JOptionPane.showMessageDialog(this, "✅ Email đã được gửi thành công!\nHãy kiểm tra thư mục mailbox.");

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "❌ Lỗi khi gửi: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SmtpClientUI().setVisible(true));
    }
}