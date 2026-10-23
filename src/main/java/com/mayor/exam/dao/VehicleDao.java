package com.mayor.exam.dao;

import com.mayor.exam.model.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDao {

    public void createTableIfNotExists() {
        String sql = """
            CREATE TABLE IF NOT EXISTS vehicles (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                model_name TEXT NOT NULL,
                plate_no TEXT NOT NULL,
                daily_rate REAL NOT NULL,
                status TEXT NOT NULL
            );
            """;
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }

    public void insertVehicle(String modelName, String plateNo, double dailyRate, String status) {
        String sql = "INSERT INTO vehicles (model_name, plate_no, daily_rate, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, modelName);
            pstmt.setString(2, plateNo);
            pstmt.setDouble(3, dailyRate);
            pstmt.setString(4, status);
            pstmt.executeUpdate();
            System.out.println("SUCCESS: Vehicle registered to fleet!");
        } catch (SQLException e) {
            System.err.println("Error inserting vehicle: " + e.getMessage());
        }
    }

    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT id, model_name, plate_no, daily_rate, status FROM vehicles";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                vehicles.add(new Vehicle(
                        rs.getInt("id"),
                        rs.getString("model_name"),
                        rs.getString("plate_no"),
                        rs.getDouble("daily_rate"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching vehicles: " + e.getMessage());
        }
        return vehicles;
    }
}