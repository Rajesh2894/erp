package com.abm.mainet.cms.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.News;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;

/**
 * @author swapnil.shirke To validate admin event section
 */
@Component
public class AdminEventsValidator extends BaseEntityValidator<News> {

    @Override
    protected void performValidations(final News entity, final EntityValidationContext<News> validationContext) {
        validationContext.rejectIfEmpty(entity.getLongDescEn(), "longDescEn");
        validationContext.rejectIfEmpty(entity.getLongDescReg(), "longDescReg");
        validationContext.rejectIfEmpty(entity.getShortDescEn(), "shortDescEn");
        validationContext.rejectIfEmpty(entity.getShortDescReg(), "shortDescReg");
        validationContext.rejectInvalidDate(entity.getNewsDate(), "newsDate");
    }

}
