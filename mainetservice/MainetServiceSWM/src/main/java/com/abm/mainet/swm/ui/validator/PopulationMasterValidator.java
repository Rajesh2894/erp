/**
 * 
 */
package com.abm.mainet.swm.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.swm.dto.PopulationMasterDTO;

/**
 * @author Lalit.Prusti
 *
 */
@Component
public class PopulationMasterValidator extends BaseEntityValidator<PopulationMasterDTO> {
    @Override
    protected void performValidations(PopulationMasterDTO entity,
            EntityValidationContext<PopulationMasterDTO> entityValidationContext) {
        performGroupValidation("codDwzid");
        entityValidationContext.rejectIfNull(entity.getPopEst(), "popEst");
        entityValidationContext.rejectIfNotSelected(entity.getPopYear(), "popYear");
    }
}
