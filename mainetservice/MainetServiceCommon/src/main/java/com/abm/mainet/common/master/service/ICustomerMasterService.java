package com.abm.mainet.common.master.service;

import java.util.List;

import com.abm.mainet.common.domain.CustomerMasterEntity;
import com.abm.mainet.common.master.dto.CustomerMasterDTO;

public interface ICustomerMasterService {

    CustomerMasterDTO getById(Long custId);

    CustomerMasterDTO saveCustomerMaster(CustomerMasterDTO customerDetails);

    CustomerMasterDTO updateCustomerMaster(CustomerMasterDTO customerDetails);

    List<CustomerMasterDTO> searchCustomer(String custName, String custAddress, Long orgId);

    List<CustomerMasterEntity> getcustomerByPanNo(String custPANNo, Long orgId);

    List<CustomerMasterEntity> getcustomerByUidNo(Long custUIDNo, Long orgId);

    List<CustomerMasterEntity> getCustomerByTINNo(String custTINNo, Long orgId);

    List<CustomerMasterEntity> getCustomerByGstNo(String custGSTNo, Long orgId);

    List<CustomerMasterEntity> getCustomerByName(String custName, Long orgId);

    List<CustomerMasterEntity> getCustomerByEmail(String custEmailId, Long orgId);

    List<CustomerMasterEntity> getcustomerByMobileNo(String custMobNo, Long orgId);

    void saveCustomerList(List<CustomerMasterDTO> custList);

    List<CustomerMasterDTO> getAllCustomer(Long orgId);

    CustomerMasterDTO getCustomerByRefNo(String custRefNo, Long orgId);
}
