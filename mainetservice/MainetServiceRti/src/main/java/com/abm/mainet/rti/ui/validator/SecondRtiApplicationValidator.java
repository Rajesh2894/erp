package com.abm.mainet.rti.ui.validator;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;
import com.abm.mainet.rti.ui.model.SecondRtiApplicationFormModel;
@Component
public class SecondRtiApplicationValidator  extends BaseEntityValidator<SecondRtiApplicationFormModel> 


{

	
	  @Resource
	    private IWorkflowTyepResolverService iWorkflowTyepResolverService;
	@Override
	protected void performValidations(SecondRtiApplicationFormModel model,
			EntityValidationContext<SecondRtiApplicationFormModel> entityValidationContext) {
		/* checking manadatory documents */
		  final ApplicationSession session = ApplicationSession.getInstance();
        if ((model.getSecondcheckList() != null) && !model.getSecondcheckList().isEmpty()) {
            for (final DocumentDetailsVO doc : model.getSecondcheckList()) {
                if (doc.getCheckkMANDATORY().equals(MainetConstants.MENU.Y) && doc.getDocumentByteCode() == null) {
                    entityValidationContext.addOptionConstraint(session.getMessage("water.fileuplaod.validtnMsg"));
                    break;
                }
            }
        }
		
        
        
        /* Check workflow is configured for department */
        WorkflowMas mas = iWorkflowTyepResolverService.resolveServiceWorkflowType(model.getOrgId(),
                Long.valueOf(model.getReqDTO().getRtiDeptId()), model.getServiceId(),
                model.getReqDTO().getApplicantDTO().getDwzid1(), model.getReqDTO().getApplicantDTO().getDwzid1(),
                model.getReqDTO().getApplicantDTO().getDwzid1(),
                model.getReqDTO().getApplicantDTO().getDwzid1(), model.getReqDTO().getApplicantDTO().getDwzid1());
        if (mas == null || mas.equals(MainetConstants.BLANK)) {
            entityValidationContext.addOptionConstraint(session.getMessage("rti.validation.workflow"));
        }
        /* end */
        
        
        
        
        
        
        
	}

}
