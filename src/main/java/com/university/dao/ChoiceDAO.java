package com.university.dao;

import com.university.model.Choice;

import java.util.List;

public interface ChoiceDAO {
    void addChoice(Choice choice);
    void deleteChoice(Long choiceId);
    void updateChoice(Choice choice);
    List<Choice> getChoicesForStudent(Long studentId);

    List<Choice> getAllChoices();
}
