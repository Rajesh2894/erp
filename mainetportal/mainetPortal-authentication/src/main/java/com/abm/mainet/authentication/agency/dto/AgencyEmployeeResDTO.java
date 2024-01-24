package com.abm.mainet.authentication.agency.dto;

import com.abm.mainet.common.dto.ResponseDTO;

public class AgencyEmployeeResDTO extends ResponseDTO {

    private static final long serialVersionUID = -3547530526697951596L;

    private Long empId;

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(final Long empId) {
        this.empId = empId;
    }
}
