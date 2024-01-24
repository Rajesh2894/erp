package com.abm.mainet.property.service;

import java.io.Serializable;
import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;

@WebService
public interface NewPropertyRegistrationService extends Serializable {

    void saveNewPropertyRegistration(ProvisionalAssesmentMstDto provAsseMstDto, Long orgId, Long empId, Long deptId,
            int langId, List<Long> finYearList);

    void callWorkFlow(ProvisionalAssesmentMstDto provisionalAssesmentMstDto, Long orgId, Long serviceId, Long deptId);

}
