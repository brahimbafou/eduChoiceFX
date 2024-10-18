package com.university.ui;

import com.university.dao.SpecialtyDAO;
import com.university.dao.SpecialtyDAOImpl;
import com.university.model.Specialty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SpecialtyFormController {

    @FXML
    private TextField specialtyIdField;
    @FXML
    private TextField specialtyNameField;
    @FXML
    private TextField availableSeatsField;

    private SpecialtyDAO specialtyDAO= new SpecialtyDAOImpl();
    private Specialty specialty;
    private boolean isUpdateMode;

    // Constructor for adding a new specialty
    public SpecialtyFormController() {
        this.isUpdateMode = false;
    }

    // Constructor for editing an existing specialty
    public SpecialtyFormController(Specialty specialty) {
        this();
        if(specialty!=null) {
            System.out.println("editing");
            this.specialty = specialty;
            this.isUpdateMode = true;
        }

    }

    @FXML
    public void initialize() {
        specialtyIdField.setDisable(true);
        if (isUpdateMode) {
            populateFields();
        } else {
            specialtyIdField.setText("Auto-generated");
        }
    }

    private void populateFields() {
        specialtyIdField.setText(specialty.getSpecialtyId().toString());
        specialtyNameField.setText(specialty.getSpecialtyName());
        availableSeatsField.setText(String.valueOf(specialty.getAvailableSeats()));
    }

    @FXML
    public void handleSubmit() {
        if (!validateInputs()) {
            return;
        }

        if (isUpdateMode) {
            updateSpecialty();
        } else {
            addSpecialty();
        }
    }

    private void addSpecialty() {
        String name = specialtyNameField.getText();
        int availableSeats = Integer.parseInt(availableSeatsField.getText());

        Specialty newSpecialty = new Specialty(name, availableSeats);
        // Call DAO method to save new specialty
        specialtyDAO.addSpecialty(newSpecialty);

        closeDialog();
    }

    private void updateSpecialty() {
        specialty.setSpecialtyName(specialtyNameField.getText());
        specialty.setAvailableSeats(Integer.parseInt(availableSeatsField.getText()));
        // Call DAO method to update specialty
        specialtyDAO.updateSpecialty(specialty);

        closeDialog();
    }

    private boolean validateInputs() {
        if (specialtyNameField.getText().isEmpty() || availableSeatsField.getText().isEmpty()) {
            showErrorAlert("All fields are required");
            return false;
        }

        try {
            Integer.parseInt(availableSeatsField.getText());
        } catch (NumberFormatException e) {
            showErrorAlert("Available seats must be a valid number");
            return false;
        }

        return true;
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }



    @FXML
    public void handleCancel() {
        closeDialog();
    }

    private void closeDialog() {
        Stage stage = (Stage) specialtyIdField.getScene().getWindow();
        stage.close();
    }
}
