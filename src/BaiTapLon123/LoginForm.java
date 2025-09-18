package BaiTapLon123;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginForm extends JFrame {
    private JTextField txtUser;
    private JPasswordField txtPass;

    public LoginForm() {
        setTitle("Đăng nhập - Email App");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new JLabel("ĐĂNG NHẬP HỆ THỐNG", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(new Color(41, 128, 185));
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
        JButton btnLogin = new JButton("Đăng nhập");
        btnLogin.setPreferredSize(new Dimension(120, 35));
        btnLogin.setBackground(new Color(39, 174, 96));
        btnLogin.setForeground(Color.WHITE);

        JButton btnRegister = new JButton("Đăng ký");
        btnRegister.setPreferredSize(new Dimension(120, 35));
        btnRegister.setBackground(new Color(52, 152, 219));
        btnRegister.setForeground(Color.WHITE);

        buttons.add(btnLogin);
        buttons.add(btnRegister);

        panel.add(buttons, BorderLayout.SOUTH);
        add(panel);

        // Sự kiện đăng nhập
        btnLogin.addActionListener(e -> login());

        // Mở form đăng ký
        btnRegister.addActionListener(e -> {
            new RegisterForm().setVisible(true);
            dispose();
        });
    }

    private void login() {
        String user = txtUser.getText().trim();
        String pass = new String(txtPass.getPassword()).trim();

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tài khoản và mật khẩu!");
            return;
        }

        try (Connection conn = Database.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM users WHERE username=? AND password=?");
            ps.setString(1, user);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
                new MainForm(user).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi kết nối CSDL!");
        }
    }
}
