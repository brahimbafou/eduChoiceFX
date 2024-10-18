package com.university.ui;

import com.university.dao.ChoiceDAO;
import com.university.dao.ChoiceDAOImpl;
import com.university.dao.SpecialtyDAO;
import com.university.dao.SpecialtyDAOImpl;
import com.university.dao.StudentDAO;
import com.university.dao.StudentDAOImpl;
import com.university.model.Choice;
import com.university.model.Specialty;
import com.university.model.Student;
import com.university.service.StudentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ChoiceFormController implements Initializable {

//    @FXML private TextField searchStudentField;
    @FXML private TextField studentIdField;
    @FXML private TextField studentNameField;
    @FXML private GridPane choicesGrid; // Use GridPane to organize choices
    @FXML private ComboBox<Student> studentComboBox;


    private final ChoiceDAO choiceDAO;
    private final StudentDAO studentDAO;
    private final SpecialtyDAO specialtyDAO;
    private Runnable onChoiceSavedCallback; // Callback for after saving choices


    private ObservableList<Student> allStudents;
    private ObservableList<Specialty> allSpecialties;

    private Student selectedStudent;
    private List<ComboBox<Specialty>> choiceComboBoxes; // Store the ComboBoxes for each choice

    public ChoiceFormController() {
        this.choiceDAO = new ChoiceDAOImpl();
        this.studentDAO = new StudentDAOImpl();
        this.specialtyDAO = new SpecialtyDAOImpl();
    }

    public ChoiceFormController(Choice choice) {
        this();
        if (choice != null) {
            this.selectedStudent = choice.getStudent();
            populateFieldsForEdit(choice);
        }
    }
    private void populateFieldsForEdit(Choice choice) {
        studentIdField.setText(String.valueOf(choice.getStudent().getStudentId()));
        studentNameField.setText(choice.getStudent().getFirstName() + " " + choice.getStudent().getLastName());
        // Logic to populate choices fields based on existing choice
    }
    @FXML
    public void initialize() {

    }

    private void loadStudents() {
        StudentService studentService = new StudentService();
        List<Student> students = studentService.getAllStudents();
        studentComboBox.setItems(FXCollections.observableArrayList(students));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        allStudents = FXCollections.observableArrayList(studentDAO.getAllStudents());
        loadStudents();
        studentComboBox.setCellFactory(new Callback<ListView<Student>, ListCell<Student>>() {
            @Override
            public ListCell<Student> call(ListView<Student> param) {
                return new ListCell<Student>() {
                    @Override
                    protected void updateItem(Student item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.getFirstName() + " " + item.getLastName() + " (" + item.getStudentId() + ")");
                        }
                    }
                };
            }
        });
        studentComboBox.setEditable(true);
        allSpecialties = FXCollections.observableArrayList(specialtyDAO.getAllSpecialties());

        choiceComboBoxes = new ArrayList<>();
    }
    @FXML
    private void handleStudentSearch(KeyEvent event) {
        String filter = studentComboBox.getEditor().getText();
        ObservableList<Student> filteredStudents = FXCollections.observableArrayList();

        for (Student student : studentComboBox.getItems()) {
            if (student.getFirstName().toLowerCase().contains(filter.toLowerCase()) ||
                    student.getLastName().toLowerCase().contains(filter.toLowerCase())) {
                filteredStudents.add(student);
            }
        }

        studentComboBox.setItems(filteredStudents);
        studentComboBox.getEditor().setText(filter);
        studentComboBox.getEditor().positionCaret(filter.length()); // Keep the cursor at the end
    }
