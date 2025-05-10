package com.planmanagement.controllers;

import com.planmanagement.Main;
import com.planmanagement.database.PlanDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

public class HomeController {
    @FXML
    private Label usernameLabel;
    @FXML
    private Label dateRegisteredLabel;
    @FXML
    private Label activePlansLabel;
    @FXML
    private Label finishedPlansLabel;

    private PlanDAO planDAO = new PlanDAO();

    @FXML
    private void initialize() {
        if (LoginController.getCurrentUser() != null) {
            updateUserInfo();
        }
    }

    private void updateUserInfo() {
        usernameLabel.setText(LoginController.getCurrentUser().getUsername());
        dateRegisteredLabel.setText(LoginController.getCurrentUser().getDateRegistered().toString());
        updatePlanCounts();
    }

    public void setUserInfo(String username, String dateRegistered, int activePlans, int finishedPlans) {
        System.out.println("Setting user info: " + username);
        if (usernameLabel != null) {
            usernameLabel.setText(username);
            dateRegisteredLabel.setText(dateRegistered);
            updatePlanCounts();
        } else {
            System.err.println("Labels not initialized!");
        }
    }

    public void updatePlanCounts() {
        if (LoginController.getCurrentUser() != null) {
            int userId = LoginController.getCurrentUser().getId();
            int activePlans = planDAO.getActivePlansCount(userId);
            int finishedPlans = planDAO.getFinishedPlansCount(userId);
            
            activePlansLabel.setText(String.valueOf(activePlans));
            finishedPlansLabel.setText(String.valueOf(finishedPlans));
        }
    }

    @FXML
    private void handleMyPlans(ActionEvent event) {
        try {
            Main.setRoot("Plans");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleFinishedPlans(ActionEvent event) {
        try {
            Main.setRoot("Plans");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            Main.setRoot("Login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 