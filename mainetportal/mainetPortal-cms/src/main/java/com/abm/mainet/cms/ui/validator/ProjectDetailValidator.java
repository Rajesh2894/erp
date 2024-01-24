package com.abm.mainet.cms.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.AboutProject;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;

/**
 * @author Manish.Gawali
 */
@Component
public class ProjectDetailValidator extends BaseEntityValidator<AboutProject> {

    @Override
    protected void performValidations(final AboutProject entity, final EntityValidationContext<AboutProject> validationContext) {

        validationContext.rejectIfEmpty(entity.getDescTitleEng(), "descTitleEng");
        validationContext.rejectIfEmpty(entity.getDescTitleReg(), "descTitleReg");

        validationContext.rejectIfEmpty(entity.getDescInfoEng(), "descInfoEng");
        validationContext.rejectIfEmpty(entity.getDescInfoReg(), "descInfoReg");

    }

}
