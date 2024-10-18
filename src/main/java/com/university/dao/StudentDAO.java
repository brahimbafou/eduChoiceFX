package com.university.dao;

import com.university.model.Student;

import java.util.List;

public interface StudentDAO {
    public boolean doesStudentIdExist(Long studentId);
    void addStudent(Student student);
    void deleteStudent(Long studentId);
    void updateStudent(Student student);
    Student findStudentById(Long studentId);
    List<Student> findStudentsByName(String name);
    List<Student> getAllStudentsSortedByAverage();
    List<Student> getAllStudents();
}
