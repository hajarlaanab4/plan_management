package com.planmanagement.controllers;

import com.planmanagement.Main;
import com.planmanagement.database.UserDAO;
import com.planmanagement.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private UserDAO userDAO = new UserDAO();
    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = userDAO.loginUser(username, password);
        if (user != null) {
            try {
                currentUser = user; // Set the current user
                FXMLLoader loader = Main.getLoader("Home");
                Parent root = loader.load();
                HomeController homeController = loader.getController();
                
                // Set user info in Home view
                homeController.setUserInfo(
                    user.getUsername(),
                    user.getDateRegistered().toString(),
                    0,  // These will be updated by updatePlanCounts
                    0
                );

                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/styles/theme.css").toExternalForm());
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(scene);
            } catch (Exception e) {
                e.printStackTrace();
                showError("Error loading home page");
            }
        } else {
            showError("Invalid username or password");
        }
    }

    @FXML
    private void handleLogout() {
        currentUser = null; // Clear the current user
        try {
            Main.setRoot("Login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
} 