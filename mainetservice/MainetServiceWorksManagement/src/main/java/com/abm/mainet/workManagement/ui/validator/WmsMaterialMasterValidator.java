package com.abm.mainet.workManagement.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.workManagement.dto.WmsMaterialMasterDto;

@Component
public class WmsMaterialMasterValidator extends BaseEntityValidator<WmsMaterialMasterDto> {

    @Override
    protected void performValidations(WmsMaterialMasterDto entity,
            EntityValidationContext<WmsMaterialMasterDto> entityValidationContext) {

        entityValidationContext = new EntityValidationContext<WmsMaterialMasterDto>(entity);

        if (entity.getMaTypeId() == null) {
            entityValidationContext.addOptionConstraint(getApplicationSession().getMessage("material.master.ratetype"));
        }
        if (entity.getMaItemNo() == null || entity.getMaItemNo().isEmpty()) {
            entityValidationContext.addOptionConstraint(getApplicationSession().getMessage("material.master.itomcode"));
        }
        if (entity.getMaDescription() == null || entity.getMaDescription().isEmpty()) {
            entityValidationContext
                    .addOptionConstraint(getApplicationSession().getMessage("material.master.matreialname"));
        }
        if (entity.getMaItemUnit() == null) {
            entityValidationContext.addOptionConstraint(getApplicationSession().getMessage("work.management.unit"));
        }
        if (entity.getMaRate() == null) {
            entityValidationContext.addOptionConstraint(getApplicationSession().getMessage("sor.baserate"));
        }

    }

}
