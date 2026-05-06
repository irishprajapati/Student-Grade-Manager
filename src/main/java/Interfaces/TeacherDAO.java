package Interfaces;
import model.Teacher;

import java.sql.SQLException;
import java.util.List;

public interface TeacherDAO {
    //method signature - what operations can teacher perform
    void addTeacher(Teacher t) throws SQLException;
     void updateTeacher(Teacher t) throws SQLException;
     void deleteTeacher(int id) throws SQLException;
     Teacher getTeacherById(int id) throws SQLException;
     Teacher getTeacherByUserId(int userId) throws SQLException;
     List<Teacher> getAllTeachers() throws SQLException;
}
