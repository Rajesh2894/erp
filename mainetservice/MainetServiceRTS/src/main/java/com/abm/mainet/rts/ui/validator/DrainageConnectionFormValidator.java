package com.abm.mainet.rts.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.rts.ui.model.DrainageConnectionModel;

@Component
public class DrainageConnectionFormValidator extends BaseEntityValidator<DrainageConnectionModel> {

	  final ApplicationSession session = ApplicationSession.getInstance();
	@Override
	protected void performValidations(DrainageConnectionModel model,
			EntityValidationContext<DrainageConnectionModel> entityValidationContext) 
	{	
		if(model.getCheckListApplFlag().equalsIgnoreCase("Y"))
		{
		   if ((model.getDrainageConnectionDto().getDocumentList() != null) && !model.getDrainageConnectionDto().getDocumentList().isEmpty()) {
	            for (final DocumentDetailsVO doc : model.getDrainageConnectionDto().getDocumentList()) {
	                if (doc.getDocumentByteCode() == null) {
	                    if (doc.getDocumentByteCode() == null) {
	                        entityValidationContext.addOptionConstraint(session.getMessage("Please Upload Mandatory Documents."));
	                        break;
	                    }
	                }
	            }
	        }
		}
	}

}
