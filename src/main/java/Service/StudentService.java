package Service;

import Interfaces.StudentDAO;
import model.Student;

import java.sql.SQLException;
import java.util.List;

public class StudentService {

    // StudentService DEPENDS ON StudentDAO
    // this dependency will be injected — by hand now, by Spring later
    private final StudentDAO studentDAO;

    public StudentService(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public void addStudent(Student student) throws SQLException {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        studentDAO.addStudent(student);
    }

    public Student getStudentById(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive");
        }
        return studentDAO.getStudentById(id);
    }

    public List<Student> getAllStudents() throws SQLException {
        return studentDAO.getAllStudents();
    }

    public void deleteStudent(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive");
        }
        studentDAO.deleteStudent(id);
    }
}