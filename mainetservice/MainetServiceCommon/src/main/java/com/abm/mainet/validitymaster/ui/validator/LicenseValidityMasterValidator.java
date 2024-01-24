package com.abm.mainet.validitymaster.ui.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.validitymaster.dto.LicenseValidityMasterDto;
import com.abm.mainet.validitymaster.service.ILicenseValidityMasterService;
import com.abm.mainet.validitymaster.ui.model.LicenseValidityMasterModel;

/**
 * @author cherupelli.srikanth
 * @since 23 september 2019
 */
@Component
public class LicenseValidityMasterValidator extends BaseEntityValidator<LicenseValidityMasterModel> {

	@Override
	protected void performValidations(LicenseValidityMasterModel model,
			EntityValidationContext<LicenseValidityMasterModel> entityValidationContext) {
		final ApplicationSession session = ApplicationSession.getInstance();

		if (model.getMasterDto().getDeptId() == 0) {
			entityValidationContext.addOptionConstraint(session.getMessage("license.validity.select.dept.name"));
		}

		if (model.getMasterDto().getServiceId() == 0) {
			entityValidationContext.addOptionConstraint(session.getMessage("license.validity.select.service.name"));
		}

		if (model.getMasterDto().getLicDependsOn() == 0) {
			entityValidationContext.addOptionConstraint(session.getMessage("license.validity.select.dependsOn"));
		}
		if (model.getMasterDto().getLicType() == 0) {
			entityValidationContext.addOptionConstraint(session.getMessage("license.validity.select.license.type"));
		}
		if (model.getMasterDto().getUnit() == 0) {
			entityValidationContext.addOptionConstraint(session.getMessage("license.validity.select.license.unit"));
		}
		if (StringUtils.isBlank(model.getMasterDto().getLicTenure())) {
			entityValidationContext.addOptionConstraint(session.getMessage("license.validity.enter.license.tenure"));
		}
		// Code added for SKDCL ENV to showing License Category and sub-category in in
		// License ValidityMaster Form in SKDCL User Story #107219 and  #143902 for tscl project 
		if ((model.getEnvSpec()!=null && model.getEnvSpec().equals(MainetConstants.FlagY))
				&& model.getDeptShortName()!=null && model.getDeptShortName().equals(MainetConstants.TradeLicense.MARKET_LICENSE)) {
			if ((model.getMasterDto().getDeptId() != 0) && (model.getMasterDto().getServiceId() != 0)
					&& StringUtils.equals(model.getSaveMode(), MainetConstants.FlagA)) {
				LicenseValidityMasterDto licValMasterDto = ApplicationContextProvider.getApplicationContext()
						.getBean(ILicenseValidityMasterService.class).getLicValDataByCategoryAndSubCategory(
								model.getMasterDto().getOrgId(), model.getMasterDto().getDeptId(),
								model.getMasterDto().getServiceId(), model.getMasterDto().getLicType(),
								model.getMasterDto().getTriCod1(), model.getMasterDto().getTriCod2());

				if (licValMasterDto != null) {
					entityValidationContext.addOptionConstraint(session.getMessage("license.validity.already.exists"));
				}
			} else if ((model.getMasterDto().getDeptId() != 0) && (model.getMasterDto().getServiceId() != 0)
					&& StringUtils.equals(model.getSaveMode(), MainetConstants.FlagE)) {
				LicenseValidityMasterDto licenseMasterByLicId = ApplicationContextProvider.getApplicationContext()
						.getBean(ILicenseValidityMasterService.class).searchLicenseValidityByLicIdAndOrgId(
								model.getMasterDto().getLicId(), model.getMasterDto().getOrgId());
				LicenseValidityMasterDto licValMasterDto = ApplicationContextProvider.getApplicationContext()
						.getBean(ILicenseValidityMasterService.class).getLicValDataByCategoryAndSubCategory(
								model.getMasterDto().getOrgId(), model.getMasterDto().getDeptId(),
								model.getMasterDto().getServiceId(), model.getMasterDto().getLicType(),
								model.getMasterDto().getTriCod1(), model.getMasterDto().getTriCod2());

				if (licValMasterDto != null) {
					if (!licenseMasterByLicId.getLicType().equals(model.getMasterDto().getLicType())) {
						entityValidationContext
								.addOptionConstraint(session.getMessage("license.validity.already.exists"));
					}
				}

			}
		}
		// end
		else if ((model.getMasterDto().getDeptId() != 0) && (model.getMasterDto().getServiceId() != 0)
				&& StringUtils.equals(model.getSaveMode(), MainetConstants.FlagA)) {
			LicenseValidityMasterDto licValMasterDto = ApplicationContextProvider.getApplicationContext()
					.getBean(ILicenseValidityMasterService.class).getLicValDataByDeptIdAndServiceId(
							model.getMasterDto().getOrgId(), model.getMasterDto().getDeptId(),
							model.getMasterDto().getServiceId(), model.getMasterDto().getLicType());

			if (licValMasterDto != null) {
				entityValidationContext.addOptionConstraint(session.getMessage("license.validity.already.exists"));
			}
		} else if ((model.getMasterDto().getDeptId() != 0) && (model.getMasterDto().getServiceId() != 0)
				&& StringUtils.equals(model.getSaveMode(), MainetConstants.FlagE)) {
			LicenseValidityMasterDto licenseMasterByLicId = ApplicationContextProvider.getApplicationContext()
					.getBean(ILicenseValidityMasterService.class).searchLicenseValidityByLicIdAndOrgId(
							model.getMasterDto().getLicId(), model.getMasterDto().getOrgId());
			LicenseValidityMasterDto licValMasterDto = ApplicationContextProvider.getApplicationContext()
					.getBean(ILicenseValidityMasterService.class).getLicValDataByDeptIdAndServiceId(
							model.getMasterDto().getOrgId(), model.getMasterDto().getDeptId(),
							model.getMasterDto().getServiceId(), model.getMasterDto().getLicType());

			if (licValMasterDto != null) {
				if (!licenseMasterByLicId.getLicType().equals(model.getMasterDto().getLicType())) {
					entityValidationContext.addOptionConstraint(session.getMessage("license.validity.already.exists"));
				}
			}

		}

	}

}
