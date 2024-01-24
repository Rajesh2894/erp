package com.abm.mainet.cms.ui.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.ProfileMaster;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.dms.utility.FileUploadUtility;

/**
 * @author swapnil.shirke
 */
@Component
public class AdminProfileMasterValidator extends BaseEntityValidator<ProfileMaster> {

    @Override
    protected void performValidations(final ProfileMaster entity,
            final EntityValidationContext<ProfileMaster> validationContext) {
        if (FileUploadUtility.getCurrent().getFileMap().size() == 0) {
            if (entity.getImagePath() == null
                    || ((entity.getImagePath() != null) && entity.getImagePath().equals(MainetConstants.BLANK))) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("profilemaster.image.errmsg"));
            }
        }

        if ((entity.getEmailId() != null) && !entity.getEmailId().equalsIgnoreCase(MainetConstants.BLANK)) {
            validationContext.rejectPatternMatcher(entity.getEmailId(), MainetConstants.EMAIL_PATTERN, "email1",
                    "Invalid Email Address. [e.g: sample@test.com].");
        }

        final String check = ApplicationSession.getInstance().getMessage(MainetConstants.UNICODE);
        if (MainetConstants.NON.equals(check)) {
            if ((entity.getpNameEn() == null) || entity.getpNameEn().isEmpty()) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("eipprofile.contactname1"));
            } else {
                if (!Pattern.compile(MainetConstants.RegEx.FIRST_NAME).matcher(entity.getpNameEn()).matches()) {
                    validationContext.addOptionConstraint(getApplicationSession()
                            .getMessage("eipprofile.contactname1en"));
                } else if ((entity.getpNameEn().replaceAll(MainetConstants.W, MainetConstants.BLANK).trim()).isEmpty()) {
                    validationContext.addOptionConstraint(getApplicationSession().getMessage("eipprofile.contactname1"));
                }
            }
            if ((entity.getDesignationEn() == null) || entity.getDesignationEn().isEmpty()) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("eipprofile.designation1"));
            } else {
                if (!Pattern.compile(MainetConstants.RegEx.ENGLISH_REG_EX).matcher(entity.getDesignationEn()).matches()) {
                    validationContext.addOptionConstraint(getApplicationSession()
                            .getMessage("eipprofile.designation1en"));
                } else if ((entity.getDesignationEn().replaceAll(MainetConstants.W, MainetConstants.BLANK).trim()).isEmpty()) {
                    validationContext.addOptionConstraint(getApplicationSession().getMessage("eipprofile.designation1"));
                }
            }
            if ((entity.getLinkTitleEn() == null) || entity.getLinkTitleEn().isEmpty()) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("eipprofile.title1"));
            } else {
                if (!Pattern.compile(MainetConstants.RegEx.ENGLISH_REG_EX).matcher(entity.getLinkTitleEn()).matches()) {
                    validationContext.addOptionConstraint(getApplicationSession()
                            .getMessage("eipprofile.title1en"));
                } else if ((entity.getLinkTitleEn().replaceAll(MainetConstants.W, MainetConstants.BLANK).trim()).isEmpty()) {
                    validationContext.addOptionConstraint(getApplicationSession().getMessage("eipprofile.title1"));
                }
            }
			/*
			 * if ((entity.getDtOfJoin() == null)) {
			 * validationContext.addOptionConstraint(getApplicationSession().getMessage(
			 * "admin.commissioner.doj")); }
			 */
            
			
			/*
			 * if ((entity.getSummaryEng() == null) || entity.getSummaryEng().isEmpty()) {
			 * validationContext.addOptionConstraint(getApplicationSession().getMessage(
			 * "eipprofile.mobileno")); }
			 */
			/*
			 * if(!entity.getSummaryEng().isEmpty()) { if (entity.getSummaryEng().length() <
			 * 10 || entity.getSummaryEng().length() > 11) {
			 * validationContext.addOptionConstraint(getApplicationSession().getMessage(
			 * "eipprofile.inmobileno")); }}
			 */
            /* else {
                if (!Pattern.compile(MainetConstants.RegEx.ENGLISH_REG_EX).matcher(entity.getSummaryEng()).matches()) {
                    validationContext.addOptionConstraint(getApplicationSession()
                            .getMessage("eipprofile.msgtocitizen1en"));
                } else if ((entity.getSummaryEng().replaceAll(MainetConstants.W, MainetConstants.BLANK).trim()).isEmpty()) {
                    validationContext.addOptionConstraint(getApplicationSession().getMessage("eipprofile.msgtocitizen1"));
                }
            }*/
            if ((entity.getProfileEn() == null) || entity.getProfileEn().isEmpty()) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("eipprofile.profile1"));
            } else {
                if (!Pattern.compile(MainetConstants.RegEx.ENGLISH_REG_EX).matcher(entity.getProfileEn()).matches()) {
                    validationContext.addOptionConstraint(getApplicationSession()
                            .getMessage("eipprofile.profile1en"));
                } else if ((entity.getProfileEn().replaceAll(MainetConstants.W, MainetConstants.BLANK).trim()).isEmpty()) {
                    validationContext.addOptionConstraint(getApplicationSession().getMessage("eipprofile.profile1"));
                }
            }

            if ((entity.getpNameReg() == null) || entity.getpNameReg().isEmpty()) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("eipprofile.contactname2"));
            } else {
                if (!Pattern.compile(MainetConstants.RegEx.MARATHI_REG_EX).matcher(entity.getpNameReg()).matches()) {
                    validationContext.addOptionConstraint(getApplicationSession()
                            .getMessage("eipprofile.contactname2mar"));
                }
            }
            if ((entity.getDesignationReg() == null) || entity.getDesignationReg().isEmpty()) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("eipprofile.designation2"));
            } else {
                if (!Pattern.compile(MainetConstants.RegEx.MARATHI_REG_EX).matcher(entity.getDesignationReg()).matches()) {
                    validationContext.addOptionConstraint(getApplicationSession()
                            .getMessage("eipprofile.designation2mar"));
                }
            }
            if ((entity.getLinkTitleReg() == null) || entity.getLinkTitleReg().isEmpty()) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("eipprofile.title2"));
            } else {
                if (!Pattern.compile(MainetConstants.RegEx.MARATHI_REG_EX).matcher(entity.getLinkTitleReg()).matches()) {
                    validationContext.addOptionConstraint(getApplicationSession()
                            .getMessage("eipprofile.title2mar"));
                }
            }
            /*
             * if ((entity.getSummaryReg() == null) || entity.getSummaryReg().isEmpty()) {
             * validationContext.addOptionConstraint(getApplicationSession().getMessage("eipprofile.msgtocitizen2")); } else { if
             * (!Pattern.compile(MainetConstants.RegEx.MARATHI_REG_EX).matcher(entity.getSummaryReg()).matches()) {
             * validationContext.addOptionConstraint(getApplicationSession() .getMessage("eipprofile.msgtocitizen2mar")); } }
             */
            if ((entity.getProfileReg() == null) || entity.getProfileReg().isEmpty()) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("eipprofile.profile2"));
            } else {
                if (!Pattern.compile(MainetConstants.RegEx.MARATHI_REG_EX).matcher(entity.getProfileReg()).matches()) {
                    validationContext.addOptionConstraint(getApplicationSession()
                            .getMessage("eipprofile.profile2mar"));
                }
            }

        } else {
            validationContext.rejectIfEmpty(entity.getpNameEn(), "pNameEn");
            validationContext.rejectIfEmpty(entity.getpNameReg(), "pNameReg");
            validationContext.rejectIfEmpty(entity.getDesignationEn(), "designationEn");
            validationContext.rejectIfEmpty(entity.getDesignationReg(), "designationReg");
            validationContext.rejectIfEmpty(entity.getLinkTitleEn(), "linkTitleEn");
            validationContext.rejectIfEmpty(entity.getLinkTitleReg(), "linkTitleReg");
			/*
			 * validationContext.rejectIfEmpty(entity.getSummaryEng(),
			 * getApplicationSession().getMessage("eip.dept.contcNo"));
			 * validationContext.rejectIfEmpty(entity.getDtOfJoin(),getApplicationSession().
			 * getMessage("admin.commissioner.doj"));
			 */
			/*
			 * if(validationContext.rejectIfEmpty(entity.getSummaryEng(), "summaryEng")) {
			 * if (entity.getSummaryEng().length() < 10 || entity.getSummaryEng().length() >
			 * 11) {
			 * validationContext.addOptionConstraint(getApplicationSession().getMessage(
			 * "eipprofile.inmobileno")); } }
			 */ 
            if(!entity.getSummaryEng().isEmpty()) {
        	if (entity.getSummaryEng().length() < 10 || entity.getSummaryEng().length() > 11) {
            	validationContext.addOptionConstraint(getApplicationSession().getMessage("eipprofile.inmobileno"));
        	}}
            
            /* validationContext.rejectIfEmpty(entity.getSummaryReg(), "summaryReg"); */
            validationContext.rejectIfEmpty(entity.getProfileEn(), "profileEn");
            validationContext.rejectIfEmpty(entity.getProfileReg(), "profileReg");
        }

    }

}
