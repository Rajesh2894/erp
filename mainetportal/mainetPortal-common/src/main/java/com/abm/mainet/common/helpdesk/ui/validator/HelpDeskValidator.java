package com.abm.mainet.common.helpdesk.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.dto.HelpDeskDTO;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;

@Component
public class HelpDeskValidator extends BaseEntityValidator<HelpDeskDTO> {

    @Override
    protected void performValidations(HelpDeskDTO entity,
            EntityValidationContext<HelpDeskDTO> validator) {
        validator.rejectIfEmpty(entity.getHelpName(), "helpName");
        // validator.rejectIfEmpty(entity.getHelpEsttime(), "helpEsttime");
        validator.rejectIfEmpty(entity.getHelpNote(), "helpNote");

        validator.rejectIfNotSelected(entity.getHelpType(), "helpType");
        validator.rejectIfNotSelected(entity.getHelpEmpid(), "helpEmpid");
        validator.rejectIfNotSelected(entity.getHelpPriority(), "helpPriority");
        validator.rejectIfNotSelected(entity.getHelpStatus(), "helpStatus");
        validator.rejectInvalidDate(entity.getHelpStartdt(), "helpStartdt");
        // validator.rejectInvalidDate(entity.getHelpEnddt(), "helpEnddt");
    }

}
