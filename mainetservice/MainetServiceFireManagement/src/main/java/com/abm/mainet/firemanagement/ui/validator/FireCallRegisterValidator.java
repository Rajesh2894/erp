package com.abm.mainet.firemanagement.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.firemanagement.dto.FireCallRegisterDTO;

@Component
public class FireCallRegisterValidator extends BaseEntityValidator<FireCallRegisterDTO> {

	@Override
	protected void performValidations(FireCallRegisterDTO entity,
			EntityValidationContext<FireCallRegisterDTO> validation) {

		
		/*
		 * validation.rejectIfEmpty(entity.getDate(), "date");
		 * validation.rejectIfEmpty(entity.getTime(), "time");
		 * validation.rejectIfEmpty(entity.getCallerMobileNo(), "callerMobileNo");
		 * validation.rejectIfEmpty(entity.getIncidentDesc(), "incidentDesc");
		 */
		  
		  

	}

}
