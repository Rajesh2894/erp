package com.abm.mainet.common.activity.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.dto.ActivityManagementDTO;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;

@Component
public class ActivityManagementValidator extends BaseEntityValidator<ActivityManagementDTO> {

    @Override
    protected void performValidations(ActivityManagementDTO entity,
            EntityValidationContext<ActivityManagementDTO> validator) {
        validator.rejectIfEmpty(entity.getActName(), "actName");
        validator.rejectIfEmpty(entity.getActEsttime(), "actEsttime");

        validator.rejectIfNotSelected(entity.getActType(), "actType");
        validator.rejectIfNotSelected(entity.getActEmpid(), "actEmpid");
        validator.rejectIfNotSelected(entity.getActPriority(), "actPriority");
        validator.rejectIfNotSelected(entity.getActStatus(), "actStatus");
        validator.rejectInvalidDate(entity.getActStartdt(), "actStartdt");
        validator.rejectInvalidDate(entity.getActEnddt(), "actEnddt");
    }

}
