package com.ps.models;

public class SalesContract {
    private String vin;
    private String saleDate;
    private double salePrice;
    private String customerName;
    private String customerPhone;
    private double salesTaxAmount;
    private double recordingFee;
    private double processingFee;
    private boolean isFinanced;
    private Vehicle vehicle;

    public SalesContract(String vin, String saleDate, String customerName, String customerPhone, Vehicle vehicle, boolean isFinanced) {
        this.vin = vin;
        this.saleDate = saleDate;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.vehicle = vehicle;
        this.salesTaxAmount = vehicle.getPrice() * 0.05;
        this.recordingFee = 100;
        if (vehicle.getPrice() < 10000) {
            this.processingFee = 295;
        }
        else {
            this.processingFee = 495;
        }
        this.isFinanced = isFinanced;
    }

    public SalesContract(String vin, String saleDate, double salePrice, String customerName, String customerPhone){
        this.vin = vin;
        this.salePrice = salePrice;
        this.saleDate = saleDate;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
    }

    // Getters and setters
    public String getVin() { return vin; }
    public void setVin(String vin) { this.vin = vin; }

    public String getSaleDate() { return saleDate; }
    public void setSaleDate(String saleDate) { this.saleDate = saleDate; }

    public double getSalePrice() { return salePrice; }
    public void setSalePrice(double salePrice) { this.salePrice = salePrice; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }

    public double getTotalPrice() {
        return vehicle.getPrice() + salesTaxAmount + recordingFee + processingFee;
    }

    public double getMonthlyPayment() {
        if(isFinanced){
            if(vehicle.getPrice() >= 10000){
                return (vehicle.getPrice() * 0.0425) / (1 - Math.pow(1 + (0.0425 / 12), -48));
            }
            else{
                return (vehicle.getPrice() * 0.0525) / (1 - Math.pow(1 + (0.0525 / 12), -24));
            }
        }
        else{
            return 0;
        }
    }

    @Override
    public String toString() {
        return String.format("VIN: %s, Sale Price: %.2f, Customer Name: %s, Customer Phone: %s",
                vin, salePrice, customerName, customerPhone);
    }
}
