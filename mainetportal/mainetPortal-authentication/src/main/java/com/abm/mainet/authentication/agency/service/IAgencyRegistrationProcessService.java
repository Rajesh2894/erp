package com.abm.mainet.authentication.agency.service;

import com.abm.mainet.authentication.agency.dto.AgencyEmployeeReqDTO;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;

public interface IAgencyRegistrationProcessService {

    public Employee saveCitizenRegistrationForm(AgencyEmployeeReqDTO newEmployee, Organisation organisation,
            String newOTPPassword, String citizen_LOCATION, Designation citizen_DESIGNATION, Long employee_TYPE, Long userId);

    Department getCitizenDepartment(Organisation organisation);
}
