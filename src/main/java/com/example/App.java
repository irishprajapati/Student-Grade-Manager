package com.example;

import impl.StudentDAOImpl;
import impl.SubjectDAOImpl;
import impl.TeacherDAOImpl;
import impl.UserDAOImpl;
import model.User;

import java.util.Scanner;

public class App {
    static Scanner scanner = new Scanner(System.in);
    static UserDAOImpl userDAO = new UserDAOImpl();
    static StudentDAOImpl studentDAO = new StudentDAOImpl();
    static TeacherDAOImpl teacherDAO = new TeacherDAOImpl();
    static SubjectDAOImpl subjectDAO = new SubjectDAOImpl();
    static GradeDAOImpl gradeDAO = new GradeDAOImpl();
    static User loggedInUser = null;
    public static void main(String[] args){

    }
}

