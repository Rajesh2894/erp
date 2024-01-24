package com.abm.mainet.common.service;

import com.abm.mainet.common.master.dto.EmployeeBean;

/**
 * @author hiren.poriya
 * @Since 16-Apr-2018
 */
public interface EmployeeProvisionService {

    /**
     * this service is used to create employee details.
     * @param employeeBean
     */
    void createEmployee(EmployeeBean employeeBean);

    /**
     * this service is used to update employee details.
     * @param employeeBean
     */
    void updateEmployee(EmployeeBean employeeBean);

}
