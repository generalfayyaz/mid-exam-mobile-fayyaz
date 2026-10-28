package com.example.mid_exam_fayyaz;

public class StudentModel {
    Integer id;
    String name, studentId;
    boolean enrolled;

    public StudentModel(String name, String studentId, boolean enrolled) {
        this.name = name;
        this.studentId = studentId;
        this.enrolled = enrolled;
    }

    public StudentModel(Integer id, String name, String studentId, boolean enrolled) {
        this.id = id;
        this.name = name;
        this.studentId = studentId;
        this.enrolled = enrolled;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public boolean isEnrolled() { return enrolled; }
    public void setEnrolled(boolean enrolled) { this.enrolled = enrolled; }
}