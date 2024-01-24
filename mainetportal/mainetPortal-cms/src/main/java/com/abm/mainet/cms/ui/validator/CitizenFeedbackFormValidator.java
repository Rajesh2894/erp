package com.abm.mainet.cms.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.Feedback;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;

/**
 * @author swapnil.shirke
 */
@Component
public class CitizenFeedbackFormValidator extends BaseEntityValidator<Feedback> {

    @Override
    protected void performValidations(final Feedback entity, final EntityValidationContext<Feedback> validationContext) {

        validationContext.rejectIfEmpty(entity.getFdUserName(), "fdUserName");

        if ((entity.getMobileNo() == null) || entity.getMobileNo().equals(MainetConstants.BLANK)) {
            validationContext.rejectIfEmpty(entity.getMobileNo(), "mobileNo");
        } else if ((entity.getMobileNo() != null) || !(entity.getMobileNo().equals(MainetConstants.BLANK))) {
            validationContext.rejectPatternMatcher(entity.getMobileNo(), MainetConstants.MOBILE_NO_REGX, "mobileNo",
                    getApplicationSession().getMessage("Employee.empmobno1"));
        }

        if ((entity.getEmailId() == null) || entity.getEmailId().equals(MainetConstants.BLANK)) {
            validationContext.rejectIfEmpty(entity.getEmailId(), "emailId");
        } else if ((entity.getEmailId() != null) || !(entity.getEmailId().equals(MainetConstants.BLANK))) {
            validationContext.rejectPatternMatcher(entity.getEmailId(), MainetConstants.EMAIL_PATTERN, "emailId",
                    getApplicationSession().getMessage("Employee.empemail1"));
        }
        validationContext.rejectIfEmpty(entity.getFeedBackDetails(), "feedBackDetails");

    }
}