//    @FXML
//    public void handleSearchStudent() {
//        String searchText = searchStudentField.getText().toLowerCase();
//
//        // Search for students by ID or name
//        List<Student> filteredStudents = allStudents.stream()
//                .filter(student -> student.getFirstName().toLowerCase().contains(searchText)
//                        || student.getLastName().toLowerCase().contains(searchText)
//                        || String.valueOf(student.getStudentId()).contains(searchText))
//                .collect(Collectors.toList());
//
//        if (filteredStudents.size() == 1) {
//            selectedStudent = filteredStudents.get(0);
//            studentIdField.setText(String.valueOf(selectedStudent.getStudentId()));
//            studentNameField.setText(selectedStudent.getFirstName() + " " + selectedStudent.getLastName());
//
//            // Generate choice fields based on the number of specialties
//            populateChoiceFields();
//        }
//    }

    private void populateChoiceFields() {
        choicesGrid.getChildren().clear();  // Clear previous choices
        choiceComboBoxes.clear();  // Reset the list of ComboBoxes

        // Define column constraints for better resizing
        ColumnConstraints labelColumn = new ColumnConstraints();
        labelColumn.setPrefWidth(120); // Set preferred width for label column

        ColumnConstraints comboBoxColumn = new ColumnConstraints();
        comboBoxColumn.setPrefWidth(200); // Set preferred width for ComboBox column

        choicesGrid.getColumnConstraints().clear(); // Clear existing constraints
        choicesGrid.getColumnConstraints().addAll(labelColumn, comboBoxColumn); // Add new constraints

        // Add rows dynamically based on the number of specialties
        for (int i = 0; i < allSpecialties.size(); i++) {
            // Create a label for the choice order (e.g., "Choice 1")
            Label choiceLabel = new Label("Choice " + (i + 1) + ":");

            // Create a ComboBox for selecting the specialty
            ComboBox<Specialty> specialtyComboBox = new ComboBox<>(allSpecialties);
            specialtyComboBox.setPromptText("Select Specialty");
            specialtyComboBox.setMinWidth(200);

            // Set the custom StringConverter directly
            specialtyComboBox.setConverter(new StringConverter<Specialty>() {
                @Override
                public String toString(Specialty specialty) {
                    return specialty != null ? specialty.getSpecialtyName() + " (" + specialty.getSpecialtyId() + ")" : "";
                }

                @Override
                public Specialty fromString(String string) {
                    return null;  // Not needed for this use case
                }
            });

            // Add event listener to prevent duplicate selections
//            specialtyComboBox.setOnAction(event -> checkForDuplicateSelections());

            // Add the label and ComboBox to the GridPane
            choicesGrid.add(choiceLabel, 0, i);  // Add label in the first column
            choicesGrid.add(specialtyComboBox, 1, i);  // Add ComboBox in the second column
            choiceComboBoxes.add(specialtyComboBox);  // Store the ComboBox for later use
        }
        // Resize the modal stage if necessary
        Stage stage = (Stage) choicesGrid.getScene().getWindow();
        stage.sizeToScene();  // Resize stage to fit content
    }


    private void checkForDuplicateSelections() {
        List<Specialty> selectedSpecialties = choiceComboBoxes.stream()
                .map(ComboBox::getValue)
                .collect(Collectors.toList());

        // Iterate over all ComboBoxes and disable already-selected specialties
        for (ComboBox<Specialty> comboBox : choiceComboBoxes) {
            Specialty currentSelection = comboBox.getValue();

            comboBox.getItems().setAll(allSpecialties);  // Reset the items
            comboBox.getItems().removeAll(selectedSpecialties);  // Remove already-selected specialties

            // Keep the current selection in the ComboBox if it exists
            if (currentSelection != null) {
                comboBox.getItems().add(currentSelection);
                comboBox.setValue(currentSelection);  // Re-select the current value
            }
        }
    }

    @FXML
    public void handleSubmit() {
        if (selectedStudent == null) {
            showErrorAlert("Please select a student.");
            return;
        }

        List<Specialty> selectedSpecialties = new ArrayList<>();
        for (ComboBox<Specialty> comboBox : choiceComboBoxes) {
            Specialty specialty = comboBox.getValue();
            if (specialty == null) {
                showErrorAlert("Please make a selection for all choices.");
                return;
            }
            selectedSpecialties.add(specialty);
        }

        // Add the choices to the database
        for (int i = 0; i < selectedSpecialties.size(); i++) {
            Specialty specialty = selectedSpecialties.get(i);
            Choice newChoice = new Choice(selectedStudent, specialty, i + 1);  // Choice order is 1-based
            choiceDAO.addChoice(newChoice);
        }
        onChoiceSavedCallback.run();
        closeDialog();
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
    public void setOnChoiceSavedCallback(Runnable callback) {
        this.onChoiceSavedCallback = callback;
    }

    private void closeDialog() {
        Stage stage = (Stage) studentComboBox.getScene().getWindow();
        stage.close();
    }
}
