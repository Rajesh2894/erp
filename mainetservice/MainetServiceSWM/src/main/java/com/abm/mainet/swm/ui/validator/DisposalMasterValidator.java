package com.abm.mainet.swm.ui.validator;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.swm.dto.DisposalMasterDTO;

public class DisposalMasterValidator extends BaseEntityValidator<DisposalMasterDTO> {

    @Override
    protected void performValidations(DisposalMasterDTO entity,
            EntityValidationContext<DisposalMasterDTO> entityValidationContext) {

        entityValidationContext.rejectIfNull(entity.getDeName(), "DisposalMasterDTO.DeName");

    }

}
