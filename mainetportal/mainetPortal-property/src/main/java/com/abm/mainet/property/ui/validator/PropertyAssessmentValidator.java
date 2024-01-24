package com.abm.mainet.property.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;

@Component
public class PropertyAssessmentValidator extends BaseEntityValidator<ProvisionalAssesmentMstDto> {

    final ApplicationSession session = ApplicationSession.getInstance();

    @Override
    protected void performValidations(ProvisionalAssesmentMstDto provDto,
            EntityValidationContext<ProvisionalAssesmentMstDto> entityValidationContext) {
    	if (provDto.getBillTotalAmt() > 0) {
		
    		if(!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),MainetConstants.ENVIRNMENT_VARIABLE.ENV_PSCL)) {
    			if (provDto.getBillPartialAmt() <= 0) {
    				entityValidationContext.addOptionConstraint(session.getMessage("water.billPayment.amount")); 
    			}
    		}
        if (provDto.getTotalSubcharge() != null && provDto.getTotalSubcharge() > 0
                && provDto.getTotalSubcharge() > provDto.getBillPartialAmt()) {
            entityValidationContext.addOptionConstraint(session.getMessage("property.billPayment.amount.surCharge"));
        }

        if (provDto.getPartialAdvancePayStatus() != null) {
            String lookUpCode = provDto.getPartialAdvancePayStatus();

            if (lookUpCode.equals(MainetConstants.Property.PP)) {
                if (provDto.getBillPartialAmt() > provDto.getBillTotalAmt() && provDto.getBillPartialAmt() != 0) {
                    entityValidationContext.addOptionConstraint(session.getMessage("property.PartialPayValid"));
                }
            }
            if (lookUpCode.equals(MainetConstants.Property.AP)) {
                if (provDto.getBillPartialAmt() < provDto.getBillTotalAmt() && provDto.getBillPartialAmt() != 0) {
                    entityValidationContext.addOptionConstraint(session.getMessage("property.AdvancePayValid"));
                }
            }
        }
    	}
    	if ((provDto.getDocs() != null) && !provDto.getDocs().isEmpty()) {
            for (final DocumentDetailsVO doc : provDto.getDocs()) {
                if (doc.getCheckkMANDATORY().equals(MainetConstants.CommonConstants.Y)) {
                    if (doc.getDocumentByteCode() == null) {
                        entityValidationContext.addOptionConstraint(session.getMessage("property.mandtory.docs"));
                        break;
                    }
                }
            }
        }
    }

}
