package com.abm.mainet.legal.ui.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.legal.dto.CourtMasterDTO;
import com.abm.mainet.legal.repository.CourtMasterRepository;

@Component
public class CourtMasterValidator extends BaseEntityValidator<CourtMasterDTO> {
	 

	@Autowired
	CourtMasterRepository courtMasterRepository;
	
    @Override
    protected void performValidations(CourtMasterDTO entity, EntityValidationContext<CourtMasterDTO> entityValidationContext) {

        if (entity.getCrtName() != null)
            entityValidationContext.rejectIfEmpty(entity.getCrtName(), "crtName");
        if (entity.getCrtType() != null)
            entityValidationContext.rejectIfNotSelected(entity.getCrtType(), "crtType");        
        /*if (entity.getCrtPhoneNo() != null)
            entityValidationContext.rejectIfEmpty(entity.getCrtPhoneNo(), "crtPhoneNo");*/
        if (entity.getCrtAddress() != null)
            entityValidationContext.rejectIfEmpty(entity.getCrtAddress(), "crtAddress");
        
        
        if(entity.getId()==null)
        {
        if(entity.getCrtName() != null && entity.getCrtType() != null)
        {
        int count= courtMasterRepository.findbyCourtNameAndCourtType(entity.getCrtName(), entity.getCrtType(),entity.getOrgId());
        
        if(count>0)
        {
        	entityValidationContext.addOptionConstraint("Duplicate entry Select different Court Name and court Type");
        	
        }
        }

        }
    }

}
