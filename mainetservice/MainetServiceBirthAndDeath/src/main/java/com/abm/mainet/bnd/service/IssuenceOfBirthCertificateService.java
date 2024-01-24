package com.abm.mainet.bnd.service;

import java.util.LinkedHashMap;

import javax.jws.WebService;

import com.abm.mainet.bnd.dto.BirthCertificateDTO;
import com.abm.mainet.bnd.dto.BirthRegistrationDTO;
import com.abm.mainet.bnd.ui.model.BirthRegistrationCertificateModel;
import com.abm.mainet.bnd.ui.model.NacForBirthRegModel;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

@WebService
public interface IssuenceOfBirthCertificateService {

	public BirthRegistrationDTO getBirthRegisteredAppliDetail(String certNo, String regNo, String regDate,
			String applicnId, Long orgId);

	public BirthRegistrationDTO getBirthIssueRegisteredAppliDetail(String certNo, String regNo, String regDate,
			String applicnId, Long orgId);

	public Long saveIssuanceOfBirtCert(BirthRegistrationDTO birthRegDto, BirthRegistrationCertificateModel model);

	public WSResponseDTO getBndCharge(WSRequestDTO wSRequestDTO);

	public WSResponseDTO getApplicableTaxes(WSRequestDTO requestDTO);

	public String getAmount(Long noOfDays, LinkedHashMap<String, String> charges);

	String initiateWorkFlowWorksService(WorkflowTaskAction workflowActionDto, WorkflowMas workFlowMas, String url,
			String workFlowFlag);

	public String updateWorkFlowDeathService(WorkflowTaskAction workflowTaskAction);

	String updateBirthApproveStatus(BirthRegistrationDTO birthRegDTO, String status, String lastDecision);

	public void updateBirthWorkFlowStatus(Long drId, String taskNamePrevious, Long orgId, String brStatus);

	void updatNoOfcopyStatus(Long brId, Long orgId, Long bdId, Long noOfCopies);

	public void setAndSaveChallanDtoOffLine(CommonChallanDTO offline,
			BirthRegistrationCertificateModel birthRegistrationCertificateModel);

	public Long saveIssuanceOfBirtCert(BirthRegistrationDTO birthRegDto);

	TbServiceReceiptMasBean getReceiptNo(Long applnId, Long orgId);

	public BirthRegistrationDTO saveIssuanceOfBirtCertFromPortal(BirthRegistrationDTO requestDTO);

	public BirthCertificateDTO saveBirthCertificate(BirthCertificateDTO nacForBirthRegDTO, NacForBirthRegModel model);
	
	public String generateCertificate(BirthCertificateDTO birthCertificateDTO, String lastDecision, String status);
	
	public BirthCertificateDTO getNacBirthCertdetail(Long apmApplicationId, Long orgId);
	
	void updatPrintStatus(Long brId, Long orgId, Long bdId, Long noOfCopies);

	public void smsAndEmailApproval(BirthRegistrationDTO birthRegDto, String lastDecision);
	
	public BirthRegistrationDTO getBirthIssuenceApplId(Long applicationId,Long orgId);

	public Long getUpdatedRegUnitByBrId(Long brId);
}
