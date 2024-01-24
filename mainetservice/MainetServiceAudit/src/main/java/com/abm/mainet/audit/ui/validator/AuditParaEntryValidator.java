package com.abm.mainet.audit.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.audit.dto.AuditParaEntryDto;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;

@Component
public class AuditParaEntryValidator extends BaseEntityValidator<AuditParaEntryDto> {

	@Override
	protected void performValidations(AuditParaEntryDto entity,
			EntityValidationContext<AuditParaEntryDto> validation) 
	{
		
		validation.rejectIfNotSelected(entity.getAuditDeptId(), "department");
		validation.rejectIfNotSelected(entity.getAuditType(), "auditType");
		validation.rejectIfNotSelected(entity.getAuditSeverity(), "severity");
		validation.rejectIfEmpty(entity.getAuditParaSub(), "subject");
		/* validation.rejectIfEmpty(entity.getAuditParaDesc(), "description"); */
		/* validation.rejectIfEmpty(entity.getAuditWorkName(), "workName"); */
		validation.compareWithCurrentDate(entity.getAuditEntryDate(), "auditEntryDateGreaterThanSysDate");
		validation.rejectInvalidDate(entity.getAuditEntryDate(), "auditEntryDate");
		
		
	}

}
