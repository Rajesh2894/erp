package com.abm.mainet.bnd.service;
import java.util.Map;

import javax.jws.WebService;

import com.abm.mainet.bnd.dto.DeathCertificateDTO;
import com.abm.mainet.bnd.dto.TbDeathregDTO;
import com.abm.mainet.bnd.ui.model.DeathRegistrationCertificateModel;
import com.abm.mainet.bnd.ui.model.NacForDeathRegModel;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

@WebService
public interface IssuenceOfDeathCertificateService {

	public  TbDeathregDTO getDeathRegisteredAppliDetail(String certNo, String regNo, String regDate, String applicnId,Long orgId);
	public  TbDeathregDTO getDeathissuRegisteredAppliDetail(String certNo, String regNo, String regDate, String applicnId,Long orgId);
	public Map<String,Object> saveIssuanceDeathCertificateDetail(TbDeathregDTO birthRegDto,DeathRegistrationCertificateModel deathModel);

	String initiateWorkFlowWorksService(WorkflowTaskAction workflowActionDto, WorkflowMas workFlowMas, String url,
			String workFlowFlag);
	
	public String updateWorkFlowDeathService(WorkflowTaskAction workflowTaskAction);

	public String updateDeathApproveStatus(TbDeathregDTO tbDeathregDTO, String status, String lastDecision);

	public void updateBirthWorkFlowStatus(Long drId, String closed, Long orgId);

	public void setAndSaveChallanDtoOffLine(CommonChallanDTO offline,DeathRegistrationCertificateModel deathRegistrationCertificateModel);

	public Map<String, Object> saveIssuanceDeathCertificateDetail(TbDeathregDTO tbDeathregDTO);
	
	TbServiceReceiptMasBean getReceiptNo(Long applnId, Long orgId);
	
	public TbDeathregDTO saveIssuanceDeathCertificateFromPortal(TbDeathregDTO birthRegDto);
	
	public DeathCertificateDTO saveDeathRegDetails(DeathCertificateDTO deathCertificateDTO, NacForDeathRegModel nacForDeathRegModel);

	public String generateCertificate(DeathCertificateDTO deathCertificateDTO, String lastDecision,
			String status);
	public DeathCertificateDTO getNacDeathCertdetail(Long applnId, Long orgId);
	public void smsAndEmailApproval(TbDeathregDTO tbDeathregDTO, String decision);
	
	public TbDeathregDTO getDeathIssuenceApplId(Long applicationId,Long orgId);
}
