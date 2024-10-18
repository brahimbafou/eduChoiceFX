package com.university.model;

import javax.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "semester1_avg")
    private float semester1Avg;

    @Column(name = "semester2_avg")
    private float semester2Avg;

    @Column(name = "semester3_avg")
    private float semester3Avg;

    @Column(name = "semester4_avg")
    private float semester4Avg;

    public Student(Long studentId, String firstName, String lastName, float semester1Avg, float semester2Avg, float semester3Avg, float semester4Avg) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.semester1Avg = semester1Avg;
        this.semester2Avg = semester2Avg;
        this.semester3Avg = semester3Avg;
        this.semester4Avg = semester4Avg;
    }

    public Student() {

    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public float getSemester1Avg() {
        return semester1Avg;
    }

    public void setSemester1Avg(float semester1Avg) {
        this.semester1Avg = semester1Avg;
    }

    public float getSemester2Avg() {
        return semester2Avg;
    }

    public void setSemester2Avg(float semester2Avg) {
        this.semester2Avg = semester2Avg;
    }

    public float getSemester3Avg() {
        return semester3Avg;
    }

    public void setSemester3Avg(float semester3Avg) {
        this.semester3Avg = semester3Avg;
    }

    public float getSemester4Avg() {
        return semester4Avg;
    }

    public void setSemester4Avg(float semester4Avg) {
        this.semester4Avg = semester4Avg;
    }

    // Getters and Setters
    // Optional: Constructor(s)

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", semester1Avg=" + semester1Avg +
                ", semester2Avg=" + semester2Avg +
                ", semester3Avg=" + semester3Avg +
                ", semester4Avg=" + semester4Avg +
                '}';
    }
}
