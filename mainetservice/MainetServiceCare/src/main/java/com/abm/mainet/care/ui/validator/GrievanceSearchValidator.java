package com.abm.mainet.care.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.care.dto.CareRequestDTO;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;

@Component
public class GrievanceSearchValidator extends BaseEntityValidator<CareRequestDTO> {

    @Override
    protected void performValidations(CareRequestDTO entity, EntityValidationContext<CareRequestDTO> entityValidationContext) {

        entityValidationContext.rejectIfEmpty(entity.getApplicationId(), "applicationId");

    }

}
