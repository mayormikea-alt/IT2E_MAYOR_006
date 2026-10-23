package com.mayor.exam.model;

public record Vehicle(
        int id,
        String modelName,
        String plateNo,
        double dailyRate,
        String status
) {}