package com.abm.mainet.materialmgmt.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.materialmgmt.dto.GoodsReceivedNotesDto;
import com.abm.mainet.materialmgmt.dto.GoodsreceivedNotesitemDto;

@Component
public class GoodsRecievedNotesInspectionValidator extends BaseEntityValidator<GoodsReceivedNotesDto> {

    final ApplicationSession session = ApplicationSession.getInstance();

    @Override
    protected void performValidations(GoodsReceivedNotesDto goodsReceivedNotesDto, EntityValidationContext<GoodsReceivedNotesDto> entityValidationContext) {
    	
    	int index =1;
    	for(GoodsreceivedNotesitemDto goodsreceivedNotesitemDto : goodsReceivedNotesDto.getGoodsreceivedNotesItemList()) {

    		boolean isError=false;
    		if(null == goodsreceivedNotesitemDto.getIspectionItemsList()) isError=true;
    		 	if(false == isError) {
					if ((MainetConstants.FlagD).equalsIgnoreCase(goodsReceivedNotesDto.getStatus())
							|| (MainetConstants.FlagI).equalsIgnoreCase(goodsReceivedNotesDto.getStatus())) {
    		 			if(MainetConstants.FlagN.equals(goodsreceivedNotesitemDto.getManagement()))
							isError = goodsreceivedNotesitemDto.getIspectionItemsList().stream().allMatch(det -> null == det.getQuantity() || null == det.getMfgDate() 
								|| null == det.getDecision() || '0' == det.getDecision() || (MainetConstants.CommonConstants.CHAR_N == det.getDecision() 
								&& det.getRejectionReason() == '0') || (MainetConstants.FlagY == goodsreceivedNotesitemDto.getIsExpiry() && det.getExpiryDate() == null));
						else if (MainetConstants.FlagB.equals(goodsreceivedNotesitemDto.getManagement()) || MainetConstants.FlagS.equals(goodsreceivedNotesitemDto.getManagement()))
							isError = goodsreceivedNotesitemDto.getIspectionItemsList().stream().allMatch(det -> null == det.getItemNo() || null == det.getQuantity()
											|| null == det.getMfgDate() || '0' == det.getDecision() || null == det.getDecision()
											|| (MainetConstants.CommonConstants.CHAR_N  == det.getDecision() && det.getRejectionReason() == null)
											|| (MainetConstants.FlagY == goodsreceivedNotesitemDto.getIsExpiry() && det.getExpiryDate() == null));
    			
        	    		if(isError) {
							entityValidationContext.addOptionConstraint(session.getMessage("material.management.grn.inspection.detail.validate")
											+ MainetConstants.WHITE_SPACE + index);
    		    		}
    		 		}else if((MainetConstants.FlagS).equalsIgnoreCase(goodsReceivedNotesDto.getStatus())) {    
    	    			isError= goodsreceivedNotesitemDto.getIspectionItemsList().stream().anyMatch(det -> det.getDecision()==MainetConstants.CommonConstants.CHAR_Y && det.getBinLocation() == null );
       	    			if(isError) {
    		    			entityValidationContext.addOptionConstraint(session.getMessage("material.management.grn.store.entry.detail.validate")
    		    					+ MainetConstants.WHITE_SPACE + index);
    		    		}
    		 		}
    		 	}
    		 	index++;
    		}
    	}
    
}
