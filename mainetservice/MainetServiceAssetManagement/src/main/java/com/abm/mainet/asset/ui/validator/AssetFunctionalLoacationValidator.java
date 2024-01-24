package com.abm.mainet.asset.ui.validator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.abm.mainet.asset.ui.dto.AssetFunctionalLocationDTO;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;

@Component
public class AssetFunctionalLoacationValidator  extends BaseEntityValidator<List<AssetFunctionalLocationDTO>>{

	private Set<String> funcLocCodeSet;

	@Override
	protected void performValidations(List<AssetFunctionalLocationDTO> assFuncLocDTOList,
			EntityValidationContext<List<AssetFunctionalLocationDTO>> validationContext) {
		
		funcLocCodeSet	=	new HashSet<String>();
		
		for(int i=0;i<assFuncLocDTOList.size();i++)
		{
			AssetFunctionalLocationDTO dto	=	assFuncLocDTOList.get(i);
			
			boolean isDuplicate	=	isDuplicate(dto);
			
			//Used to check whether duplicate entry present in the form when dynamic add funcitonality being used in data table.
			if(!isDuplicate)
				validationContext.addOptionConstraint(getApplicationSession().getMessage("asset.functional.location.vldnn.code") + " Row "+(i+1) );

			//field property validation section.
			if(dto.getFuncLocationCode()==null || dto.getFuncLocationCode().isEmpty())
				validationContext.addOptionConstraint(getApplicationSession().getMessage("asset.functional.location.vldnn.code") + " Row "+(i+1) );
			if(dto.getDescription()==null || dto.getFuncLocationCode().isEmpty())
				validationContext.addOptionConstraint(getApplicationSession().getMessage("asset.functional.location.vldnn.description") + " Row "+(i+1) );
			if(dto.getParentId()==null)
				validationContext.addOptionConstraint(getApplicationSession().getMessage("asset.functional.location.vldnn.parentid") + " Row "+(i+1) );
			if(dto.getStartPoint()==null)
				validationContext.addOptionConstraint(getApplicationSession().getMessage("asset.functional.location.vldnn.startpoint") + " Row "+(i+1) );
			if(dto.getEndPoint()==null)
				validationContext.addOptionConstraint(getApplicationSession().getMessage("asset.functional.location.vldnn.endpoint") + " Row "+(i+1) );
			if(dto.getUnit()==null)
				validationContext.addOptionConstraint(getApplicationSession().getMessage("asset.functional.location.vldnn.unitofmeasurement") + " Row "+(i+1)  );

		}
		
	}
	
	private boolean isDuplicate(AssetFunctionalLocationDTO funcLocDTO)
	{
		String locCode	=	funcLocDTO.getFuncLocationCode();
		if(!funcLocCodeSet.contains(funcLocDTO.getFuncLocationCode()))
		{
			funcLocCodeSet.add(locCode);
			
			return true;
		}
		else
		{
			return false;
		}
	}

}
