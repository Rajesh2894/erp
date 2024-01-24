package com.abm.mainet.common.ui.validator;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;

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
        entityValidationContext.rejectIfEmpty(entity.getAreaName(),(ApplicationSession.getInstance().getMessage("water.dis.address.line")));
        entityValidationContext.rejectIfEmpty(entity.getPinCode(),(ApplicationSession.getInstance().getMessage("water.dis.pin.code")));

        if(!(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENVIRNMENT_VARIABLE.ENV_SKDCL) || Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.SUDA))){
        if (!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL) && (entity.getIsBPL() == null || entity.getIsBPL().isEmpty()
                || entity.getIsBPL().equals(MainetConstants.MENU._0))) {
            entityValidationContext
                    .addOptionConstraint(ApplicationSession.getInstance().getMessage("water.validation.selpoverty"));
        } else {
            if (!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL) && (entity.getIsBPL().equals(MainetConstants.YES) && (entity.getBplNo() == null || entity.getBplNo().isEmpty()))) {
                entityValidationContext
                        .addOptionConstraint(ApplicationSession.getInstance().getMessage("water.validation.bplNo"));
            }
        }
        }else {
        	if(entity.getIsBPL() == null || entity.getIsBPL().isEmpty()
                     || entity.getIsBPL().equals(MainetConstants.MENU._0)){	
        		entity.setIsBPL("");
        		}
        }
        
		if(StringUtils.isEmpty(entity.getWardZoneValidFlag())) {
			performGroupValidation("dwzid");
		}
		  
		     }

}