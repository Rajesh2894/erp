package com.abm.mainet.vehicle.management.validator;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.vehicle.management.dto.GenVehicleMasterDTO;

public class VehicleMasterValidator extends BaseEntityValidator<GenVehicleMasterDTO> {

	@Override
	protected void performValidations(GenVehicleMasterDTO entity,
			EntityValidationContext<GenVehicleMasterDTO> entityValidationContext) {
		
		entityValidationContext.rejectIfNull(entity.getVeId(),  "VehicleMasterDTO.veId");
		entityValidationContext.rejectIfNull(entity.getVeNo(),  "VehicleMasterDTO.veNo");
	}
}
