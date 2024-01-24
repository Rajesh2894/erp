package com.abm.mainet.cms.ui.validator;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.EIPContactUs;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;

@Component
public class AdminContactUsFormValidator extends BaseEntityValidator<EIPContactUs> {

    @Override
    protected void performValidations(final EIPContactUs entity, final EntityValidationContext<EIPContactUs> validationContext) {

        validationContext.rejectIfEmpty(entity.getSequenceNo(), "sequenceNo");
        validationContext.rejectIfNotSelected(entity.getFlag(), "flag");

        // validationContext.rejectIfEmpty(entity.getTelephoneNo1En(), "telephoneNo1En");
        // validationContext.rejectIfEmpty(entity.getTelephoneNo2En(), "telephoneNo2En");
        // validationContext.rejectIfEmpty(entity.getEmail1(), "email1");
        if ((entity.getTelephoneNo2En() != null) || !entity.getTelephoneNo2En().equalsIgnoreCase(MainetConstants.BLANK)) {
            validationContext.rejectPatternMatcher(entity.getTelephoneNo2En(), MainetConstants.PHONE_NUMBER_REGX,
                    "telephoneNo1En", "Invalid Phone Number 1");
        }

        if (StringUtils.isNotBlank(entity.getEmail1())) {
            validationContext.rejectPatternMatcher(entity.getEmail1(), MainetConstants.EMAIL_PATTERN, "email1",
                    "Invalid Email Address. [e.g: sample@test.com].");
        }

        final String check = ApplicationSession.getInstance().getMessage("unicode");
        if (MainetConstants.NON.equals(check)) {
            if ((entity.getContactNameEn() == null) || entity.getContactNameEn().isEmpty()) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("eipcontactus.contactname1"));
            } else {
                if (!Pattern.compile(MainetConstants.RegEx.FIRST_NAME).matcher(entity.getContactNameEn()).matches()) {
                    validationContext.addOptionConstraint(getApplicationSession()
                            .getMessage("eipcontactus.contactname1en"));
                } else if ((entity.getContactNameEn().replaceAll(MainetConstants.W, MainetConstants.BLANK).trim()).isEmpty()) {
                    validationContext.addOptionConstraint(getApplicationSession().getMessage("eipcontactus.contactname1"));
                }
            }
            if ((entity.getDepartmentEn() == null) || entity.getDepartmentEn().isEmpty()) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("eipcontactus.deptname1"));
            } else {
                if (!Pattern.compile(MainetConstants.RegEx.ENGLISH_REG_EX).matcher(entity.getDepartmentEn()).matches()) {
                    validationContext.addOptionConstraint(getApplicationSession()
                            .getMessage("eipcontactus.deptname1en"));
                } else if ((entity.getDepartmentEn().replaceAll(MainetConstants.W, MainetConstants.BLANK).trim()).isEmpty()) {
                    validationContext.addOptionConstraint(getApplicationSession().getMessage("eipcontactus.deptname1"));
                }
            }
			if (!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
					MainetConstants.APP_NAME.DSCL)) {
				if ((entity.getDesignationEn() == null) || entity.getDesignationEn().isEmpty()
						|| entity.getDesignationEn().contentEquals("0")) {
					validationContext
							.addOptionConstraint(getApplicationSession().getMessage("eipcontactus.designation1"));
				}
			}
			  /*else {
                if (!Pattern.compile(MainetConstants.RegEx.ENGLISH_REG_EX).matcher(entity.getDesignationEn()).matches()) {
                    validationContext.addOptionConstraint(getApplicationSession()
                            .getMessage("eipcontactus.designation1en"));
                } else if ((entity.getDesignationEn().replaceAll(MainetConstants.W, MainetConstants.BLANK).trim()).isEmpty()) {
                    validationContext.addOptionConstraint(getApplicationSession().getMessage("eipcontactus.designation1"));
                }
            }*/
            /*
             * if ((entity.getMuncipalityName() == null) || entity.getMuncipalityName().isEmpty()) {
             * validationContext.addOptionConstraint(getApplicationSession().getMessage("eipcontactus.muncipality1")); } else { if
             * (!Pattern.compile(MainetConstants.RegEx.ENGLISH_REG_EX).matcher(entity.getMuncipalityName()).matches()) {
             * validationContext.addOptionConstraint(getApplicationSession() .getMessage("eipcontactus.muncipality1en")); } else
             * if ((entity.getMuncipalityName().replaceAll(MainetConstants.W, MainetConstants.BLANK).trim()).isEmpty()) {
             * validationContext.addOptionConstraint(getApplicationSession().getMessage("eipcontactus.muncipality1")); } }
             */
            if ((entity.getAddress2En() != null) || !entity.getAddress2En().isEmpty()) {
                if (!Pattern.compile(MainetConstants.RegEx.ENGLISH_REG_EX).matcher(entity.getAddress2En()).matches()) {
                    validationContext.addOptionConstraint(getApplicationSession()
                            .getMessage("eipcontactus.address1en"));
                } else if ((entity.getAddress2En().replaceAll(MainetConstants.W, MainetConstants.BLANK).trim()).isEmpty()) {
                    validationContext.addOptionConstraint(getApplicationSession().getMessage("eipcontactus.address1"));
                }
            }

            if ((entity.getContactNameReg() == null) || entity.getContactNameReg().isEmpty()) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("eipcontactus.contactname2"));
            } else {
                if (!Pattern.compile(MainetConstants.RegEx.MARATHI_REG_EX).matcher(entity.getContactNameReg()).matches()) {
                    validationContext.addOptionConstraint(getApplicationSession()
                            .getMessage("eipcontactus.contactname2ma"));
                }
            }
            if ((entity.getDepartmentReg() == null) || entity.getDepartmentReg().isEmpty()) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("eipcontactus.deptname2"));
            } else {
                if (!Pattern.compile(MainetConstants.RegEx.MARATHI_REG_EX).matcher(entity.getDepartmentReg()).matches()) {
                    validationContext.addOptionConstraint(getApplicationSession()
                            .getMessage("eipcontactus.deptname2mar"));
                }
            }
           /* if ((entity.getDesignationReg() == null) || entity.getDesignationReg().isEmpty()) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("eipcontactus.designation2"));
            } else {
                if (!Pattern.compile(MainetConstants.RegEx.MARATHI_REG_EX).matcher(entity.getDesignationReg()).matches()) {
                    validationContext.addOptionConstraint(getApplicationSession()
                            .getMessage("eipcontactus.designation2mar"));
                }
            }*/
            /*
             * if ((entity.getMuncipalityNameReg() == null) || entity.getMuncipalityNameReg().isEmpty()) {
             * validationContext.addOptionConstraint(getApplicationSession().getMessage("eipcontactus.muncipality2")); } else { if
             * (!Pattern.compile(MainetConstants.RegEx.MARATHI_REG_EX).matcher(entity.getMuncipalityNameReg()).matches()) {
             * validationContext.addOptionConstraint(getApplicationSession() .getMessage("eipcontactus.muncipality2mar")); } }
             */
            if ((entity.getAddress2Reg() != null) || !entity.getAddress2Reg().isEmpty()) {

                if (!Pattern.compile(MainetConstants.RegEx.MARATHI_REG_EX).matcher(entity.getAddress2Reg()).matches()) {
                    validationContext.addOptionConstraint(getApplicationSession()
                            .getMessage("eipcontactus.address2mar"));
                }
            }
        } else {
            validationContext.rejectIfEmpty(entity.getContactNameEn(), "contactNameEn");
            validationContext.rejectIfEmpty(entity.getContactNameReg(), "contactNameReg");
            validationContext.rejectIfEmpty(entity.getDepartmentEn(), "departmentEn");
            validationContext.rejectIfEmpty(entity.getDepartmentReg(), "departmentReg");
			//validationContext.rejectIf(entity.getDesignationEn(), "designationEn");
			if (!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
					MainetConstants.APP_NAME.DSCL)) {
				if ((entity.getDesignationEn() == null) || entity.getDesignationEn().isEmpty()
						|| entity.getDesignationEn().contentEquals("0")) {
					validationContext
							.addOptionConstraint(getApplicationSession().getMessage("eipcontactus.designation1"));
				}
			}
          //  validationContext.rejectIfEmpty(entity.getDesignationReg(), "designationReg");
            /*
             * validationContext.rejectIfEmpty(entity.getMuncipalityName(), "muncipalityName");
             * validationContext.rejectIfEmpty(entity.getMuncipalityNameReg(), "muncipalityNameReg");
             */
            /*
             * validationContext.rejectIfEmpty(entity.getAddress2Reg(), "address2Reg");
             * validationContext.rejectIfEmpty(entity.getAddress2En(), "address2En");
             */
        }
    }
}
