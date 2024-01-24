package com.abm.mainet.disastermanagement.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.disastermanagement.dto.DailyWeatherDTO;

@Component
public class DailyWeatherValidator extends BaseEntityValidator<DailyWeatherDTO>{

	@Override
	protected void performValidations(DailyWeatherDTO entity,
			EntityValidationContext<DailyWeatherDTO> Validation) {
		     Validation.rejectIfNotSelected(entity.getEmployee(), "employee");
		     Validation.rejectIfEmpty(entity.getMinTemperature(),"minTemparture");
		     Validation.rejectIfEmpty(entity.getMaxTemperature(),"maxTemparture");
		     Validation.rejectIfEmpty(entity.getHumidity(),"humidity");
		     Validation.rejectIfEmpty(entity.getRainFall(),"rainFall");
		     Validation.rejectIfEmpty(entity.getRainSpeed(),"rainSpeed");
		     Validation.rejectIfEmpty(entity.getWindSpeed(),"windSpeed");
		     
		
	}

}
