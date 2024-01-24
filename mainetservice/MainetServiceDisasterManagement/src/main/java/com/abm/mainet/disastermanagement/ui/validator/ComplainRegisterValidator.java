package com.abm.mainet.disastermanagement.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.disastermanagement.dto.ComplainRegisterDTO;

@Component
public class ComplainRegisterValidator extends BaseEntityValidator<ComplainRegisterDTO> {

	@Override
	protected void performValidations(ComplainRegisterDTO entity,
			EntityValidationContext<ComplainRegisterDTO> validation) {

		//validation.rejectEmptyList(entity.getDepartment(), "department");
		//validation.rejectIfEmpty(entity.getComplainerName(), "complainerName");
		//validation.rejectIfEmpty(entity.getComplainerMobile(), "complainerMobile");
		validation.rejectIfEmpty(entity.getComplainerAddress(), "complainerAddress");
		validation.rejectIfEmpty(entity.getComplaintDescription(), "complaintDescription");
		validation.rejectIfEmpty(entity.getLocation(), "location");
		performGroupValidation("complaintType");

	}

}
