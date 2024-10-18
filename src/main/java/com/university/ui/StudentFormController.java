package com.university.ui;

import com.university.dao.StudentDAO;
import com.university.dao.StudentDAOImpl;
import com.university.model.Student;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class StudentFormController implements Initializable {

    @FXML private TextField studentIdField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField semester1Field;
    @FXML private TextField semester2Field;
    @FXML private TextField semester3Field;
    @FXML private TextField semester4Field;
    @FXML private Label validationMessage;

    private final StudentDAO studentDAO;
    private boolean isUpdateMode= false;
    private Student student;
    private Runnable onStudentSavedCallback;

    public StudentFormController() {
        studentDAO = new StudentDAOImpl();
    }

    // Constructor for updating
    public StudentFormController(Student student) {
        this();
        if(student!=null) {
            System.out.println("editing");
            this.student = student;
            this.isUpdateMode = true;
        }
    }

    private void populateFields() {
        studentIdField.setText(student.getStudentId().toString());
        firstNameField.setText(student.getFirstName());
        lastNameField.setText(student.getLastName());
        semester1Field.setText(String.valueOf(student.getSemester1Avg()));
        semester2Field.setText(String.valueOf(student.getSemester2Avg()));
        semester3Field.setText(String.valueOf(student.getSemester3Avg()));
        semester4Field.setText(String.valueOf(student.getSemester4Avg()));
    }

    @FXML
    public void validateStudentId() {
        String studentIdText = studentIdField.getText();

        // Check if the student ID is empty
        if (studentIdText.isEmpty()) {
            validationMessage.setText("Student ID cannot be empty");
            return;
        }

        // Validate if the input is a valid number
        try {
            Long.parseLong(studentIdText);  // Attempt to parse the ID as a number
            validationMessage.setText("");  // Clear any previous error messages
        } catch (NumberFormatException e) {
            validationMessage.setText("Student ID must be a number");
            return;
        }

        // Check if the student ID already exists
        if (studentDAO.doesStudentIdExist(Long.parseLong(studentIdText))) {
            validationMessage.setText("Student ID already exists");
        } else {
            validationMessage.setText("");  // Clear the message if valid
        }
    }

    @FXML
    public void handleSubmit() {
        if (!validateInputs()) {
            return;
        }

        if (isUpdateMode) {
            updateStudent();
        } else {
            addStudent();
        }
    }

    private void addStudent() {
        Long studentId = Long.parseLong(studentIdField.getText());
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        float semester1Avg = Float.parseFloat(semester1Field.getText());
        float semester2Avg = Float.parseFloat(semester2Field.getText());
        float semester3Avg = Float.parseFloat(semester3Field.getText());
        float semester4Avg = Float.parseFloat(semester4Field.getText());

        if (studentDAO.doesStudentIdExist(studentId)) {
            showErrorAlert("Student ID already exists");
            return;
        }

        Student newStudent = new Student(studentId, firstName, lastName, semester1Avg, semester2Avg, semester3Avg, semester4Avg);
        studentDAO.addStudent(newStudent);
        showConfirmationAlert("Student added successfully!");
        onStudentSavedCallback.run();
        closeDialog();
    }

    private void updateStudent() {
        student.setFirstName(firstNameField.getText());
        student.setLastName(lastNameField.getText());
        student.setSemester1Avg(Float.parseFloat(semester1Field.getText()));
        student.setSemester2Avg(Float.parseFloat(semester2Field.getText()));
        student.setSemester3Avg(Float.parseFloat(semester3Field.getText()));
        student.setSemester4Avg(Float.parseFloat(semester4Field.getText()));

        studentDAO.updateStudent(student);
        showConfirmationAlert("Student updated successfully!");
        onStudentSavedCallback.run();
        closeDialog();
    }

    private boolean validateInputs() {
        if (studentIdField.getText().isEmpty() || firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() ||
                semester1Field.getText().isEmpty() || semester2Field.getText().isEmpty() || semester3Field.getText().isEmpty() ||
                semester4Field.getText().isEmpty()) {
            showErrorAlert("All fields are required");
            return false;
        }

        try {
            if (!isUpdateMode) {
                Long.parseLong(studentIdField.getText());
            }
            Float.parseFloat(semester1Field.getText());
            Float.parseFloat(semester2Field.getText());
            Float.parseFloat(semester3Field.getText());
            Float.parseFloat(semester4Field.getText());
        } catch (NumberFormatException e) {
            showErrorAlert("Please enter valid numeric values for ID and averages");
            return false;
        }

        return true;
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }

    private void showConfirmationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.showAndWait();
    }

    @FXML
    public void handleCancel() {
        closeDialog();
    }

    private void closeDialog() {
        Stage stage = (Stage) studentIdField.getScene().getWindow();
        stage.close();
    }

    public void setOnStudentSavedCallback(Runnable callback) {
        this.onStudentSavedCallback = callback;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("isUpdateMode: " + isUpdateMode);
        if (isUpdateMode) {
            System.out.println("is update mode");
            populateFields();
            studentIdField.setDisable(true);
        }
    }}
