package com.abm.mainet.materialmgmt.ui.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.materialmgmt.dto.ItemOpeningBalanceDetDto;
import com.abm.mainet.materialmgmt.dto.ItemOpeningBalanceDto;

@Component
public class ItemOpeningBalanceValidator extends BaseEntityValidator<ItemOpeningBalanceDto> {

    final ApplicationSession session = ApplicationSession.getInstance();

    @Override
    protected void performValidations(ItemOpeningBalanceDto balanceDto,
            EntityValidationContext<ItemOpeningBalanceDto> entityValidationContext) {
            if (balanceDto.getStoreId() == null) {
                entityValidationContext.addOptionConstraint(session.getMessage("material.item.pleaseSelectStoreName"));
            }
            if (balanceDto.getStoreId() == null) {
                entityValidationContext.addOptionConstraint(session.getMessage("material.item.pleaseEnterOpeningDate"));
            }
            if (balanceDto.getStoreId() == null) {
                entityValidationContext.addOptionConstraint(session.getMessage("material.item.pleaseSelectItemName"));
            }
            if (balanceDto.getStoreId() == null) {
                entityValidationContext.addOptionConstraint(session.getMessage("material.item.pleaseEnterOpeningBalance"));
            }
            if (balanceDto.getStoreId() == null) {
                entityValidationContext.addOptionConstraint(session.getMessage("material.item.pleaseEnterUnitPrice"));
            }
            
            int index =1;
            for (ItemOpeningBalanceDetDto itemOpeningBalanceDetDto : balanceDto.getItemOpeningBalanceDetDto()) {
				
            	if (itemOpeningBalanceDetDto.getBinLocId() == null) {
                    entityValidationContext.addOptionConstraint(session.getMessage("material.item.selectBinLocationForRow")+index);
                }
            	
            	if("N".equals(balanceDto.getValueMethodCode())) {
                	
            		 if (itemOpeningBalanceDetDto.getQuantity() == null) {
                         entityValidationContext.addOptionConstraint(session.getMessage("material.item.enterQuantityForRow"+index));
                     }
                }else if("B".equals(balanceDto.getValueMethodCode())) {                	
                	 if (itemOpeningBalanceDetDto.getQuantity() == null) {
                         entityValidationContext.addOptionConstraint(session.getMessage("material.item.enterQuantityForRow"+index));
                     }
                     if (StringUtils.isBlank(itemOpeningBalanceDetDto.getItemNo())) {
                         entityValidationContext.addOptionConstraint(session.getMessage("material.item.enterBatchNumberForRow"+index));
                     }
                	
                }
                else {
                	
                	if (StringUtils.isBlank(itemOpeningBalanceDetDto.getItemNo())) {
                         entityValidationContext.addOptionConstraint(session.getMessage("material.item.enterSerialNumberForRow"+index));
                     }
                    
                }
            	
			 }
        
    }

}
