package com.abm.mainet.firemanagement.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.firemanagement.dto.FireCallRegisterDTO;

@Component
public class FireCallClosureValidator extends BaseEntityValidator<FireCallRegisterDTO> {

	@Override
	protected void performValidations(FireCallRegisterDTO entity,
			EntityValidationContext<FireCallRegisterDTO> validation) {

		
		  validation.rejectIfEmpty(entity.getCallAttendDate(), "callAttendDate");
		  validation.rejectIfEmpty(entity.getCallAttendTime(), "callAttendTime");
		  validation.rejectIfEmpty(entity.getCloserRemarks(), "closerRemarks");
		  validation.rejectIfEmpty(entity.getFireStationsAttendCallList(), "fireStationsAttendCall");
		  validation.rejectCompareDate(entity.getDate(), "date", entity.getCallAttendDate(), "callAttendDate");

	}

}
