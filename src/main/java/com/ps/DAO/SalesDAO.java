package com.ps.DAO;

import com.ps.DAO.interfaces.SalesInt;
import com.ps.models.SalesContract;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SalesDAO implements SalesInt {
    private BasicDataSource dataSource;

    public SalesDAO(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void saveSalesContract(SalesContract salesContract) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO sales_contracts (VIN, sale_date, sale_price, customer_name, customer_phone) VALUES (?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, salesContract.getVin());
            preparedStatement.setString(2, salesContract.getSaleDate());
            preparedStatement.setDouble(3, salesContract.getSalePrice());
            preparedStatement.setString(4, salesContract.getCustomerName());
            preparedStatement.setString(5, salesContract.getCustomerPhone());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SalesContract getSalesContractByVin(String vin) {
        SalesContract salesContract = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM sales_contracts WHERE VIN = ?")) {
            preparedStatement.setString(1, vin);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                salesContract = new SalesContract(
                        resultSet.getString("VIN"),
                        resultSet.getString("sale_date"),
                        resultSet.getDouble("sale_price"),
                        resultSet.getString("customer_name"),
                        resultSet.getString("customer_phone")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salesContract;
    }
}
