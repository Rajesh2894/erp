package com.abm.mainet.common.master.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.CustomerMasterEntity;
import com.abm.mainet.common.domain.CustomerMasterHistory;
import com.abm.mainet.common.master.dao.CustomerMasterDAO;
import com.abm.mainet.common.master.dto.CustomerMasterDTO;
import com.abm.mainet.common.master.mapper.CustomerMasterMapper;
import com.abm.mainet.common.master.repository.CustomerMasterRepository;

@Service
public class CustomerMasterServiceImpl implements ICustomerMasterService {

    private static Logger log = Logger.getLogger(CustomerMasterServiceImpl.class);

    @Autowired
    private CustomerMasterRepository customerMasterRepository;

    @Autowired
    private CustomerMasterDAO customerMasterDAO;

    @Autowired
    private CustomerMasterMapper customerMasterMapper;

    @Autowired
    private AuditService auditService;

    @Override
    @Transactional(readOnly = true)
    public CustomerMasterDTO getById(Long custId) {
        CustomerMasterEntity customerMasterEntity = customerMasterRepository.findOne(custId);
        CustomerMasterDTO dto = new CustomerMasterDTO();
        BeanUtils.copyProperties(customerMasterEntity, dto);
        return dto;
    }

    @Override
    @Transactional
    public CustomerMasterDTO saveCustomerMaster(CustomerMasterDTO customerDetails) {
        CustomerMasterEntity customerMasterEntity = new CustomerMasterEntity();
        BeanUtils.copyProperties(customerDetails, customerMasterEntity);
        customerMasterEntity = customerMasterRepository.save(customerMasterEntity);

        CustomerMasterHistory custMasterHistory = new CustomerMasterHistory();
        custMasterHistory.sethStatus(MainetConstants.InsertMode.ADD.getStatus());

        try {
            auditService.createHistory(customerMasterEntity, custMasterHistory);
        } catch (Exception e) {
            log.error("Could not make audit entry for " + customerMasterEntity, e);
        }
        BeanUtils.copyProperties(customerMasterEntity, customerDetails);
        return customerDetails;
    }

    @Override
    @Transactional
    public CustomerMasterDTO updateCustomerMaster(CustomerMasterDTO customerDetails) {
        CustomerMasterEntity customerMasterEntity = new CustomerMasterEntity();
        BeanUtils.copyProperties(customerDetails, customerMasterEntity);
        customerMasterEntity = customerMasterRepository.save(customerMasterEntity);
        
        CustomerMasterHistory custMasterHistory = new CustomerMasterHistory();
        custMasterHistory.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());

        try {
            auditService.createHistory(customerMasterEntity, custMasterHistory);
        } catch (Exception e) {
            log.error("Could not make audit entry for " + customerMasterEntity, e);
        }
        BeanUtils.copyProperties(customerMasterEntity, customerDetails);
        return customerDetails;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerMasterDTO> searchCustomer(String custName, String custAddress, Long orgId) {
        List<CustomerMasterEntity> customerList = customerMasterDAO.searchCustomer(custName, custAddress, orgId);
        return customerMasterMapper.mapEntityListToDTOList(customerList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerMasterEntity> getcustomerByPanNo(String custPANNo, Long orgId) {

        return customerMasterRepository.getCustomerByPanNo(custPANNo, orgId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerMasterEntity> getcustomerByUidNo(Long custUIDNo, Long orgId) {

        return customerMasterRepository.getCustomerByUidNo(custUIDNo, orgId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerMasterEntity> getCustomerByTINNo(String custTINNo, Long orgId) {

        return customerMasterRepository.getCustomerByTIN(custTINNo, orgId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerMasterEntity> getCustomerByGstNo(String custGSTNo, Long orgId) {

        return customerMasterRepository.getCustomerByGst(custGSTNo, orgId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerMasterEntity> getcustomerByMobileNo(String custMobNo, Long orgId) {

        return customerMasterRepository.getCustomerByMobileNo(custMobNo, orgId);
    }

    @Override
    @Transactional
    public void saveCustomerList(List<CustomerMasterDTO> custList) {
        List<CustomerMasterEntity> customerMasterList = customerMasterMapper.mapDTOListToEntityList(custList);

        customerMasterList = customerMasterRepository.save(customerMasterList);
        customerMasterList.forEach(customer -> {
            CustomerMasterHistory custMasterHistory = new CustomerMasterHistory();
            custMasterHistory.sethStatus(MainetConstants.InsertMode.ADD.getStatus());
            try {
                auditService.createHistory(customer, custMasterHistory);
            } catch (Exception e) {
                log.error("Could not make audit entry for " + customer, e);
            }
        });

    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerMasterEntity> getCustomerByName(String custName, Long orgId) {
        return customerMasterRepository.getCustomerByName(custName, orgId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerMasterEntity> getCustomerByEmail(String custEmailId, Long orgId) {
        return customerMasterRepository.getCustomerByEmail(custEmailId, orgId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerMasterDTO> getAllCustomer(Long orgId) {
        List<CustomerMasterDTO> custDTOList = customerMasterRepository.getAllCustomer(orgId).stream().map(entity -> {
            CustomerMasterDTO dto = new CustomerMasterDTO();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        }).collect(Collectors.toList());

        return custDTOList;
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerMasterDTO getCustomerByRefNo(String custRefNo, Long orgId) {
        CustomerMasterDTO dto = new CustomerMasterDTO();
        CustomerMasterEntity customerMasterEntity = customerMasterRepository.getCustomerByRefNo(orgId, custRefNo);
        if (customerMasterEntity != null)
        BeanUtils.copyProperties(customerMasterEntity, dto);
        return dto;
    }

}
