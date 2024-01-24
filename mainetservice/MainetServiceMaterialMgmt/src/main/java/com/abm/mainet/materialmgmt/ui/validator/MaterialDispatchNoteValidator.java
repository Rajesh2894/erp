package com.abm.mainet.materialmgmt.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.materialmgmt.dto.MaterialDispatchNoteDTO;
import com.abm.mainet.materialmgmt.dto.MaterialDispatchNoteItemsDTO;

@Component
public class MaterialDispatchNoteValidator extends BaseEntityValidator<MaterialDispatchNoteDTO> {

    final ApplicationSession session = ApplicationSession.getInstance();

	@Override
	protected void performValidations(MaterialDispatchNoteDTO materialDispatchNoteDTO, EntityValidationContext<MaterialDispatchNoteDTO> entityValidationContext) {

		boolean isError = false;
		/*if(null == materialDispatchNoteDTO.getStatus()) {
			isError = materialDispatchNoteDTO.getMatDispatchItemList().stream().allMatch(matItemDto -> matItemDto.getIssuedQty() == null || matItemDto.getInspectorRemarks() == null);
			if(isError)
				entityValidationContext.addOptionConstraint(session.getMessage("material.management.mdn.issue.details.validate"));
		} else { */
			isError = materialDispatchNoteDTO.getMatDispatchItemList().stream().allMatch(matItemDto -> matItemDto.getAcceptQty() == null || matItemDto.getRejectQty() == null || matItemDto.getStoreRemarks() == null);
			int index = 1;
			for (MaterialDispatchNoteItemsDTO matItemDto : materialDispatchNoteDTO.getMatDispatchItemList()) {
				if (matItemDto.getAcceptQty() == null || matItemDto.getRejectQty() == null || matItemDto.getStoreRemarks() == null) {
					entityValidationContext.addOptionConstraint(session.getMessage("material.management.mdn.inspection.details.validate") + MainetConstants.WHITE_SPACE + index);
					isError = true;
				}
				index++;
			}
		//}		
	}
    
}
