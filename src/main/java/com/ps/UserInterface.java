package com.ps;

import com.ps.DAO.LeaseDAO;
import com.ps.DAO.SalesDAO;
import com.ps.DAO.VehicleDAO;
import com.ps.models.LeaseContract;
import com.ps.models.SalesContract;
import com.ps.models.Vehicle;
import org.apache.commons.dbcp2.BasicDataSource;

import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private static VehicleDAO vehicleDAO;
    private static SalesDAO salesDAO;
    private static LeaseDAO leaseDAO;

    private static Scanner scanner = new Scanner(System.in);

    public static void init(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Database username and password must be provided as arguments.");
        }

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl("jdbc:mysql://localhost:3306/dealership");
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        basicDataSource.setUsername(args[0]);
        basicDataSource.setPassword(args[1]);
        vehicleDAO = new VehicleDAO(basicDataSource);
        salesDAO = new SalesDAO(basicDataSource);
        leaseDAO = new LeaseDAO(basicDataSource);
    }

    public static void display(String[] args) {
        init(args);

        int command;

        do {
            System.out.println("What would you like to do?");
            System.out.println("1) Find vehicles within a price range");
            System.out.println("2) Find vehicles by make / model");
            System.out.println("3) Find vehicles by year range");
            System.out.println("4) Find vehicles by color");
            System.out.println("5) Find vehicles by mileage range");
            System.out.println("6) Find vehicle by VIN");
            System.out.println("7) List ALL vehicles");
            System.out.println("8) Add a vehicle");
            System.out.println("9) Remove a vehicle");
            System.out.println("10) Sell/Lease a vehicle");
            System.out.println("0) Exit");
            System.out.print("Please make a selection: ");

            command = scanner.nextInt();
            switch (command) {
                case 1:
                    processGetByPriceRequest();
                    break;
                case 2:
                    processGetByMakeModelRequest();
                    break;
                case 3:
                    processGetByYearRequest();
                    break;
                case 4:
                    processGetByColorRequest();
                    break;
                case 5:
                    processGetByMileageRequest();
                    break;
                case 6:
                    processGetByVinRequest();
                    break;
                case 7:
                    processGetAllVehiclesRequest();
                    break;
                case 8:
                    processAddVehicleRequest();
                    break;
                case 9:
                    processRemoveVehicleRequest();
                    break;
                case 10:
                    processSellLeaseVehicleRequest();
                    break;
                case 0:
                    System.out.println("Have a good day!");
                    break;
                default:
                    System.out.println("Command not found.");
            }
        } while (command != 0);
    }

    private static void processGetByPriceRequest() {
        System.out.print("Enter minimum price: ");
        double minPrice = scanner.nextDouble();
        System.out.print("Enter maximum price: ");
        double maxPrice = scanner.nextDouble();

        List<Vehicle> vehicles = vehicleDAO.getVehiclesByPriceRange(minPrice, maxPrice);
        displayVehicles(vehicles);
    }

    private static void processGetByMakeModelRequest() {
        System.out.print("Enter make: ");
        String make = scanner.next();
        System.out.print("Enter model: ");
        String model = scanner.next();

        List<Vehicle> vehicles = vehicleDAO.getVehiclesByMakeModel(make, model);
        displayVehicles(vehicles);
    }

    private static void processGetByYearRequest() {
        System.out.print("Enter start year: ");
        int startYear = scanner.nextInt();
        System.out.print("Enter end year: ");
        int endYear = scanner.nextInt();

        List<Vehicle> vehicles = vehicleDAO.getVehiclesByYearRange(startYear, endYear);
        displayVehicles(vehicles);
    }

    private static void processGetByColorRequest() {
        System.out.print("Enter color: ");
        String color = scanner.next();

        List<Vehicle> vehicles = vehicleDAO.getVehiclesByColor(color);
        displayVehicles(vehicles);
    }

    private static void processGetByMileageRequest() {
        System.out.print("Enter minimum mileage: ");
        int minMileage = scanner.nextInt();
        System.out.print("Enter maximum mileage: ");
        int maxMileage = scanner.nextInt();

        List<Vehicle> vehicles = vehicleDAO.getVehiclesByMileageRange(minMileage, maxMileage);
        displayVehicles(vehicles);
    }

    private static void processGetByVinRequest() {
        System.out.print("Enter VIN: ");
        String vin = scanner.next();

        Vehicle vehicle = vehicleDAO.getVehicleByVin(vin);
        if (vehicle != null) {
            System.out.println(vehicle);
        } else {
            System.out.println("Vehicle not found.");
        }
    }

    private static void processGetAllVehiclesRequest() {
        List<Vehicle> vehicles = vehicleDAO.getAllVehicles();
        displayVehicles(vehicles);
    }

    private static void processAddVehicleRequest() {
        System.out.print("VIN: ");
        String vin = scanner.next();
        System.out.print("Year: ");
        int year = scanner.nextInt();
        System.out.print("Make: ");
        String make = scanner.next();
        System.out.print("Model: ");
        String model = scanner.next();
        System.out.print("Color: ");
        String color = scanner.next();
        System.out.print("Odometer: ");
        int odometer = scanner.nextInt();
        System.out.print("Price: ");
        double price = scanner.nextDouble();

        Vehicle vehicle = new Vehicle(vin, year, make, model, color, odometer, price, false);
        vehicleDAO.addVehicle(vehicle);
    }

    private static void processRemoveVehicleRequest() {
        System.out.print("Enter VIN of the vehicle to remove: ");
        String vin = scanner.next();

        vehicleDAO.removeVehicle(vin);
    }

    private static void processSellLeaseVehicleRequest() {
        System.out.print("Enter VIN of the vehicle to sell/lease: ");
        String vin = scanner.next();

        Vehicle vehicle = vehicleDAO.getVehicleByVin(vin);

        if (vehicle == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        if (vehicle.isSold()) {
            System.out.println("Vehicle is already sold.");
            return;
        }

        System.out.print("Enter 1 to sell or 2 to lease: ");
        int choice = scanner.nextInt();

        if (choice == 1) {
            // Handle sale
            System.out.print("Enter sale date (YYYY-MM-DD): ");
            String saleDate = scanner.next();
            System.out.print("Enter customer name: ");
            String customerName = scanner.next();
            System.out.print("Enter customer phone: ");
            String customerPhone = scanner.next();
            System.out.print("Is this financed? (true/false): ");
            boolean isFinanced = scanner.nextBoolean();

            SalesContract salesContract = new SalesContract(vin, saleDate, customerName, customerPhone, vehicle, isFinanced);
            salesDAO.saveSalesContract(salesContract);

            vehicle.setSold(true);
            vehicleDAO.updateVehicle(vehicle);
        } else if (choice == 2) {
            // Handle lease
            System.out.print("Enter lease start date (YYYY-MM-DD): ");
            String leaseStartDate = scanner.next();
            System.out.print("Enter lease end date (YYYY-MM-DD): ");
            String leaseEndDate = scanner.next();
            System.out.print("Enter customer name: ");
            String customerName = scanner.next();
            System.out.print("Enter customer phone: ");
            String customerPhone = scanner.next();

            LeaseContract leaseContract = new LeaseContract(vin, leaseStartDate, leaseEndDate, customerName, customerPhone, vehicle);
            leaseDAO.saveLeaseContract(leaseContract);
        } else {
            System.out.println("Invalid option.");
        }
    }

    private static void displayVehicles(List<Vehicle> vehicles) {
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle);
        }
    }
}
