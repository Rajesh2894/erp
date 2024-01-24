package com.abm.mainet.property.ui.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;

@Component
public class RuleErrorValidator extends BaseEntityValidator<ProvisionalAssesmentMstDto> {

    @Override
    protected void performValidations(ProvisionalAssesmentMstDto entity,
            EntityValidationContext<ProvisionalAssesmentMstDto> entityValidationContext) {
        // D#18545 Error MSG for Rule found or not
        entity.getProvisionalAssesmentDetailDtoList().forEach(dtos -> {
            // make error MSG. based on error MSG
            if (!StringUtils.isEmpty(dtos.getErrorMsg())) {
                // add error MSG here like year - unit no (SR no) - rule not found
                entityValidationContext
                        .addOptionConstraint(dtos.getProFaYearIdDesc() + " Sr. No " + dtos.getAssdUnitNo() + " Rule Not Found");
            }
        });
    }

}
