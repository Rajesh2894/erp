package com.abm.mainet.bnd.service;


import com.abm.mainet.bnd.dto.DeathCertificateDTO;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

public interface IDeathCertificateApprovalService {

	DeathCertificateDTO getDeathCertificateDetail(Long applicationNo, Long orgId);

	public String updateWorkFlowDeathService(WorkflowTaskAction workflowTaskAction);

	void updateDeathApproveStatus(DeathCertificateDTO deathCertificateDTO, String status, String lastDecision);

	void updateDeathWorkFlowStatus(Long drRId, String wfStatus, Long orgId, String drstatus);
	
	Boolean checkEmployeeRole(UserSession ses);
	
	public void smsAndEmailApproval(DeathCertificateDTO deathCertificateDTO,String decision);
	
	public void smsAndEmailApprovalForNacDeath(DeathCertificateDTO deathCertificateDTO,String decision);
}
