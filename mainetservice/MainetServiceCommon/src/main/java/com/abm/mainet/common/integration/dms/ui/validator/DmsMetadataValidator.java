package com.abm.mainet.common.integration.dms.ui.validator;

import java.util.List;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.ui.model.DmsMetadataModel;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

@Component
public class DmsMetadataValidator extends BaseEntityValidator<DmsMetadataModel> {

	@Override
	protected void performValidations(DmsMetadataModel dmsMetadataModel,
			EntityValidationContext<DmsMetadataModel> entityValidationContext) {

		final ApplicationSession session = ApplicationSession.getInstance();
		List<LookUp> lookupList = dmsMetadataModel.getLookUpList();

		if (dmsMetadataModel.getDeptId() == null) {
			entityValidationContext.addOptionConstraint(session.getMessage("dms.pleaseSelectDept"));
		}

		lookupList.forEach(data -> {
			LookUp lookup = CommonMasterUtility.getHierarchicalLookUp(data.getLookUpId(),
					UserSession.getCurrent().getOrganisation().getOrgid());

			/*if (lookup.getOtherField().equals(MainetConstants.FlagM)
					&& (data.getOtherField().equals(MainetConstants.BLANK) || data.getOtherField() == null)) {
				entityValidationContext.addOptionConstraint(
						session.getMessage("dms.pleaseSelect") + MainetConstants.WHITE_SPACE + lookup.getLookUpDesc());
			}*/
			
			if (MainetConstants.FlagM.equals(lookup.getOtherField())
					&& (data.getOtherField() == null || MainetConstants.BLANK.equals(data.getOtherField()))) {
				entityValidationContext.addOptionConstraint(
						session.getMessage("dms.pleaseSelect") + MainetConstants.WHITE_SPACE + lookup.getLookUpDesc());
			}

		});

		if (dmsMetadataModel.getAttachments() == null || dmsMetadataModel.getAttachments().isEmpty()) {
			entityValidationContext.addOptionConstraint(session.getMessage("dms.uploadMand"));
		}

	}

}
