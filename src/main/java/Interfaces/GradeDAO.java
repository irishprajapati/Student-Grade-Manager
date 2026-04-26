package Interfaces;

import model.Grade;
import model.Student;

import java.sql.SQLException;
import java.util.List;

public interface GradeDAO {
    void addGrade(Grade g) throws SQLException;
    void updateGrade(Grade g) throws SQLException;
    void deleteGrade(int delete) throws SQLException;

    List<Grade> getGradesByStudentId(int studentId) throws SQLException;
    List<Grade> getGradeBySubjectId(int subjectId) throws SQLException;
    Grade getGradeById(int id) throws SQLException;

    Grade getGradeByStudentAndSubject(int studentId, int subjectId) throws SQLException;
}
