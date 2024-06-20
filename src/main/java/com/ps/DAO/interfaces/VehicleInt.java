package com.ps.DAO.interfaces;

import com.ps.models.Vehicle;
import java.util.List;

public interface VehicleInt {
    List<Vehicle> getVehiclesByPriceRange(double minPrice, double maxPrice);
    List<Vehicle> getVehiclesByMakeModel(String make, String model);
    List<Vehicle> getVehiclesByYearRange(int startYear, int endYear);
    List<Vehicle> getVehiclesByColor(String color);
    List<Vehicle> getVehiclesByMileageRange(int minMileage, int maxMileage);
    List<Vehicle> getAllVehicles();
    Vehicle getVehicleByVin(String vin);
    void addVehicle(Vehicle vehicle);
    void removeVehicle(String vin);
    void updateVehicle(Vehicle vehicle);
}
