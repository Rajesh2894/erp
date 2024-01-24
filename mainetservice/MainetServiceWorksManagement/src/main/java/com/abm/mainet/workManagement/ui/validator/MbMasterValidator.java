package com.abm.mainet.workManagement.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.workManagement.dto.MeasurementBookDetailsDto;
import com.abm.mainet.workManagement.dto.MeasurementBookMasterDto;

@Component
public class MbMasterValidator extends BaseEntityValidator<MeasurementBookMasterDto> {

	@Override
	protected void performValidations(MeasurementBookMasterDto mbDto,
			EntityValidationContext<MeasurementBookMasterDto> entityValidationContext) {

		int count = 0;

		if (mbDto.getWorkMbTakenDate() == null) {
			entityValidationContext.addOptionConstraint(
					getApplicationSession().getMessage("wms.actual.measurement.taken.date.must.notbe.empty"));
		}
		if (mbDto.getWorkMbBroDate() == null) {
			entityValidationContext.addOptionConstraint(
					getApplicationSession().getMessage("wms.measurement.brought.date.must.notbe.empty"));
		}
		if (mbDto.getWorkOrId() == 0 || mbDto.getWorkOrId() == null) {
			entityValidationContext
					.addOptionConstraint(getApplicationSession().getMessage("wms.work.orderNo.mustbe.selected"));
		}

		for (MeasurementBookDetailsDto bookDetailsDto : mbDto.getMbDetails()) {
			if (bookDetailsDto.isChecked()) {
				count++;
			}
		}
		if (count == 0) {
			entityValidationContext
					.addOptionConstraint(getApplicationSession().getMessage("wms.atleast.one.contractItems.for.MB.mustbe.selected"));
		}

	}

}
