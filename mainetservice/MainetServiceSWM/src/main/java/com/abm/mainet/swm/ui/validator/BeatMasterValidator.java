package com.abm.mainet.swm.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.swm.dto.BeatMasterDTO;

@Component
public class BeatMasterValidator extends BaseEntityValidator<BeatMasterDTO> {

	@Override
	protected void performValidations(BeatMasterDTO entity,
			EntityValidationContext<BeatMasterDTO> entityValidationContext) {
		
		entityValidationContext.rejectIfNull(entity.getBeatNo(),  "route.master.routeNo.validation");
		
		
	}



}
