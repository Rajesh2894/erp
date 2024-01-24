package com.abm.mainet.additionalservices.service;

import java.util.List;

import com.abm.mainet.additionalservices.dto.NursingHomeSummaryDto;
import com.abm.mainet.additionalservices.dto.TreeCutingTrimingSummaryDto;
import com.abm.mainet.additionalservices.dto.TreeCuttingInfoDto;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbCfcApplicationMst;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

public interface TreeCuttingPermissionService {

	String saveTreeInfo(TreeCuttingInfoDto treeCuttingInfoDto, CFCApplicationAddressEntity addressEntity,
			TbCfcApplicationMst cfcApplicationMst,TreeCutingTrimingSummaryDto cutingTrimingSummaryDto);
	
	public List<TreeCutingTrimingSummaryDto> getAllByServiceIdAndAppId(Long serviceId, String refId, Long orgId);
	
	public TreeCuttingInfoDto getTreeCuttingInfo(Long appId);

	Boolean checkEmployeeRole(UserSession ses);

	String updateWorkFlowService(WorkflowTaskAction workflowTaskAction);

	String createApplicationNumber(RequestDTO requestDto);
}
