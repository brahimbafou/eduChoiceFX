package com.university.util;

import com.university.model.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DatabaseUtil {
    public static void emptyAllTables() {
        Transaction transaction = null;

        // Start a new session
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Disable foreign key checks
            session.createSQLQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();

            // Execute SQL to truncate each table individually
            session.createSQLQuery("TRUNCATE TABLE choices").executeUpdate();
            session.createSQLQuery("TRUNCATE TABLE students").executeUpdate();
            session.createSQLQuery("TRUNCATE TABLE specialties").executeUpdate();

            // Re-enable foreign key checks
            session.createSQLQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();

            transaction.commit();
            System.out.println("All tables emptied successfully!");

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }


    public static void dropAllTables() {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Drop tables in the correct order
            session.createSQLQuery("DROP TABLE IF EXISTS choices").executeUpdate();
            session.createSQLQuery("DROP TABLE IF EXISTS students").executeUpdate();
            session.createSQLQuery("DROP TABLE IF EXISTS specialties").executeUpdate();

            transaction.commit();
            System.out.println("All tables dropped successfully!");

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
    public static void logAllStudents() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Student> students = session.createQuery("FROM Student", Student.class).list();
            System.out.println("Logging all students:");

            for (Student student : students) {
                System.out.println("ID: " + student.getStudentId() +
                        ", Name: " + student.getFirstName() + " " + student.getLastName() +
                        ", Avg Sem1: " + student.getSemester1Avg() +
                        ", Avg Sem2: " + student.getSemester2Avg() +
                        ", Avg Sem3: " + student.getSemester3Avg() +
                        ", Avg Sem4: " + student.getSemester4Avg());
            }
        }
    }
}
