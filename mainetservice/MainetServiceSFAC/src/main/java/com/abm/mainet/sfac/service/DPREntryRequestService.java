package com.abm.mainet.sfac.service;

import java.util.List;

import com.abm.mainet.sfac.dto.DPREntryMasterDto;

public interface DPREntryRequestService {

	DPREntryMasterDto getDetailById(Long dprId);

	List<DPREntryMasterDto> getDPRDetails(Long fpoID, Long allocationYear);

	DPREntryMasterDto saveAndUpdateApplication(DPREntryMasterDto mastDto, List<Long> removedIds);
	
	 void updateApprovalStatusAndRemark(DPREntryMasterDto oldMasDto, String lastDecision,
			String status);

	DPREntryMasterDto fetchDPREntryReqDetailbyAppId(Long valueOf);

}
