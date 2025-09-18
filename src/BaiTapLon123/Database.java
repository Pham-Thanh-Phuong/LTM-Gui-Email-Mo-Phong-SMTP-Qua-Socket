package BaiTapLon123;

import java.sql.*;

public class Database {
    private static final String URL = "jdbc:sqlite:email_app.db";

    static {
        try (Connection conn = getConnection();
             Statement st = conn.createStatement()) {
            // Tạo bảng users nếu chưa có
            st.execute("CREATE TABLE IF NOT EXISTS users(" +
                       "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                       "username TEXT UNIQUE," +
                       "password TEXT)");

            // Tạo bảng emails nếu chưa có
            st.execute("CREATE TABLE IF NOT EXISTS emails(" +
                       "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                       "sender TEXT," +
                       "receiver TEXT," +
                       "subject TEXT," +
                       "content TEXT," +
                       "time TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
