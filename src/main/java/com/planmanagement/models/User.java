package com.planmanagement.models;

import java.time.LocalDate;

public class User {
    private int id;
    private String username;
    private String password;
    private LocalDate dateRegistered;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.dateRegistered = LocalDate.now();
    }

    public User(int id, String username, String password, LocalDate dateRegistered) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.dateRegistered = dateRegistered;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(LocalDate dateRegistered) {
        this.dateRegistered = dateRegistered;
    }
} 