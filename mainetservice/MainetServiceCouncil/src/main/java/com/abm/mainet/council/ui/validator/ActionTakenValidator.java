package com.abm.mainet.council.ui.validator;

import java.util.List;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.council.dto.CouncilActionTakenDto;

@Component
public class ActionTakenValidator extends BaseEntityValidator<List<CouncilActionTakenDto>> {

	@Override
	protected void performValidations(List<CouncilActionTakenDto> entity,
			EntityValidationContext<List<CouncilActionTakenDto>> validationContext) {

		for (CouncilActionTakenDto dto : entity) {
			 if (dto.getPatDepId() == null || dto.getPatDepId() == 0) {
		            validationContext.addOptionConstraint(getApplicationSession().getMessage("council.action.validation.patDepId"));
		        }

		        if (dto.getPatDetails() == null || dto.getPatDetails() == "") {
		            validationContext
		                    .addOptionConstraint(getApplicationSession().getMessage("council.action.validation.patActionDetails"));
		        }
		}
	}

}
