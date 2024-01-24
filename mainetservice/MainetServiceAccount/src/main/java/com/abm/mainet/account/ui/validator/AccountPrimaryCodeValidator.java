
package com.abm.mainet.account.ui.validator;

import java.util.Iterator;

import org.springframework.validation.BindingResult;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.acccount.dto.AccountHeadPrimaryAccountCodeMasterBean;
import com.abm.mainet.common.integration.acccount.dto.AccountHeadPrimaryAccountCodeMasterDTO;
import com.abm.mainet.common.utility.ApplicationSession;

/**
 * @author prasant.sahu
 *
 */
public class AccountPrimaryCodeValidator {

    public void primaryCodeValidation(final AccountHeadPrimaryAccountCodeMasterBean primaryCodeMasterBean,
            final BindingResult bindingResult,
            Boolean isMasterExist, final Iterator<?> it) {
        final ApplicationSession session = ApplicationSession.getInstance();
        if (MainetConstants.BLANK.equals(primaryCodeMasterBean.getParentCode())) {
            bindingResult.addError(new org.springframework.validation.FieldError(
                    MainetConstants.PRIMARYCODEMASTER.PRIMARYCODE_BEAN, MainetConstants.BLANK, null, false,
                    new String[] { MainetConstants.ERRORS }, null, session.getMessage("account.master.sel.primAcCode")));
        }
        if (MainetConstants.BLANK.equals(primaryCodeMasterBean.getParentDesc())) {
            bindingResult.addError(new org.springframework.validation.FieldError(
                    MainetConstants.PRIMARYCODEMASTER.PRIMARYCODE_BEAN, MainetConstants.BLANK, null, false,
                    new String[] { MainetConstants.ERRORS }, null, session.getMessage("account.master.sel.primAcDesc")));
        }
        if (MainetConstants.BLANK.equals(primaryCodeMasterBean.getParentFinalCode())) {
            bindingResult.addError(new org.springframework.validation.FieldError(
                    MainetConstants.PRIMARYCODEMASTER.PRIMARYCODE_BEAN, MainetConstants.BLANK, null, false,
                    new String[] { MainetConstants.ERRORS }, null, session.getMessage("account.master.sel.primAcFinalCode")));
        }

        if (primaryCodeMasterBean.getPrimaryAcHeadId() == null) {
            while (it.hasNext()) {
                if (primaryCodeMasterBean.getParentCode().equals(it.next())) {
                    bindingResult.addError(
                            new org.springframework.validation.FieldError(MainetConstants.PRIMARYCODEMASTER.PRIMARYCODE_BEAN,
                                    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                                    session.getMessage("function.master.parentCodeExist")));
                    isMasterExist = true;
                    break;
                }
            }
        }
        for (final AccountHeadPrimaryAccountCodeMasterDTO dto : primaryCodeMasterBean.getListDto()) {

            if (primaryCodeMasterBean.getListDto().size() == 1) {
                if (MainetConstants.ZERO.equals(dto.getChildLevel()) && MainetConstants.ZERO.equals(dto.getChildParentLevel())
                        && MainetConstants.BLANK.equals(dto.getChildCode())
                        && MainetConstants.BLANK.equals(dto.getChildParentCode())
                        && MainetConstants.BLANK.equals(dto.getChildDesc())) {
                    break;
                }
            }

            if (MainetConstants.ZERO.equals(dto.getChildLevel()) || MainetConstants.ZERO.equals(dto.getChildParentLevel())
                    || MainetConstants.BLANK.equals(dto.getChildCode()) || MainetConstants.BLANK.equals(dto.getChildParentCode())
                    || dto.getChildDesc().equals(MainetConstants.BLANK)) {
                bindingResult.addError(new org.springframework.validation.FieldError(
                        MainetConstants.PRIMARYCODEMASTER.PRIMARYCODE_BEAN, MainetConstants.FUNCTION_MASTER.PARENT_LVL, null,
                        false, new String[] { MainetConstants.ERRORS }, null, session.getMessage("account.master.sel.reqField")));
            }

            if (((dto.getChildParentLevel() != null) && !MainetConstants.BLANK.equals(dto.getChildParentLevel()))
                    && ((dto.getChildLevel() != null) && !MainetConstants.BLANK.equals(dto.getChildLevel()))) {
                if (Integer.parseInt(dto.getChildParentLevel()) > Integer.parseInt(dto.getChildLevel())) {
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.PRIMARYCODEMASTER.PRIMARYCODE_BEAN, MainetConstants.FUNCTION_MASTER.PARENT_LVL, null,
                            false, new String[] { MainetConstants.ERRORS }, null,
                            session.getMessage("function.master.selectCorrectLevel")));

                    if ((Integer.parseInt(dto.getChildParentLevel()) - Integer.parseInt(dto.getChildLevel())) > 1) {
                        bindingResult.addError(new org.springframework.validation.FieldError(
                                MainetConstants.PRIMARYCODEMASTER.PRIMARYCODE_BEAN, MainetConstants.FUNCTION_MASTER.PARENT_LVL,
                                null, false, new String[] { MainetConstants.ERRORS }, null,
                                session.getMessage("function.master.selectCorrectLevel")));
                    }
                }
            } else {
                bindingResult.addError(
                        new org.springframework.validation.FieldError(MainetConstants.PRIMARYCODEMASTER.PRIMARYCODE_BEAN,
                                MainetConstants.FUNCTION_MASTER.PARENT_LVL, null, false, new String[] { MainetConstants.ERRORS },
                                null, session.getMessage("function.master.selectCorrectLevel")));
            }

        }
    }
}
