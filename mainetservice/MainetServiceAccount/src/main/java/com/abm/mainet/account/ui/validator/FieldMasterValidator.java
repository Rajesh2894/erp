
package com.abm.mainet.account.ui.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.acccount.dto.AccountFieldDto;
import com.abm.mainet.common.integration.acccount.dto.AccountFieldMasterBean;
import com.abm.mainet.common.utility.ApplicationSession;

/**
 * @author tejas.kotekar
 *
 */
public class FieldMasterValidator implements Validator {

    @Override
    public boolean supports(final Class<?> clazz) {

        return AccountFieldMasterBean.class.equals(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {

        final AccountFieldMasterBean accountFieldMasterBean = (AccountFieldMasterBean) target;
        final ApplicationSession session = ApplicationSession.getInstance();

        if (accountFieldMasterBean.getListDto().size() == 1) {
            final AccountFieldDto dto = accountFieldMasterBean.getListDto().get(MainetConstants.EMPTY_LIST);
            if ((dto.getChildLevelCode() == 0L) && (dto.getChildParentLevelCode() == 0L)
                    && MainetConstants.BLANK.equals(dto.getChildCode())
                    && MainetConstants.BLANK.equals(dto.getChildParentCode())
                    && MainetConstants.BLANK.equals(dto.getChildDesc())) {
                accountFieldMasterBean.getListDto().remove(MainetConstants.EMPTY_LIST);
            }
        }

        for (final AccountFieldDto accountFieldDto : accountFieldMasterBean.getListDto()) {
            if ((accountFieldDto.getChildCode() == null) || accountFieldDto.getChildCode().isEmpty()) {
                ValidationUtils.rejectIfEmpty(errors, MainetConstants.FIELD_MASTER.FIELD_CODE, "scrutiny.mode.empty.err",
                        session.getMessage("field.master.validator.fieldchildcode"));
            }
            if ((accountFieldDto.getChildDesc() == null) || accountFieldDto.getChildDesc().isEmpty()) {
                ValidationUtils.rejectIfEmpty(errors, MainetConstants.FIELD_MASTER.FIELD_DESC, "scrutiny.mode.empty.err",
                        session.getMessage("field.master.validator.fieldchilddesc"));
            }
        }

        if ((accountFieldMasterBean.getFieldCode() == null)
                || accountFieldMasterBean.getFieldCode().equals(MainetConstants.BLANK)) {
            ValidationUtils.rejectIfEmpty(errors, MainetConstants.FUND_MASTER.PARENT_CODE, "scrutiny.level.empty.err",
                    session.getMessage("field.master.validator.fieldcode"));

        }
        if ((accountFieldMasterBean.getFieldCompcode() == null)
                || accountFieldMasterBean.getFieldCompcode().equals(MainetConstants.BLANK)) {
            ValidationUtils.rejectIfEmpty(errors, MainetConstants.FIELD_MASTER.PARENT_FINAL_CODE, "scrutiny.role.empty.err",
                    session.getMessage("field.master.validator.parentfield"));
        }
        if ((accountFieldMasterBean.getFieldDesc() == null)
                || accountFieldMasterBean.getFieldDesc().equals(MainetConstants.BLANK)) {
            ValidationUtils.rejectIfEmpty(errors, MainetConstants.FIELD_MASTER.PARENT_DESC, "scrutiny.mode.empty.err",
                    session.getMessage("field.master.validator.fielddesc"));
        }

    }

}
