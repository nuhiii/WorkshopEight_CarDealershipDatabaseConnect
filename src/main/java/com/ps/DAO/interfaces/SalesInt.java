package com.ps.DAO.interfaces;

import com.ps.models.SalesContract;

public interface SalesInt {
    void saveSalesContract(SalesContract salesContract);
    SalesContract getSalesContractByVin(String vin);
}
