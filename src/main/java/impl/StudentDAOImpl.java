package impl;

import Interfaces.StudentDAO;
import dao.BaseDao;
import model.Gender;
import model.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl extends BaseDao implements StudentDAO {

    @Override
    public void addStudent(Student s) throws SQLException {
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
            ps.setDate(8, java.sql.Date.valueOf(s.getDateOfBirth()));
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Student added successfully");
            }
        } catch (SQLException e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }

    @Override
    public void deleteStudent(int id) throws SQLException {
        String sql = "DELETE FROM students WHERE id=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student deleted successfully");
            } else {
                System.out.println("Student not found with id: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error while deleting student: " + e.getMessage());
        }
    }

    @Override
    public void updateStudent(Student s) throws SQLException {
        String sql = "UPDATE students SET full_name=?, gender=?, phone_number=?, email=?, location=?, guardian_name=?, date_of_birth=? WHERE id=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, s.getFullName());
            ps.setString(2, s.getGender().name());
            ps.setString(3, s.getPhoneNumber());
            ps.setString(4, s.getEmail());
            ps.setString(5, s.getLocation());
            ps.setString(6, s.getGuardianName());
            ps.setDate(7, java.sql.Date.valueOf(s.getDateOfBirth()));
            ps.setInt(8, s.getId());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Student updated successfully");
            } else {
                System.out.println("Student not found with id: " + s.getId());
            }
        } catch (SQLException e) {
            System.out.println("Error updating student: " + e.getMessage());
        }
    }

    @Override
    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                students.add(new Student(
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
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error getting students: " + e.getMessage());
        }
        return students;
    }

    @Override
    public Student getStudentById(int id) throws SQLException {
        String sql = "SELECT * FROM students WHERE id=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
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
        } catch (SQLException e) {
            System.out.println("Error getting student by id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Student getStudentByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM students WHERE user_id=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
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
        } catch (SQLException e) {
            System.out.println("Error getting student by userId: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Student> getStudentBySubjectId(int subjectId) throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT DISTINCT s.* FROM students s " +
                "JOIN grades g ON s.id = g.student_id " +
                "WHERE g.subject_id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, subjectId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                students.add(new Student(
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
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error getting students by subjectId: " + e.getMessage());
        }
        return students;
    }

    @Override
    public Student getOwnProfile(int userId) throws SQLException {
        String sql = "SELECT * FROM students WHERE user_id=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
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
        } catch (SQLException e) {
            System.out.println("Error getting own profile: " + e.getMessage());
        }
        return null;
    }
}