package com.planmanagement.database;

import com.planmanagement.models.Plan;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PlanDAO {
    public boolean addPlan(Plan plan) {
        String sql = "INSERT INTO plans (user_id, description, start_date, end_date, status, created_at) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, plan.getUserId());
            stmt.setString(2, plan.getDescription());
            stmt.setDate(3, Date.valueOf(plan.getStartDate()));
            stmt.setDate(4, Date.valueOf(plan.getEndDate()));
            stmt.setString(5, plan.getStatus().name());
            stmt.setTimestamp(6, Timestamp.valueOf(plan.getCreatedAt()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePlan(Plan plan) {
        String sql = "UPDATE plans SET description = ?, start_date = ?, end_date = ?, status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, plan.getDescription());
            stmt.setDate(2, Date.valueOf(plan.getStartDate()));
            stmt.setDate(3, Date.valueOf(plan.getEndDate()));
            stmt.setString(4, plan.getStatus().name());
            stmt.setInt(5, plan.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Plan> getPlansByUser(int userId) {
        List<Plan> plans = new ArrayList<>();
        String sql = "SELECT * FROM plans WHERE user_id = ? ORDER BY created_at DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Plan plan = new Plan();
                plan.setId(rs.getInt("id"));
                plan.setUserId(rs.getInt("user_id"));
                plan.setDescription(rs.getString("description"));
                plan.setStartDate(rs.getDate("start_date").toLocalDate());
                plan.setEndDate(rs.getDate("end_date").toLocalDate());
                plan.setStatus(Plan.PlanStatus.valueOf(rs.getString("status")));
                plan.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                Timestamp finishedAt = rs.getTimestamp("finished_at");
                if (finishedAt != null) {
                    plan.setFinishedAt(finishedAt.toLocalDateTime());
                }
                plans.add(plan);
            }
        } catch (SQLException e) {
            System.err.println("Error loading plans: " + e.getMessage());
            e.printStackTrace();
        }
        return plans;
    }

    public boolean updatePlanStatus(int planId, Plan.PlanStatus status) {
        String sql = "UPDATE plans SET status = ?, finished_at = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status.name());
            if (status == Plan.PlanStatus.FINISHED) {
                stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            } else {
                stmt.setNull(2, Types.TIMESTAMP);
            }
            stmt.setInt(3, planId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePlan(int planId) {
        String sql = "DELETE FROM plans WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, planId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getActivePlansCount(int userId) {
        String sql = "SELECT COUNT(*) FROM plans WHERE user_id = ? AND status = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, Plan.PlanStatus.ACTIVE.name());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getFinishedPlansCount(int userId) {
        String sql = "SELECT COUNT(*) FROM plans WHERE user_id = ? AND status = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, Plan.PlanStatus.FINISHED.name());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
} 