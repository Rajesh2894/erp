package com.abm.mainet.sfac.service;

import java.util.List;

import com.abm.mainet.sfac.dto.BlockAllocationDto;
import com.abm.mainet.sfac.dto.EquityGrantMasterDto;
import com.abm.mainet.sfac.dto.FPOMasterDto;
import com.abm.mainet.sfac.ui.model.EquityGrantApprovalModel;

public interface EquityGrantRequestMasterService {

	FPOMasterDto getFPODetails(Long masId);

	EquityGrantMasterDto saveAndUpdateApplication(EquityGrantMasterDto mastDto);

	List<EquityGrantMasterDto> getAppliacationDetails(Long fpoId,String status);

	EquityGrantMasterDto getDetailById(Long egId);

	EquityGrantMasterDto updateApplication(EquityGrantMasterDto mastDto);

	void updateApprovalStatusAndRemark(EquityGrantMasterDto oldMasDto, String status, String lastDecision);

	EquityGrantMasterDto fetchEquityDetailsbyAppId(Long valueOf);

}
