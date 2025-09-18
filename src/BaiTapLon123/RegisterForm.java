package BaiTapLon123;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RegisterForm extends JFrame {
    private JTextField txtUser;
    private JPasswordField txtPass;

    public RegisterForm() {
        setTitle("Đăng ký - Email App");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new JLabel("ĐĂNG KÝ TÀI KHOẢN", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(new Color(192, 57, 43));
        panel.add(lblTitle, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
        form.add(new JLabel("Tài khoản:"));
        txtUser = new JTextField();
        form.add(txtUser);

        form.add(new JLabel("Mật khẩu:"));
        txtPass = new JPasswordField();
        form.add(txtPass);

        panel.add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnRegister = new JButton("Đăng ký");
        btnRegister.setPreferredSize(new Dimension(120, 35));
        btnRegister.setBackground(new Color(52, 152, 219));
        btnRegister.setForeground(Color.WHITE);

        JButton btnBack = new JButton("Quay lại");
        btnBack.setPreferredSize(new Dimension(120, 35));
        btnBack.setBackground(new Color(149, 165, 166));
        btnBack.setForeground(Color.WHITE);

        buttons.add(btnRegister);
        buttons.add(btnBack);

        panel.add(buttons, BorderLayout.SOUTH);
        add(panel);

        // Xử lý đăng ký
        btnRegister.addActionListener(e -> register());

        // Quay lại login
        btnBack.addActionListener(e -> {
            new LoginForm().setVisible(true);
            dispose();
        });
    }

    private void register() {
        String user = txtUser.getText().trim();
        String pass = new String(txtPass.getPassword()).trim();

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống tài khoản hoặc mật khẩu!");
            return;
        }

        try (Connection conn = Database.getConnection()) {
            PreparedStatement check = conn.prepareStatement("SELECT * FROM users WHERE username=?");
            check.setString(1, user);
            ResultSet rs = check.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Tài khoản đã tồn tại!");
                return;
            }

            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO users(username,password) VALUES(?,?)");
            ps.setString(1, user);
            ps.setString(2, pass);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Đăng ký thành công!");
            new LoginForm().setVisible(true);
            dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi đăng ký!");
        }
    }
}
