package com.abm.mainet.council.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.council.ui.model.CouncilMemberMasterModel;

@Component
public class MemberValidator extends BaseEntityValidator<CouncilMemberMasterModel> {

    @Override
    protected void performValidations(CouncilMemberMasterModel entity,
            EntityValidationContext<CouncilMemberMasterModel> entityValidationContext) {
        performGroupValidation("couMemMasterDto.couEleWZId");
    }

}
