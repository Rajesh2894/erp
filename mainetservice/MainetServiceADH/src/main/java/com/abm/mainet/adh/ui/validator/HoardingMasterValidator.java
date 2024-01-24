/**
 * 
 */
package com.abm.mainet.adh.ui.validator;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.abm.mainet.adh.ui.model.HoardingMasterModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author Anwarul.Hassan
 *
 * @since 16-Aug-2019
 */
@Component
public class HoardingMasterValidator extends BaseEntityValidator<HoardingMasterModel> {

    /*
     * Validating the Hoarding Master Entry Form (non-Javadoc)
     * 
     * @see
     * com.abm.mainet.common.ui.validator.BaseEntityValidator#performValidations(
     * java.lang.Object, com.abm.mainet.common.ui.validator.EntityValidationContext)
     */
    @Override
    protected void performValidations(HoardingMasterModel model,
	    EntityValidationContext<HoardingMasterModel> entityValidationContext) {
	final ApplicationSession session = ApplicationSession.getInstance();

	performGroupValidation("hoardingMasterDto.hoardingTypeId");

	if (model.getHoardingMasterDto().getHoardingRegDate() == null) {
	    entityValidationContext.addOptionConstraint(session.getMessage("hoarding.master.validate.regdate"));
	}
	if (StringUtils.isBlank(model.getHoardingMasterDto().getHoardingDescription())) {
	    entityValidationContext.addOptionConstraint(session.getMessage("hoarding.master.validate.description"));
	}

	if (model.getHoardingMasterDto().getHoardingLength() == null
		|| model.getHoardingMasterDto().getHoardingLength().compareTo(BigDecimal.ZERO) == 0) {
	    entityValidationContext.addOptionConstraint(session.getMessage("hoarding.master.validate.hrdlength"));
	}
	if (model.getHoardingMasterDto().getHoardingHeight() == null
		|| model.getHoardingMasterDto().getHoardingHeight().compareTo(BigDecimal.ZERO) == 0) {
	    entityValidationContext.addOptionConstraint(session.getMessage("hoarding.master.validate.height"));
	}
	/*
	 * if (model.getHoardingMasterDto().getHoardingArea() == null ||
	 * model.getHoardingMasterDto().getHoardingArea() == 0) {
	 * entityValidationContext.addOptionConstraint(session.getMessage(
	 * "hoarding.master.validate.hoardingArea")); }
	 */
	if ((model.getHoardingMasterDto().getLocationId() == null || model.getHoardingMasterDto().getLocationId() == 0) && !(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL))) {
	    entityValidationContext.addOptionConstraint(session.getMessage("hoarding.master.validate.locationId"));
	}
	if (model.getHoardingMasterDto().getHoardingStatus() == 0
		|| model.getHoardingMasterDto().getHoardingStatus() == null) {
	    entityValidationContext.addOptionConstraint(session.getMessage("hoarding.master.validate.status"));
	}
	if (model.getHoardingMasterDto().getHoardingPropTypeId() == null
		|| model.getHoardingMasterDto().getHoardingPropTypeId() == 0) {
	    entityValidationContext.addOptionConstraint(session.getMessage("hoarding.validate.land.ownership"));
	}
	/*
	 * if (model.getHoardingMasterDto().getHoardingZone1() == null ||
	 * model.getHoardingMasterDto().getHoardingZone1() == 0) {
	 * entityValidationContext.addOptionConstraint(session.getMessage(
	 * "hoarding.master.validate.hoardingZone")); }
	 */
	/*if (model.getHoardingMasterDto().getDisplayTypeId() == null
		|| model.getHoardingMasterDto().getDisplayTypeId() == 0) {
	    entityValidationContext.addOptionConstraint(session.getMessage("hoarding.master.validate.displayTypeId"));
	}*/

    }

}
