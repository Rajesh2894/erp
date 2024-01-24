package com.abm.mainet.common.master.ui.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.dto.TbTaxMas;

public class TaxMasterValidator implements Validator {

    @Override
    public boolean supports(final Class<?> aClass) {
        return TbTaxMas.class.equals(aClass);
    }

    @Override
    public void validate(final Object object, final Errors errors) {

        ValidationUtils.rejectIfEmpty(errors, MainetConstants.CommonMasterUi.TAX_DESC_ID,MainetConstants.CommonMasterUi.TAX_DESCID_EMPTY, MainetConstants.CommonMasterUiValidator.TAXNAME_NOT_EMPTY);
        ValidationUtils.rejectIfEmpty(errors, MainetConstants.AccountChequeOrCashDeposite.DP_DEPT_ID,MainetConstants.CommonMasterUi.TAX_DP_DEPTID, MainetConstants.CommonMasterUiValidator.SELECT_DEPT);
        ValidationUtils.rejectIfEmpty(errors, MainetConstants.CommonMasterUi.TAX_APPLICABLE, MainetConstants.CommonMasterUiValidator.TAX_APPLICABLE_EMPTY,MainetConstants.CommonMasterUiValidator.SELECT_APPLICABLE);
        ValidationUtils.rejectIfEmpty(errors, MainetConstants.CommonMasterUi.TAX_METHOD, MainetConstants.CommonMasterUiValidator.TAX_METHOD_EMPTY, MainetConstants.CommonMasterUiValidator.TAX_CAL_METHOD);
        ValidationUtils.rejectIfEmpty(errors, MainetConstants.CommonMasterUi.TAX_GROUP, MainetConstants.CommonMasterUiValidator.TAX_GROUP_EMPTY,MainetConstants.CommonMasterUiValidator.TAX_GROUP);
    }

}
