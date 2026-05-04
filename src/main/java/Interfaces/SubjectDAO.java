package Interfaces;

import model.Subject;

import java.sql.SQLException;
import java.util.List;

public interface SubjectDAO {
    void addSubject(Subject s) throws SQLException;
    void deleteSubject(int id) throws SQLException;
    void updateSubject(Subject s) throws SQLException;
    Subject getSubjectById(int id) throws SQLException;
    Subject getSubjectByName(String name) throws SQLException;
    List<Subject> getAllSubjects() throws SQLException;
    List<Subject> getSubjectByTeacherId(int teacherId) throws SQLException;
}
