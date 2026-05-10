package impl;

import model.Subject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

import Exception.DatabaseException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SubjectDAOImplTest {

    Connection mockConnection;
    PreparedStatement mockPreparedStatement;
    ResultSet mockResultSet;

    SubjectDAOImpl dao;
    Subject s;

    @BeforeEach
    void setup() throws Exception {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        dao = new SubjectDAOImpl() {
            @Override
            protected Connection getConnection() throws SQLException {
                return mockConnection;
            }
        };

        when(mockConnection.prepareStatement(any(String.class)))
                .thenReturn(mockPreparedStatement);

        when(mockPreparedStatement.executeQuery())
                .thenReturn(mockResultSet);

        s = new Subject(
                1,
                "Mathematics",
                1,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    @Nested
    class AddSubject {

        @Test
        void shouldAddSubjectSuccessfully() throws SQLException {
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);
            assertDoesNotThrow(() -> dao.addSubject(s));
        }

        @Test
        void shouldThrowWhenNoRowsAffected() throws SQLException {
            when(mockPreparedStatement.executeUpdate()).thenReturn(0);
            assertThrows(DatabaseException.class, () -> dao.addSubject(s));
        }

        @Test
        void shouldThrowWhenSQLFails() throws SQLException {
            when(mockConnection.prepareStatement(any(String.class)))
                    .thenThrow(new SQLException("Connection lost"));
            assertThrows(DatabaseException.class, () -> dao.addSubject(s));
        }
    }

    @Nested
    class DeleteSubject {

        @Test
        void shouldDeleteSuccessfully() throws SQLException {
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);
            assertDoesNotThrow(() -> dao.deleteSubject(1));
        }

        @Test
        void shouldThrowWhenNotFound() throws SQLException {
            when(mockPreparedStatement.executeUpdate()).thenReturn(0);
            assertThrows(DatabaseException.class, () -> dao.deleteSubject(1));
        }

        @Test
        void shouldThrowWhenSQLFails() throws SQLException {
            when(mockConnection.prepareStatement(any(String.class)))
                    .thenThrow(new SQLException("Connection lost"));
            assertThrows(DatabaseException.class, () -> dao.deleteSubject(1));
        }
    }

    @Nested
    class UpdateSubject {

        @Test
        void shouldUpdateSuccessfully() throws SQLException {
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);
            assertDoesNotThrow(() -> dao.updateSubject(s));
        }

        @Test
        void shouldThrowWhenNoRowsAffected() throws SQLException {
            when(mockPreparedStatement.executeUpdate()).thenReturn(0);
            assertThrows(DatabaseException.class, () -> dao.updateSubject(s));
        }

        @Test
        void shouldThrowWhenSQLFails() throws SQLException {
            when(mockConnection.prepareStatement(any(String.class)))
                    .thenThrow(new SQLException("Connection lost"));
            assertThrows(DatabaseException.class, () -> dao.updateSubject(s));
        }
    }

    @Test
    void shouldReturnSubjectWhenFoundById() throws SQLException {
        when(mockResultSet.next()).thenReturn(true);
        mockSubjectMapping();

        Subject result = dao.getSubjectById(1);

        assertNotNull(result);
        assertEquals("Mathematics", result.getName());
    }

    @Test
    void shouldReturnNullWhenNotFoundById() throws SQLException {
        when(mockResultSet.next()).thenReturn(false);
        Subject result = dao.getSubjectById(999);
        assertNull(result);
    }

    @Test
    void shouldReturnSubjectWhenFoundByName() throws SQLException {
        when(mockResultSet.next()).thenReturn(true);
        mockSubjectMapping();

        Subject result = dao.getSubjectByName("Mathematics");

        assertNotNull(result);
        assertEquals("Mathematics", result.getName());
    }

    @Test
    void shouldReturnNullWhenNotFoundByName() throws SQLException {
        when(mockResultSet.next()).thenReturn(false);
        Subject result = dao.getSubjectByName("Unknown");
        assertNull(result);
    }

    @Test
    void shouldReturnAllSubjects() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, true, false);

        when(mockResultSet.getInt("id")).thenReturn(1, 2);
        when(mockResultSet.getString("name")).thenReturn("Math", "Science");
        when(mockResultSet.getInt("teacher_id")).thenReturn(1, 2);
        when(mockResultSet.getTimestamp("created_at"))
                .thenReturn(Timestamp.valueOf(LocalDateTime.now()));
        when(mockResultSet.getTimestamp("updated_at"))
                .thenReturn(Timestamp.valueOf(LocalDateTime.now()));

        List<Subject> subjects = dao.getAllSubjects();

        assertEquals(2, subjects.size());
    }

    @Test
    void shouldReturnEmptyListWhenNoSubjects() throws SQLException {
        when(mockResultSet.next()).thenReturn(false);
        List<Subject> subjects = dao.getAllSubjects();
        assertNotNull(subjects);
        assertEquals(0, subjects.size());
    }

    @Test
    void shouldReturnSubjectsByTeacherId() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, false);
        mockSubjectMapping();

        List<Subject> subjects = dao.getSubjectByTeacherId(1);

        assertEquals(1, subjects.size());
    }

    @Test
    void shouldReturnEmptyWhenNoSubjectsByTeacher() throws SQLException {
        when(mockResultSet.next()).thenReturn(false);
        List<Subject> subjects = dao.getSubjectByTeacherId(999);

        assertNotNull(subjects);
        assertEquals(0, subjects.size());
    }

    @Test
    void shouldThrowWhenSQLFailsInGetByTeacher() throws SQLException {
        when(mockConnection.prepareStatement(any(String.class)))
                .thenThrow(new SQLException("Connection lost"));

        assertThrows(DatabaseException.class,
                () -> dao.getSubjectByTeacherId(1));
    }

    void mockSubjectMapping() throws SQLException {
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("Mathematics");
        when(mockResultSet.getInt("teacher_id")).thenReturn(1);
        when(mockResultSet.getTimestamp("created_at"))
                .thenReturn(Timestamp.valueOf(LocalDateTime.now()));
        when(mockResultSet.getTimestamp("updated_at"))
                .thenReturn(Timestamp.valueOf(LocalDateTime.now()));
    }
}