package dao;

import db.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseDao {
    protected Connection getConnection () throws SQLException{
        return DBConnection.getConnection();
    }
    protected void handleException(String message, SQLException e){
        System.out.println(message + " : " + e.getMessage());
    }
}
