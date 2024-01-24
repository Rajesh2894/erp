package com.abm.mainet.common.ui.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.utility.ApplicationSession;

@Component
public class CommonApplicantDetailValidator extends BaseEntityValidator<ApplicantDetailDTO> {

    @Override
    protected void performValidations(final ApplicantDetailDTO entity,
            final EntityValidationContext<ApplicantDetailDTO> entityValidationContext) {
        entityValidationContext.rejectIfNotSelected(entity.getApplicantTitle(), "applicantTitle");
        entityValidationContext.rejectIfEmpty(entity.getApplicantFirstName(), "applicantFirstName");
        entityValidationContext.rejectIfEmpty(entity.getApplicantLastName(), "applicantLastName");
        entityValidationContext.rejectIfEmpty(entity.getMobileNo(), "mobileNo");
        if ((entity.getEmailId() != null) && !entity.getEmailId().equals(MainetConstants.BLANK)) {
            if (!Pattern.compile(MainetConstants.EMAIL_PATTERN).matcher(entity.getEmailId()).matches()) {
                entityValidationContext.addOptionConstraint("emailId.invalid");
            }
        }
        entityValidationContext.rejectIfEmpty(entity.getAreaName(), "Address Line 1 ");
        entityValidationContext.rejectIfEmpty(entity.getPinCode(), "pinCode");
        if (entity.getIsBPL() == null || entity.getIsBPL().isEmpty()
                || entity.getIsBPL().equals(MainetConstants.MENU._0)) {
            entityValidationContext
                    .addOptionConstraint(ApplicationSession.getInstance().getMessage("water.validation.selpoverty"));
        } else {
            if (entity.getIsBPL().equals(MainetConstants.FlagY) && (entity.getBplNo() == null || entity.getBplNo().isEmpty())) {
                entityValidationContext
                        .addOptionConstraint(ApplicationSession.getInstance().getMessage("water.validation.bplNo"));
            }
        }
        performGroupValidation("dwzid");
    }

}