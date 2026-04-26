package Interfaces;

import model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    void addUser(User u) throws SQLException;
    void deleteUser(int id) throws SQLException;
    void updateUser(int id) throws SQLException;
    User getUserById(int id) throws SQLException;
    User getUserByUserId(int userId) throws SQLException;
    User getUserByFullName(String fullName) throws SQLException;
    List<User> getAllUser() throws SQLException;
}
