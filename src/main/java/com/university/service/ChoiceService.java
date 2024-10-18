package com.university.service;

import com.university.dao.ChoiceDAO;
import com.university.dao.ChoiceDAOImpl;
import com.university.model.Choice;

import java.util.List;

public class ChoiceService {
    private ChoiceDAO choiceDAO = new ChoiceDAOImpl();

    public void addChoice(Choice choice) {
        choiceDAO.addChoice(choice);
    }

    public void deleteChoice(Long choiceId) {
        choiceDAO.deleteChoice(choiceId);
    }

    public void updateChoice(Choice choice) {
        choiceDAO.updateChoice(choice);
    }


    public List<Choice> getAllChoices() {
        return choiceDAO.getAllChoices();
    }

    public List<Choice> getChoicesForStudent(Long studentId) {
        return choiceDAO.getChoicesForStudent(studentId);
    }

}
