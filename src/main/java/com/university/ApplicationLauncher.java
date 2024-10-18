package com.university;

import com.university.model.Student;
import com.university.service.StudentService;
import com.university.util.HibernateUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class ApplicationLauncher extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        HibernateUtil.getSessionFactory();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/university/ui/dashboard.fxml")));
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setMinWidth(860);
        primaryStage.setHeight(480);
        primaryStage.setResizable(false);
        primaryStage.setTitle("EduChoiceFX");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();

    }
    @Override
    public void stop() {
        HibernateUtil.shutdown();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
