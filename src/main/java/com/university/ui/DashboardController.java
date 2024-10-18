package com.university.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private HBox topActionBar, closeHBox, choicesTabHBox, studentsTabHBox, specialitiesTabHBox;

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private void handleCloseAction() {
        Stage stage = (Stage) closeHBox.getScene().getWindow();
        stage.close(); // Close the current window
    }

    @FXML
    private void handleActionBarPress(MouseEvent event) {
        // Capture the initial click position to calculate the offset
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    private void handleActionBarDrag(MouseEvent event) {
        // Ensure the stage moves smoothly with the mouse drag
        Stage stage = (Stage) topActionBar.getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    @FXML
    private VBox contentArea;
    @FXML
    private HBox activeTab;
    public void handleStudentsManagement() {
        setActiveTab(studentsTabHBox);
        loadView("students_view.fxml");
    }

    @FXML
    public void handleSpecialtyManagement() {
        setActiveTab(specialitiesTabHBox);
        loadView("specialties_view.fxml");
    }

    @FXML
    public void handleChoicesManagement() {
        setActiveTab(choicesTabHBox);
        loadView("choices_view.fxml");
    }

    private void setActiveTab(HBox newActiveTab) {
        System.out.println("setActiveTab: " + newActiveTab);
        // Remove active class from the currently active tab
        if (activeTab != null) {
            activeTab.getStyleClass().remove("active-tab");
        }

        // Set the new active tab
        activeTab = newActiveTab;
        activeTab.getStyleClass().add("active-tab");
    }
    private void loadView(String fxml) {
        System.out.println("Loading" + fxml);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/university/ui/" + fxml));
            Node view = loader.load();
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setActiveTab(studentsTabHBox);
        loadView("students_view.fxml");
    }
}
