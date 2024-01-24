package com.abm.mainet.legal.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.legal.dto.CaseEntryDTO;

@Component
public class CaseEntryValidator extends BaseEntityValidator<CaseEntryDTO> {

    @Override
    protected void performValidations(CaseEntryDTO entity,
            EntityValidationContext<CaseEntryDTO> validation) {
    	
    	validation.rejectIfEmpty(entity.getCseName(),"cseName");
    	validation.rejectIfEmpty(entity.getCseSuitNo(),"cseSuitNo");
    	validation.rejectIfEmpty(entity.getCseDeptid(),"cseDeptid");
    	validation.rejectIfEmpty(entity.getCseCatId1() , "cseCatId1");
    	validation.rejectIfEmpty(entity.getCseMatdet1(), "cseMatdet1");
    	validation.rejectIfEmpty(entity.getCseRemarks(),"cseRemarks");
    	
    }

}
