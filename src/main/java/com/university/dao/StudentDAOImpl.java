package com.university.dao;

import com.university.model.Student;
import com.university.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {

    // Method to check if a student with the given ID already exists in the database
    @Override
    public boolean doesStudentIdExist(Long studentId) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Query<Student> query = session.createQuery("FROM Student WHERE studentId = :studentId", Student.class);
            query.setParameter("studentId", studentId);
            return query.uniqueResult() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void addStudent(Student student) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(student);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }


    @Override
    public void deleteStudent(Long studentId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Student student = session.get(Student.class, studentId);
            if (student != null) {
                session.delete(student);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void updateStudent(Student student) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(student);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Student findStudentById(Long studentId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Student.class, studentId);
        }
    }

    @Override
    public List<Student> findStudentsByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Student> query = session.createQuery("FROM Student WHERE firstName LIKE :name OR lastName LIKE :name", Student.class);
            query.setParameter("name", "%" + name + "%");
            return query.list();
        }
    }

    @Override
    public List<Student> getAllStudentsSortedByAverage() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Student ORDER BY (semester1Avg + semester2Avg + semester3Avg + semester4Avg) / 4 DESC", Student.class).list();
        }
    }
    @Override
    public List<Student> getAllStudents() {
        List<Student> students = Collections.emptyList();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            students = session.createQuery("FROM Student ORDER BY studentId", Student.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
        }
        return students;
    }


}
