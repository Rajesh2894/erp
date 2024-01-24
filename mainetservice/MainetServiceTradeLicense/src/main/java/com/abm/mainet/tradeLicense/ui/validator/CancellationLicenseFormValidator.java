package com.abm.mainet.tradeLicense.ui.validator;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;
import com.abm.mainet.tradeLicense.ui.model.CancellationLicenseFormModel;

@Component
public class CancellationLicenseFormValidator extends BaseEntityValidator<CancellationLicenseFormModel>{

	@Resource
    private IWorkflowTyepResolverService iWorkflowTyepResolverService;
	
	 @Autowired
	 private ServiceMasterService serviceMasterService;
	
	@Override
	protected void performValidations(CancellationLicenseFormModel model,
			EntityValidationContext<CancellationLicenseFormModel> entityValidationContext) {
		 final ApplicationSession session = ApplicationSession.getInstance();
	

		/* Check workflow is configured for department */
		WorkflowMas mas=null;
		try {
			mas = iWorkflowTyepResolverService.resolveServiceWorkflowType(model.getUserSession().getOrganisation().getOrgid(),
					Long.valueOf(model.getServiceMaster().getTbDepartment().getDpDeptid()), model.getServiceMaster().getSmServiceId(),
					model.getTradeDetailDTO().getTrdWard1(), model.getTradeDetailDTO().getTrdEWard2(),
					model.getTradeDetailDTO().getTrdWard3(), model.getTradeDetailDTO().getTrdEWard4(),
					model.getTradeDetailDTO().getTrdEWard5());
		}
		catch (Exception e)
		{
			
		}
		if (mas == null || mas.equals(MainetConstants.BLANK)) {
			entityValidationContext.addOptionConstraint(session.getMessage("ml.validation.workflow"));
		}
		/* end */
		//Defect #105205
		String processName = serviceMasterService.getProcessName(model.getServiceMaster().getSmServiceId(), model.getUserSession().getOrganisation().getOrgid());
	    if(!processName.equalsIgnoreCase(MainetConstants.LandEstate.PROCESS_SCRUITNY)) {
	    	entityValidationContext.addOptionConstraint(session.getMessage("ml.validation.workflow.scrutiny"));
	    }

	}
	    
		
		
	

}
