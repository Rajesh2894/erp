package com.abm.mainet.rts.service;

import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.rts.dto.BirthCertificateDTO;
import com.abm.mainet.rts.ui.model.BirthCertificateModel;

public interface IBirthCertificateService {

	public BirthCertificateDTO saveBirthCertificate(BirthCertificateDTO birthCertificateDto,
			BirthCertificateModel model);

	public BirthCertificateDTO saveBirthCertificates(BirthCertificateDTO birthCertificateDto);

	public BirthCertificateDTO getBirthRegisteredAppliDetail(Long applicationId, Long orgId);

	public void saveBirthRegDet(BirthCertificateDTO birthRegDto);

	public void updateBirthApproveStatusBR(BirthCertificateDTO birthRegDTO, String status, String lastDecision);

	void updateBirthWorkFlowStatusBR(Long brId, String taskNamePrevious, Long orgId, String brStatus);

	public String updateWorkFlowDeathService(WorkflowTaskAction workflowTaskAction);

	public WSResponseDTO getApplicableTaxes(WSRequestDTO requestDTO);

	public WSResponseDTO getBndCharge(WSRequestDTO wSRequestDTO);

	Boolean checkEmployeeRole(UserSession ses);
	
	public void smsAndEmailApproval(BirthCertificateDTO birthCertificateDto,String decision);
	
	public BirthCertificateDTO getBirthRegisteredAppliDetails(BirthCertificateDTO birthCertificateDto);

}
