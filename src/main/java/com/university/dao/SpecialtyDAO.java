package com.university.dao;

import com.university.model.Specialty;

import java.util.List;

public interface SpecialtyDAO {
    void addSpecialty(Specialty specialty);
    void deleteSpecialty(Long specialtyId);
    void updateSpecialty(Specialty specialty);
    Specialty findSpecialtyById(Long specialtyId);
    List<Specialty> getAllSpecialties();
}
