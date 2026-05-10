package impl;
import Interfaces.TeacherDAO;
import model.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import Exception.DatabaseException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TeacherDAOImplTest {
    Connection mockConnection;
    PreparedStatement mockPreparedStatement;
    ResultSet mockResultSet;
    TeacherDAO dao;
    Teacher t;
    @BeforeEach
    void setUp() throws Exception{
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        dao = new TeacherDAOImpl(){
            @Override
            protected Connection getConnection() throws SQLException{
                return mockConnection;
            }
        };
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        t = new Teacher(
                1, 1, "Rakesh Thapa",
                "Bhaktapur", "9841787666",
                LocalDate.of(2000, 1, 12).atStartOfDay(),
                LocalDate.of(2000, 1, 23).atStartOfDay()
        );
    }

    @Nested
    @DisplayName("Adding a teacher")
    class AddTeacher{
        @Test
        @DisplayName("Adding a teacher")
        void shouldAddTeacher() throws SQLException{
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);
            assertDoesNotThrow(()-> dao.addTeacher(t));
        }
        @Test
        @DisplayName("should throw database exception when no rows are affected")
        void shouldThrowDatabaseExceptionWhenNoRowsAffected() throws SQLException{
            when(mockPreparedStatement.executeUpdate()).thenReturn(0);
            assertThrows(DatabaseException.class, ()-> dao.addTeacher(t));
        }

        @Test
        @DisplayName("When SQL query fails")
        void shouldThrowWhenSQLFails() throws SQLException{
            when(mockConnection.prepareStatement(any(String.class))).thenThrow(new SQLException("Connection lost"));
            assertThrows(DatabaseException.class, ()-> dao.addTeacher(t));
        }
    }
@Nested
@DisplayName("Updating a teacher")
class UpdateTeacher{
    @Test
    @DisplayName("Should successfully update Teacher information")
    void shouldUpdateTeacherSuccessfully() throws SQLException{
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        assertDoesNotThrow(()-> dao.updateTeacher(t));
    }
    @Test
    @DisplayName("should throw when database doesnt affect rows")
    void shouldThrowWhenNoRowsAffected() throws SQLException{
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);
        assertThrows(DatabaseException.class, ()-> dao.updateTeacher(t));
    }
    @Test
    @DisplayName("Should throw when SQL query fails")
    void shouldThrowWhenSQLFails() throws SQLException{
    when(mockConnection.prepareStatement(any(String.class))).thenThrow(new SQLException("Connection lost"));
    assertThrows(DatabaseException.class, ()-> dao.updateTeacher(t));
    }
}
@Nested
    @DisplayName("Deleting a teacher")
    class DeleteTeacher{
    @Test
    @DisplayName("Deleting a teacher successfully")
    void shouldDeleteTeacherSuccessfully() throws SQLException{
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        assertDoesNotThrow(()-> dao.deleteTeacher(1));
    }
    @Test
    @DisplayName("Should throw the DatabaseException when no rows are affected")
    void shouldThrowWhenNoRowsAffected() throws SQLException{
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);
        assertThrows(DatabaseException.class, ()-> dao.deleteTeacher(0));
    }
    @Test
    @DisplayName("Should throw when SQL query fails")
    void shouldThrowWhenSQLFails() throws SQLException{
        when(mockConnection.prepareStatement(any(String.class))).thenThrow(new SQLException("Connection lost"));
        assertThrows(DatabaseException.class, ()-> dao.deleteTeacher(1));
    }
}
@Nested
@DisplayName("Fetching teacher details from id")
    class getTeacherById{
    @Test
    @DisplayName("Getting information by teacher id")
    void shouldFetchTeacherFromTeacherId() throws SQLException{
    when(mockResultSet.next()).thenReturn(true);
    when(mockResultSet.getInt("user_id")).thenReturn(1);
    when(mockResultSet.getString("full_name")).thenReturn("Alex Intaa");
    when(mockResultSet.getString("location")).thenReturn("Bhaisipati");
    when(mockResultSet.getString("phone_number")).thenReturn("9841865655");
        when(mockResultSet.getTimestamp("created_at"))
                .thenReturn(Timestamp.valueOf(LocalDateTime.now()));
        when(mockResultSet.getTimestamp("updated_at"))
                .thenReturn(Timestamp.valueOf(LocalDateTime.now()));

        Teacher information = dao.getTeacherById(1);
        assertNotNull(information);
        assertEquals("Alex Intaa", information.getFullName());
        assertEquals("Bhaisipati", information.getLocation());
        //assertEquals("9841865655", information.getPhoneNumber());
    }
    @Test
    @DisplayName("Should throw when database affect zero rows")
    void shouldThrowWhenZeroRowsAffected() throws SQLException{
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);
        assertThrows(DatabaseException.class, ()-> dao.updateTeacher(t));
    }
    @Test
    @DisplayName("Should throw when SQL query fails")
    void shouldThrowWhenSQLFails() throws SQLException{
        when(mockConnection.prepareStatement(any(String.class))).thenThrow(new SQLException("Connection lost"));
        assertThrows(DatabaseException.class, ()-> dao.updateTeacher(t));
    }
}
@Nested
@DisplayName("Returning a list of teachers")
    class getAllTeachers{
    @Test
    void shouldReturnTeachersSuccessfully() throws SQLException{
        when(mockResultSet.next()).thenReturn(true,true,false);
        when(mockResultSet.getInt("user_id")).thenReturn(1,2,3);
        when(mockResultSet.getString("full_name")).thenReturn("Alex Sir","Thomas Shelby");
        when(mockResultSet.getString("location")).thenReturn("Koteshwpr", "Thapathali");
        when(mockResultSet.getString("phone_number")).thenReturn("9841787865", "9841222288");
        when(mockResultSet.getTimestamp("created_at"))
                .thenReturn(Timestamp.valueOf(LocalDateTime.now()));
        when(mockResultSet.getTimestamp("updated_at"))
                .thenReturn(Timestamp.valueOf(LocalDateTime.now()));
        List<Teacher> teacherList = dao.getAllTeachers();
        assertEquals(2, teacherList.size());
        assertEquals("Alex Sir", teacherList.get(0).getFullName());
        assertEquals("Thomas Shelby", teacherList.get(1).getFullName());

    }
    @Test
    @DisplayName("Should throw when SQL query fails")
    void shouldThrowWhenSQLFails() throws SQLException{
        when(mockConnection.prepareStatement(any(String.class))).thenThrow(new SQLException("Connection lost"));
        assertThrows(DatabaseException.class, ()-> dao.getAllTeachers());
    }
}
}
