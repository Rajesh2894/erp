package com.abm.mainet.common.master.ui.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.dto.DesignationBean;

/**
 * @author prasant.sahu
 *
 */

public class DesignationValidator implements Validator {
    @Override
    public boolean supports(final Class<?> aClass) {
        return DesignationBean.class.equals(aClass);
    }

    @Override
    public void validate(final Object object, final Errors errors) {
        final DesignationBean designationBean = (DesignationBean) object;

        if ((designationBean.getDsgname() == null) || designationBean.getDsgname().equals(MainetConstants.BLANK)) {
            ValidationUtils.rejectIfEmpty(errors,MainetConstants.CommonMasterUiValidator.DSG_NAME, "scrutiny.level.empty.err",MainetConstants.CommonMasterUiValidator.DSG_MUST_NOT_EMPTY );
        }
        if ((designationBean.getDsgnameReg() == null) || designationBean.getDsgnameReg().equals("")) {
            ValidationUtils.rejectIfEmpty(errors, MainetConstants.CommonMasterUiValidator.DSG_NAME_REG, "scrutiny.role.empty.err",MainetConstants.CommonMasterUiValidator.DSG_NAME_REG_NOT_EMPTY);
        }
        if ((designationBean.getDsgshortname() == null) || designationBean.getDsgshortname().equals("")) {
            ValidationUtils.rejectIfEmpty(errors,MainetConstants.CommonMasterUiValidator.DSG_SHORT_NAME, "scrutiny.mode.empty.err",
                    MainetConstants.CommonMasterUiValidator.DSG_SHORT_NAME_NOT_EMPTY);
        }
    }
}
