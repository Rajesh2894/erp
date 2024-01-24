package com.abm.mainet.swm.ui.validator;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.swm.dto.VehicleMasterDTO;

public class VehicleMasterValidator extends BaseEntityValidator<VehicleMasterDTO> {

	@Override
	protected void performValidations(VehicleMasterDTO entity,
			EntityValidationContext<VehicleMasterDTO> entityValidationContext) {
		
		entityValidationContext.rejectIfNull(entity.getVeId(),  "VehicleMasterDTO.veId");
		entityValidationContext.rejectIfNull(entity.getVeNo(),  "VehicleMasterDTO.veNo");
	}

}
