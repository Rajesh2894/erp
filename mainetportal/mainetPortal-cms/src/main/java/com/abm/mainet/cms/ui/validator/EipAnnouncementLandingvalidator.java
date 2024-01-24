package com.abm.mainet.cms.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.EIPAnnouncementLanding;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;

@Component
public class EipAnnouncementLandingvalidator extends BaseEntityValidator<EIPAnnouncementLanding> {
    @Override
    protected void performValidations(final EIPAnnouncementLanding entity,
            final EntityValidationContext<EIPAnnouncementLanding> validationContext) {
        validationContext.rejectIfEmpty(entity.getAnnounceDescEng(), "EngAnnounce");
        validationContext.rejectIfEmpty(entity.getAnnounceDescReg(), "RegAnnounce");

    }
}
