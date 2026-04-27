package impl;

import Interfaces.TeacherDAO;
import dao.BaseDao;
import model.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TeacherDAOImpl extends BaseDao implements TeacherDAO {
    @Override
    public void addTeacher(Teacher t) throws SQLException {
        String sql = "INSERT INTO teachers(id, user_id, full_name, location, phone_number, created_at, updated_at)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try(Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1, t.getId());
            ps.setInt(2, t.getUserId());
            ps.setString(3, t.getFullName());
            ps.setString(4, t.getLocation());
            ps.setString(5, t.getPhoneNumber());
            ps.setTimestamp(6, java.sql.Timestamp.valueOf(t.getCreatedAt()));
            ps.setTimestamp(7, java.sql.Timestamp.valueOf(t.getUpdatedAt()));
            int rows = ps.executeUpdate();
            if(rows>0){
                System.out.println("Teacher added successfully");
            }
        }catch (SQLException e){
            System.out.println("Error adding Teacher: " + e.getMessage());
        }
    }

    @Override
    public void updateTeacher(Teacher t) throws SQLException {
        String sql = "UPDATE teachers SET full_name=?, location=?, phone_number=?";
        try(Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, t.getFullName());
            ps.setString(2, t.getLocation());
            ps.setString(3, t.getPhoneNumber());
            int rows = ps.executeUpdate();
            if(rows > 0){
                System.out.println("Teacher updated successfully");
            }
        }catch (SQLException e){
            System.out.println("Error updating teacher: " + e.getMessage());
        }
    }

    @Override
    public void deleteTeacher(int id) throws SQLException {
        String sql = "DELETE FROM teachers WHERE id=?";
        try(Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1, id);
            int rows = ps.executeQuery();

        }


    }

    @Override
    public Teacher getTeacherById(int id) throws SQLException {
        return null;
    }

    @Override
    public Teacher getTeacherByUserId(int userId) throws SQLException {
        return null;
    }

    @Override
    public List<Teacher> getAllTeachers() throws SQLException {
        return List.of();
    }
}
