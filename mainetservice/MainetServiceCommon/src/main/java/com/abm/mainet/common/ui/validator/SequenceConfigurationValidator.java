package com.abm.mainet.common.ui.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.service.ISequenceConfigMasterService;
import com.abm.mainet.common.ui.model.SequenceConfigurationModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author sadik.shaikh
 *
 */
@Component
public class SequenceConfigurationValidator extends BaseEntityValidator<SequenceConfigurationModel> {

	@Autowired
	ISequenceConfigMasterService masterService;

	/*
	 * This is method is responsible to validate whether the entered data is already
	 * exist in database or not. If exist then show an error message.
	 */

	@Override
	protected void performValidations(SequenceConfigurationModel model,
			EntityValidationContext<SequenceConfigurationModel> entityValidationContext) {
		final ApplicationSession session = ApplicationSession.getInstance();

		if (model.getConfigMasterDTO().getSeqConfigId() == null) {
			Long seqName = model.getConfigMasterDTO().getSeqName();
			Long deptId = model.getConfigMasterDTO().getDeptId();
			String status = model.getConfigMasterDTO().getSeqStatus();
			Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			status = status.isEmpty() || status.equals("0") ? null : status;

			boolean flag = masterService.checkSequenceByPattern(orgId, deptId, seqName);
			if (flag == false) {
				entityValidationContext.addOptionConstraint(session.getMessage("Entered data is already exist"));
			}
		}

	}

}
