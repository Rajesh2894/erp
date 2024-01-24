package com.abm.mainet.common.master.ui.validator;

import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.dto.TbOrganisation;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

public class TbOrganisationValidator implements Validator {

    @Override
    public boolean supports(final Class<?> aClass) {
        return TbOrganisation.class.equals(aClass);
    }

    @Override
    public void validate(final Object object, final Errors errors) {

        final TbOrganisation orgMst = (TbOrganisation) object;
        final BindingResult customError = (BindingResult) errors;

        if ((orgMst.getUlbOrgID().intValue() == 0) || (orgMst.getUlbOrgID() == null)) {
            customError.addError(new ObjectError(MainetConstants.CommonMasterUi.ULB_ORG_ID,MainetConstants.CommonMasterUiValidator.SELECT_ORGID));
        }
        ValidationUtils.rejectIfEmpty(errors, MainetConstants.CommonMasterUi.ONLS_ORG_NAME,MainetConstants.CommonMasterUi.TB_ORG_ERR_ONLS_ORG_NAME,MainetConstants.CommonMasterUiValidator.ENETER_ENGNAME);
        ValidationUtils.rejectIfEmpty(errors, MainetConstants.CommonMasterUi.ONLS_ORG_NAME_MAR, MainetConstants.CommonMasterUi.TB_ORG_ERR_ONLS_ORG_NAMEMAR,
                MainetConstants.CommonMasterUiValidator.ENTER_REGIONAL);
        ValidationUtils.rejectIfEmpty(errors,MainetConstants.CommonMasterUi.ORG_SHORT_NAME,MainetConstants.CommonMasterUi.TB_ORG_ERR_ORG_SHORT_NAME,MainetConstants.CommonMasterUiValidator.ENTER_SHIRT_CODE);
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SFAC)) {
        	if ((orgMst.getSdbId1().intValue() == 0) || (orgMst.getSdbId1() == null)) {
                customError.addError(new ObjectError(MainetConstants.CommonMasterUi.ORG_CPID_STATE,MainetConstants.CommonMasterUiValidator.ENTER_STATE));
            }
        	if ((orgMst.getSdbId2().intValue() == 0) || (orgMst.getSdbId2() == null)) {
                  customError.addError(new ObjectError(MainetConstants.CommonMasterUi.ORG_CPIDIS,MainetConstants.CommonMasterUiValidator.ENTER_DISTRICT));
             }
        	if ((orgMst.getSdbId3().intValue() == 0) || (orgMst.getSdbId3() == null)) {
                customError.addError(new ObjectError("sdbId3",MainetConstants.CommonMasterUiValidator.ENTER_BLOCK));
           }
        }else {
        	  if ((orgMst.getOrgCpdIdState().intValue() == 0) || (orgMst.getOrgCpdIdState() == null)) {
                  customError.addError(new ObjectError(MainetConstants.CommonMasterUi.ORG_CPID_STATE,MainetConstants.CommonMasterUiValidator.ENTER_STATE));
              }       	  
        	  if ((orgMst.getOrgCpdIdDis().intValue() == 0) || (orgMst.getOrgCpdIdDis() == null)) {
                  customError.addError(new ObjectError(MainetConstants.CommonMasterUi.ORG_CPIDIS,MainetConstants.CommonMasterUiValidator.ENTER_DISTRICT));
              }       	  
        	  if ((orgMst.getOrgCpdIdOst().intValue() == 0) || (orgMst.getOrgCpdIdOst() == null)) {
                  customError.addError(new ObjectError(MainetConstants.CommonMasterUi.ORG_CPID_OST,MainetConstants.CommonMasterUiValidator.ENTER_SUBTYPE));
              }
        	    if ((orgMst.getOrgCpdIdDiv().intValue() == 0) || (orgMst.getOrgCpdIdDiv() == null)) {
                    customError.addError(new ObjectError(MainetConstants.CommonMasterUi.ORG_CPID_DIV,MainetConstants.CommonMasterUiValidator.ENTER_DIVISION));
                }
        }
        
        if ((orgMst.getOrgCpdId().intValue() == 0) || (orgMst.getOrgCpdId() == null)) {
            customError.addError(new ObjectError(MainetConstants.CommonMasterUi.ORG_CPDID,MainetConstants.CommonMasterUiValidator.ENTER_TYPE));
        }
       
    }

}
