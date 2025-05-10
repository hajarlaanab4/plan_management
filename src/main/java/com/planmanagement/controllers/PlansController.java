package com.planmanagement.controllers;

import com.planmanagement.Main;
import com.planmanagement.database.PlanDAO;
import com.planmanagement.models.Plan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PlansController {
    @FXML
    private TextArea planTextArea;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TableView<Plan> plansTable;
    @FXML
    private TableColumn<Plan, String> planColumn;
    @FXML
    private TableColumn<Plan, LocalDate> startDateColumn;
    @FXML
    private TableColumn<Plan, LocalDate> endDateColumn;
    @FXML
    private TableColumn<Plan, LocalDateTime> dateCreatedColumn;
    @FXML
    private TableColumn<Plan, String> statusColumn;

    private PlanDAO planDAO = new PlanDAO();
    private ObservableList<Plan> plansList = FXCollections.observableArrayList();
    private Plan selectedPlan;

    @FXML
    public void initialize() {
        // Initialize table columns
        planColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        dateCreatedColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        plansTable.setItems(plansList);

        // Add selection listener
        plansTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedPlan = newSelection;
                planTextArea.setText(selectedPlan.getDescription());
                startDatePicker.setValue(selectedPlan.getStartDate());
                endDatePicker.setValue(selectedPlan.getEndDate());
            }
        });

        // Load existing plans
        loadPlans(getCurrentUserId());
    }

    @FXML
    private void handleAdd() {
        if (validateInput()) {
            Plan plan = new Plan();
            plan.setUserId(getCurrentUserId());
            plan.setDescription(planTextArea.getText());
            plan.setStartDate(startDatePicker.getValue());
            plan.setEndDate(endDatePicker.getValue());
            plan.setStatus(Plan.PlanStatus.ACTIVE);
            
            if (planDAO.addPlan(plan)) {
                loadPlans(getCurrentUserId());
                clearForm();
                refreshHomeView();
            } else {
                showAlert("Error", "Failed to add plan");
            }
        }
    }

    @FXML
    private void handleUpdate() {
        if (selectedPlan == null && validateInput()) {
            selectedPlan.setDescription(planTextArea.getText());
            selectedPlan.setStartDate(startDatePicker.getValue());
            selectedPlan.setEndDate(endDatePicker.getValue());

            if (planDAO.updatePlan(selectedPlan)) {
                loadPlans(getCurrentUserId());
                clearForm();
                refreshHomeView();
            } else {
                showAlert("Error", "Failed to update plan");
            }
        } else {
            showAlert("Warning", "Please select a plan to update");
        }
    }

    @FXML
    private void handleClear() {
        clearForm();
    }

    @FXML
    private void handleDelete() {
        if (selectedPlan != null) {
            if (planDAO.deletePlan(selectedPlan.getId())) {
                loadPlans(getCurrentUserId());
                clearForm();
                refreshHomeView();
            } else {
                showAlert("Error", "Failed to delete plan");
            }
        } else {
            showAlert("Warning", "Please select a plan to delete");
        }
    }

    @FXML
    private void handleHome() {
        try {
            Main.setRoot("Home");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() {
        try {
            Main.setRoot("Login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleMarkAsFinished() {
        if (selectedPlan != null) {
            if (selectedPlan.getStatus() == Plan.PlanStatus.FINISHED) {
                showAlert("Warning", "This plan is already finished");
                return;
            }
            
            selectedPlan.setStatus(Plan.PlanStatus.FINISHED);
            if (planDAO.updatePlanStatus(selectedPlan.getId(), Plan.PlanStatus.FINISHED)) {
                loadPlans(getCurrentUserId());
                clearForm();
                refreshHomeView();
            } else {
                showAlert("Error", "Failed to mark plan as finished");
            }
        } else {
            showAlert("Warning", "Please select a plan to mark as finished");
        }
    }

    private void clearForm() {
        planTextArea.clear();
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        selectedPlan = null;
        plansTable.getSelectionModel().clearSelection();
    }

    private boolean validateInput() {
        if (planTextArea.getText().trim().isEmpty()) {
            showAlert("Warning", "Please enter a plan description");
            return false;
        }
        if (startDatePicker.getValue() == null) {
            showAlert("Warning", "Please select a start date");
            return false;
        }
        if (endDatePicker.getValue() == null) {
            showAlert("Warning", "Please select an end date");
            return false;
        }
        if (endDatePicker.getValue().isBefore(startDatePicker.getValue())) {
            showAlert("Warning", "End date cannot be before start date");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void loadPlans(int userId) {
        System.out.println("Loading plans for user ID: " + userId);
        List<Plan> plans = planDAO.getPlansByUser(userId);
        System.out.println("Found " + plans.size() + " plans");
        plansList.setAll(plans);
        System.out.println("Plans loaded into table");
    }

    private int getCurrentUserId() {
        return LoginController.getCurrentUser().getId();
    }

    private void refreshHomeView() {
        try {
            FXMLLoader loader = Main.getLoader("Home");
            loader.load();
            HomeController homeController = loader.getController();
            homeController.updatePlanCounts();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 