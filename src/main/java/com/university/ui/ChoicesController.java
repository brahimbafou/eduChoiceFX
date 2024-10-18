package com.university.ui;

import com.university.dao.ChoiceDAO;
import com.university.dao.ChoiceDAOImpl;
import com.university.dao.StudentDAO;
import com.university.dao.StudentDAOImpl;
import com.university.model.Choice;
import com.university.model.Student;
import com.university.service.ChoiceService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;

public class ChoicesController {

    @FXML private TableView<Choice> choiceTableView;
    @FXML private TableColumn<Choice, Long> choiceIdColumn;
    @FXML private TableColumn<Choice, String> studentNameColumn;
    @FXML private TableColumn<Choice, String> studentIdColumn;
    @FXML private TableColumn<Choice, String> specialtyColumn;
    @FXML private TableColumn<Choice, Integer> choiceOrderColumn;
    @FXML private TableColumn<Choice, Void> actionCol;


    private ObservableList<Choice> choiceList = FXCollections.observableArrayList();
    public void loadChoices() {
        ChoiceService choiceService = new ChoiceService();
        List<Choice> choices = choiceService.getAllChoices();
        choiceList = FXCollections.observableArrayList(choices);
        choiceTableView.setItems(choiceList);
    }

    @FXML public void initialize() {
        // Set up the table columns
        choiceIdColumn.setCellValueFactory(new PropertyValueFactory<>("choiceId"));
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentFullName"));
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("StudentId"));
        specialtyColumn.setCellValueFactory(new PropertyValueFactory<>("specialtyName"));
        choiceOrderColumn.setCellValueFactory(new PropertyValueFactory<>("choiceOrder"));
        actionCol.setCellFactory(getActionCellFactory());
        loadChoices();
    }
    private Callback<TableColumn<Choice, Void>, TableCell<Choice, Void>> getActionCellFactory() {
        return param -> new TableCell<Choice, Void>() {
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
                        Choice choice = getTableView().getItems().get(getIndex());
                        handleEditAction(choice);
                    });

                    deleteButton.setOnAction(event -> {
                        Choice choice = getTableView().getItems().get(getIndex());
                        handleDeleteAction(choice);
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

    @FXML
    public void handleAddChoice() {
        showModal("/com/university/ui/choice_form.fxml", null);
    }

    private void handleEditAction(Choice choice) {
        showModal("/com/university/ui/choice_form.fxml", choice);
    }

    private void showModal(String fxmlFile, Choice choice) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));

            // Create and set the controller
            ChoiceFormController formController;
            if (choice == null) {
                formController = new ChoiceFormController(); // For adding a choice
            } else {
                formController = new ChoiceFormController(choice); // For editing
            }
            loader.setController(formController);

            Parent modalRoot = loader.load();
            formController.setOnChoiceSavedCallback(this::loadChoices);

            Stage modalStage = new Stage();
            modalStage.initStyle(StageStyle.UNDECORATED);
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setScene(new Scene(modalRoot));

            modalStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void handleDeleteAction(Choice choice) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Delete");
        confirmationAlert.setHeaderText("Delete Student");
        confirmationAlert.setContentText("Are you sure you want to delete the choice of: " + choice.getStudent().getFirstName() + " " + choice.getStudent().getLastName() + "?");

        // Show the confirmation dialog and wait for user input
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                System.out.println("Delete choice of: " + choice.getStudent().getFirstName() + " " + choice.getStudent().getLastName());

                choiceList.remove(choice);
                ChoiceDAO choiceDAO = new ChoiceDAOImpl();
                choiceDAO.deleteChoice(choice.getChoiceId());

                loadChoices();
            } else {
                System.out.println("Delete cancelled");
            }
        });
    }
}
