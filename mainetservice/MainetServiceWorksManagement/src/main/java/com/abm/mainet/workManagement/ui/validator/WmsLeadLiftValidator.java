package com.abm.mainet.workManagement.ui.validator;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.workManagement.dto.WmsLeadLiftMasterDto;

@Component
public class WmsLeadLiftValidator extends BaseEntityValidator<WmsLeadLiftMasterDto> {

    @Override
    protected void performValidations(WmsLeadLiftMasterDto dto,
            EntityValidationContext<WmsLeadLiftMasterDto> validationContext) {
        if (dto.getSorId() == null) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("leadlift.master.select.sorName"));
        }
        if (dto.getLeLiFlag() == null) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("work.estimate.select.rate.type"));
        }
        if (dto.getLeLiSlabFlg().equals(MainetConstants.Y_FLAG)) {
            if (dto.getLeLiFrom() == null) {
                validationContext.addOptionConstraint(
                        getApplicationSession().getMessage("leadlift.master.select.fromValue"));
            }
        } else {
            if (dto.getLeLiFrom() != null) {
                validationContext.addOptionConstraint(
                        getApplicationSession().getMessage("leadlift.master.select.flatRate"));
            }
        }
        if (dto.getLeLiTo() == null) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("leadlift.master.select.toValue"));
        }
        if (dto.getLeLiUnit() == null) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("leadlift.master.select.unit"));
        }
        if (dto.getLeLiRate() == null || dto.getLeLiRate().toString().isEmpty()
                || dto.getLeLiRate().compareTo(new BigDecimal(0)) <= 0) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("leadlift.master.select.charges"));
        }

    }

}
