package com.abm.mainet.firemanagement.ui.validator;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.firemanagement.dto.OccuranceBookDTO;

public class OccuranceBookValidator extends BaseEntityValidator<OccuranceBookDTO> {

	@Override
	protected void performValidations(OccuranceBookDTO entity, EntityValidationContext<OccuranceBookDTO> validation) {
		
		
		if(entity.getDate()!=null)
			validation.rejectIfEmpty(entity.getDate(),"date");
		if(entity.getTime()!=null)
			validation.rejectIfEmpty(entity.getTime(), "time");	

	}

}

 