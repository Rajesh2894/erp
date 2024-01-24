package com.abm.mainet.additionalservices.service;

import java.util.List;
import java.util.Map;

import com.abm.mainet.additionalservices.dto.CFCNursingHomeInfoDTO;
import com.abm.mainet.additionalservices.dto.CFCSonographyMastDto;
import com.abm.mainet.additionalservices.dto.NursingHomeSummaryDto;
import com.abm.mainet.additionalservices.ui.model.NursingHomePermisssionModel;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.TbCfcApplicationMst;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

public interface CFCNursingHomeService {

	public String saveCFCNursingHomeReg(CFCNursingHomeInfoDTO cfcNursingHomeInfoDTO,
			CFCApplicationAddressEntity addressEntity, TbCfcApplicationMst applicationMst,
			NursingHomePermisssionModel nursingHomePermisssionModel);

	public List<String> findAllApplicationNo();

	public List<NursingHomeSummaryDto> getAllByServiceIdAndAppId(Long serviceId, String refId, Long orgId);

	public CFCNursingHomeInfoDTO findByApplicationId(Long appId);

	public WSResponseDTO getApplicableTaxes(WSRequestDTO requestDTO);

	public WSResponseDTO getBndCharge(WSRequestDTO wSRequestDTO);

	Boolean checkEmployeeRole(UserSession ses);

	String updateWorkFlowService(WorkflowTaskAction workflowTaskAction);
	
	Map<Long, Double> getLoiCharges(Long applicationId, Long serviceId, Long orgId) throws CloneNotSupportedException;

	WardZoneBlockDTO getWordZoneBlockByApplicationId(Long applicationId, Long serviceId, Long org);



	public String saveCFCSonographyReg(CFCSonographyMastDto cfcSonographyMastDto, CFCApplicationAddressEntity addressEntity,
			TbCfcApplicationMst cfcApplicationMst, NursingHomePermisssionModel nursingHomePermisssionModel);

	CFCSonographyMastDto findDetByApplicationId(Long appId);

}
