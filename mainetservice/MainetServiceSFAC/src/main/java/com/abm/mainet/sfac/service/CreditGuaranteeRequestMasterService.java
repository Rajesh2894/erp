package com.abm.mainet.sfac.service;

import java.util.List;

import com.abm.mainet.sfac.dto.CreditGuaranteeCGFMasterDto;
import com.abm.mainet.sfac.dto.EquityGrantMasterDto;
import com.abm.mainet.sfac.dto.FPOMasterDto;

public interface CreditGuaranteeRequestMasterService {

	FPOMasterDto getFPODetails(Long masId);

	List<CreditGuaranteeCGFMasterDto> getAppliacationDetails(Long fpoId, String status);

	CreditGuaranteeCGFMasterDto saveAndUpdateApplication(CreditGuaranteeCGFMasterDto mastDto);

	CreditGuaranteeCGFMasterDto fetchCreditGauranteebyAppId(Long valueOf);

	void updateApprovalStatusAndRemark(CreditGuaranteeCGFMasterDto oldMasDto, String lastDecision, String status);

}
