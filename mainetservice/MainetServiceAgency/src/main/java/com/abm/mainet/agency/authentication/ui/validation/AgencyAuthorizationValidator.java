package com.abm.mainet.agency.authentication.ui.validation;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.EmployeeDTO;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;

/**
 * @author vinay.jangir
 *
 */
@Component
public class AgencyAuthorizationValidator extends BaseEntityValidator<EmployeeDTO> {

    @Override
    protected void performValidations(final EmployeeDTO entity,
            final EntityValidationContext<EmployeeDTO> entityValidationContext) {
        entityValidationContext.rejectIfNotSelected(entity.getEmplType(),MainetConstants.AGENCY.AGENCY_TYPE);
    }

}
