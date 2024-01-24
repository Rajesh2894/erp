package com.abm.mainet.legal.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.legal.dto.JudgeMasterDTO;

@Component
public class JudgeMasterValidator extends BaseEntityValidator<JudgeMasterDTO> {

    @Override
    protected void performValidations(JudgeMasterDTO entity,
            EntityValidationContext<JudgeMasterDTO> entityValidationContext) {
        if (entity.getJudgeFName() != null)
            entityValidationContext.rejectIfEmpty(entity.getJudgeFName(), "judgeFName");
        if (entity.getJudgeLName() != null)
            entityValidationContext.rejectIfEmpty(entity.getJudgeLName(), "judgeLName");
       /* if (entity.getJudgeGender() != null) {
            entityValidationContext.rejectIfNotSelected(entity.getJudgeGender(), "judgeGender");*/
        }

        /*
         * entityValidationContext.rejectInvalidDate(entity.getJudgeDob(), "judgeDob"); if (entity.getJudgeContactNo() != null) {
         * entityValidationContext.rejectIfEmpty(entity.getJudgeContactNo(), "judgeContactNo"); } if (entity.getJudgeAddress() !=
         * null) { entityValidationContext.rejectIfEmpty(entity.getJudgeAddress(), "judgeAddress"); }
         */

    }


