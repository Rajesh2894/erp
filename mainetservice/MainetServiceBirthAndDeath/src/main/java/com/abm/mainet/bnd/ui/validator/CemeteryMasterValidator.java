package com.abm.mainet.bnd.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.bnd.dto.CemeteryMasterDTO;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;

@Component
public class CemeteryMasterValidator extends BaseEntityValidator<CemeteryMasterDTO> {
	
	@Override
	protected void performValidations(CemeteryMasterDTO dto,
			EntityValidationContext<CemeteryMasterDTO> entityValidationContext) {
		
			entityValidationContext.rejectIfEmpty(dto.getCeName(), "Cemetery Name");
			entityValidationContext.rejectIfEmpty(dto.getCeName(), "Cemetery Address");
	}

}
