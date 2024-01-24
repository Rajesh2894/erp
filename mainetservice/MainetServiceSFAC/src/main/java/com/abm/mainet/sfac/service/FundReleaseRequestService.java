package com.abm.mainet.sfac.service;

import java.util.List;

import com.abm.mainet.sfac.dto.FundReleaseRequestMasterDto;

public interface FundReleaseRequestService {

	FundReleaseRequestMasterDto getDetailById(Long frrId);

	List<FundReleaseRequestMasterDto> getFundReleaseReqDetails(Long iaId, String applicationRef, Long fy);

	FundReleaseRequestMasterDto saveAndUpdateApplication(FundReleaseRequestMasterDto mastDto, List<Long> removedIds);

	FundReleaseRequestMasterDto fetchFundReleasedReqDetailbyAppId(Long valueOf);

	void updateApprovalStatusAndRemark(FundReleaseRequestMasterDto oldMasDto, String lastDecision, String status);

}
