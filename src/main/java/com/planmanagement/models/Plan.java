package com.planmanagement.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Plan {
    private int id;
    private int userId;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private PlanStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime finishedAt;

    public enum PlanStatus {
        ACTIVE,
        FINISHED
    }

    public Plan() {
        this.status = PlanStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
    }

    public Plan(int userId, String description, LocalDate startDate, LocalDate endDate) {
        this();
        this.userId = userId;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public PlanStatus getStatus() {
        return status;
    }

    public void setStatus(PlanStatus status) {
        this.status = status;
        if (status == PlanStatus.FINISHED) {
            this.finishedAt = LocalDateTime.now();
        }
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(LocalDateTime finishedAt) {
        this.finishedAt = finishedAt;
    }
} 