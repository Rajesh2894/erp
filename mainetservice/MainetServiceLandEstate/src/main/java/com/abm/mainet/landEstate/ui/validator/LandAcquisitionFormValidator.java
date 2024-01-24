package com.abm.mainet.landEstate.ui.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.landEstate.dto.LandAcquisitionDto;

@Component
public class LandAcquisitionFormValidator extends BaseEntityValidator<LandAcquisitionDto> {
    @Override
    protected void performValidations(LandAcquisitionDto dto,
            EntityValidationContext<LandAcquisitionDto> validationContext) {

        if (StringUtils.isEmpty(dto.getPayTo())) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("land.acq.val.payTo"));
        }

        if (StringUtils.isEmpty(dto.getLnArea())) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("land.acq.val.lnArea"));
        }

        if (StringUtils.isEmpty(dto.getLnAddress())) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("land.acq.val.lnAddress"));
        }

        if (dto.getLocId() == null || dto.getLocId() == 0) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("land.acq.val.locId_chosn"));
        }

        if (dto.getAcqModeId() == null || dto.getAcqModeId() == 0) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("land.acq.val.acqType"));
        }

        if (StringUtils.isEmpty(dto.getLnServno())) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("land.acq.val.surveyNo"));
        }

        if (StringUtils.isEmpty(dto.getCurrentUsage())) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("land.acq.val.currentUsage"));
        }

        if (StringUtils.isEmpty(dto.getAcqPurpose())) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("land.acq.val.acqPurpose"));
        }

        if (StringUtils.isEmpty(dto.getLnDesc())) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("land.acq.val.lnDesc"));
        }

        if (StringUtils.isEmpty(dto.getLnOth())) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("land.acq.val.lnOth"));
        }

    }

}
