package com.abm.mainet.cms.ui.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.EIPAboutUs;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.util.ApplicationSession;

@Component
public class AdminAboutUsFormValidator extends BaseEntityValidator<EIPAboutUs> {

    @Override
    protected void performValidations(final EIPAboutUs entity, final EntityValidationContext<EIPAboutUs> validationContext) {
        final String check = ApplicationSession.getInstance().getMessage(MainetConstants.UNICODE);
        if (MainetConstants.NON.equals(check)) {

            if ((entity.getDescriptionEn() == null) || entity.getDescriptionEn().isEmpty()) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("eipaboutus.desc1"));
            } else {
                if (!Pattern.compile(MainetConstants.RegEx.ENGLISH_REG_EX).matcher(entity.getDescriptionEn()).matches()) {
                    validationContext.addOptionConstraint(getApplicationSession()
                            .getMessage("eipaboutus.desc1eng"));
                } else if ((entity.getDescriptionEn().replaceAll(MainetConstants.W, MainetConstants.BLANK).trim()).isEmpty()) {
                    validationContext.addOptionConstraint(getApplicationSession().getMessage("eipaboutus.desc1"));
                }
            }

            if ((entity.getDescriptionEn1() == null) || entity.getDescriptionEn1().isEmpty()) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("eipaboutus.desc2"));
            } else {
                if (!Pattern.compile(MainetConstants.RegEx.ENGLISH_REG_EX).matcher(entity.getDescriptionEn1()).matches()) {
                    validationContext.addOptionConstraint(getApplicationSession()
                            .getMessage("eipaboutus.desc2eng"));
                } else if ((entity.getDescriptionEn1().replaceAll(MainetConstants.W, MainetConstants.BLANK).trim()).isEmpty()) {
                    validationContext.addOptionConstraint(getApplicationSession().getMessage("eipaboutus.desc2"));
                }
            }

            if ((entity.getDescriptionReg() == null) || entity.getDescriptionReg().isEmpty()) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("eipaboutus.desc3"));
            } else {
                if (!Pattern.compile(MainetConstants.RegEx.MARATHI_REG_EX).matcher(entity.getDescriptionReg()).matches()) {
                    validationContext.addOptionConstraint(getApplicationSession()
                            .getMessage("eipaboutus.desc3mar"));
                }
            }

            if ((entity.getDescriptionReg1() == null) || entity.getDescriptionReg1().isEmpty()) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("eipaboutus.desc4"));
            } else {
                if (!Pattern.compile(MainetConstants.RegEx.MARATHI_REG_EX).matcher(entity.getDescriptionReg1()).matches()) {
                    validationContext.addOptionConstraint(getApplicationSession()
                            .getMessage("eipaboutus.desc4mar"));
                }
            }
        } else {
            validationContext.rejectIfEmpty(entity.getDescriptionEn().replaceAll("\\s", MainetConstants.BLANK), "descriptionEn");
            validationContext.rejectIfEmpty(entity.getDescriptionReg().replaceAll("\\s", MainetConstants.BLANK),
                    "descriptionReg");
            validationContext.rejectIfEmpty(entity.getDescriptionEn1().replaceAll("\\s", MainetConstants.BLANK),
                    "descriptionEn1");
            validationContext.rejectIfEmpty(entity.getDescriptionReg1().replaceAll("\\s", MainetConstants.BLANK),
                    "descriptionReg1");
        }

    }
}
