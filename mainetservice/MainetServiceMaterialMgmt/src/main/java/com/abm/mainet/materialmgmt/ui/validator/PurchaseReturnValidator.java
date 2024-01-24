package com.abm.mainet.materialmgmt.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.materialmgmt.dto.PurchaseReturnDto;

@Component
public class PurchaseReturnValidator extends BaseEntityValidator<PurchaseReturnDto> {

    final ApplicationSession session = ApplicationSession.getInstance();

    @Override
    protected void performValidations(PurchaseReturnDto purchaseReturnDto, EntityValidationContext<PurchaseReturnDto> entityValidationContext) {
    	
    	boolean isError=false;
    	if(null == purchaseReturnDto.getPurchaseReturnDetDtoList() || purchaseReturnDto.getPurchaseReturnDetDtoList().isEmpty()) {
    		isError=true;
			entityValidationContext.addOptionConstraint(ApplicationSession.getInstance().getMessage("material.management.rejected.items.details.valid"));    		
    	}else if(false == isError) {
			isError= purchaseReturnDto.getPurchaseReturnDetDtoList().stream().allMatch(det -> null == det.getItemId() || null == det.getUomName() || null == det.getQuantity() || null == det.getRejectionRemark());
    		if(isError) 
    			entityValidationContext.addOptionConstraint(ApplicationSession.getInstance().getMessage("material.management.rejected.items.details.valid"));    		
    	}
    }    
}
