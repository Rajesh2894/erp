package com.abm.mainet.cfc.objection.service;

import java.util.Set;
import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.util.LookUp;


public interface IObjectionDetailService {

	
    Set<LookUp> getDepartmentList(ObjectionDetailsDto objDto);

    Set<LookUp> getLocationByDepartment(ObjectionDetailsDto dto);

	Set<LookUp> findALlActiveServiceByDeptId(ObjectionDetailsDto dto, Long orgId);

    ObjectionDetailsDto saveObjectionAndCallWorkFlow(ObjectionDetailsDto objectionDetailsDto);
    
    CommonChallanDTO getCharges(ObjectionDetailsDto objDto);

	ObjectionDetailsDto fetchRtiAppDetailByRefNo(ObjectionDetailsDto dto);

}
