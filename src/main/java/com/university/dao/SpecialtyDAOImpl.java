package com.university.dao;

import com.university.model.Specialty;
import com.university.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class SpecialtyDAOImpl implements SpecialtyDAO {

    @Override
    public void addSpecialty(Specialty specialty) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(specialty);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSpecialty(Long specialtyId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Specialty specialty = session.get(Specialty.class, specialtyId);
            if (specialty != null) {
                session.delete(specialty);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void updateSpecialty(Specialty specialty) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(specialty);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Specialty findSpecialtyById(Long specialtyId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Specialty.class, specialtyId);
        }
    }

    @Override
    public List<Specialty> getAllSpecialties() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Specialty", Specialty.class).list();
        }
    }
}
