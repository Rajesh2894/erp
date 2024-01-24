package com.abm.mainet.buildingplan.service;

import java.util.Date;

import com.abm.mainet.buildingplan.dto.ProfessionalRegistrationDTO;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

public interface ProfessionalRegistrationService {

	public void saveRegForm(ProfessionalRegistrationDTO professionalRegDTO);

	public ProfessionalRegistrationDTO getDetailByAppIdAndOrgId(Long applicationId, Long orgid);

	public boolean executeApprovalWorkflowAction(WorkflowTaskAction taskAction, String smShortdesc, Long smServiceId,
			String smShortdesc2);

	public void saveOBPASData(ProfessionalRegistrationDTO professionalRegDTO);
}
