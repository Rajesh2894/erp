package com.abm.mainet.agency.authentication.ui.validation;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.EmployeeDTO;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;

/**
 *
 * @author Rajdeep.Sinha
 *
 */

@Component
public class AgencyAuthorizationFormValidator extends BaseEntityValidator<EmployeeDTO> {

    @Override
    protected void performValidations(final EmployeeDTO entity,
            final EntityValidationContext<EmployeeDTO> entityValidationContext) {
        if ((entity.getAuthStatus() != null) && entity.getAuthStatus().equals(MainetConstants.Common_Constant.ACTIVE_FLAG)) {

            if (entity.getGmid() == 0) {
                entityValidationContext.addOptionConstraint("Please Assign Group..");
            }
        }
    }
}
