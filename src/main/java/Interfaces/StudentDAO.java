package Interfaces;

import model.Student;

import java.sql.SQLException;
import java.util.List;

public interface StudentDAO {
    //Admin operations
    void addStudent(Student s) throws SQLException;
    void deleteStudent(int id) throws SQLException;
    void updateStudent(Student s) throws SQLException;
    List<Student> getAllStudents() throws SQLException;

    //Shared operation
    Student getStudentById(int id) throws SQLException;
    Student getStudentByUserId(int userId) throws SQLException;

    //Teacher operations
    List<Student> getStudentBySubjectId(int subjectId) throws SQLException;

    //Student operations
    Student getOwnProfile(int id) throws SQLException;
}
