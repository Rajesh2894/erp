package com.abm.mainet.additionalservices.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.abm.mainet.additionalservices.dto.NOCforOtherGovtDeptDto;
import com.abm.mainet.additionalservices.dto.NursingHomeSummaryDto;
import com.abm.mainet.additionalservices.ui.model.NOCForOtherGovtDeptModel;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.TbCfcApplicationMst;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

public interface NOCForOtherGovtDeptService {

	// public List<NursingHomeSummaryDto> getAllByServiceIdAndAppId(Long serviceId,
	// Long appId, Long orgId);

	public String saveApplicantData(NOCforOtherGovtDeptDto noCforOtherGovtDeptDto,
			CFCApplicationAddressEntity addressEntity, TbCfcApplicationMst cfcApplicationMst,
			NOCForOtherGovtDeptModel nocForOtherGovtDeptModel);

	List<NursingHomeSummaryDto> getAllByServiceIdAndAppId(Long serviceId, String refId, Long orgId);

	WSResponseDTO getApplicableTaxes(WSRequestDTO requestDTO);

	WSResponseDTO getBndCharge(WSRequestDTO wSRequestDTO);

	Boolean checkEmployeeRole(UserSession ses);

	String updateWorkFlowService(WorkflowTaskAction workflowTaskAction);

}
