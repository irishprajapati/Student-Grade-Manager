package impl;

import Interfaces.StudentDAO;
import dao.BaseDao;
import model.Gender;
import model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Exception.DatabaseException;

public class StudentDAOImpl extends BaseDao implements StudentDAO {

    @Override
    public void addStudent(Student s) {
        String sql = "INSERT INTO students(user_id, full_name, gender, phone_number, email, location, guardian_name, date_of_birth) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, s.getUserId());
            ps.setString(2, s.getFullName());
            ps.setString(3, s.getGender().name());
            ps.setString(4, s.getPhoneNumber());
            ps.setString(5, s.getEmail());
            ps.setString(6, s.getLocation());
            ps.setString(7, s.getGuardianName());
            ps.setDate(8, Date.valueOf(s.getDateOfBirth()));

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new DatabaseException("Failed to add student, no rows affected.");
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error adding student", e);
        }
    }

    @Override
    public void deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new DatabaseException("Student not found with id: " + id);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error deleting student", e);
        }
    }

    @Override
    public void updateStudent(Student s) {
        String sql = "UPDATE students SET full_name=?, gender=?, phone_number=?, email=?, location=?, guardian_name=?, date_of_birth=? WHERE id=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, s.getFullName());
            ps.setString(2, s.getGender().name());
            ps.setString(3, s.getPhoneNumber());
            ps.setString(4, s.getEmail());
            ps.setString(5, s.getLocation());
            ps.setString(6, s.getGuardianName());
            ps.setDate(7, Date.valueOf(s.getDateOfBirth()));
            ps.setInt(8, s.getId());

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new DatabaseException("Student not found with id: " + s.getId());
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error updating student", e);
        }
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                students.add(mapStudent(rs));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error getting students", e);
        }

        return students;
    }

    @Override
    public Student getStudentById(int id) {
        String sql = "SELECT * FROM students WHERE id=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapStudent(rs);
            }

            return null;

        } catch (SQLException e) {
            throw new DatabaseException("Error getting student by id", e);
        }
    }

    @Override
    public Student getStudentByUserId(int userId) {
        String sql = "SELECT * FROM students WHERE user_id=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapStudent(rs);
            }

            return null;

        } catch (SQLException e) {
            throw new DatabaseException("Error getting student by userId", e);
        }
    }

    @Override
    public List<Student> getStudentBySubjectId(int subjectId) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT DISTINCT s.* FROM students s " +
                "JOIN grades g ON s.id = g.student_id " +
                "WHERE g.subject_id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, subjectId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                students.add(mapStudent(rs));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error getting students by subjectId", e);
        }

        return students;
    }

    @Override
    public Student getOwnProfile(int userId) {
        String sql = "SELECT * FROM students WHERE user_id=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapStudent(rs);
            }

            return null;

        } catch (SQLException e) {
            throw new DatabaseException("Error getting own profile", e);
        }
    }

    // Reusable mapper (avoids duplication)
    private Student mapStudent(ResultSet rs) throws SQLException {
        return new Student(
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getString("full_name"),
                Gender.valueOf(rs.getString("gender")),
                rs.getString("phone_number"),
                rs.getString("email"),
                rs.getString("location"),
                rs.getString("guardian_name"),
                rs.getDate("date_of_birth").toLocalDate(),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}