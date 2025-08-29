package murach.data;

import murach.business.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDB {

    private static String url() {
        String env = System.getenv("DB_URL");
        if (env != null && !env.isEmpty())
            return env;
        return "jdbc:h2:file:./data/emaillist;DB_CLOSE_DELAY=-1;MODE=MySQL";
    }

    private static String user() {
        return System.getenv().getOrDefault("DB_USER", "sa");
    }

    private static String pass() {
        return System.getenv().getOrDefault("DB_PASSWORD", "");
    }

    private static Connection getConnection() throws SQLException {
        try {
            if (url().startsWith("jdbc:h2"))
                Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException ignored) {
        }
        return DriverManager.getConnection(url(), user(), pass());
    }

    /** Tạo bảng nếu chưa tồn tại */
    public static void init() {
        String sql = """
				    CREATE TABLE IF NOT EXISTS users (
				      id IDENTITY PRIMARY KEY,
				      email VARCHAR(255) UNIQUE NOT NULL,
				      first_name VARCHAR(100),
				      last_name  VARCHAR(100),
				      date_of_birth VARCHAR(20),
				      ref VARCHAR(50),
				      want_offers BOOLEAN,
				      want_email  BOOLEAN,
				      contact VARCHAR(50)
				    )
				""";
        try (Connection c = getConnection(); Statement st = c.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void upsert(User u) {
        String sql = """
				    MERGE INTO users (email, first_name, last_name, date_of_birth, ref, want_offers, want_email, contact)
				    KEY(email) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
				""";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, u.getEmail());
            ps.setString(2, u.getFirstName());
            ps.setString(3, u.getLastName());
            ps.setString(4, u.getDateOfBirth());
            ps.setString(5, u.getRef());
            ps.setBoolean(6, u.isWantOffers());
            ps.setBoolean(7, u.isWantEmail());
            ps.setString(8, u.getContact());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return map(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<User> listAll() {
        List<User> out = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY id DESC";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next())
                out.add(map(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return out;
    }

    private static User map(ResultSet rs) throws SQLException {
        return new User(rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"),
                rs.getString("date_of_birth"), rs.getString("ref"), rs.getBoolean("want_offers"),
                rs.getBoolean("want_email"), rs.getString("contact"));
    }
}