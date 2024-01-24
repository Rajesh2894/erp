package com.abm.mainet.workManagement.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.workManagement.dto.WorkOrderDto;

/**
 * @author vishwajeet.kumar
 * @since 21 May 2018
 */
@Component
public class WorkOrderGenerationValidator extends BaseEntityValidator<WorkOrderDto> {

	@Override
	protected void performValidations(WorkOrderDto workOrderDto,
			EntityValidationContext<WorkOrderDto> entityValidationContext) {

		/*if (workOrderDto.getAgreementFromDate() == null || workOrderDto.getAgreementFromDate().toString().isEmpty()) {
			entityValidationContext.addOptionConstraint(
					getApplicationSession().getMessage("work.order.please.enter.agreement.form.date"));
		}

		if (workOrderDto.getAgreementToDate() == null || workOrderDto.getAgreementToDate().toString().isEmpty()) {
			entityValidationContext.addOptionConstraint(
					getApplicationSession().getMessage("work.order.please.enter.agreement.to.date"));
		}*/

		if (workOrderDto.getLiabilityPeriod() == null || workOrderDto.getLiabilityPeriod().toString().isEmpty()) {
			entityValidationContext.addOptionConstraint(
					getApplicationSession().getMessage("work.order.please.enter.defect.liability.period"));
		}

		if (workOrderDto.getStartDate() == null || workOrderDto.getStartDate().toString().isEmpty()) {
			entityValidationContext.addOptionConstraint(
					getApplicationSession().getMessage("work.order.please.enter.date.to.start.the.work"));
		}
	}

}
