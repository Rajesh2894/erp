package com.abm.mainet.rnl.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.rnl.dto.EstatePropMaster;
import com.abm.mainet.rnl.ui.model.EstatePropMasModel;

/**
 * @author ritesh.patil
 *
 */
@Component
public class PropMasterFormValidator extends BaseEntityValidator<EstatePropMasModel> {

	@Override
	protected void performValidations(final EstatePropMasModel model,
			final EntityValidationContext<EstatePropMasModel> entityValidationContext) {

		final ApplicationSession session = ApplicationSession.getInstance();
		final EstatePropMaster estatePropMaster = model.getEstatePropMaster();

		if ((estatePropMaster.getEstateId() == null) || (estatePropMaster.getEstateId() == 0)) {
			entityValidationContext.addOptionConstraint(session.getMessage("rl.property.master.estate.validate.msg"));
		}

		if (estatePropMaster.getName() == null) {
			entityValidationContext.addOptionConstraint(session.getMessage("rl.property.master.prop.validate.msg"));
		}

		if ((estatePropMaster.getOccupancy() == null) || (estatePropMaster.getOccupancy() == 0)) {
			entityValidationContext.addOptionConstraint(session.getMessage("rl.property.master.occup.validate.msg"));
		}

		if ((estatePropMaster.getUsage() == null) || (estatePropMaster.getUsage() == 0)) {
			entityValidationContext.addOptionConstraint(session.getMessage("rl.property.master.usage.validate.msg"));
		}

		if (!(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)|| Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL))) {

			if (model.isFloorCheck()) {
				if ((estatePropMaster.getFloor() == null) || (estatePropMaster.getFloor() == 0)) {
					entityValidationContext
							.addOptionConstraint(session.getMessage("rl.property.master.floor.validate.msg"));
				}
			}
			if ((estatePropMaster.getRoadType() == null) || (estatePropMaster.getRoadType() == 0)) {
				entityValidationContext.addOptionConstraint(session.getMessage("rl.property.master.road.validate.msg"));
			}

		}
		

		// check occupancyType
		LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(estatePropMaster.getOccupancy(),
				estatePropMaster.getOrgId(), PrefixConstants.RnLPrefix.OCCUPANCY);
		if (lookUp.getLookUpCode().equalsIgnoreCase(PrefixConstants.CPD_VALUE_RENT)) {
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL) || Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {

			} else {
				if ((estatePropMaster.getPropLatitude() == null) || estatePropMaster.getPropLatitude().isEmpty()) {
					entityValidationContext
							.addOptionConstraint(session.getMessage("rl.property.master.latitude.validate.msg"));
				}

				if ((estatePropMaster.getPropLongitude() == null) || estatePropMaster.getPropLongitude().isEmpty()) {
					entityValidationContext
							.addOptionConstraint(session.getMessage("rl.property.master.longitude.validate.msg"));
				}

			}

		}

	}

}
