package impl;

import Interfaces.GradeDAO;
import model.Grade;
import db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static db.DBConnection.getConnection;

public class GradeDAOImpl implements GradeDAO {
    @Override
    public void addGrade(Grade g) throws SQLException {
    String sql = "INSERT INTO grades(student_id, subject_id, marks, grade) VALUES(?,?,?,?)";
    try(Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(sql)){
        ps.setInt(1, g.getStudentId());
        ps.setInt(2, g.getSubjectId());
        ps.setDouble(3, g.getMarks());
        ps.setString(4, g.getGrade());
        int rows = ps.executeUpdate();
        if(rows > 0)
    }
    }

    @Override
    public void updateGrade(Grade g) throws SQLException {

    }

    @Override
    public void deleteGrade(int delete) throws SQLException {

    }

    @Override
    public List<Grade> getGradesByStudentId(int studentId) throws SQLException {
        return List.of();
    }

    @Override
    public List<Grade> getGradeBySubjectId(int subjectId) throws SQLException {
        return List.of();
    }

    @Override
    public Grade getGradeById(int id) throws SQLException {
        return null;
    }

    @Override
    public Grade getGradeByStudentAndSubject(int studentId, int subjectId) throws SQLException {
        return null;
    }
}
