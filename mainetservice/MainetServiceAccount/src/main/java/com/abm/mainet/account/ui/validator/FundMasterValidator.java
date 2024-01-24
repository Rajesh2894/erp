
package com.abm.mainet.account.ui.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.acccount.dto.AccountFundDto;
import com.abm.mainet.common.integration.acccount.dto.AccountFundMasterBean;
import com.abm.mainet.common.utility.ApplicationSession;

/**
 * @author prasant.sahu
 *
 */

public class FundMasterValidator implements Validator {
    @Override
    public boolean supports(final Class<?> aClass) {
        return AccountFundMasterBean.class.equals(aClass);
    }

    @Override
    public void validate(final Object object, final Errors errors) {
        final AccountFundMasterBean accountFundMasterBean = (AccountFundMasterBean) object;
        final ApplicationSession session = ApplicationSession.getInstance();
        if (accountFundMasterBean.getListDto().size() == 1) {
            final AccountFundDto dto = accountFundMasterBean.getListDto().get(MainetConstants.EMPTY_LIST);
            if ((dto.getChildLevelCode() == 0L) && (dto.getChildParentLevelCode() == 0L)
                    && MainetConstants.BLANK.equals(dto.getChildCode())
                    && MainetConstants.BLANK.equals(dto.getChildParentCode())
                    && MainetConstants.BLANK.equals(dto.getChildDesc())) {
                accountFundMasterBean.getListDto().remove(MainetConstants.EMPTY_LIST);
            }
        }

        for (final AccountFundDto accountFundDto : accountFundMasterBean.getListDto()) {
            if ((accountFundDto.getChildCode() == null) || accountFundDto.getChildCode().isEmpty()) {
                ValidationUtils.rejectIfEmpty(errors, MainetConstants.FUND_MASTER.FUND_CODE, "scrutiny.mode.empty.err",
                        session.getMessage("fund.master.validator.fundchildcode"));
            }
            if ((accountFundDto.getChildDesc() == null) || accountFundDto.getChildDesc().isEmpty()) {
                ValidationUtils.rejectIfEmpty(errors, MainetConstants.FUND_MASTER.FUND_DESC, "scrutiny.mode.empty.err",
                        session.getMessage("fund.master.validator.fundchilddesc"));
            }
        }

        if ((accountFundMasterBean.getParentCode() == null)
                || accountFundMasterBean.getParentCode().equals(MainetConstants.BLANK)) {
            ValidationUtils.rejectIfEmpty(errors, MainetConstants.FUNCTION_MASTER.PARENT_LVL, "scrutiny.level.empty.err",
                    session.getMessage("fund.master.validator.fundlevel"));

        }
        if ((accountFundMasterBean.getFundCode() == null) || accountFundMasterBean.getFundCode().equals(MainetConstants.BLANK)) {
            ValidationUtils.rejectIfEmpty(errors, MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.PARENTCODE,
                    "scrutiny.level.empty.err", session.getMessage("fund.master.validator.fundcode"));

        }
        if ((accountFundMasterBean.getFundDesc() == null) || accountFundMasterBean.getFundDesc().equals(MainetConstants.BLANK)) {
            ValidationUtils.rejectIfEmpty(errors, MainetConstants.FIELD_MASTER.PARENT_DESC, "scrutiny.mode.empty.err",
                    session.getMessage("fund.master.validator.funddesc"));
        }

    }
}
