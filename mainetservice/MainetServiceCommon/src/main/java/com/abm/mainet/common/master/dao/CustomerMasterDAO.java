package com.abm.mainet.common.master.dao;

import java.util.List;

import com.abm.mainet.common.domain.CustomerMasterEntity;

public interface CustomerMasterDAO {

    List<CustomerMasterEntity> searchCustomer(String custName, String custAddress, Long orgId);
}
