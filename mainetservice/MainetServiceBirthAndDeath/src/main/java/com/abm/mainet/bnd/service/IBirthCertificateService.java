package com.abm.mainet.bnd.service;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.abm.mainet.bnd.dto.BirthCertificateDTO;
import com.abm.mainet.bnd.dto.ViewBirthCertiDetailRequestDto;
import com.abm.mainet.bnd.ui.model.BirthCertificateModel;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

@WebService
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces(MediaType.APPLICATION_JSON)
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
	
	public void smsAndEmailApprovalNacBirth(BirthCertificateDTO birthCertificateDto,String decision);
	
	public BirthCertificateDTO getBirthRegisteredAppliDetails(BirthCertificateDTO birthCertificateDto);
	
	/*
	 * public List<RequestDTO> searchData(Long applicationId, Long serviceId, Long
	 * orgId);
	 */
	
	public List<Long> getApplicationNo(Long orgId, Long serviceId);

	void updateBirthWorkFlowStatus(Long brId, String taskNamePrevious, Long orgId, String brStatus);

	public void smsAndEmailApproval(BirthCertificateDTO birthCertificateDto,String decision);

	List<BirthCertificateDTO> viewBirthRegisteredAppliDetails(ViewBirthCertiDetailRequestDto viewRequestDto);

	
}
