package com.abm.mainet.account.ui.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.abm.mainet.account.dto.TbAcPayToBank;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.utility.ApplicationSession;

public class PayToBankValidator implements Validator {
    @Override
    public boolean supports(final Class<?> aClass) {
        return TbAcPayToBank.class.equals(aClass);
    }

    @Override
    public void validate(final Object object, final Errors errors) {
        final TbAcPayToBank acPayToBank = (TbAcPayToBank) object;

        if ((acPayToBank.getPtbBankcode() == null)
                || acPayToBank.getPtbBankcode().equals(MainetConstants.BLANK)) {
            ValidationUtils.rejectIfEmpty(errors,
                    MainetConstants.TbAcPayToBank.TB_BANK_CODE, MainetConstants.TbAcPayToBank.ERROR_BANK_CODE,
                    ApplicationSession.getInstance().getMessage("pay.bank.bankcode"));
        }

    }
}
