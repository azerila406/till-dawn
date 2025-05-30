package com.tilldawn.model.user;

import java.sql.*;

public class UserRepository {
    private static UserRepository repo;
    private Connection connection;

    private final String DB_URL = "jdbc:sqlite:users.db";

    private UserRepository() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            initializeDatabase();
            ensureGuestExists();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static UserRepository getInstance() {
        if (repo == null) {
            repo = new UserRepository();
        }
        return repo;
    }

    private void initializeDatabase() throws SQLException {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS users (
                username TEXT PRIMARY KEY,
                password TEXT,
                security_question TEXT,
                security_answer TEXT,
                score INTEGER DEFAULT 0
            );
        """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
        }
    }

    private void ensureGuestExists() {
        if (getByUsername("GUEST") == null) {
            add(new User("GUEST", "", "", ""));
        }
    }

    public void add(User user) {
        String sql = """
            INSERT OR REPLACE INTO users
            (username, password, security_question, security_answer, score)
            VALUES (?, ?, ?, ?, ?);
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getSecurityQuestion());
            stmt.setString(4, user.getSecurityAnswer());
            stmt.setInt(5, user.getScore());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("security_question"),
                    rs.getString("security_answer")
                );
                // Set score after construction
                user.increaseScore(rs.getInt("score"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User get(String username, String password) {
        User user = getByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public User getGuest() {
        return getByUsername("GUEST");
    }

    public void updateScore(String username, int newScore) {
        String sql = "UPDATE users SET score = ? WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, newScore);
            stmt.setString(2, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
