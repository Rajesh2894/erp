package com.abm.mainet.common.master.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.dto.InformationDeskDto;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;

@Component
public class InformationHelpDeskValidator extends BaseEntityValidator<InformationDeskDto>{

	@Override
	protected void performValidations(InformationDeskDto entity,
			EntityValidationContext<InformationDeskDto> entityValidationContext) {
		final ApplicationSession session = ApplicationSession.getInstance();
		
		if (entity.getDeptId() == null && entity.getServiceId() == null) {
			entityValidationContext.addOptionConstraint(session.getMessage("search_data"));
		}
		
	}

}
