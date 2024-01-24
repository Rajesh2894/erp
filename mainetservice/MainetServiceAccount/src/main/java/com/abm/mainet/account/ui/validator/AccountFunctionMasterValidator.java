
package com.abm.mainet.account.ui.validator;

import java.util.Iterator;

import org.springframework.validation.BindingResult;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.acccount.dto.AccountFunctionDto;
import com.abm.mainet.common.integration.acccount.dto.AccountFunctionMasterBean;
import com.abm.mainet.common.utility.ApplicationSession;

/**
 * @author prasant.sahu
 *
 */
public class AccountFunctionMasterValidator {

    public void functionMasterValidation(final AccountFunctionMasterBean tbAcFunctionMaster, final BindingResult bindingResult,
            final Iterator<?> it) {
        final ApplicationSession session = ApplicationSession.getInstance();
        if (MainetConstants.BLANK.equals(tbAcFunctionMaster.getParentCode())) {
            bindingResult.addError(new org.springframework.validation.FieldError(MainetConstants.FUNCTION_MASTER.FUNC_MASTER,
                    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                    session.getMessage("function.master.selectParentCode")));
        }
        if (MainetConstants.BLANK.equals(tbAcFunctionMaster.getParentDesc())) {
            bindingResult.addError(new org.springframework.validation.FieldError(MainetConstants.FUNCTION_MASTER.FUNC_MASTER,
                    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                    session.getMessage("function.master.selectParentDesc")));
        }
        if (MainetConstants.BLANK.equals(tbAcFunctionMaster.getParentFinalCode())) {
            bindingResult.addError(new org.springframework.validation.FieldError(MainetConstants.FUNCTION_MASTER.FUNC_MASTER,
                    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                    session.getMessage("function.master.selectParentFinalCode")));
        }

        if (tbAcFunctionMaster.getListDto().size() == 1) {
            final AccountFunctionDto dto = tbAcFunctionMaster.getListDto().get(MainetConstants.EMPTY_LIST);
            if (MainetConstants.ZERO.equals(dto.getChildLevel()) && MainetConstants.ZERO.equals(dto.getChildParentLevel())
                    && MainetConstants.BLANK.equals(dto.getChildCode()) && MainetConstants.BLANK.equals(dto.getChildParentCode())
                    && MainetConstants.BLANK.equals(dto.getChildDesc())) {
                tbAcFunctionMaster.getListDto().remove(MainetConstants.EMPTY_LIST);
            }
        }

        if (tbAcFunctionMaster.getFunctionId() == null) {
            while (it.hasNext()) {
                if (tbAcFunctionMaster.getParentCode().equals(it.next())) {
                    bindingResult
                            .addError(new org.springframework.validation.FieldError(MainetConstants.FUNCTION_MASTER.FUNC_MASTER,
                                    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                                    session.getMessage("function.master.parentCodeExist")));
                    break;
                }
            }
        }

        for (final AccountFunctionDto dto : tbAcFunctionMaster.getListDto()) {

            if (Integer.parseInt(dto.getChildParentLevel()) > Integer.parseInt(dto.getChildLevel())) {
                bindingResult.addError(new org.springframework.validation.FieldError(MainetConstants.FUNCTION_MASTER.FUNC_MASTER,
                        MainetConstants.FUNCTION_MASTER.PARENT_LVL, null, false, new String[] { MainetConstants.ERRORS }, null,
                        session.getMessage("function.master.selectCorrectLevel")));

                if ((Integer.parseInt(dto.getChildParentLevel()) - Integer.parseInt(dto.getChildLevel())) > 1) {
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.FUNCTION_MASTER.FUNC_MASTER, MainetConstants.FUNCTION_MASTER.PARENT_LVL, null, false,
                            new String[] { MainetConstants.ERRORS }, null,
                            session.getMessage("function.master.selectCorrectLevel")));
                }
            }
        }
    }

}
