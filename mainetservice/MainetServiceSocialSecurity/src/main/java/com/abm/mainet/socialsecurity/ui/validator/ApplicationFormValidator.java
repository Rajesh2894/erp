/**
 * 
 */
package com.abm.mainet.socialsecurity.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.socialsecurity.ui.model.SchemeApplicationFormModel;
 

/**
 * @author satish.rathore
 *
 */
@Component
public class ApplicationFormValidator extends BaseEntityValidator<SchemeApplicationFormModel> {

	/*
	 * @Override protected void performValidations(ApplicationFormDto dto,
	 * EntityValidationContext<ApplicationFormDto> entityValidationContext) {
	 * 
	 * // entityValidationContext.rejectIfEmpty(dto.getBankNameId(), "bankNameId");
	 * 
	 * }
	 * 
	 * 
	 */
    //code added by rahul.chaubey : Vlaidating the documents 
    final ApplicationSession session = ApplicationSession.getInstance();

    @Override
    protected void performValidations(SchemeApplicationFormModel model,
            EntityValidationContext<SchemeApplicationFormModel> entityValidationContext) {
//model.getRenewalFormDto().getUploaddoc()
        if ((model.getApplicationformdto().getDocumentList() != null) && !model.getApplicationformdto().getDocumentList().isEmpty()) {
            for (final DocumentDetailsVO doc : model.getApplicationformdto().getDocumentList()) {
                if (doc.getDocumentByteCode() == null) {
                    if (doc.getDocumentByteCode() == null) {
                        entityValidationContext.addOptionConstraint(session.getMessage("social.sec.mandtory.docs"));
                        break;
                    }
                }
            }
        }
    }
}
