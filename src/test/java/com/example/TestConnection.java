package com.example;

import db.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) throws SQLException {
        try(Connection con = DBConnection.getConnection()){
            if(con != null){
                System.out.println("Database connected successfully");
                System.out.println("DB: " + con.getMetaData().getDatabaseProductName());
                System.out.println("Database name: " + con.getCatalog());
            }
        }catch (SQLException e){
            System.out.println("Failed to connect to database: " + e.getMessage());
        }
    }
}
