package com.abm.mainet.common.ui.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.TbOrganisationRest;

/**
 * @author vishwajeet.kumar
 *
 */
@Component
public class OrganisationMasterValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return TbOrganisationRest.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        TbOrganisationRest orgMasterRest = (TbOrganisationRest) object;
        BindingResult customError = (BindingResult) errors;

        if (orgMasterRest.getUlbOrgID().intValue() == 0 || orgMasterRest.getUlbOrgID() == null) {
            customError.addError(new ObjectError(MainetConstants.CommonMasterUi.ULB_ORG_ID, "Please enter Organisation Id"));
        }

        ValidationUtils.rejectIfEmpty(errors, MainetConstants.CommonMasterUi.ONLS_ORG_NAME,
                MainetConstants.CommonMasterUi.TB_ORG_ERR_ONLS_ORG_NAME,
                "Please enter Name(English)");
        ValidationUtils.rejectIfEmpty(errors, MainetConstants.CommonMasterUi.ONLS_ORG_NAME_MAR,
                MainetConstants.CommonMasterUi.TB_ORG_ERR_ONLS_ORG_NAMEMAR,
                "Please enter Name(Regional)");
        ValidationUtils.rejectIfEmpty(errors, MainetConstants.CommonMasterUi.ORG_SHORT_NAME,
                MainetConstants.CommonMasterUi.TB_ORG_ERR_ORG_SHORT_NAME,
                "Please enter Shortcode");

        if (orgMasterRest.getOrgCpdIdState().intValue() == 0 || orgMasterRest.getOrgCpdIdState() == null) {
            customError.addError(new ObjectError(MainetConstants.CommonMasterUi.ORG_CPID_STATE, "Please enter State"));
        }

        if (orgMasterRest.getOrgCpdId().intValue() == 0 || orgMasterRest.getOrgCpdId() == null) {
            customError.addError(new ObjectError(MainetConstants.CommonMasterUi.ORG_CPDID, "Please enter Type"));
        }

        if (orgMasterRest.getOrgCpdIdDis().intValue() == 0 || orgMasterRest.getOrgCpdIdDis() == null) {
            customError.addError(new ObjectError(MainetConstants.CommonMasterUi.ORG_CPIDIS, "Please enter District"));
        }

        if (orgMasterRest.getOrgCpdIdOst().intValue() == 0 || orgMasterRest.getOrgCpdIdOst() == null) {
            customError.addError(new ObjectError(MainetConstants.CommonMasterUi.ORG_CPID_OST, "Please enter Subtype"));
        }

        if (orgMasterRest.getOrgCpdIdDiv().intValue() == 0 || orgMasterRest.getOrgCpdIdDiv() == null) {
            customError.addError(new ObjectError(MainetConstants.CommonMasterUi.ORG_CPID_DIV, "Please enter Division"));
        }

    }

}
