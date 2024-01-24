package com.abm.mainet.cms.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.SubLinkFieldDetails;
import com.abm.mainet.cms.ui.model.SectionEntryFormModel;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;

/**
 * @author Pranit.Mhatre
 * @since 21 February, 2014
 */
@Component
public class SectionEntryValidator extends BaseEntityValidator<SectionEntryFormModel> {

    @Override
    protected void performValidations(final SectionEntryFormModel model,
            final EntityValidationContext<SectionEntryFormModel> validationContext) {
        validationContext.rejectIfEmpty(model.getEntity().getLinksMaster().getLinkId(), "moduleName", true);
        if (model.getEntity().getIsLinkModify().equals("null")) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("eip.err.isHighlighted"));
        }

        if (model.getEntity().getSubLinkOrder() == null) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("eip.order.empty"));
        }

        validationContext.rejectIfEmpty(model.getEntity().getSubLinkNameEn(), "sectionNameEn");
        validationContext.rejectIfEmpty(model.getEntity().getSubLinkNameRg(), "sectionNameRg");
        validationContext.rejectIfNotSelected(model.getEntity().getSectionType0(), "sectionType");

        if (!model.isListMode()) {
            for (final SubLinkFieldDetails obj : model.getSubLinkFieldMapping().getSubLinkFieldlist()) {
                validationContext.rejectIfEmpty(obj.getFieldNameEn(), "fieldNameEn");
                validationContext.rejectIfEmpty(obj.getFieldNameRg(), "fieldNameRg");
                validationContext.rejectIfEmpty(obj.getFieldType(), "fieldType", true);
            }

        }

    }

}
