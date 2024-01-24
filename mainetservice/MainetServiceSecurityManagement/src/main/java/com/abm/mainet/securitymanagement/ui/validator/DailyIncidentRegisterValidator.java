package com.abm.mainet.securitymanagement.ui.validator;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.securitymanagement.dto.DailyIncidentRegisterDTO;

public class DailyIncidentRegisterValidator extends BaseEntityValidator<DailyIncidentRegisterDTO> {

	@Override
	protected void performValidations(DailyIncidentRegisterDTO dto,
			EntityValidationContext<DailyIncidentRegisterDTO> entityValidationContext) {
		    
		entityValidationContext.rejectIfEmpty(dto.getDate(),"date");
		
		entityValidationContext.rejectIfEmpty(dto.getTime(),"time");
		
		entityValidationContext.rejectIfEmpty(dto.getRemarks(),"remarks");
		
        entityValidationContext.rejectIfEmpty(dto.getNameVisitingId(),"nameVisitingOff");
		
	}

}
