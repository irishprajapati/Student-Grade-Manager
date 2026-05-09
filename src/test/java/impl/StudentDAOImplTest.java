package impl;

import model.Gender;
import model.Student;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import Exception.DatabaseException;


import javax.xml.crypto.Data;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StudentDAOImplTest {
    //fake objects - Mockito creates them
    Connection mockConnection;
    PreparedStatement mockPreparedStatement;
    ResultSet mockResultSet;

    StudentDAOImpl dao;
    @BeforeEach
    void setup () throws Exception{
        //Step 1: create the fake objects
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        //Step 2: create a StudentDAOImpl that overrides getConnection()
        dao = new StudentDAOImpl(){
            @Override
            protected Connection getConnection() throws SQLException{
                return mockConnection;
            }
        };
        when(mockConnection.prepareStatement(any(String.class)))
                .thenReturn(mockPreparedStatement);

        when(mockPreparedStatement.executeQuery())
                .thenReturn(mockResultSet);
    }
    @DisplayName("Returns null when student is not found")
    @Test
    void shouldReturnNullWhenStudentNotFound() throws SQLException{
        when(mockResultSet.next()).thenReturn(false);
        Student result = dao.getStudentById(999);
        assertNull(result);
    }
    @DisplayName("Return information if student is found")
    @Test
    void shouldReturnStudentWhenFound() throws Exception {
      when(mockResultSet.next()).thenReturn(true);
      when(mockResultSet.getInt("id")).thenReturn(10);
      when(mockResultSet.getInt("user_id")).thenReturn(1);
      when(mockResultSet.getString("full_name")).thenReturn("Ram Thapa");
      when(mockResultSet.getString("gender")).thenReturn("MALE");
      when(mockResultSet.getString("phone_number")).thenReturn("9841787876");
    when(mockResultSet.getString("email")).thenReturn("ram@example.com");
    when(mockResultSet.getString("location")).thenReturn("Kathmandu");
    when(mockResultSet.getString("guardian_name")).thenReturn("Hari Sharma");
    when(mockResultSet.getDate("date_of_birth")).thenReturn(Date.valueOf(LocalDate.of(2001,1,12)));
        when(mockResultSet.getTimestamp("created_at"))
                .thenReturn(Timestamp.valueOf(LocalDateTime.now()));
        when(mockResultSet.getTimestamp("updated_at"))
                .thenReturn(Timestamp.valueOf(LocalDateTime.now()));
        Student result = dao.getStudentByUserId(10);

        assertNotNull(result);
        assertEquals("Ram Thapa", result.getFullName());
        assertEquals("Kathmandu", result.getLocation());
    }
    @Nested
    class DeleteStudent {
        //add student query
        @DisplayName("Returns the database exception if student is not found")
        @Test
        void shouldThrowDatabaseExceptionWhenStudentNotFound() throws SQLException {
            when(mockPreparedStatement.executeUpdate()).thenReturn(0);
            Student s = new Student(
                    1,
                    "Ram Thapa",
                    Gender.MALE,
                    "9841786756",
                    "ram@example.com",
                    "Kathmandu", "Hari Sharma",
                    LocalDate.of(2000, 1, 15)
            );
            assertThrows(DatabaseException.class, () -> dao.addStudent(s));
        }

        @Test
        void shouldThrowWhenStudentNotFound() throws SQLException {
            when(mockPreparedStatement.executeUpdate()).thenReturn(0);
            assertThrows(DatabaseException.class, () -> dao.deleteStudent(1));
        }

        @Test
        void shouldDeleteStudentSuccessfully() throws SQLException {
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);
            assertDoesNotThrow(() -> dao.deleteStudent(1));
        }
    }
    @Nested
    class UpdateStudent {
        @DisplayName("Return when Student information is not updated")
        @Test
        void shouldThrowWhenStudentIsNotUpdated() throws SQLException {
            when(mockPreparedStatement.executeUpdate()).thenReturn(0);
            Student s = new Student(
                    1, "Ram Thapa", Gender.MALE,
                    "9841000000", "ram@example.com",
                    "Kathmandu", "Hari Sharma",
                    LocalDate.of(2000, 1, 15)
            );
            assertThrows(DatabaseException.class, () -> dao.updateStudent(s));
        }

        @Test
        @DisplayName("Return Information when update is successful")
        void shouldUpdateStudentSuccessfully() throws SQLException {
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);
            Student s = new Student(
                    1, "Ram Thapa", Gender.MALE,
                    "9841000000", "ram@example.com",
                    "Kathmandu", "Hari Sharma",
                    LocalDate.of(2000, 1, 15)
            );
            assertDoesNotThrow(() -> dao.updateStudent(s));
        }

        @Test
        void shouldThrowWhenSQLFails() throws SQLException {
            when(mockConnection.prepareStatement(any(String.class))).thenThrow(new SQLException("Connection lost"));
            Student s = new Student(
                    1, "Ram Thapa", Gender.MALE,
                    "9841000000", "ram@example.com",
                    "Kathmandu", "Hari Sharma",
                    LocalDate.of(2000, 1, 15)
            );
            assertThrows(DatabaseException.class, () -> dao.updateStudent(s));
        }
    }
    @Nested
    class getOwnProfile {
        @Test
        void shouldReturnStudentWhenProfileFound() throws SQLException {
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt("id")).thenReturn(1);
            when(mockResultSet.getInt("user_id")).thenReturn(1);
            when(mockResultSet.getString("full_name")).thenReturn("Ram Thapa");
            when(mockResultSet.getString("gender")).thenReturn("MALE");
            when(mockResultSet.getString("phone_number")).thenReturn("9841000000");
            when(mockResultSet.getString("email")).thenReturn("ram@example.com");
            when(mockResultSet.getString("location")).thenReturn("Kathmandu");
            when(mockResultSet.getString("guardian_name")).thenReturn("Hari Sharma");
            when(mockResultSet.getDate("date_of_birth"))
                    .thenReturn(Date.valueOf(LocalDate.of(2000, 1, 15)));
            when(mockResultSet.getTimestamp("created_at"))
                    .thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            when(mockResultSet.getTimestamp("updated_at"))
                    .thenReturn(Timestamp.valueOf(LocalDateTime.now()));

            Student result = dao.getOwnProfile(1);

            assertNotNull(result);
            assertEquals("Ram Thapa", result.getFullName());
        }
        @Test
        void shouldThrowWhenSQLFails() throws SQLException {
            when(mockConnection.prepareStatement(any(String.class))).thenThrow(new SQLException("Connection lost"));
            Student s = new Student(
                    1, "Ram Thapa", Gender.MALE,
                    "9841000000", "ram@example.com",
                    "Kathmandu", "Hari Sharma",
                    LocalDate.of(2000, 1, 15)
            );
            assertThrows(DatabaseException.class, ()-> dao.getOwnProfile(1));
        }
    }
    @Test
    void shouldThrowNullWhenProfileNotFound() throws SQLException{
        when(mockResultSet.next()).thenReturn(false);
        Student result = dao.getOwnProfile(999);
        assertNull(result);
    }


