package impl;

import model.Role;
import model.User;
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

public class UserDAOImplTest {

    Connection mockConnection;
    PreparedStatement mockPreparedStatement;
    ResultSet mockResultSet;

    UserDAOImpl dao;

    @BeforeEach
    void setup() throws Exception {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        dao = new UserDAOImpl() {
            @Override
            protected Connection getConnection() throws SQLException {
                return mockConnection;
            }
        };

        when(mockConnection.prepareStatement(any(String.class)))
                .thenReturn(mockPreparedStatement);

        when(mockPreparedStatement.executeQuery())
                .thenReturn(mockResultSet);
    }

    @Nested
    class AddUser {

        User u;

        @BeforeEach
        void init() {
            u = new User("John Doe", "Password@123", Role.ADMIN);
        }

        @Test
        @DisplayName("Should add user successfully")
        void shouldAddUserSuccessfully() throws SQLException {
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);

            assertDoesNotThrow(() -> dao.addUser(u));
        }

        @Test
        @DisplayName("Should throw when no rows affected")
        void shouldThrowWhenNoRowsAffected() throws SQLException {
            when(mockPreparedStatement.executeUpdate()).thenReturn(0);

            assertThrows(DatabaseException.class, () -> dao.addUser(u));
        }

        @Test
        @DisplayName("Should throw when SQL fails")
        void shouldThrowWhenSQLFails() throws SQLException {
            when(mockConnection.prepareStatement(any(String.class)))
                    .thenThrow(new SQLException("Connection lost"));

            assertThrows(DatabaseException.class, () -> dao.addUser(u));
        }
    }

    @Nested
    class DeleteUser {

        @Test
        void shouldDeleteSuccessfully() throws SQLException {
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);

            assertDoesNotThrow(() -> dao.deleteUser(1));
        }

        @Test
        void shouldThrowWhenUserNotFound() throws SQLException {
            when(mockPreparedStatement.executeUpdate()).thenReturn(0);

            assertThrows(DatabaseException.class, () -> dao.deleteUser(1));
        }

        @Test
        void shouldThrowWhenSQLFails() throws SQLException {
            when(mockConnection.prepareStatement(any(String.class)))
                    .thenThrow(new SQLException("Connection lost"));

            assertThrows(DatabaseException.class, () -> dao.deleteUser(1));
        }
    }

    @Nested
    class UpdateUser {

        User u;

        @BeforeEach
        void init() {
            u = new User(
                    1,
                    "John Doe",
                    "Password@123",
                    Role.ADMIN,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );
        }

        @Test
        void shouldUpdateSuccessfully() throws SQLException {
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);

            assertDoesNotThrow(() -> dao.updateUser(u));
        }

        @Test
        void shouldThrowWhenNoRowsAffected() throws SQLException {
            when(mockPreparedStatement.executeUpdate()).thenReturn(0);

            assertThrows(DatabaseException.class, () -> dao.updateUser(u));
        }

        @Test
        void shouldThrowWhenSQLFails() throws SQLException {
            when(mockConnection.prepareStatement(any(String.class)))
                    .thenThrow(new SQLException("Connection lost"));

            assertThrows(DatabaseException.class, () -> dao.updateUser(u));
        }
    }

    @Nested
    class GetUserById {

        @Test
        void shouldReturnUserWhenFound() throws SQLException {
            when(mockResultSet.next()).thenReturn(true);

            mockUserResultSet();

            User result = dao.getUserById(1);

            assertNotNull(result);
            assertEquals("John Doe", result.getfullname());
        }

        @Test
        void shouldReturnNullWhenNotFound() throws SQLException {
            when(mockResultSet.next()).thenReturn(false);

            User result = dao.getUserById(999);

            assertNull(result);
        }
    }

    @Nested
    class GetUserByFullName {

        @Test
        void shouldReturnUserWhenFound() throws SQLException {
            when(mockResultSet.next()).thenReturn(true);

            mockUserResultSet();

            User result = dao.getUserByFullName("John Doe");

            assertNotNull(result);
            assertEquals("John Doe", result.getfullname());
        }

        @Test
        void shouldReturnNullWhenNotFound() throws SQLException {
            when(mockResultSet.next()).thenReturn(false);

            User result = dao.getUserByFullName("Unknown");

            assertNull(result);
        }
    }

    @Nested
    class GetAllUsers {

        @Test
        void shouldReturnUsersList() throws SQLException {
            when(mockResultSet.next()).thenReturn(true, true, false);

            when(mockResultSet.getInt("id")).thenReturn(1, 2);
            when(mockResultSet.getString("full_name")).thenReturn("John Doe", "Jane Doe");
            when(mockResultSet.getString("password")).thenReturn("pass1", "pass2");
            when(mockResultSet.getString("role")).thenReturn("USER", "ADMIN");
            when(mockResultSet.getTimestamp("created_at"))
                    .thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            when(mockResultSet.getTimestamp("updated_at"))
                    .thenReturn(Timestamp.valueOf(LocalDateTime.now()));

            List<User> users = dao.getAllUser();

            assertEquals(2, users.size());
        }

        @Test
        void shouldReturnEmptyList() throws SQLException {
            when(mockResultSet.next()).thenReturn(false);

            List<User> users = dao.getAllUser();

            assertNotNull(users);
            assertEquals(0, users.size());
        }
    }

    void mockUserResultSet() throws SQLException {
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("full_name")).thenReturn("John Doe");
        when(mockResultSet.getString("password")).thenReturn("hashed");
        when(mockResultSet.getString("role")).thenReturn("USER");
        when(mockResultSet.getTimestamp("created_at"))
                .thenReturn(Timestamp.valueOf(LocalDateTime.now()));
        when(mockResultSet.getTimestamp("updated_at"))
                .thenReturn(Timestamp.valueOf(LocalDateTime.now()));
    }
}