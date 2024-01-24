package com.abm.mainet.water.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;

/**
 * @author Rahul.Yadav
 *
 */
@Component
public class WaterBillGenerationValidator extends BaseEntityValidator<TbCsmrInfoDTO> {

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.core.validator.BaseEntityValidator#performValidations(java.lang.Object,
     * com.abm.mainetservice.web.core.validator.EntityValidationContext)
     */
    @Override
    protected void performValidations(final TbCsmrInfoDTO entity,
            final EntityValidationContext<TbCsmrInfoDTO> entityValidationContext) {
        entityValidationContext.rejectIfNotSelected(entity.getCsMeteredccn(), "csMeteredccn");
        entityValidationContext.rejectIfNotSelected(entity.getCsCcnsize(), "csCcnsize");
        performGroupValidation("codDwzid");
        performGroupValidation("trmGroup");
    }

}