@Nested
@DisplayName("Fetching Students By Subject ID")
    class getStudentBySubjectId{
    @Test
    void shouldReturnStudentsWhenFound() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("id")).thenReturn(1, 2);
        when(mockResultSet.getInt("user_id")).thenReturn(1, 2);
        when(mockResultSet.getString("full_name")).thenReturn("Ram Thapa", "Hari Sharma");
        when(mockResultSet.getString("gender")).thenReturn("MALE", "FEMALE");
        when(mockResultSet.getString("phone_number")).thenReturn("9841000000", "9841000001");
        when(mockResultSet.getString("email")).thenReturn("ram@example.com", "hari@example.com");
        when(mockResultSet.getString("location")).thenReturn("Kathmandu", "Bhaktapur");
        when(mockResultSet.getString("guardian_name")).thenReturn("Hari Sharma", "Ram Sharma");
        when(mockResultSet.getDate("date_of_birth"))
                .thenReturn(Date.valueOf(LocalDate.of(2000, 1, 15)));
        when(mockResultSet.getTimestamp("created_at"))
                .thenReturn(Timestamp.valueOf(LocalDateTime.now()));
        when(mockResultSet.getTimestamp("updated_at"))
                .thenReturn(Timestamp.valueOf(LocalDateTime.now()));

        List<Student> students = dao.getStudentBySubjectId(1);

        assertEquals(2, students.size());
        assertEquals("Ram Thapa", students.get(0).getFullName());
        assertEquals("Hari Sharma", students.get(1).getFullName());
    }
    @Test
    void shouldReturnEmptyListWhenNoStudentsFound() throws SQLException {
        when(mockResultSet.next()).thenReturn(false);
        List<Student> students = dao.getStudentBySubjectId(999);
        assertNotNull(students);
        assertEquals(0, students.size());
    }

    @Test
    void shouldThrowWhenSQLFails() throws SQLException {
        when(mockConnection.prepareStatement(any(String.class)))
                .thenThrow(new SQLException("Connection lost"));
        assertThrows(DatabaseException.class, () -> dao.getStudentBySubjectId(1));
    }
    }
}
