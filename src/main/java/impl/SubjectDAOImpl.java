package impl;

import Interfaces.SubjectDAO;
import dao.BaseDao;
import model.Subject;
import Exception.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAOImpl extends BaseDao implements SubjectDAO {

    @Override
    public void addSubject(Subject s) {
        String sql = "INSERT INTO subjects(name, teacher_id, created_at, updated_at) VALUES(?,?,?,?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, s.getName());
            ps.setInt(2, s.getTeacherId());
            ps.setTimestamp(3, Timestamp.valueOf(s.getCreatedAt()));
            ps.setTimestamp(4, Timestamp.valueOf(s.getUpdatedAt()));

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new DatabaseException("Failed to add subject");
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error adding subject", e);
        }
    }

    @Override
    public void deleteSubject(int id) {
        String sql = "DELETE FROM subjects WHERE id=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new DatabaseException("Subject not found with id: " + id);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error deleting subject", e);
        }
    }

    @Override
    public void updateSubject(Subject s) {
        String sql = "UPDATE subjects SET name=?, teacher_id=?, updated_at=? WHERE id=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, s.getName());
            ps.setInt(2, s.getTeacherId());
            ps.setTimestamp(3, Timestamp.valueOf(s.getUpdatedAt()));
            ps.setInt(4, s.getId());

            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new DatabaseException("Subject not found with id: " + s.getId());
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error updating subject", e);
        }
    }

    @Override
    public Subject getSubjectById(int id) {
        String sql = "SELECT * FROM subjects WHERE id=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapSubject(rs);
            }

            return null;

        } catch (SQLException e) {
            throw new DatabaseException("Error getting subject by id", e);
        }
    }

    @Override
    public Subject getSubjectByName(String name) {
        String sql = "SELECT * FROM subjects WHERE name=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapSubject(rs);
            }

            return null;

        } catch (SQLException e) {
            throw new DatabaseException("Error getting subject by name", e);
        }
    }

    @Override
    public List<Subject> getAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT * FROM subjects";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                subjects.add(mapSubject(rs));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error getting all subjects", e);
        }

        return subjects;
    }

    @Override
    public List<Subject> getSubjectByTeacherId(int teacherId) {
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT * FROM subjects WHERE teacher_id=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, teacherId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                subjects.add(mapSubject(rs));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error getting subjects by teacherId", e);
        }

        return subjects;
    }

    private Subject mapSubject(ResultSet rs) throws SQLException {
        return new Subject(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("teacher_id"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}