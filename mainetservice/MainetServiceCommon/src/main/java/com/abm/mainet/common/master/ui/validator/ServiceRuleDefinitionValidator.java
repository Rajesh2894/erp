package com.abm.mainet.common.master.ui.validator;

import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.dto.TbServicesMst;

public class ServiceRuleDefinitionValidator implements Validator {

    @Override
    public boolean supports(final Class<?> aClass) {
        return TbServicesMst.class.equals(aClass);
    }

    @Override
    public void validate(final Object object, final Errors errors) {

        final TbServicesMst serviceMst = (TbServicesMst) object;
        final BindingResult customError = (BindingResult) errors;

        ValidationUtils.rejectIfEmpty(errors, MainetConstants.CommonMasterUi.CDM_DEPTID, MainetConstants.CommonMasterUiValidator.SERVICE_DEPTID_EMPTY_ERR, "Department Name must be selected");
        ValidationUtils.rejectIfEmpty(errors, MainetConstants.CommonMasterUi.SMS_SERVICE_NAME,MainetConstants.CommonMasterUiValidator.SERVICE_MASTER_EMPTY);
        ValidationUtils.rejectIfEmpty(errors, MainetConstants.CommonMasterUi.SMS_SHORT_DESC,MainetConstants.CommonMasterUiValidator.SERVICE_SHORT_DESC_EMPTY,
                MainetConstants.CommonMasterUiValidator.SERVICE_SHORT_DESC);
        ValidationUtils.rejectIfEmpty(errors, MainetConstants.CommonMasterUi.SMS_CHECK_VERIFY, MainetConstants.CommonMasterUiValidator.SERVICE_CHECKLIST_VERIFY,
                MainetConstants.CommonMasterUiValidator.CHECK_LIST_VERIFICATION_APP);

        if ((serviceMst.getComN1() != null) && (serviceMst.getComN1().intValue() == 0)) {
            customError.addError(new ObjectError(MainetConstants.CommonMasterUi.SMS_CHALLAN_DURATION,MainetConstants.CommonMasterUiValidator.CHALLAN_DURATION));
        }

        if (MainetConstants.Common_Constant.NO.equalsIgnoreCase(serviceMst.getFreeOrChargeableCheck())) {
            if (!MainetConstants.Common_Constant.YES.equalsIgnoreCase(serviceMst.getSmScrutinyChargeFlag())
                    && !MainetConstants.Common_Constant.YES.equalsIgnoreCase(serviceMst.getSmAppliChargeFlag())) {
                customError.addError(new ObjectError(MainetConstants.CommonMasterUi.SMS_FEE_SCHEDULE,MainetConstants.CommonMasterUiValidator.SELECT_APPLICABLE_CHARGE));
            }
        }

        ValidationUtils.rejectIfEmpty(errors,MainetConstants.CommonMasterUi.SM_PRINT_RESPONSE,MainetConstants.CommonMasterUiValidator.SERVICE_PRINT_RESP_EMPTY_ERR,
                MainetConstants.CommonMasterUiValidator.PRINT_RESPONSE_SELECTED);
    }

}
