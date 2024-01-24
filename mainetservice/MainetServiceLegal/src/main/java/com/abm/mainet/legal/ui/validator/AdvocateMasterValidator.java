package com.abm.mainet.legal.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.legal.dto.AdvocateMasterDTO;

@Component
public class AdvocateMasterValidator extends BaseEntityValidator<AdvocateMasterDTO> {

    @Override
    protected void performValidations(AdvocateMasterDTO entity,
            EntityValidationContext<AdvocateMasterDTO> entityValidationContext) {

      //  entityValidationContext.rejectIfNotSelected(entity.getAdvGen(), "advGen");
        entityValidationContext.rejectIfEmpty(entity.getAdvFirstNm(), "advFirstNm");
        entityValidationContext.rejectIfEmpty(entity.getAdvLastNm(), "advLastNm");
        entityValidationContext.rejectIfEmpty(entity.getAdvMobile(), "advMobile");
       // entityValidationContext.rejectIfEmpty(entity.getAdvPanno(), "advPanno");
       // entityValidationContext.rejectIfEmpty(entity.getAdvUid(), "advUid");
        entityValidationContext.rejectIfEmpty(entity.getAdvAppfromdate(), "advAppfromdate");
      //  entityValidationContext.rejectIfEmpty(entity.getAdvApptodate(), "advApptodate");
        //entityValidationContext.rejectIfEmpty(entity.getAdvExperience(), "advExperience");

    }

}
