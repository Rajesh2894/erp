package com.abm.mainet.workManagement.ui.validator;

import java.util.List;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.workManagement.dto.MileStoneDTO;
@Component
public class MilestoneValidatorDet extends BaseEntityValidator<List<MileStoneDTO>> {

	//128984 and 123976   To validate the duplicate milestone entries
	
	@Override
	protected void performValidations(List<MileStoneDTO> dtos,
			EntityValidationContext<List<MileStoneDTO>> validationContext) {
			
		if(!dtos.isEmpty()) {
			for (int i = 0; i < dtos.size(); i++) {
				  for (int j = i+1; j < dtos.size(); j++) {
				   
					  if(dtos.get(i).getMileStoneDesc().equals(dtos.get(j).getMileStoneDesc())) {
						  validationContext.addOptionConstraint(getApplicationSession().getMessage("wms.milestonr.duplicate"));
					  }
				  }
				}
		}
		
	}
}
