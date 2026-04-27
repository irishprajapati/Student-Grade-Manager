package impl;

import Interfaces.UserDAO;
import dao.BaseDao;
import model.Role;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl extends BaseDao implements UserDAO {
    @Override
    public void addUser(User u) throws SQLException {
        String sql = "INSERT INTO users(full_name, password, role) VALUES(?,?,?)";
        try(Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, u.getfullname());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getRole().name());
            int rows = ps.executeUpdate();
            if(rows>0){
                System.out.println("User added successfully as: " + u.getfullname());
            }
        }catch (SQLException e){
            System.out.println("Error adding user: " + e.getMessage());
        }
    }

    @Override
    public void deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id=?";
        try(Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            if(rowsAffected  > 0){
                System.out.println("User deleted successfully");
            }else{
                System.out.println("User not found with id: " + id);
            }
        }catch (SQLException e){
            System.out.println("Error deleting user: " + e.getMessage());
        }
    }

    @Override
    public void updateUser(User u) throws SQLException {
        String sql = "UPDATE users SET full_name=?, password=?, role=? WHERE id=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getfullname());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getRole().name());
            ps.setInt(4, u.getId());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("User updated successfully");
            } else {
                System.out.println("User not found with id: " + u.getId());
            }
        } catch (SQLException e) {
            System.out.println("Error updating user: " + e.getMessage());
        }
    }

    @Override
    public User getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("full_name"),
                        rs.getString("password"),
                        Role.valueOf(rs.getString("role")),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            System.out.println("Error getting user: " + e.getMessage());
        }
        return null;
    }

    @Override
    public User getUserByFullName(String fullName) throws SQLException {
        String sql = "SELECT * FROM users WHERE full_name=?";
        try(Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, fullName);
            ResultSet rs= ps.executeQuery();
            if(rs.next()){
                return new User(
                        rs.getInt("id"),
                        rs.getString("full_name"),
                        rs.getString("password"),
                        Role.valueOf(rs.getString("role")),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
            }
        }catch (SQLException e){
            System.out.println("Error getting User by name: " + e.getMessage());
        }
        return  null;
    }

    @Override
    public List<User> getAllUser() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try(Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("full_name"),
                        rs.getString("password"),
                        Role.valueOf(rs.getString("role")),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
                users.add(user);
            }
        }catch (SQLException e){
            System.out.println("Error getting all users: " + e.getMessage());
        }
        return users;
    }
}
