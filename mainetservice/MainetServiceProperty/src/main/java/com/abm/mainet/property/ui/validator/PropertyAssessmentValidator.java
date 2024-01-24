package com.abm.mainet.property.ui.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;

@Component
public class PropertyAssessmentValidator extends BaseEntityValidator<ProvisionalAssesmentMstDto> {

    final ApplicationSession session = ApplicationSession.getInstance();

    @Override
    protected void performValidations(ProvisionalAssesmentMstDto provDto,
            EntityValidationContext<ProvisionalAssesmentMstDto> entityValidationContext) {
		Organisation org = new Organisation();
		org.setOrgid(provDto.getOrgId());
    	LookUp billDeletionInactive = null;
            try {
            	billDeletionInactive = CommonMasterUtility.getValueFromPrefixLookUp("BDI", "ENV", org);
            }catch (Exception e) {
            	
    		}

		if (((billDeletionInactive == null || (StringUtils.isNotBlank(billDeletionInactive.getOtherField())
				&& StringUtils.equals(billDeletionInactive.getOtherField(), MainetConstants.FlagN)
				|| (StringUtils.isBlank(provDto.getEditableFlag())
						|| StringUtils.equals(provDto.getEditableFlag(), MainetConstants.FlagN))))
				&& provDto.getBillTotalAmt() > 0 && provDto.getManualReceiptDate() == null)) {
			
            if (StringUtils.isBlank(provDto.getPaymentCheck()) && provDto.getBillPartialAmt() <= 0) {
                entityValidationContext.addOptionConstraint(session.getMessage("water.billPayment.amount"));
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
    }

}
