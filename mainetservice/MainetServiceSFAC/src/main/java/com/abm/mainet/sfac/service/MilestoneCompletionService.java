package com.abm.mainet.sfac.service;

import java.util.List;

import com.abm.mainet.sfac.dto.MilestoneCompletionMasterDto;
import com.abm.mainet.sfac.dto.MilestoneMasterDto;

public interface MilestoneCompletionService {

	List<MilestoneMasterDto> getMilestoneDetails(Long cbboId);

	MilestoneCompletionMasterDto findMilestoneDetails(Long msId, Long cbboId);

	MilestoneCompletionMasterDto saveAndUpdateApplication(MilestoneCompletionMasterDto mastDto);

	void updateApprovalStatusAndRemark(MilestoneCompletionMasterDto oldMasDto, String lastDecision, String status);

	MilestoneCompletionMasterDto fetchMilestoneCompletionbyAppId(Long valueOf);

	MilestoneCompletionMasterDto getDetailById(Long mscId);

	List<MilestoneCompletionMasterDto> getMilestoneDetails(Long iaId, Long cbboId, String status);

	List<MilestoneMasterDto> getMilestoneDetailsByID(Long msId);

}
