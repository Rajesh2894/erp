package com.abm.mainet.common.ui.validator;

import java.util.List;

/*import org.joda.time.Days;
import org.joda.time.LocalDate;*/
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants.Common;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;

@Component
public class CommonOfflineMasterValidator extends BaseEntityValidator<CommonChallanDTO> {

    @Override
    protected void performValidations(final CommonChallanDTO entity,
            final EntityValidationContext<CommonChallanDTO> validationContext) {

    	//Defect #32656   	  
    	if((entity.getAmountToPay() != null && entity.getAmountToPay() != "0.0") || (entity.getAmountToShow() != null && entity.getAmountToShow() > 0.0))
        if (entity.getOnlineOfflineCheck() == null) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("pg.selPayMode"));
        }

        if ((entity.getOnlineOfflineCheck() != null) && entity.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT.OFFLINE)) {
            validationContext.rejectIfNotSelected(entity.getOflPaymentMode(), "oflPaymentMode");

            if ((entity.getOfflinePaymentText() != null)
                    && entity.getOfflinePaymentText().equalsIgnoreCase("PPO")) {
                validationContext.rejectIfEmpty(entity.getPoNo(), "poNo");
                validationContext.rejectInvalidDate(entity.getPoDate(), "poDate");

            }
            if ((entity.getOfflinePaymentText() != null)
                    && entity.getOfflinePaymentText().equalsIgnoreCase("PDD")) {
                validationContext.rejectInvalidDate(entity.getDdDate(), "ddDate");
                validationContext.rejectIfEmpty(entity.getDdNo(), "ddNo");

            }
            if ((entity.getOfflinePaymentText() != null)
                    && entity.getOfflinePaymentText().equalsIgnoreCase("PCB")) {
                if (entity.getBankaAccId() == null) {
                    validationContext.addOptionConstraint(getApplicationSession().getMessage("pg.bank.select"));
                } else {
                    if ((entity.getBankaAccId() != null) && (entity.getBankaAccId() == 0)) {
                        validationContext.addOptionConstraint(getApplicationSession().getMessage("pg.bank.select"));
                    }
                }
            }

        }

        if ((entity.getOnlineOfflineCheck() != null)
                && entity.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT.PAY_AT_ULB_COUNTER)) {

            if ((entity.getPayModeIn() == null)
                    || entity.getPayModeIn().equals(MainetConstants.TypeId.ZERO)) {

                validationContext.addOptionConstraint(getApplicationSession().getMessage("payment.paymentMode.error"));
            } else {

                final LookUp lookUp = findLookUpInstance(Common.PAY_AT_COUNTER);

                if (entity.getPayModeIn() != lookUp.getLookUpId()) {

                    if ((entity.getBmDrawOn() == null)
                            || entity.getBmDrawOn().equals("0")
                            || entity.getBmDrawOn().equals(MainetConstants.BLANK)) {

                        validationContext.addOptionConstraint(getApplicationSession().getMessage("payment.bankName.error"));
                    } else {

                        if ((entity.getBmBankAccountId() == null)
                                || entity.getBmBankAccountId().equals(
                                        MainetConstants.TypeId.ZERO)) {
                            validationContext.addOptionConstraint(getApplicationSession().getMessage(
                                    "payment.bank.accountNo.error"));
                        }

                        if ((entity.getBmChqDDNo() == null)
                                || entity.getBmChqDDNo().equals(
                                        MainetConstants.TypeId.ZERO)) {
                            validationContext.addOptionConstraint(getApplicationSession().getMessage(
                                    "payment.bank.chqOrDDNo.error"));
                        }

                        if ((entity.getBmChqDDDate() == null)) {
                            validationContext.addOptionConstraint(getApplicationSession().getMessage(
                                    "payment.bank.chqOrDDDate.error"));
                        }

                    }
                }
                // added for reset all value in case of cash
                if (entity.getPayModeIn() == lookUp.getLookUpId()) {

                    entity.setBmBankAccountId(null);
                    entity.setBmChqDDNo(null);
                    entity.setBmChqDDDate(null);
                    entity.setBmDrawOn("");
                    entity.setCbBankId(null);
                }

            }

            if ((entity.getAmountToPay() != null) && (entity.getAmountToPay() != MainetConstants.BLANK)) {

                final List<LookUp> lookUps = CommonMasterUtility.getLookUps(Common.PAY_AT_COUNTER,
                        UserSession.getCurrent().getOrganisation());
                for (final LookUp lookUp : lookUps) {

                    if (lookUp.getLookUpCode().equals(MainetConstants.PAYMENT.PaymentMode.CHEQUE)
                            || lookUp.getLookUpCode().equals(MainetConstants.PAYMENT.PaymentMode.DEMAND_DRAFT)) {

                        if (lookUp.getLookUpId() == entity.getPayModeIn()) {

                            if (lookUp.getOtherField() != null) {

                                if (Double.parseDouble(lookUp.getOtherField()) >= Double.parseDouble(entity.getAmountToPay())) {
                                    validationContext.addOptionConstraint(getApplicationSession()
                                            .getMessage("payment.bank.chequeOrDDAmount.error"));

                                    break;
                                }
                            }
                        }

                    }
                }

            }

        }
    }

    private LookUp findLookUpInstance(final String prefix) {

        final List<LookUp> lookUps = CommonMasterUtility.getLookUps(prefix, UserSession.getCurrent().getOrganisation());
        for (final LookUp lookUp : lookUps) {

            if (lookUp.getLookUpCode().equals(MainetConstants.PAYMENT.PaymentMode.CASH)) {

                return lookUp;
            }
        }

        return new LookUp();

    }

}
