package com.example;

import java.sql.SQLException;
import java.util.*;
import java.time.LocalDate;

import model.*;
import impl.*;
import Interfaces.*;
import util.PasswordUtil;

public class App {

    static Scanner scanner = new Scanner(System.in);

    static UserDAO userDAO = new UserDAOImpl();
    static StudentDAO studentDAO = new StudentDAOImpl();
    static TeacherDAO teacherDAO = new TeacherDAOImpl();
    static SubjectDAO subjectDAO = new SubjectDAOImpl();
    static GradeDAO gradeDAO = new GradeDAOImpl();

    static User loggedInUser = null;

    public static void main(String[] args) {
        while (true) {
            login();
        }
    }

    static void login() {
        try {
            System.out.print("\nEnter your name: ");
            String fullName = scanner.nextLine().trim();

            User user = userDAO.getUserByFullName(fullName);

            if (user == null) {
                System.out.println("User not found");
                return;
            }

            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            if (!PasswordUtil.verifyPassword(password, user.getPassword())) {
                System.out.println("Invalid password");
                return;
            }

            loggedInUser = user;

            // FIX: handle missing getFullName safely
            String name;
            try {
                name = user.getfullname();
            } catch (Exception e) {
                name = fullName;
            }

            System.out.println("Welcome " + name);

            showMenu();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ─── MENU ROUTER ────────────────────────────────

    static void showMenu() {
        if (loggedInUser == null) return;

        switch (loggedInUser.getRole()) {
            case ADMIN:
                adminMenu();
                break;
            case TEACHER:
                teacherMenu();
                break;
            case STUDENT:
                studentMenu();
                break;
        }
    }

    // ─── ADMIN MENU ────────────────────────────────

    static void adminMenu() {
        while (true) {
            try {
                System.out.println("\n--- Admin Menu ---");
                System.out.println("1. Add Student");
                System.out.println("2. Delete Student");
                System.out.println("3. Get All Students");
                System.out.println("4. Add Teacher");
                System.out.println("5. Delete Teacher");
                System.out.println("6. Get All Teachers");
                System.out.println("7. Add Subject");
                System.out.println("8. Get All Subjects");
                System.out.println("9. Add Grade");
                System.out.println("10. View Grades by Student");
                System.out.println("11. Logout");
                System.out.print("Choose: ");

                int choice = getIntInput();

                switch (choice) {
                    case 1: addStudent(); break;
                    case 2: deleteStudent(); break;
                    case 3: getAllStudents(); break;
                    case 4: addTeacher(); break;
                    case 5: deleteTeacher(); break;
                    case 6: getAllTeachers(); break;
                    case 7: addSubject(); break;
                    case 8: getAllSubjects(); break;
                    case 9: addGrade(); break;
                    case 10: getGradesByStudent(); break;
                    case 11: logout(); return;
                    default: System.out.println("Invalid choice");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // ─── STUDENT MENU ────────────────────────────────

    static void studentMenu() {
        while (true) {
            try {
                System.out.println("\n--- Student Menu ---");
                System.out.println("1. View My Profile");
                System.out.println("2. View My Grades");
                System.out.println("3. Logout");
                System.out.print("Choose: ");

                int choice = getIntInput();

                switch (choice) {
                    case 1: viewMyProfile(); break;
                    case 2: viewMyGrades(); break;
                    case 3: logout(); return;
                    default: System.out.println("Invalid choice");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // ─── TEACHER MENU ────────────────────────────────

    static void teacherMenu() {
        while (true) {
            try {
                System.out.println("\n--- Teacher Menu ---");
                System.out.println("1. View My Profile");
                System.out.println("2. View My Subjects");
                System.out.println("3. View Students in Subject");
                System.out.println("4. View Grades by Subject");
                System.out.println("5. Update Grade");
                System.out.println("6. Logout");
                System.out.print("Choose: ");

                int choice = getIntInput();

                switch (choice) {
                    case 1: viewMyProfile(); break;
                    case 2: viewMySubjects(); break;
                    case 3: viewStudentsInMySubject(); break;
                    case 4: getGradesBySubject(); break;
                    case 5: updateGrade(); break;
                    case 6: logout(); return;
                    default: System.out.println("Invalid choice");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // ─── HELPERS ────────────────────────────────

    static int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Enter valid number:");
            scanner.next();
        }
        int val = scanner.nextInt();
        scanner.nextLine();
        return val;
    }

    static void logout() {
        loggedInUser = null;
        System.out.println("Logged out");
    }

    static void addStudent() {
        try {
            System.out.print("User ID: ");
            int userId = getIntInput();

            System.out.print("Full Name: ");
            String name = scanner.nextLine();

            System.out.print("Gender: ");
            Gender gender = Gender.valueOf(scanner.nextLine().toUpperCase());

            System.out.print("Phone: ");
            String phone = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Location: ");
            String location = scanner.nextLine();

            System.out.print("Guardian: ");
            String guardian = scanner.nextLine();

            System.out.print("DOB: ");
            LocalDate dob = LocalDate.parse(scanner.nextLine());

            studentDAO.addStudent(
                    new Student(userId, name, gender, phone, email, location, guardian, dob)
            );

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void deleteStudent() throws SQLException {
        studentDAO.deleteStudent(getIntInput());
    }

    static void getAllStudents() {
        try {
            List<Student> list = studentDAO.getAllStudents();
            if (list.isEmpty()) System.out.println("No students");
            else list.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void addTeacher() {
        try {
            int userId = getIntInput();

            String name = scanner.nextLine();
            String location = scanner.nextLine();
            String phone = scanner.nextLine();

            Teacher t = new Teacher(name, location, phone);
            t.setUserId(userId);

            teacherDAO.addTeacher(t);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void deleteTeacher() throws SQLException {
        teacherDAO.deleteTeacher(getIntInput());
    }

    static void getAllTeachers() {
        try {
            List<Teacher> list = teacherDAO.getAllTeachers();
            if (list.isEmpty()) System.out.println("No teachers");
            else list.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void addSubject() throws SQLException {
        String name = scanner.nextLine();
        int tid = getIntInput();
        subjectDAO.addSubject(new Subject(name, tid));
    }

    static void getAllSubjects() {
        try {
            List<Subject> list = subjectDAO.getAllSubjects();
            if (list.isEmpty()) System.out.println("No subjects");
            else list.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void addGrade() throws SQLException {
        int sid = getIntInput();
        int sub = getIntInput();
        double marks = scanner.nextDouble();
        scanner.nextLine();

        gradeDAO.addGrade(new Grade(sid, sub, marks));
    }

    static void getGradesByStudent() {
        try {
            List<Grade> list = gradeDAO.getGradesByStudentId(getIntInput());
            if (list.isEmpty()) System.out.println("No grades");
            else list.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void viewMyProfile() {
        try {
            switch (loggedInUser.getRole()) {
                case STUDENT:
                    System.out.println(studentDAO.getStudentByUserId(loggedInUser.getId()));
                    break;
                case TEACHER:
                    System.out.println(teacherDAO.getTeacherByUserId(loggedInUser.getId()));
                    break;
                case ADMIN:
                    System.out.println(loggedInUser);
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void viewMyGrades() {
        try {
            Student s = studentDAO.getStudentByUserId(loggedInUser.getId());
            List<Grade> list = gradeDAO.getGradesByStudentId(s.getId());

            if (list.isEmpty()) System.out.println("No grades");
            else list.forEach(System.out::println);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void viewMySubjects() {
        System.out.println("Not implemented in DAO");
    }

    static void viewStudentsInMySubject() {
        System.out.println("Not implemented in DAO");
    }

    static void getGradesBySubject() {
        System.out.println("Not implemented in DAO");
    }

    static void updateGrade() {
        try {
            int id = getIntInput();
            Grade g = gradeDAO.getGradeById(id);

            if (g == null) {
                System.out.println("Not found");
                return;
            }

            double marks = scanner.nextDouble();
            scanner.nextLine();

            g.setMarks(marks);
            gradeDAO.updateGrade(g);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}