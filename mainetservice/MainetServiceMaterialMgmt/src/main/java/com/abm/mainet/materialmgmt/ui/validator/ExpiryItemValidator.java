package com.abm.mainet.materialmgmt.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.materialmgmt.dto.ExpiryItemsDto;

@Component
public class ExpiryItemValidator extends BaseEntityValidator<ExpiryItemsDto> {

    final ApplicationSession session = ApplicationSession.getInstance();

    @Override
    protected void performValidations(ExpiryItemsDto expiryItemsDto, EntityValidationContext<ExpiryItemsDto> entityValidationContext) {
    	
    	boolean isError=false;
    	if(null == expiryItemsDto.getExpiryItemDetailsDtoList() || expiryItemsDto.getExpiryItemDetailsDtoList().isEmpty()) {
    		isError=true;
			entityValidationContext.addOptionConstraint(ApplicationSession.getInstance().getMessage("material.management.expired.item.details.empty.valid"));    		
    	}else if(false == isError) {
    		isError= expiryItemsDto.getExpiryItemDetailsDtoList().stream().allMatch(det -> det.getFlag() == null );
   			if(isError)
   				entityValidationContext.addOptionConstraint(ApplicationSession.getInstance().getMessage("material.management.mark.for.disposal"));    		
    	}
    }
    
}
