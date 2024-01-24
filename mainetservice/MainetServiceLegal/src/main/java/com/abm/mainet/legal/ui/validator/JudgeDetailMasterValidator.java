package com.abm.mainet.legal.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.legal.dto.JudgeDetailMasterDTO;

@Component
public class JudgeDetailMasterValidator extends BaseEntityValidator<JudgeDetailMasterDTO> {

    @Override
    protected void performValidations(JudgeDetailMasterDTO entity,
            EntityValidationContext<JudgeDetailMasterDTO> entityValidationContext) {
        // TODO Auto-generated method stub
        if (entity.getCrtType() != null)
            entityValidationContext.rejectIfNotSelected(entity.getCrtType(), "crtType");
        if (entity.getCrtName() != null)
            entityValidationContext.rejectIfNotSelected(entity.getCrtName(), "crtName");
        if (entity.getCrtAddress() != null)
            entityValidationContext.rejectIfEmpty(entity.getCrtAddress(), "crtAddress");

        entityValidationContext.rejectInvalidDate(entity.getFromPeriod(), "fromPeriod");

        entityValidationContext.rejectInvalidDate(entity.getToPeriod(), "toPeriod");
        if (entity.getJudgeStatus() != null)
            entityValidationContext.rejectIfNotSelected(entity.getJudgeStatus(), "judgeStatus");

    }

}
