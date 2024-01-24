package com.abm.mainet.tradeLicense.ui.validator;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;
import com.abm.mainet.tradeLicense.ui.model.HawkerLicenseApplicationFormModel;

@Component
public class HawkerApplicationFormValidator extends BaseEntityValidator<HawkerLicenseApplicationFormModel> {
	
	@Resource
    private IWorkflowTyepResolverService iWorkflowTyepResolverService;

    @Override
    protected void performValidations(HawkerLicenseApplicationFormModel model,
            EntityValidationContext<HawkerLicenseApplicationFormModel> entityValidationContext) {
        final ApplicationSession session = ApplicationSession.getInstance();
	
	
	 /* checking manadatory documents */
    if ((model.getCheckList() != null) && !model.getCheckList().isEmpty()) {
        for (final DocumentDetailsVO doc : model.getCheckList()) {
            if (doc.getCheckkMANDATORY().equals(MainetConstants.MENU.Y) && doc.getDocumentByteCode() == null) {
                //entityValidationContext.addOptionConstraint(session.getMessage("water.fileuplaod.validtnMsg"));
                break;
            }
        }
    }
    /* end */
    
    

	/* Check workflow is configured for department */
	WorkflowMas mas=null;
	try {
		mas = iWorkflowTyepResolverService.resolveServiceWorkflowType(model.getUserSession().getOrganisation().getOrgid(),
				Long.valueOf( model.getServiceMaster().getTbDepartment().getDpDeptid()), model.getServiceMaster().getSmServiceId(),
				model.getTradeMasterDetailDTO().getTrdWard1(), model.getTradeMasterDetailDTO().getTrdWard2(),
				model.getTradeMasterDetailDTO().getTrdWard3(), model.getTradeMasterDetailDTO().getTrdWard4(),
				model.getTradeMasterDetailDTO().getTrdWard5());
	}
	catch (Exception e)
	{
		
	}
	if (mas == null || mas.equals(MainetConstants.BLANK)) {
		entityValidationContext.addOptionConstraint(session.getMessage("ml.validation.workflow"));
	}
	/* end */
    

}
    
} 
