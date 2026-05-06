package impl;

import Interfaces.GradeDAO;
import dao.BaseDao;
import model.Grade;
import Exception.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static db.DBConnection.getConnection;

public class GradeDAOImpl extends BaseDao implements GradeDAO {

    @Override
    public void addGrade(Grade g) {
        String sql = "INSERT INTO grades(student_id, subject_id, marks, grade) VALUES(?,?,?,?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, g.getStudentId());
            ps.setInt(2, g.getSubjectId());
            ps.setDouble(3, g.getMarks());
            ps.setString(4, g.getGrade());

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new DatabaseException("Failed to add grade, no rows affected");
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error adding grade", e);
        }
    }

    @Override
    public void updateGrade(Grade g) {
        String sql = "UPDATE grades SET student_id=?, subject_id=?, marks=?, grade=? WHERE id=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, g.getStudentId());
            ps.setInt(2, g.getSubjectId());
            ps.setDouble(3, g.getMarks());
            ps.setString(4, g.getGrade());
            ps.setInt(5, g.getId()); // ❗ YOU MISSED THIS

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new DatabaseException("Grade not found with id: " + g.getId());
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error updating grade", e);
        }
    }

    @Override
    public void deleteGrade(int id) {
        String sql = "DELETE FROM grades WHERE id=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new DatabaseException("Grade not found with id: " + id);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error deleting grade", e);
        }
    }

    @Override
    public List<Grade> getGradesByStudentId(int studentId) {
        List<Grade> grades = new ArrayList<>();

        String sql = "SELECT * FROM grades WHERE student_id=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                grades.add(mapGrade(rs));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error fetching grades by studentId", e);
        }

        return grades; // ❗ YOU FORGOT THIS
    }

    @Override
    public List<Grade> getGradeBySubjectId(int subjectId) {
        List<Grade> grades = new ArrayList<>();

        String sql = "SELECT * FROM grades WHERE subject_id=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, subjectId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                grades.add(mapGrade(rs));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error fetching grades by subjectId", e);
        }

        return grades;
    }

    @Override
    public Grade getGradeById(int id) {
        String sql = "SELECT * FROM grades WHERE id=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapGrade(rs);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error fetching grade by id", e);
        }

        return null;
    }

    @Override
    public Grade getGradeByStudentAndSubject(int studentId, int subjectId) {
        String sql = "SELECT * FROM grades WHERE student_id=? AND subject_id=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, subjectId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapGrade(rs);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error fetching grade by student and subject", e);
        }

        return null;
    }

    // 🔥 Central mapping method (you were missing this)
    private Grade mapGrade(ResultSet rs) throws SQLException {
        return new Grade(
                rs.getInt("id"),
                rs.getInt("student_id"),
                rs.getInt("subject_id"),
                rs.getDouble("marks")
        );
    }
}