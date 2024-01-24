package com.abm.mainet.common.integration.dms.ui.validator;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.ui.model.DmsManagementModel;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;

@Component
public class DmsManagementValidator extends BaseEntityValidator<DmsManagementModel> {

	@Override
	protected void performValidations(DmsManagementModel dmsMetadataModel,
			EntityValidationContext<DmsManagementModel> entityValidationContext) {

		final ApplicationSession session = ApplicationSession.getInstance();

		if (dmsMetadataModel.getDeptId() == null) {
			entityValidationContext.addOptionConstraint(session.getMessage("dms.pleaseSelectDept"));
		}
		
		if(dmsMetadataModel.getDocActType().equals("BD")) {
		if (dmsMetadataModel.getNoOfDays() == null) {
			if(StringUtils.isNotBlank(dmsMetadataModel.getDocAction()) &&
					dmsMetadataModel.getDocAction().equals(MainetConstants.FlagR)) {
				entityValidationContext.addOptionConstraint(session.getMessage("dms.docManagement.validn.noOfRetrvlDays"));
			}else if(StringUtils.isNotBlank(dmsMetadataModel.getDocAction()) &&
					dmsMetadataModel.getDocAction().equals(MainetConstants.FlagD)) {
				entityValidationContext.addOptionConstraint(session.getMessage("dms.docManagement.validn.noOfDelDays"));
			}
		}
		}else if(dmsMetadataModel.getDocActType().equals("BDT") && dmsMetadataModel.getDocActionDate() == null) {
			entityValidationContext.addOptionConstraint(session.getMessage("dms.docManagement.validn.docActDate"));
		}
		
		if (StringUtils.isBlank(dmsMetadataModel.getDocAction())) {
			entityValidationContext.addOptionConstraint(session.getMessage("dms.docManagement.docAction"));
		}
		
		if (StringUtils.isNotBlank(dmsMetadataModel.getDocAction()) &&
				dmsMetadataModel.getDocAction().equals(MainetConstants.FlagR) &&
				dmsMetadataModel.getDocRtrvlDays()==null ) {
			entityValidationContext.addOptionConstraint(session.getMessage("dms.docManagement.validtn.docRtrvlDays"));
		}
	}

}
