package BaiTapLon123;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class MainForm extends JFrame {
    private String currentUser;
    private JTextField txtTo, txtSubject;
    private JTextArea txtContent;
    private JTable inboxTable, sentTable;
    private DefaultTableModel inboxModel, sentModel;

    public MainForm(String user) {
        this.currentUser = user;
        setTitle("📧 Ứng dụng Email - Người dùng: " + user);
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // =============== TAB GỬI THƯ ===============
        JPanel sendPanel = new JPanel(new BorderLayout(10, 10));
        sendPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel top = new JPanel(new GridLayout(2, 2, 5, 5));
        top.add(new JLabel("Người nhận:"));
        txtTo = new JTextField();
        top.add(txtTo);

        top.add(new JLabel("Chủ đề:"));
        txtSubject = new JTextField();
        top.add(txtSubject);

        txtContent = new JTextArea();
        JScrollPane scroll = new JScrollPane(txtContent);

        JPanel bottom = new JPanel();
        JButton btnSend = new JButton("✉️ Gửi");
        JButton btnLogout = new JButton("🚪 Đăng xuất");
        bottom.add(btnSend);
        bottom.add(btnLogout);

        sendPanel.add(top, BorderLayout.NORTH);
        sendPanel.add(scroll, BorderLayout.CENTER);
        sendPanel.add(bottom, BorderLayout.SOUTH);

        tabbedPane.add("📤 Gửi thư", sendPanel);

        // =============== TAB INBOX ===============
        inboxModel = new DefaultTableModel(new String[]{"Người gửi", "Chủ đề", "Nội dung"}, 0);
        inboxTable = new JTable(inboxModel);
        JScrollPane inboxScroll = new JScrollPane(inboxTable);
        tabbedPane.add("📥 Hộp thư đến", inboxScroll);

        // =============== TAB SENT ===============
        sentModel = new DefaultTableModel(new String[]{"Người nhận", "Chủ đề", "Nội dung"}, 0);
        sentTable = new JTable(sentModel);
        JScrollPane sentScroll = new JScrollPane(sentTable);
        tabbedPane.add("📑 Thư đã gửi", sentScroll);

        add(tabbedPane);

        // =============== SỰ KIỆN ===============
        btnSend.addActionListener(e -> sendEmail());
        btnLogout.addActionListener(e -> {
            new LoginForm().setVisible(true);
            dispose();
        });

        // Double click inbox
        inboxTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = inboxTable.getSelectedRow();
                    if (row != -1) {
                        String sender = inboxModel.getValueAt(row, 0).toString();
                        String subject = inboxModel.getValueAt(row, 1).toString();
                        String content = inboxModel.getValueAt(row, 2).toString();
                        showEmailDetail("📥 Hộp thư đến", sender, subject, content, true);
                    }
                }
            }
        });

        // Double click sent
        sentTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = sentTable.getSelectedRow();
                    if (row != -1) {
                        String receiver = sentModel.getValueAt(row, 0).toString();
                        String subject = sentModel.getValueAt(row, 1).toString();
                        String content = sentModel.getValueAt(row, 2).toString();
                        showEmailDetail("📤 Thư đã gửi", receiver, subject, content, false);
                    }
                }
            }
        });

        // Load dữ liệu
        loadInbox();
        loadSent();
    }

    // =============== GỬI EMAIL ===============
    private void sendEmail() {
        String to = txtTo.getText().trim();
        String subject = txtSubject.getText().trim();
        String content = txtContent.getText().trim();

        if (to.isEmpty() || subject.isEmpty() || content.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        try {
            Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO emails(sender,receiver,subject,content) VALUES(?,?,?,?)");
            ps.setString(1, currentUser);
            ps.setString(2, to);
            ps.setString(3, subject);
            ps.setString(4, content);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "✅ Gửi email thành công!");
            txtTo.setText("");
            txtSubject.setText("");
            txtContent.setText("");

            loadSent();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Lỗi khi gửi email!");
        }
    }

    // =============== HIỂN THỊ EMAIL CHI TIẾT ===============
    private void showEmailDetail(String title, String user, String subject, String content, boolean isInbox) {
        JTextArea txtDetail = new JTextArea();
        txtDetail.setEditable(false);
        txtDetail.setLineWrap(true);
        txtDetail.setWrapStyleWord(true);

        txtDetail.setText(
            (isInbox ? "Người gửi: " : "Người nhận: ") + user + "\n" +
            "Chủ đề: " + subject + "\n\n" +
            "Nội dung:\n" + content
        );

        JScrollPane scrollPane = new JScrollPane(txtDetail);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JOptionPane.showMessageDialog(this, scrollPane, title, JOptionPane.INFORMATION_MESSAGE);
    }

    // =============== LOAD DỮ LIỆU INBOX ===============
    private void loadInbox() {
        try {
            inboxModel.setRowCount(0);
            Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT sender, subject, content FROM emails WHERE receiver=?");
            ps.setString(1, currentUser);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                inboxModel.addRow(new Object[]{
                        rs.getString("sender"),
                        rs.getString("subject"),
                        rs.getString("content")
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // =============== LOAD DỮ LIỆU SENT ===============
    private void loadSent() {
        try {
            sentModel.setRowCount(0);
            Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT receiver, subject, content FROM emails WHERE sender=?");
            ps.setString(1, currentUser);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sentModel.addRow(new Object[]{
                        rs.getString("receiver"),
                        rs.getString("subject"),
                        rs.getString("content")
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
