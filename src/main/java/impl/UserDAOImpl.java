package impl;

import Interfaces.UserDAO;
import dao.BaseDao;
import model.Role;
import model.User;
import Exception.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl extends BaseDao implements UserDAO {

    @Override
    public void addUser(User u) {
        String sql = "INSERT INTO users(full_name, password, role) VALUES(?,?,?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getfullname());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getRole().name());

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new DatabaseException("Failed to add user, no rows affected.");
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error adding user", e);
        }
    }

    @Override
    public void deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new DatabaseException("User not found with id: " + id);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error deleting user", e);
        }
    }

    @Override
    public void updateUser(User u) {
        String sql = "UPDATE users SET full_name=?, password=?, role=? WHERE id=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getfullname());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getRole().name());
            ps.setInt(4, u.getId());

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new DatabaseException("User not found with id: " + u.getId());
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error updating user", e);
        }
    }

    @Override
    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapUser(rs);
            }

            return null;

        } catch (SQLException e) {
            throw new DatabaseException("Error getting user by id", e);
        }
    }

    @Override
    public User getUserByFullName(String fullName) {
        String sql = "SELECT * FROM users WHERE full_name=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, fullName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapUser(rs);
            }

            return null;

        } catch (SQLException e) {
            throw new DatabaseException("Error getting user by full name", e);
        }
    }

    @Override
    public List<User> getAllUser() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                users.add(mapUser(rs));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error getting all users", e);
        }

        return users;
    }

    // reusable mapper
    private User mapUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("full_name"),
                rs.getString("password"),
                Role.valueOf(rs.getString("role")),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}