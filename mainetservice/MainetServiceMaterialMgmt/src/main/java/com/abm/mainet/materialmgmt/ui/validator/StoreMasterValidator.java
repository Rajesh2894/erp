package com.abm.mainet.materialmgmt.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.materialmgmt.dto.StoreMasterDTO;

@Component
public class StoreMasterValidator extends BaseEntityValidator<StoreMasterDTO>{

	@Override
	protected void performValidations(StoreMasterDTO entity,EntityValidationContext<StoreMasterDTO> entityValidationContext) {
		entityValidationContext.rejectIfNull(entity.getStoreId(),  "StoreId Cann't be Empty!...");
		
	}

}
