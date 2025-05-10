-- Drop database if exists and create new one
DROP DATABASE IF EXISTS plan_management;
CREATE DATABASE plan_management;
USE plan_management;

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    date_registered DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create plans table
CREATE TABLE IF NOT EXISTS plans (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    status ENUM('ACTIVE', 'FINISHED') DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    finished_at TIMESTAMP NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Insert test users
INSERT INTO users (username, password, date_registered) VALUES
('admin123', 'admin123', CURRENT_DATE),
('test_user', 'test123', CURRENT_DATE);

-- Create indexes
CREATE INDEX idx_plans_user_id ON plans(user_id);
CREATE INDEX idx_plans_status ON plans(status); 