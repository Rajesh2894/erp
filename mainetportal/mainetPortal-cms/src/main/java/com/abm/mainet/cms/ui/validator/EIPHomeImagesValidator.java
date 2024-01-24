package com.abm.mainet.cms.ui.validator;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cms.domain.EIPHomeImages;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.dms.utility.FileUploadUtility;

/**
 * @author vinay.jangir
 *
 */

@Component
public class EIPHomeImagesValidator extends BaseEntityValidator<EIPHomeImages> {

    @Transactional
    @Override
    protected void performValidations(final EIPHomeImages entity,
            final EntityValidationContext<EIPHomeImages> entityValidationContext) {
        if (entity.getModuleType().equals(MainetConstants.FlagS)) {
            entityValidationContext.rejectIfNotSelected(entity.getHmImgOrder(), "ImageOrder");
        } else {
            entityValidationContext.rejectIfNotSelected(entity.getHmImgOrder(), "ImageSideOrder");
        }

        if (FileUploadUtility.getCurrent().getFileMap().size() == 0) {
            entityValidationContext.rejectIfNull(entity.getImagePath(),getApplicationSession().getMessage("mayorForm.ProfileImage"));
        }

    }

}
