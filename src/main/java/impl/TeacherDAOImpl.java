package impl;

import Interfaces.TeacherDAO;
import dao.BaseDao;
import model.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAOImpl extends BaseDao implements TeacherDAO {
    @Override
    public void addTeacher(Teacher t) throws SQLException {
        String sql = "INSERT INTO teachers( user_id, full_name, location, phone_number, created_at, updated_at)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try(Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){
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
        String sql = "UPDATE teachers SET full_name=?, location=?, phone_number=?, updated_at=? WHERE id=?";
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
            int rows = ps.executeUpdate();
            if(rows>0){
                System.out.println("Teacher deleted successfully");
            }
        }catch (SQLException e){
            System.out.println("Error deleting teacher: " + e.getMessage());
        }
    }

    @Override
    public Teacher getTeacherById(int id) throws SQLException {
        String sql = "SELECT * FROM teachers WHERE id=?";
        try(Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new Teacher(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("full_name"),
                        rs.getString("location"),
                        rs.getString("phone_number"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
            }
        }catch (SQLException e){
            System.out.println("Error getting teacher details by Id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Teacher getTeacherByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM teachers WHERE user_id=?";
        try(Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new Teacher(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("full_name"),
                        rs.getString("location"),
                        rs.getString("phone_number"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
            }
        }catch (SQLException e){
            System.out.println("Error getting teacher details by Id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Teacher> getAllTeachers() throws SQLException {
        List<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT * FROM teachers";
        try(Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                Teacher teacher = new Teacher(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("full_name"),
                        rs.getString("location"),
                        rs.getString("phone_number"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
                teachers.add(teacher);
            }
        }catch (SQLException e){
            System.out.println("Error getting all Teachers: " + e.getMessage());
        }
        return teachers;
    }
}
