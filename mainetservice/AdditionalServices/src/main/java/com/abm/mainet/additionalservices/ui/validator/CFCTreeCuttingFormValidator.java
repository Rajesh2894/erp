package com.abm.mainet.additionalservices.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.additionalservices.ui.model.TreeCuttingTrimmingPermissionModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;

@Component
public class CFCTreeCuttingFormValidator extends BaseEntityValidator<TreeCuttingTrimmingPermissionModel> {

	@Override
	protected void performValidations(TreeCuttingTrimmingPermissionModel model,
			EntityValidationContext<TreeCuttingTrimmingPermissionModel> entityValidationContext) {
		final ApplicationSession session = ApplicationSession.getInstance();

		if ((model.getTreeCutingTrimingSummaryDto().getDocumentList() != null)
				&& !model.getTreeCutingTrimingSummaryDto().getDocumentList().isEmpty()) {
			for (final DocumentDetailsVO doc : model.getTreeCutingTrimingSummaryDto().getDocumentList()) {
				if (doc.getCheckkMANDATORY().equals(MainetConstants.MENU.Y) && doc.getDocumentByteCode() == null) {
					entityValidationContext.addOptionConstraint(session.getMessage("water.fileuplaod.validtnMsg"));
					break;
				}
			}
		}

	}

}
