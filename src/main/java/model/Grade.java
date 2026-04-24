package model;

public class Grade {
    private int id;
    private int studentId;
    private int subjectId;
    private double marks;
    private String grade;

    // fetch from DB
    public Grade(int id, int studentId, int subjectId, double marks) {
        this.id = id;
        this.studentId = validateStudentId(studentId);
        this.subjectId = validateSubjectId(subjectId);
        this.marks = validateMarks(marks);
        this.grade = calculateGrade(marks);
    }

    // create new
    public Grade(int studentId, int subjectId, double marks) {
        this.studentId = validateStudentId(studentId);
        this.subjectId = validateSubjectId(subjectId);
        this.marks = validateMarks(marks);
        this.grade = calculateGrade(marks);
    }

    public int getId() { return id; }
    public int getStudentId() { return studentId; }
    public int getSubjectId() { return subjectId; }
    public double getMarks() { return marks; }
    public String getGrade() { return grade; }

    // marks and grade always move together
    public void setMarks(double marks) {
        this.marks = validateMarks(marks);
        this.grade = calculateGrade(marks);
    }

    public void setStudentId(int studentId) {
        this.studentId = validateStudentId(studentId);
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = validateSubjectId(subjectId);
    }

    private int validateStudentId(int studentId) {
        if (studentId <= 0) throw new IllegalArgumentException("StudentId is invalid");
        return studentId;
    }

    private int validateSubjectId(int subjectId) {
        if (subjectId <= 0) throw new IllegalArgumentException("SubjectId is invalid");
        return subjectId;
    }

    private double validateMarks(double marks) {
        if (marks < 0.00 || marks > 100.00)
            throw new IllegalArgumentException("Marks must be between 0 and 100");
        return marks;
    }

    private String calculateGrade(double marks) {
        if (marks >= 90) return "A+";
        if (marks >= 80) return "A";
        if (marks >= 70) return "B+";
        if (marks >= 60) return "B";
        if (marks >= 50) return "C";
        if (marks >= 40) return "D";
        return "F";
    }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", subjectId=" + subjectId +
                ", marks=" + marks +
                ", grade='" + grade + '\'' +
                '}';
    }
}