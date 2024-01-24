
package com.abm.mainet.account.ui.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.abm.mainet.account.dto.AccountJournalVoucherEntryBean;

/**
 * @author deepika.pimpale
 *
 */

public class AccountJournalVoucherValidator implements Validator {

    /*
     * (non-Javadoc)
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    @Override
    public boolean supports(final Class<?> clazz) {
        return AccountJournalVoucherEntryBean.class.equals(clazz);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    @Override
    public void validate(final Object target, final Errors errors) {

    }

}
