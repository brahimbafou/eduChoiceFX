package com.university.ui;

import com.university.dao.StudentDAO;
import com.university.dao.StudentDAOImpl;
import com.university.model.Student;
import com.university.service.StudentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

public class StudentsController {

    @FXML private TableView<Student> studentTableView;
    @FXML private TableColumn<Student, Long> studentIdColumn;
    @FXML private TableColumn<Student, String> firstNameColumn;
    @FXML private TableColumn<Student, String> lastNameColumn;
    @FXML private TableColumn<Student, Float> semester1Column;
    @FXML private TableColumn<Student, Float> semester2Column;
    @FXML private TableColumn<Student, Float> semester3Column;
    @FXML private TableColumn<Student, Float> semester4Column;
    @FXML private TableColumn<Student, Void> actionCol;

    private ObservableList<Student> studentList = FXCollections.observableArrayList();
    public void loadStudents() {
        StudentService studentService = new StudentService();
        List<Student> students = studentService.getAllStudents();
        studentList = FXCollections.observableArrayList(students);
        studentTableView.setItems(studentList);
    }

    @FXML
    public void initialize() {
        // Initialize the table columns
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        semester1Column.setCellValueFactory(new PropertyValueFactory<>("semester1Avg"));
        semester2Column.setCellValueFactory(new PropertyValueFactory<>("semester2Avg"));
        semester3Column.setCellValueFactory(new PropertyValueFactory<>("semester3Avg"));
        semester4Column.setCellValueFactory(new PropertyValueFactory<>("semester4Avg"));
        actionCol.setCellFactory(getActionCellFactory());

        loadStudents();
    }

    private Callback<TableColumn<Student, Void>, TableCell<Student, Void>> getActionCellFactory() {
        return param -> new TableCell<Student, Void>() {
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
                        Student student = getTableView().getItems().get(getIndex());
                        handleEditAction(student);
                    });

                    deleteButton.setOnAction(event -> {
                        Student student = getTableView().getItems().get(getIndex());
                        handleDeleteAction(student);
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
    public void handleAddStudent() {
        showModal("/com/university/ui/student_form.fxml", null);
    }

    private void handleEditAction(Student student) {
        showModal("/com/university/ui/student_form.fxml", student);
    }

    private void handleDeleteAction(Student student) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Delete");
        confirmationAlert.setHeaderText("Delete Student");
        confirmationAlert.setContentText("Are you sure you want to delete the student: " + student.getFirstName() + " " + student.getLastName() + "?");

        // Show the confirmation dialog and wait for user input
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                System.out.println("Delete student: " + student.getFirstName());

                studentList.remove(student);
                StudentDAO studentDAO = new StudentDAOImpl();
                studentDAO.deleteStudent(student.getStudentId());

                loadStudents();
            } else {
                System.out.println("Delete cancelled");
            }
        });
    }

    private void showModal(String fxmlFile, Student student) {
        System.out.println("showModal: " + student);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));

            // Check if it's for adding or updating a student
            StudentFormController formController = new StudentFormController(student);
            loader.setController(formController);  // Set the controller here before loading
            Parent modalRoot = loader.load();

            formController.setOnStudentSavedCallback(this::loadStudents);

            Stage modalStage = new Stage();
            modalStage.initStyle(StageStyle.UNDECORATED);
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setScene(new Scene(modalRoot));

            // Show the modal
            modalStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
