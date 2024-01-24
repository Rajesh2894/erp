package com.abm.mainet.additionalservices.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.additionalservices.ui.model.NOCForOtherGovtDeptModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;

@Component
public class CFCNOCFormValidator extends BaseEntityValidator<NOCForOtherGovtDeptModel> {

	@Override
	protected void performValidations(NOCForOtherGovtDeptModel model,
			EntityValidationContext<NOCForOtherGovtDeptModel> entityValidationContext) {

		final ApplicationSession session = ApplicationSession.getInstance();

		if ((model.getNoCforOtherGovtDeptDto().getDocumentList() != null)
				&& !model.getNoCforOtherGovtDeptDto().getDocumentList().isEmpty()) {
			for (final DocumentDetailsVO doc : model.getNoCforOtherGovtDeptDto().getDocumentList()) {
				if (doc.getCheckkMANDATORY().equals(MainetConstants.MENU.Y) && doc.getDocumentByteCode() == null) {
					entityValidationContext.addOptionConstraint(session.getMessage("water.fileuplaod.validtnMsg"));
					break;
				}
			}
		}

	}

}
