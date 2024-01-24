package com.abm.mainet.agency.dao;

import com.abm.mainet.agency.dto.TPAgencyReqDTO;
import com.abm.mainet.agency.dto.TPAgencyResDTO;
import com.abm.mainet.common.domain.Employee;

/**
 * @author Arun.Chavda
 *
 */
public interface AgencyRegistrationProcessDao {

    Employee saveAgnEmployeeDetails(Employee employee);
    TPAgencyResDTO getAuthStatus(TPAgencyReqDTO requestDTO);
    void updatedAuthStatus(Long empId, Long orgId, String flag);
}
