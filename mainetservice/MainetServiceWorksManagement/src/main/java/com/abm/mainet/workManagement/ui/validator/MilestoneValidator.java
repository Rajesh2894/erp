/**
 * 
 */
package com.abm.mainet.workManagement.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.workManagement.dto.MileStoneDTO;

/**
 * @author Saiprasad.Vengurlekar
 *
 */

@Component
public class MilestoneValidator extends BaseEntityValidator<MileStoneDTO> {

    @Override
    protected void performValidations(MileStoneDTO dto,
            EntityValidationContext<MileStoneDTO> validationContext) {

        if (dto.getMileStoneDesc() == null) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("wms.Milestone.Description"));
        }
        if (dto.getMileStoneWeight() == null) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("wms.Milestone.Weightage"));
        }

        if (dto.getMsStartDate() == null) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("wms.Milestone.StartDate"));
        }
        if (dto.getMsEndDate() == null) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("wms.Milestone.Enddate"));
        }

    }
}
