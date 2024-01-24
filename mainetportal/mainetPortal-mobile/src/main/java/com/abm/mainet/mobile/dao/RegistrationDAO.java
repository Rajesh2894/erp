package com.abm.mainet.mobile.dao;

import com.abm.mainet.mobile.dto.EmployeeRequestDTO;

/**
 * @author umashanker.kanaujiya
 *
 */
public interface RegistrationDAO {

    /**
     * @param entity
     * @param employeeDTO
     * @return
     */
    boolean getEmployee(EmployeeRequestDTO employeeDTO);

}
