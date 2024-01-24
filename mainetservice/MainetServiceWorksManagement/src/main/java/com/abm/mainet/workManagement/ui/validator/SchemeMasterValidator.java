package com.abm.mainet.workManagement.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.workManagement.dto.SchemeMastDatailsDTO;
import com.abm.mainet.workManagement.dto.SchemeMasterDTO;

/**
 * @author vishwajeet.kumar
 * @since 12 Dec 2017
 */
@Component
public class SchemeMasterValidator extends BaseEntityValidator<SchemeMasterDTO> {

    @Override
    protected void performValidations(SchemeMasterDTO schemeDto,
            EntityValidationContext<SchemeMasterDTO> validationContext) {
        /*
         * if (schemeDto.getWmSchCode() == null || schemeDto.getWmSchCode().isEmpty()) {
         * validationContext.addOptionConstraint(getApplicationSession().getMessage("scheme.master.validation.schemecode")); }
         */
        if (schemeDto.getWmSchNameEng() == null || schemeDto.getWmSchNameEng().isEmpty()) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("scheme.master.validation.schemename.eng"));
        }
        if (schemeDto.getWmSchNameReg() == null || schemeDto.getWmSchNameReg().isEmpty()) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("scheme.master.validation.schemename.reg"));
        }
        /*
         * if (schemeDto.getWmSchStrDate() == null || schemeDto.getWmSchStrDate().toString().isEmpty()) {
         * validationContext.addOptionConstraint(getApplicationSession().getMessage("scheme.master.validation.schemestartdate"));
         * }
         */

        for (SchemeMastDatailsDTO detailsDTO : schemeDto.getMastDetailsDTO()) {
            if (detailsDTO.getSchDSpon() != null && !detailsDTO.getSchDSpon().isEmpty()) {
                if (detailsDTO.getSchSharPer() == null) {
                    validationContext
                            .addOptionConstraint(getApplicationSession().getMessage("scheme.master.enter.sharePercentage"));
                }
            }
        }

    }

}
