package com.ps.DAO;

import com.ps.DAO.interfaces.LeaseInt;
import com.ps.models.LeaseContract;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LeaseDAO implements LeaseInt {
    private BasicDataSource dataSource;

    public LeaseDAO(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void saveLeaseContract(LeaseContract leaseContract) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO lease_contracts (VIN, lease_start_date, lease_end_date, monthly_payment, customer_name, customer_phone) VALUES (?, ?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, leaseContract.getVin());
            preparedStatement.setString(2, leaseContract.getLeaseStartDate());
            preparedStatement.setString(3, leaseContract.getLeaseEndDate());
            preparedStatement.setDouble(4, leaseContract.getMonthlyPayment());
            preparedStatement.setString(5, leaseContract.getCustomerName());
            preparedStatement.setString(6, leaseContract.getCustomerPhone());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public LeaseContract getLeaseContractByVin(String vin) {
        LeaseContract leaseContract = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM lease_contracts WHERE VIN = ?")) {
            preparedStatement.setString(1, vin);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                leaseContract = new LeaseContract(
                        resultSet.getString("VIN"),
                        resultSet.getString("lease_start_date"),
                        resultSet.getString("lease_end_date"),
                        resultSet.getDouble("monthly_payment"),
                        resultSet.getString("customer_name"),
                        resultSet.getString("customer_phone")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaseContract;
    }
}
