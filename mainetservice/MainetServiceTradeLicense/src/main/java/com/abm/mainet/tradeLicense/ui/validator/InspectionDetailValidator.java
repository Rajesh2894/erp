package com.abm.mainet.tradeLicense.ui.validator;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.tradeLicense.dto.InspectionDetailDto;

@Component
public class InspectionDetailValidator extends BaseEntityValidator<InspectionDetailDto>{

	@Override
	protected void performValidations(InspectionDetailDto dto,
			EntityValidationContext<InspectionDetailDto> entityValidationContext) {
		final ApplicationSession session = ApplicationSession.getInstance();
		/*if(StringUtils.isNumeric(dto.getLicNo())) {
			entityValidationContext.addOptionConstraint(session.getMessage("validate.license.no"));
		}*/
		if(StringUtils.isEmpty(dto.getLicNo())) {
			entityValidationContext.addOptionConstraint(session.getMessage("validate.inspection.licno"));
		}
		if(dto.getInspDate() == null) {
			entityValidationContext.addOptionConstraint(session.getMessage("validate.inspection.date"));
		}
		if(StringUtils.isEmpty(dto.getInspectorName())) {
			entityValidationContext.addOptionConstraint(session.getMessage("validate.inspector.name"));
		}
		/*if(dto.getInspNo() == null) {
			entityValidationContext.addOptionConstraint(session.getMessage("validate.inspection.no"));
		}
		*/
		if(StringUtils.isEmpty(dto.getDescription())) {
			entityValidationContext.addOptionConstraint(session.getMessage("validate.inspection.desc"));
		}
	}

}
