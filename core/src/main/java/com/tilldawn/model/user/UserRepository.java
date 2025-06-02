package com.tilldawn.model.user;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            score INTEGER DEFAULT 0,
            kills INTEGER DEFAULT 0,
            longest_time INTEGER DEFAULT 0,
            path TEXT
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
        (username, password, security_question, security_answer, score, kills, longest_time, path)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?);
    """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getSecurityQuestion());
            stmt.setString(4, user.getSecurityAnswer());
            stmt.setInt(5, user.getScore());
            stmt.setInt(6, user.getKills());
            stmt.setInt(7, user.getLongestTime());
            stmt.setString(8, user.getPath());
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
                user.setScore(rs.getInt("score"));
                user.setKills(rs.getInt("kills"));
                user.setLongestTime(rs.getInt("longest_time"));
                user.setPath(rs.getString("path"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void changePassword(String username, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newPassword);
            stmt.setString(2, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeUsername(String oldUsername, String newUsername) {
        String sql = "UPDATE users SET username = ? WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newUsername);
            stmt.setString(2, oldUsername);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(String username) {
        if (username.equals("GUEST")) return;
        String sql = "DELETE FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changePath(String username, String newPath) {
        String sql = "UPDATE users SET path = ? WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newPath);
            stmt.setString(2, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public List<User> getByUsername() {
        String sql = "SELECT * FROM users ORDER BY username COLLATE NOCASE ASC LIMIT 10";
        return getUsersFromQuery(sql);
    }

    public List<User> getByLongestTime() {
        String sql = "SELECT * FROM users ORDER BY longest_time DESC LIMIT 10";
        return getUsersFromQuery(sql);
    }

    public List<User> getByKills() {
        String sql = "SELECT * FROM users ORDER BY kills DESC LIMIT 10";
        return getUsersFromQuery(sql);
    }

    public List<User> getByScore() {
        String sql = "SELECT * FROM users ORDER BY score DESC LIMIT 10";
        return getUsersFromQuery(sql);
    }

    private List<User> getUsersFromQuery(String sql) {
        List<User> users = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                User user = new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("security_question"),
                    rs.getString("security_answer")
                );
                user.setScore(rs.getInt("score"));
                user.setKills(rs.getInt("kills"));
                user.setLongestTime(rs.getInt("longest_time"));
                user.setPath(rs.getString("path"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void updateUser(User user) {
        String sql = """
        UPDATE users SET
            password = ?,
            security_question = ?,
            security_answer = ?,
            score = ?,
            kills = ?,
            longest_time = ?,
            path = ?
        WHERE username = ?;
    """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getPassword());
            stmt.setString(2, user.getSecurityQuestion());
            stmt.setString(3, user.getSecurityAnswer());
            stmt.setInt(4, user.getScore());
            stmt.setInt(5, user.getKills());
            stmt.setInt(6, user.getLongestTime());
            stmt.setString(7, user.getPath());
            stmt.setString(8, user.getUsername());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
