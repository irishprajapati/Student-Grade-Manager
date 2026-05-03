package impl;

import Interfaces.TeacherDAO;
import dao.BaseDao;
import model.Teacher;
import Exception.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAOImpl extends BaseDao implements TeacherDAO {

    @Override
    public void addTeacher(Teacher t) {
        String sql = "INSERT INTO teachers(user_id, full_name, location, phone_number, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, t.getUserId());
            ps.setString(2, t.getFullName());
            ps.setString(3, t.getLocation());
            ps.setString(4, t.getPhoneNumber());
            ps.setTimestamp(5, Timestamp.valueOf(t.getCreatedAt()));
            ps.setTimestamp(6, Timestamp.valueOf(t.getUpdatedAt()));

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new DatabaseException("Failed to add teacher, no rows affected.");
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error adding teacher", e);
        }
    }

    @Override
    public void updateTeacher(Teacher t) {
        String sql = "UPDATE teachers SET full_name=?, location=?, phone_number=?, updated_at=? WHERE id=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, t.getFullName());
            ps.setString(2, t.getLocation());
            ps.setString(3, t.getPhoneNumber());
            ps.setTimestamp(4, Timestamp.valueOf(t.getUpdatedAt()));
            ps.setInt(5, t.getId());

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new DatabaseException("Teacher not found with id: " + t.getId());
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error updating teacher", e);
        }
    }

    @Override
    public void deleteTeacher(int id) {
        String sql = "DELETE FROM teachers WHERE id=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new DatabaseException("Teacher not found with id: " + id);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error deleting teacher", e);
        }
    }

    @Override
    public Teacher getTeacherById(int id) {
        String sql = "SELECT * FROM teachers WHERE id=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapTeacher(rs);
            }

            return null;

        } catch (SQLException e) {
            throw new DatabaseException("Error getting teacher by id", e);
        }
    }

    @Override
    public Teacher getTeacherByUserId(int userId) {
        String sql = "SELECT * FROM teachers WHERE user_id=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapTeacher(rs);
            }

            return null;

        } catch (SQLException e) {
            throw new DatabaseException("Error getting teacher by userId", e);
        }
    }

    @Override
    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT * FROM teachers";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                teachers.add(mapTeacher(rs));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error getting all teachers", e);
        }

        return teachers;
    }

    // reusable mapper
    private Teacher mapTeacher(ResultSet rs) throws SQLException {
        return new Teacher(
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getString("full_name"),
                rs.getString("location"),
                rs.getString("phone_number"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}