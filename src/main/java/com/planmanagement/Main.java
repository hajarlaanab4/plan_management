package com.planmanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.sun.javafx.runtime.VersionInfo;

public class Main extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/styles/theme.css").toExternalForm());
        primaryStage.setTitle("Plan Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void setRoot(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/" + fxml + ".fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("/styles/theme.css").toExternalForm());
        primaryStage.setScene(scene);
    }

    public static FXMLLoader getLoader(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/" + fxml + ".fxml"));
        return loader;
    }

    public static void main(String[] args) {
        launch(args);
    }
} 