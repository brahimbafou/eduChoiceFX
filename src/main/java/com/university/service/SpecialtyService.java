package com.university.service;

import com.university.dao.SpecialtyDAO;
import com.university.dao.SpecialtyDAOImpl;
import com.university.model.Specialty;

import java.util.List;

public class SpecialtyService {
    private SpecialtyDAO specialtyDAO = new SpecialtyDAOImpl();

    public void addSpecialty(Specialty specialty) {
        specialtyDAO.addSpecialty(specialty);
    }

    public void deleteSpecialty(Long specialtyId) {
        specialtyDAO.deleteSpecialty(specialtyId);
    }

    public void updateSpecialty(Specialty specialty) {
        specialtyDAO.updateSpecialty(specialty);
    }

    public Specialty findSpecialtyById(Long specialtyId) {
        return specialtyDAO.findSpecialtyById(specialtyId);
    }

    public List<Specialty> getAllSpecialties() {
        return specialtyDAO.getAllSpecialties();
    }
}
