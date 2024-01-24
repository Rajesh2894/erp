package com.abm.mainet.authentication.admin.ui.validator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.util.UserSession;

/**
 * @author vinay.jangir
 *
 */

@Component
public class AdminRegistrationValidator extends BaseEntityValidator<Employee> {

    @Override
    protected void performValidations(final Employee entity, final EntityValidationContext<Employee> validationContext) {
        validationContext.rejectIfNotSelected(entity.getTitle(), "title");
        validationContext.rejectIfEmpty(entity.getEmpname(), "empname");
        validationContext.rejectIfEmpty(entity.getEmpLName(), "empLName");

        if (entity.getTbDepartment() != null) {
            validationContext.rejectIfNotSelected(entity.getTbDepartment().getDpDeptid(), "Department");
        }

        if (entity.getDesignation().getDsgid() == 0L) {
            validationContext.rejectIfNotSelected(entity.getDesignation().getDsgid(), "designation.dsgid");
        }

        validationContext.rejectIfNotSelected(entity.getEmpGender(), "empGender");

        if ((entity.getEmpdob() == null) || entity.getEmpdob().equals(MainetConstants.BLANK)) {
            validationContext.rejectInvalidDate(entity.getEmpdob(), "empdob");
        } else {

            final Date empdob = entity.getEmpdob();

            final SimpleDateFormat formatDateJava = new SimpleDateFormat(MainetConstants.COMMON_DATE_FORMAT);

            final String dobstring = formatDateJava.format(empdob);
            final String[] date1 = dobstring.split(MainetConstants.WINDOWS_SLASH);
            final String day = date1[0];
            final String month = date1[1];
            final String year = date1[2];
            final Integer day1 = Integer.valueOf(day);
            final Integer month1 = Integer.valueOf(month);
            final Integer year1 = Integer.valueOf(year);

            if ((month1 < 1) || (month1 > 12)) {
                validationContext
                        .addOptionConstraint(getApplicationSession().getMessage(MainetConstants.CITIZEN_LOGIN_REG_DOB_ERROR1));
            }

            if ((day1 < 1) || (day1 > 31)) {
                validationContext
                        .addOptionConstraint(getApplicationSession().getMessage(MainetConstants.CITIZEN_LOGIN_REG_DOB_ERROR1));
            }

            if (((month1 == 4) || (month1 == 6) || (month1 == 9) || (month1 == 11)) && (day1 == 31)) {
                validationContext
                        .addOptionConstraint(getApplicationSession().getMessage(MainetConstants.CITIZEN_LOGIN_REG_DOB_ERROR1));
            }

            if (month1 == 2) { // check for february 29th
                final boolean isleap = ((year1 % 4) == 0) && (((year1 % 100) != 0) || ((year1 % 400) == 0));
                if ((day1 > 29) || ((day1 == 29) && !isleap)) {
                    validationContext.addOptionConstraint(
                            getApplicationSession().getMessage(MainetConstants.CITIZEN_LOGIN_REG_DOB_ERROR1));
                }
            }
            final Calendar now = Calendar.getInstance();
            int curr_year = now.get(Calendar.YEAR);
            int curr_month = now.get(Calendar.MONTH) + 1; // Note: zero based!
            final int curr_date = now.get(Calendar.DAY_OF_MONTH);
            if (curr_date < day1) {
                curr_month = curr_month - 1;
            }
            if (curr_month < month1) {
                curr_year = curr_year - 1;
            }
            curr_year = curr_year - year1;
            if (curr_year <= 0) {
                validationContext
                        .addOptionConstraint(getApplicationSession().getMessage(MainetConstants.CITIZEN_LOGIN_REG_DOB_ERROR1));
            } else if (curr_year < 18) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("citizen.login.reg.dob.error3"));
            }

        }

        validationContext.rejectIfEmpty(entity.getEmpAddress(), "empAddress");
        validationContext.rejectIfEmpty(entity.getPincode(), "pincode");

        if ((entity.getPincode() != null) && (entity.getPincode() != MainetConstants.BLANK)) {
            int count = 0;
            if (entity.getPincode().length() <= 6) {
                if (entity.getPincode().length() < 6) {
                    validationContext
                            .addOptionConstraint(getApplicationSession().getMessage("eip.admin.login.reg.pincode.length.error"));
                }
                for (int i = 0; i < entity.getPincode().length(); i++) {

                    final char pincode = entity.getPincode().charAt(i);
                    if (pincode == '0') {
                        count++;
                    }

                }
                if (count == 6) {
                    validationContext
                            .addOptionConstraint(getApplicationSession().getMessage("eip.admin.login.reg.pincode.digits.error"));
                }

            }
        }

        if (entity.getEmpmobno() == MainetConstants.BLANK) {
            validationContext.rejectIfEmpty(entity.getEmpmobno(), "empmobno");
        } else if (entity.getEmpmobno().length() <= 10) {
            int count = 0;

            if (entity.getEmpmobno().length() < 10) {
                if (UserSession.getCurrent().getLanguageId() == 1L) {
                    validationContext.addOptionConstraint(getApplicationSession().getMessage("eip.admin.reg.incorrectMobNo"));
                } else {
                    validationContext.addOptionConstraint(getApplicationSession().getMessage("eip.admin.reg.incorrectMobNo"));
                }
            }

            for (int i = 0; i < entity.getEmpmobno().length(); i++) {
                final char mob = entity.getEmpmobno().charAt(i);
                if (mob == '0') {
                    count++;
                }

            }
            if (count == 10) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("eip.admin.login.valid.mob.error"));
            }

        }

        if ((entity.getEmpemail() != null) && !entity.getEmpemail().equals(MainetConstants.BLANK)) {
            validationContext.rejectPatternMatcher(entity.getEmpemail(), MainetConstants.EMAIL_PATTERN, "empemail",
                    getApplicationSession().getMessage("eip.admin.reg.emailErrorMsg"));
        }

        if (entity.getPanCardNo() == MainetConstants.BLANK) {
            validationContext.rejectIfEmpty(entity.getPanCardNo(), "panCardNo");
        } else {
            final String panCardNo = entity.getPanCardNo();
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
