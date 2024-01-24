package com.abm.mainet.bnd.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.bnd.dto.HospitalMasterDTO;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;

@Component
public class HospitalMasterValidator extends BaseEntityValidator<HospitalMasterDTO> {

	@Override
	protected void performValidations(HospitalMasterDTO dto,
			EntityValidationContext<HospitalMasterDTO> entityValidationContext) {

			entityValidationContext.rejectIfEmpty(dto.getHiName(), "hiName");
			entityValidationContext.rejectIfEmpty(dto.getHiAddr(), "hiAddr");
			//entityValidationContext.rejectIfEmpty(dto.getHiCode(), "hiCode");
	}

}
