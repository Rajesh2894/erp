package com.abm.mainet.swm.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.swm.dto.SLRMEmployeeMasterDTO;

@Component
public class SLRMEmployeeValidator extends BaseEntityValidator<SLRMEmployeeMasterDTO>{

    @Override
    protected void performValidations(SLRMEmployeeMasterDTO entity,
            EntityValidationContext<SLRMEmployeeMasterDTO> entityValidationContext) {
        // TODO Auto-generated method stub
        
    }

}
