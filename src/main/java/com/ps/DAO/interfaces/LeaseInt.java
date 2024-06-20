package com.ps.DAO.interfaces;

import com.ps.models.LeaseContract;

public interface LeaseInt {
    void saveLeaseContract(LeaseContract leaseContract);
    LeaseContract getLeaseContractByVin(String vin);
}
