package com.abm.mainet.adh.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.adh.dto.InspectionEntryDto;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;

/**
 * @author Anwarul.Hassan
 * @since 13-Nov-2019
 */
@Component
public class InspectionEntryValidator extends BaseEntityValidator<InspectionEntryDto> {

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.common.ui.validator.BaseEntityValidator#performValidations(java.lang.Object,
     * com.abm.mainet.common.ui.validator.EntityValidationContext)
     */
    @Override
    protected void performValidations(InspectionEntryDto entryDto,
            EntityValidationContext<InspectionEntryDto> entityValidationContext) {
        final ApplicationSession session = ApplicationSession.getInstance();

        if (entryDto.getAdhId() == null) {
            entityValidationContext.addOptionConstraint(session.getMessage("adh.validate.license.number"));
        }
        if (entryDto.getInesDate() == null) {
            entityValidationContext.addOptionConstraint(session.getMessage("adh.validate.inspection.date"));
        }
        if (entryDto.getInesEmpId() == null) {
            entityValidationContext.addOptionConstraint(session.getMessage("adh.validate.inspector.name"));
        }
        // for (InspectionEntryDetDto detDto : entryDto.getInspectionEntryDetDto()) {
        // if (detDto.getRemarkId() == 0 || detDto.getRemarkId() == null) {
        // entityValidationContext.addOptionConstraint(session.getMessage("Please select terms/conditions"));
        // }
        // if (detDto.getRemarkStatusId() == 0 || detDto.getRemarkStatusId() == null) {
        // entityValidationContext.addOptionConstraint(session.getMessage("Please select status"));
        // }
        // if (detDto.getObservation() == null) {
        // entityValidationContext.addOptionConstraint(session.getMessage("Please enter observation"));
        // }
        // }
    }

}
