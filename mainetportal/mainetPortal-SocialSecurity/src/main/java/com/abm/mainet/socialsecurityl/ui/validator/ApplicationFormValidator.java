/**
 * 
 */
package com.abm.mainet.socialsecurityl.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.socialsecurity.ui.dto.ApplicationFormDto;

/**
 * @author satish.rathore
 *
 */
@Component
public class ApplicationFormValidator extends BaseEntityValidator<ApplicationFormDto> {

    @Override
    protected void performValidations(ApplicationFormDto dto,
            EntityValidationContext<ApplicationFormDto> entityValidationContext) {

        // entityValidationContext.rejectIfEmpty(dto.getBankNameId(), "bankNameId");

    }

}
