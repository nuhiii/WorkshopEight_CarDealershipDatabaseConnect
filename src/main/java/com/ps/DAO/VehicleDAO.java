package com.ps.DAO;

import com.ps.DAO.interfaces.VehicleInt;
import com.ps.models.Vehicle;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO implements VehicleInt {
    private BasicDataSource dataSource;

    public VehicleDAO(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Vehicle> getVehiclesByPriceRange(double minPrice, double maxPrice) {
        List<Vehicle> vehicles = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM vehicles WHERE price BETWEEN ? AND ?")) {
            preparedStatement.setDouble(1, minPrice);
            preparedStatement.setDouble(2, maxPrice);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                vehicles.add(mapRowToVehicle(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> getVehiclesByMakeModel(String make, String model) {
        List<Vehicle> vehicles = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM vehicles WHERE make = ? AND model = ?")) {
            preparedStatement.setString(1, make);
            preparedStatement.setString(2, model);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                vehicles.add(mapRowToVehicle(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> getVehiclesByYearRange(int startYear, int endYear) {
        List<Vehicle> vehicles = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM vehicles WHERE year BETWEEN ? AND ?")) {
            preparedStatement.setInt(1, startYear);
            preparedStatement.setInt(2, endYear);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                vehicles.add(mapRowToVehicle(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> getVehiclesByColor(String color) {
        List<Vehicle> vehicles = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM vehicles WHERE color = ?")) {
            preparedStatement.setString(1, color);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                vehicles.add(mapRowToVehicle(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> getVehiclesByMileageRange(int minMileage, int maxMileage) {
        List<Vehicle> vehicles = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM vehicles WHERE mileage BETWEEN ? AND ?")) {
            preparedStatement.setInt(1, minMileage);
            preparedStatement.setInt(2, maxMileage);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                vehicles.add(mapRowToVehicle(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM vehicles");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                vehicles.add(mapRowToVehicle(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    @Override
    public Vehicle getVehicleByVin(String vin) {
        Vehicle vehicle = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM vehicles WHERE VIN = ?")) {
            preparedStatement.setString(1, vin);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                vehicle = mapRowToVehicle(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicle;
    }

    @Override
    public void addVehicle(Vehicle vehicle) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO vehicles (VIN, year, make, model, color, mileage, price, SOLD) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, vehicle.getVin());
            preparedStatement.setInt(2, vehicle.getYear());
            preparedStatement.setString(3, vehicle.getMake());
            preparedStatement.setString(4, vehicle.getModel());
            preparedStatement.setString(5, vehicle.getColor());
            preparedStatement.setInt(6, vehicle.getOdometer());
            preparedStatement.setDouble(7, vehicle.getPrice());
            preparedStatement.setBoolean(8, vehicle.isSold());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeVehicle(String vin) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM vehicles WHERE VIN = ?")) {
            preparedStatement.setString(1, vin);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateVehicle(Vehicle vehicle) {
        String query = "UPDATE vehicles SET year = ?, make = ?, model = ?, color = ?, mileage = ?, price = ?, SOLD = ? WHERE VIN = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, vehicle.getYear());
            preparedStatement.setString(2, vehicle.getMake());
            preparedStatement.setString(3, vehicle.getModel());
            preparedStatement.setString(4, vehicle.getColor());
            preparedStatement.setInt(5, vehicle.getOdometer());
            preparedStatement.setDouble(6, vehicle.getPrice());
            preparedStatement.setBoolean(7, vehicle.isSold());
            preparedStatement.setString(8, vehicle.getVin());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Vehicle mapRowToVehicle(ResultSet resultSet) throws SQLException {
        return new Vehicle(
                resultSet.getString("VIN"),
                resultSet.getInt("year"),
                resultSet.getString("make"),
                resultSet.getString("model"),
                resultSet.getString("color"),
                resultSet.getInt("mileage"),
                resultSet.getDouble("price"),
                resultSet.getBoolean("SOLD")
        );
    }
}
