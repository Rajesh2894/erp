package com.abm.mainet.socialsecurity.service;

import java.util.List;

import com.abm.mainet.common.dto.BankMasterDTO;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.socialsecurity.ui.dto.ApplicationFormDto;
import com.abm.mainet.socialsecurity.ui.dto.CriteriaDto;

public interface ISchemeApplicationFormService {

	ApplicationFormDto saveApplicationDetails(ApplicationFormDto applicationformdto);

	List<LookUp> FindSecondLevelPrefixByFirstLevelPxCode(Long orgId, String parentPx, Long parentpxId, Long level);

	List<BankMasterDTO> getBankList(Long orgId);

	List<Object[]> findAllActiveServicesWhichIsNotActual(Long orgId, Long psmDpDeptid, long lookUpId,
			String notActualFlag);

	 Long getCriteriaGridId (CriteriaDto dto);
}
