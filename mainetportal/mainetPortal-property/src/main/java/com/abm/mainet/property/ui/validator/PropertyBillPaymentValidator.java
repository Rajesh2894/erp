package com.abm.mainet.property.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.property.dto.BillPaymentDetailDto;

@Component
public class PropertyBillPaymentValidator extends BaseEntityValidator<BillPaymentDetailDto> {

    final ApplicationSession session = ApplicationSession.getInstance();

    @Override
    protected void performValidations(BillPaymentDetailDto billPayDto,
            EntityValidationContext<BillPaymentDetailDto> entityValidationContext) {
        // TODO Auto-generated method stub
        if (billPayDto.getTotalPaidAmt() <= 0) {
            entityValidationContext.addOptionConstraint(session.getMessage("property.EnterAmountValid"));
        }
        if (billPayDto.getTotalSubcharge() > 0 && billPayDto.getTotalPaidAmt() < billPayDto.getTotalSubcharge()) {
            entityValidationContext.addOptionConstraint(session.getMessage("property.billPayment.amount.surCharge"));
        }
        String lookUpCode = billPayDto.getPartialAdvancePayStatus();

        if (lookUpCode.equals(MainetConstants.Property.PP)) {
            if (billPayDto.getTotalPaidAmt() > billPayDto.getTotalPayableAmt() && billPayDto.getTotalPaidAmt() != 0) {
                entityValidationContext.addOptionConstraint(session.getMessage("property.PartialPayValid.propertyBill"));

            }
        }
        if (lookUpCode.equals(MainetConstants.Property.AP)) {
            if (billPayDto.getTotalPaidAmt() < billPayDto.getTotalPayableAmt() && billPayDto.getTotalPaidAmt() != 0) {
                entityValidationContext.addOptionConstraint(session.getMessage("property.AdvancePayValid.propertyBill"));

            }
        }
    }

}
