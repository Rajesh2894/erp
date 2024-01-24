package com.abm.mainet.tradeLicense.ui.validator;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.tradeLicense.service.IRenewalLicenseApplicationService;
import com.abm.mainet.tradeLicense.ui.model.RenewalLicenseFormModel;

@Component
public class RenewalLicenseApplicationValidator extends BaseEntityValidator<RenewalLicenseFormModel> {

	@Autowired
	private IRenewalLicenseApplicationService renewalLicenseApplicationService;
	
	@Override
	protected void performValidations(RenewalLicenseFormModel model,
			EntityValidationContext<RenewalLicenseFormModel> entityValidationContext) {

		final ApplicationSession session = ApplicationSession.getInstance();
		
	if(!renewalLicenseApplicationService.isKDMCEnvPresent()) {
		Long renewalPeriod = model.getTradeMasterDetailDTO().getRenewalMasterDetailDTO().getRenewalPeriod();

		if (renewalPeriod != null) {
			LookUp lookup = CommonMasterUtility.getNonHierarchicalLookUpObject(renewalPeriod,
					model.getUserSession().getOrganisation());

			Long renewalBeforeToDatedays = Long.valueOf(lookup.getOtherField());

			long DifferenceInDays = Utility.getDaysBetweenDates(new Date(),model.getTradeMasterDetailDTO().getTrdLictoDate());

			if (DifferenceInDays <= 0) {
				DifferenceInDays = 0;
			}
			
			if (DifferenceInDays > 0) {

				if (DifferenceInDays > renewalBeforeToDatedays) {

					entityValidationContext.addOptionConstraint(
							session.getMessage("renewal.license.day.validity1") + " " + renewalBeforeToDatedays +" "+session.getMessage("renewal.license.day.validity2") );

				}

			}

		}
		
	}
	}
	
}
