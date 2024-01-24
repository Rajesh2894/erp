package com.abm.mainet.rnl.ui.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.rnl.ui.model.PropFreezeModel;

@Component
public class PropFreezeFormValidator extends BaseEntityValidator<PropFreezeModel> {

    ApplicationSession session = ApplicationSession.getInstance();

    @Override
    protected void performValidations(final PropFreezeModel model,
            final EntityValidationContext<PropFreezeModel> entityValidationContext) {

        if ((model.getLocId() == null) || (model.getLocId() == 0L)) {
            entityValidationContext.addOptionConstraint(session.getMessage("rnl.propfreeze.location"));
        }

        if ((model.getEstateId() == null) || (model.getEstateId() == 0L)) {
            entityValidationContext.addOptionConstraint(session.getMessage("rnl.estate.name"));
        }

        if ((model.getEstateBookingDTO().getPropId() == null) || (model.getEstateBookingDTO().getPropId() == 0L)) {
            entityValidationContext.addOptionConstraint(session.getMessage("rnl.estate.prop.name"));
        }
        if ((model.getEstateBookingDTO().getPropId() != null) && (model.getEstateBookingDTO().getPropId() != 0L)) {
            if (model.getEstateBookingDTO().getFromDate() == null) {
                entityValidationContext.addOptionConstraint(session.getMessage("rnl.estate.from.date"));
            }
            if (model.getEstateBookingDTO().getToDate() == null) {
                entityValidationContext.addOptionConstraint(session.getMessage("rnl.estate.to.date"));
            }
        }
        if ((model.getEstateBookingDTO().getShiftId() == null) || (model.getEstateBookingDTO().getShiftId() == 0L)) {
            entityValidationContext.addOptionConstraint(session.getMessage("rnl.estate.shift.name"));
        }
        if (StringUtils.isBlank(model.getEstateBookingDTO().getReasonOfFreezing())) {
            entityValidationContext.addOptionConstraint(session.getMessage("rnl.prop.freez.reason.of.freezing"));

        }
    }

}
