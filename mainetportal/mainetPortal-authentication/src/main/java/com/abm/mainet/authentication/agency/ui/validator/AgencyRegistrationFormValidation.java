package com.abm.mainet.authentication.agency.ui.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.abm.mainet.authentication.agency.dto.AgencyEmployeeReqDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.util.UserSession;

@Component
public class AgencyRegistrationFormValidation extends BaseEntityValidator<AgencyEmployeeReqDTO> {

    @Override
    protected void performValidations(final AgencyEmployeeReqDTO newAgency,
            final EntityValidationContext<AgencyEmployeeReqDTO> validationContext) {

        validationContext.rejectIfNotSelected(newAgency.getEmpGender(), "empGender");
        validationContext.rejectInvalidDate(newAgency.getEmpdob(), "empdob");

        if ((newAgency.getEmpmobno() == null) || newAgency.getEmpmobno().equals(MainetConstants.BLANK)) {
            validationContext.rejectIfEmpty(newAgency.getEmpmobno(), "empmobno");
        } else {
            final String mobile = newAgency.getEmpmobno();
            int count = 0;
            for (int i = 0; i < mobile.length(); i++) {
                final char eachDigit = mobile.charAt(i);

                if (eachDigit == '0') {
                    count++;
                }
            }

            if (count == 10) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("agency.login.valid.mob.error"));
            } else if (mobile.length() <= 9) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("agency.login.valid.10digit.mb.error"));
            }

        }

        if ((newAgency.getEmpemail() != null) && !newAgency.getEmpemail().equals(MainetConstants.BLANK)) {
            validationContext.rejectPatternMatcher(newAgency.getEmpemail(), MainetConstants.EMAIL_PATTERN, "empemail",
                    getApplicationSession().getMessage("agency.login.valid.email.error"));
        }

        if (newAgency.getPanCardNo() == MainetConstants.BLANK) {
            validationContext.rejectIfEmpty(newAgency.getPanCardNo(), "panCardNo");
        } else {
            final String panCardNo = newAgency.getPanCardNo();
            final Pattern panCardPattern = Pattern.compile(MainetConstants.PANCARD_PATTERN);
            final Matcher matcher = panCardPattern.matcher(panCardNo);
            if (!matcher.matches()) {
                if (UserSession.getCurrent().getLanguageId() == 1L) {
                    validationContext.addOptionConstraint(getApplicationSession().getMessage("eip.admin.reg.improperPanCardMsg"));
                } else {
                    validationContext.addOptionConstraint(getApplicationSession().getMessage("eip.admin.reg.improperPanCardMsg"));
                }
            }

        }

    }
}
