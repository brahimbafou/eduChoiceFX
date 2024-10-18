package com.university.ui;

import com.university.dao.SpecialtyDAO;
import com.university.dao.SpecialtyDAOImpl;
import com.university.model.Specialty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;

public class SpecialtiesController {

    @FXML private TableView<Specialty> specialtyTableView;
    @FXML private TableColumn<Specialty, Long> specialtyIdColumn;
    @FXML private TableColumn<Specialty, String> specialtyNameColumn;
    @FXML private TableColumn<Specialty, Integer> availableSeatsColumn;
    @FXML private TableColumn<Specialty, Void> actionCol;

    private SpecialtyDAO specialtyDAO= new SpecialtyDAOImpl();

    // Assume that specialtyDAO is already defined and initialized

    @FXML
    public void initialize() {
        specialtyDAO = new SpecialtyDAOImpl();

        // Initialize the table columns
        specialtyIdColumn.setCellValueFactory(new PropertyValueFactory<>("specialtyId"));
        specialtyNameColumn.setCellValueFactory(new PropertyValueFactory<>("specialtyName"));
        availableSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("availableSeats"));

        // Add the actions column
        actionCol.setCellFactory(getActionCellFactory());

        loadSpecialties();
    }



    private Callback<TableColumn<Specialty, Void>, TableCell<Specialty, Void>> getActionCellFactory() {
        return param -> new TableCell<Specialty, Void>() {
            private Node actionBox;
            private Button editButton;
            private Button deleteButton;

            {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/university/ui/table_action_buttons.fxml"));
                    actionBox = loader.load();

                    editButton = (Button) loader.getNamespace().get("editButton");
                    deleteButton = (Button) loader.getNamespace().get("deleteButton");

                    editButton.setOnAction(event -> {
                        Specialty specialty = getTableView().getItems().get(getIndex());
                        handleEditAction(specialty);
                    });

                    deleteButton.setOnAction(event -> {
                        Specialty specialty = getTableView().getItems().get(getIndex());
                        handleDeleteAction(specialty);
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(actionBox);
                }
            }
        };
    }

    private void handleEditAction(Specialty specialty) {
        showModal("/com/university/ui/specialty_form.fxml", specialty);
    }

    private void handleDeleteAction(Specialty specialty) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Delete");
        confirmationAlert.setHeaderText("Delete Specialty");
        confirmationAlert.setContentText("Are you sure you want to delete the specialty: " + specialty.getSpecialtyName() + "?");

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                specialtyDAO.deleteSpecialty(specialty.getSpecialtyId());
                loadSpecialties();
            }
        });
    }


    private void loadSpecialties() {
        List<Specialty> specialties = specialtyDAO.getAllSpecialties();
        specialtyTableView.getItems().setAll(specialties);
    }

    @FXML
    public void handleAddSpecialty() {
        showModal("/com/university/ui/specialty_form.fxml", null);
    }

    @FXML
    public void handleEditSpecialty() {
        Specialty selected = specialtyTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showModal("/com/university/ui/specialty_form.fxml", selected);
        }
    }

    @FXML
    public void handleDeleteSpecialty() {
        Specialty selected = specialtyTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            specialtyDAO.deleteSpecialty(selected.getSpecialtyId()); // Delete from DAO
            loadSpecialties(); // Reload the table
        }
    }

    private void showModal(String fxmlFile, Specialty specialty) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            SpecialtyFormController formController = new SpecialtyFormController(specialty);
            loader.setController(formController);
            Parent modalRoot = loader.load();

            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setScene(new Scene(modalRoot));
            modalStage.showAndWait();

            loadSpecialties(); // Refresh the table after modal is closed
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
