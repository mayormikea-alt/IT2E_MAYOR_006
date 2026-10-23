package com.mayor.exam.ui;

import com.mayor.exam.dao.VehicleDao;
import com.mayor.exam.model.Vehicle;

import java.util.List;
import java.util.Scanner;

public class VehicleMenu {
    private final VehicleDao dao;
    private final Scanner scanner;

    public VehicleMenu() {
        this.dao = new VehicleDao();
        this.scanner = new Scanner(System.in);
        dao.createTableIfNotExists();
    }

    public void displayMenu() {
        int choice = 0;
        do {
            System.out.println("\n==========================================================");
            System.out.println("                 CAR RENTAL FLEET TRACKER                 ");
            System.out.println("==========================================================");
            System.out.println("[1] Add New Vehicle");
            System.out.println("[2] View Fleet Vehicles");
            System.out.println("[3] Exit System");
            System.out.println("==========================================================");
            System.out.print("Enter choice: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1 -> addVehicleFlow();
                    case 2 -> viewVehiclesFlow();
                    case 3 -> System.out.println("\nExiting system... Thank you!");
                    default -> System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // consume invalid token
            }
        } while (choice != 3);
    }

    private void addVehicleFlow() {
        System.out.println("\n--- ADD NEW VEHICLE ---");
        System.out.print("Enter Model Name: ");
        String modelName = scanner.nextLine();
        System.out.print("Enter License Plate Number: ");
        String plateNo = scanner.nextLine();

        double dailyRate = 0.0;
        while (true) {
            System.out.print("Enter Daily Rate: ");
            if (scanner.hasNextDouble()) {
                dailyRate = scanner.nextDouble();
                scanner.nextLine(); // consume newline
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid number for daily rate.");
                scanner.next();
            }
        }

        System.out.print("Enter Status (AVAILABLE / RENTED): ");
        String status = scanner.nextLine().trim().toUpperCase();

        dao.insertVehicle(modelName, plateNo, dailyRate, status);
    }

    private void viewVehiclesFlow() {
        List<Vehicle> vehicles = dao.getAllVehicles();

        System.out.println("\n==========================================================================================");
        System.out.println("                                RENTAL FLEET VEHICLES                                     ");
        System.out.println("==========================================================================================");
        System.out.println("+-----+--------------------+--------------------+--------------+-------------+");
        System.out.printf("| %-3s | %-18s | %-18s | %-12s | %-11s |%n",
                "ID", "MODEL NAME", "PLATE NO", "DAILY RATE", "STATUS");
        System.out.println("+-----+--------------------+--------------------+--------------+-------------+");

        for (Vehicle v : vehicles) {
            System.out.printf("| %-3d | %-18s | %-18s | %-12.2f | %-11s |%n",
                    v.id(), v.modelName(), v.plateNo(), v.dailyRate(), v.status());
        }
        System.out.println("+-----+--------------------+--------------------+--------------+-------------+");
        System.out.println("Total Records Displayed: " + vehicles.size());
    }
}