package com.abm.mainet.property.service;

import java.util.List;

import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;

public interface AmalgamationService {

    void saveAmulgamatedProperty(
            ProvisionalAssesmentMstDto provisionalAssesmentMstDto, List<Long> finYearList, Long deptId,
            List<ProvisionalAssesmentMstDto> childList);

    List<TbBillMas> fetchNotPaidBillsByPropNo(List<String> assNo, long orgId);

}
