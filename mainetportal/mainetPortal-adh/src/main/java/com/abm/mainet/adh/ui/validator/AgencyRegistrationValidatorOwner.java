/**
 * 
 */
package com.abm.mainet.adh.ui.validator;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.Utility;

/**
 * @author Sharvan kumar Mandal
 * @since 11 June 2021
 */
@Component
public class AgencyRegistrationValidatorOwner extends BaseEntityValidator<AdvertiserMasterDto> {

	@Override
	protected void performValidations(AdvertiserMasterDto model,
			EntityValidationContext<AdvertiserMasterDto> entityValidationContext) {

		final ApplicationSession session = ApplicationSession.getInstance();

		if (StringUtils.isBlank(model.getAgencyName())) {
			entityValidationContext
					.addOptionConstraint(session.getMessage("advertiser.master.validate.advertiser.name"));
		}
		if (StringUtils.isBlank(model.getAgencyAdd())) {
			entityValidationContext
					.addOptionConstraint(session.getMessage("advertiser.master.validate.advertiser.address"));
		}

		if (model.getAgencyLicFromDate() == null) {
			entityValidationContext
					.addOptionConstraint(session.getMessage("advertiser.master.validate.advertiser.licfromdate"));
		}
		if (model.getAgencyLicToDate() == null) {
			entityValidationContext
					.addOptionConstraint(session.getMessage("advertiser.master.validate.advertiser.lictodate"));
		}

		if ((model.getAgencyLicToDate() != null && model.getAgencyLicFromDate() != null)
				&& (Utility.compareDate(model.getAgencyLicToDate(), model.getAgencyLicFromDate()))) {
			entityValidationContext
					.addOptionConstraint(session.getMessage("advertiser.master.validate.lic.to.date.from.date"));
		}

		if (StringUtils.isNotBlank(model.getApplicationTypeFlag())
				&& StringUtils.equalsIgnoreCase(model.getApplicationTypeFlag(), MainetConstants.FlagN)) {
			if (model.getAgencyLicFromDate() == null) {
				entityValidationContext
						.addOptionConstraint(session.getMessage("advertiser.master.validate.advertiser.licfromdate"));
			} else {
				if (!Utility.comapreDates(model.getAgencyLicFromDate(), new Date())) {
					entityValidationContext
							.addOptionConstraint(session.getMessage("License from date should be current date"));
				}
			}
		}

	}



}
