package com.abm.mainet.workManagement.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.workManagement.dto.MeasurementBookDetailsDto;

@Component
public class MbDetailsValidator extends BaseEntityValidator<MeasurementBookDetailsDto> {

	@Override
	protected void performValidations(MeasurementBookDetailsDto mbDetailsDto,
			EntityValidationContext<MeasurementBookDetailsDto> entityValidationContext) {
		
		if(mbDetailsDto.getWorkActualQty()==null) {
			
		}
		
	}

}
