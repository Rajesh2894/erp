package com.abm.mainet.vehiclemanagement.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.vehiclemanagement.dto.VehicleLogBookDTO;

@Component
public class VehicleLogBookValid extends BaseEntityValidator<VehicleLogBookDTO> {

	@Override
	protected void performValidations(VehicleLogBookDTO entity, EntityValidationContext<VehicleLogBookDTO> validation) {

         validation.rejectIfEmpty(entity.getVeNo(), "veNo");
		  validation.rejectIfEmpty(entity.getDriverName(), " driverName");
		 validation.rejectIfEmpty(entity.getTypeOfVehicle(), "   typeOfVehicle");
	//	 validation.rejectIfEmpty(entity.getDate(), "date");
		/* validation.rejectIfEmpty(entity.getTime(),"time"); */
	
		validation.rejectIfEmpty(entity.getDayStartMeterReading(), "dayStartMeterReading");
		validation.rejectIfEmpty(entity.getDayEndMeterReading(), "dayEndMeterReading");

	}

}
