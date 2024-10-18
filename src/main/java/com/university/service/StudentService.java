package com.university.service;

import com.university.dao.StudentDAO;
import com.university.dao.StudentDAOImpl;
import com.university.model.Student;

import java.util.List;

public class StudentService {
    private StudentDAO studentDAO = new StudentDAOImpl();

    public void addStudent(Student student) {
        studentDAO.addStudent(student);
    }

    public void deleteStudent(Long studentId) {
        studentDAO.deleteStudent(studentId);
    }

    public void updateStudent(Student student) {
        studentDAO.updateStudent(student);
    }

    public Student findStudentById(Long studentId) {
        return studentDAO.findStudentById(studentId);
    }

    public List<Student> findStudentsByName(String name) {
        return studentDAO.findStudentsByName(name);
    }

    public List<Student> getAllStudentsSortedByAverage() {
        return studentDAO.getAllStudentsSortedByAverage();
    }

    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }
}
