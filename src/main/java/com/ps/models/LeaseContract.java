package com.ps.models;

public class LeaseContract {
    private String vin;
    private String leaseStartDate;
    private String leaseEndDate;
    private double monthlyPayment;
    private String customerName;
    private String customerPhone;
    private double expectedEndingValue;
    private double leaseFee;
    private double totalPrice;

    public LeaseContract(String vin, String leaseStartDate, String leaseEndDate, String customerName, String customerPhone, Vehicle vehicle) {
        this.vin = vin;
        this.leaseStartDate = leaseStartDate;
        this.leaseEndDate = leaseEndDate;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.expectedEndingValue = vehicle.getPrice() * 0.50;
        this.leaseFee = vehicle.getPrice() * 0.07;
    }

    public LeaseContract(String vin, String leaseStartDate, String leaseEndDate, double monthlyPayment, String customerName, String customerPhone){
        this.vin = vin;
        this.leaseStartDate = leaseStartDate;
        this.leaseEndDate = leaseEndDate;
        this.monthlyPayment = monthlyPayment;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
    }

    // Getters and setters
    public String getVin() { return vin; }
    public void setVin(String vin) { this.vin = vin; }

    public String getLeaseStartDate() { return leaseStartDate; }
    public void setLeaseStartDate(String leaseStartDate) { this.leaseStartDate = leaseStartDate; }

    public String getLeaseEndDate() { return leaseEndDate; }
    public void setLeaseEndDate(String leaseEndDate) { this.leaseEndDate = leaseEndDate; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }

    public double getTotalPrice() {
        this.totalPrice = expectedEndingValue + leaseFee;
        return totalPrice;
    }

    public double getMonthlyPayment() {
        this.monthlyPayment = (getTotalPrice() * (0.04 / 12)) / (1 - Math.pow(1 + (0.04 / 12), -36));
        return monthlyPayment;
    }

    @Override
    public String toString() {
        return String.format("VIN: %s, Lease Start Date: %s, Lease End Date: %s, Monthly Payment: %.2f, Customer Name: %s, Customer Phone: %s",
                vin, leaseStartDate, leaseEndDate, monthlyPayment, customerName, customerPhone);
    }
}
