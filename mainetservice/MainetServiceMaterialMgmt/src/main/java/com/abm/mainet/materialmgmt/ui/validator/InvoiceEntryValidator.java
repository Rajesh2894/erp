package com.abm.mainet.materialmgmt.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.materialmgmt.dto.InvoiceEntryDTO;


@Component
public class InvoiceEntryValidator extends BaseEntityValidator<InvoiceEntryDTO> {

    final ApplicationSession session = ApplicationSession.getInstance();

    @Override
    protected void performValidations(InvoiceEntryDTO invoiceEntryDTO, EntityValidationContext<InvoiceEntryDTO> entityValidationContext) {
    	
    	boolean isError=false;
    	if(null == invoiceEntryDTO.getInvoiceEntryGRNDTOList() || invoiceEntryDTO.getInvoiceEntryGRNDTOList().isEmpty()) {
    		isError=true;
			entityValidationContext.addOptionConstraint(ApplicationSession.getInstance().getMessage("material.management.grn.empty.validation"));    		
    	}else if(false == isError) {
			isError= invoiceEntryDTO.getInvoiceEntryGRNDTOList().stream().allMatch(det -> null == det.getGrnId() || null == det.getGrnDate());
    		if(isError) 
    			entityValidationContext.addOptionConstraint(ApplicationSession.getInstance().getMessage("material.management.grn.details.empty.validation"));    		
    	}
    	
   		if(null == invoiceEntryDTO.getInvoiceEntryDetailDTOList() || invoiceEntryDTO.getInvoiceEntryDetailDTOList().isEmpty()) {
   			isError=true;
   			entityValidationContext.addOptionConstraint(ApplicationSession.getInstance().getMessage("material.management.items.empty.validation"));    		
    	}else if(false == isError) {
			isError = invoiceEntryDTO.getInvoiceEntryDetailDTOList().stream().allMatch(det -> null == det.getGrnId() || null == det.getGrnItemEntryId()
							|| null == det.getItemId() || null == det.getUomName() || null == det.getQuantity() || null == det.getUnitPrice() 
							|| null == det.getTaxableValue() || null == det.getTax() || null == det.getTotalAmt());
    		if(isError) 
    			entityValidationContext.addOptionConstraint(ApplicationSession.getInstance().getMessage("material.management.items.details.empty.validation"));    		
    	}
    }

}






