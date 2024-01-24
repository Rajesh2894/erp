package com.abm.mainet.cms.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.EipUserContactUs;
import com.abm.mainet.cms.ui.model.CitizenContactUsModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;

@Component
public class CitizenContactUsValidator extends BaseEntityValidator<CitizenContactUsModel> {

    @Override
    protected void performValidations(final CitizenContactUsModel model,
            final EntityValidationContext<CitizenContactUsModel> entityValidationContext) {
        final EipUserContactUs entity = model.getEipUserContactUs();
        entityValidationContext.rejectIfEmpty(entity.getPhoneNo(), "phoneNo");
        if (!entity.getPhoneNo().equals(MainetConstants.BLANK)) {
            entityValidationContext.rejectPatternMatcher(entity.getPhoneNo(),
                    MainetConstants.MOB_PATTERN, "phoneNo",
                    "Invalid.Mobile");
        }
        /*if(entity.getPhoneNo().length()>0) {
        	entityValidationContext.rejectPatternMatcher(entity.getPhoneNo(),
                    MainetConstants.MOB_PATTERN, "phoneNo",
                    "Invalid.AgencyOwnerMobile");
        }*/
       
        Boolean skdclCheck = Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENVIRNMENT_VARIABLE.ENV_SKDCL);
        if(skdclCheck) {
	        if(entity.getMoblieExtension().equals(MainetConstants.BLANK) && entity.getMoblieExtension().length()<2)
	        {
	        	entityValidationContext.rejectPatternMatcher(entity.getMoblieExtension(),MainetConstants.MOB_EXT , "moblieExtension","citizen.login.valid.mob.ext.error");
	        }
        }
        entityValidationContext.rejectIfEmpty(entity.getFirstName(), "firstName");
        entityValidationContext.rejectIfEmpty(entity.getLastName(), "lastName");
        entityValidationContext.rejectIfEmpty(entity.getEmailId(), "emailId");
        if (!entity.getEmailId().equals(MainetConstants.BLANK)) {
            entityValidationContext.rejectPatternMatcher(entity.getEmailId(),
                    MainetConstants.EMAIL_PATTERN, "emailId",
                    "Invalid.Email");
        }
        entityValidationContext.rejectIfEmpty(entity.getDescQuery(), "descQuery");

    }

}