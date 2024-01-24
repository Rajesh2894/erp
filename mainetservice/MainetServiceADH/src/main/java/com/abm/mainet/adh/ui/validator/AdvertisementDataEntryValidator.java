/**
 * 
 */
package com.abm.mainet.adh.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.adh.ui.model.AdvertisementDataEntryModel;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author Anwarul.Hassan
 * @since 30-Sep-2019
 */
@Component
public class AdvertisementDataEntryValidator extends BaseEntityValidator<AdvertisementDataEntryModel> {

    /*
     * Validating the Advertisement Data Entry Form (non-Javadoc)
     * @see com.abm.mainet.common.ui.validator.BaseEntityValidator#performValidations(java.lang.Object,
     * com.abm.mainet.common.ui.validator.EntityValidationContext)
     */
    @Override
    protected void performValidations(AdvertisementDataEntryModel model,
            EntityValidationContext<AdvertisementDataEntryModel> entityValidationContext) {
        ApplicationSession session = ApplicationSession.getInstance();

        if (model.getAdvertisementDto().getAppCategoryId() == 0 || model.getAdvertisementDto().getAppCategoryId() == null) {
            entityValidationContext.addOptionConstraint(session.getMessage("adh.validate.advertiser.category"));
        }
        if (model.getAdvertisementDto().getLicenseType() == 0) {
            entityValidationContext.addOptionConstraint(session.getMessage("adh.validate.licanse.type"));
        }
        if (model.getAdvertisementDto().getLocCatType() == null) {
            entityValidationContext.addOptionConstraint(session.getMessage("adh.validate.location.type"));
        }
        if (model.getAdvertisementDto().getAgencyId() == 0) {
            entityValidationContext.addOptionConstraint(session.getMessage("adh.validate.advertiser.name"));
        }
        if (model.getAdvertisementDto().getLocId() == 0) {
            entityValidationContext.addOptionConstraint(session.getMessage("adh.validate.location"));
        }
        if (model.getAdvertisementDto().getPropTypeId() == 0) {
            entityValidationContext.addOptionConstraint(session.getMessage("adh.validate.property.type"));
        }
        if (model.getAdvertisementDto().getAdhStatus() == null) {
            entityValidationContext.addOptionConstraint(session.getMessage("adh.validate.license.status"));
        }
        if ((model.getAdvertisementDto().getLicenseToDate() != null && model.getAdvertisementDto().getLicenseFromDate() != null)
                && (Utility.compareDate(model.getAdvertisementDto().getLicenseToDate(),
                        model.getAdvertisementDto().getLicenseFromDate()))) {
            entityValidationContext
                    .addOptionConstraint(session.getMessage("adh.validate.lic.to.date.from.date"));
        }

    }

}
