package com.abm.mainet.disastermanagement.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.disastermanagement.dto.ComplainRegisterDTO;

@Component
public class DepartmentalScrutinyValidator extends BaseEntityValidator<ComplainRegisterDTO> {

	@Override
	protected void performValidations(ComplainRegisterDTO dto, EntityValidationContext<ComplainRegisterDTO> entityValidationContext) {

//			entityValidationContext.rejectIfEmpty(dto.getHiName(), "hiName");
//			entityValidationContext.rejectIfEmpty(dto.getHiAddr(), "hiAddr");
//			entityValidationContext.rejectIfEmpty(dto.getHiCode(), "hiCode");
	}

}
