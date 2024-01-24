package com.abm.mainet.vehiclemanagement.ui.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.vehiclemanagement.dto.OEMWarrantyDTO;

@Component
public class OEMMaintenanceValidator extends BaseEntityValidator<OEMWarrantyDTO> {

    @Override
    public void performValidations(OEMWarrantyDTO entity,
            EntityValidationContext<OEMWarrantyDTO> entityValidationContext) {

    	boolean isError=false;
    	if(StringUtils.isBlank(entity.getRemarks()) || null == entity.getMaintanceDate()) isError=true;
    	if(null == entity.getTbvmoemwarrantydetails()) isError=true;
    	if(false == isError)
    	isError= entity.getTbvmoemwarrantydetails().stream().allMatch(d -> null == d.getPartType() || null == d.getPartPosition() || null == d.getLastDateOfWarranty());
    	if(isError) {
    		entityValidationContext.addOptionConstraint(ApplicationSession.getInstance().getMessage("Please Complete Spart Parts form for Mandatory Field"));
    	}
    	
   
    }
}
