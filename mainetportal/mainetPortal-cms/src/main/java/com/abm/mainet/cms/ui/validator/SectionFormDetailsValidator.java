package com.abm.mainet.cms.ui.validator;

import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.SubLinkFieldDetails;
import com.abm.mainet.cms.domain.SubLinkFieldMapping;
import com.abm.mainet.cms.ui.model.SectionDetailsModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.util.Utility;

/**
 * @author pranit.mhatre
 */
@Component
public class SectionFormDetailsValidator extends BaseEntityValidator<SectionDetailsModel> {

    @Override
    protected void performValidations(final SectionDetailsModel entity,
            final EntityValidationContext<SectionDetailsModel> validationContext) {
        final SubLinkFieldDetails fieldDetails = entity.getEntity();

        String fieldName = MainetConstants.BLANK;
        String fieldNameEn = MainetConstants.BLANK;
        String fieldNameRg = MainetConstants.BLANK;

        final BeanWrapper wrapper = new BeanWrapperImpl(fieldDetails);
        if(fieldDetails.getSubLinkMaster().getIsArchive() !=null && fieldDetails.getSubLinkMaster().getIsArchive().equals(MainetConstants.FlagY)) {
        	
        	validationContext.rejectIfNull(fieldDetails.getIssueDate(), "issueDate");
            validationContext.rejectIfNull(fieldDetails.getValidityDate(), "validityDate");
            
        if ((fieldDetails.getIssueDate() != null) && (fieldDetails.getValidityDate() != null)) {
			
            if (fieldDetails.getValidityDate().before(fieldDetails.getIssueDate())) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("PublicNotices.date.validate"));
            }

        }
        }

        final List<SubLinkFieldMapping> mappings = entity.getSection().getSubLinkFieldMappings();
        for (final ListIterator<SubLinkFieldMapping> iterator = mappings.listIterator(); iterator.hasNext();) {
            final SubLinkFieldMapping map = iterator.next();

            if (MainetConstants.IsDeleted.NOT_DELETE.equals(map.getIsDeleted())
                    && MainetConstants.IsDeleted.DELETE.equals(map.getIsUsed())
                    && MainetConstants.IsDeleted.DELETE.equals(map.getIsMandatory())) {
                final boolean isInputField = ((map.getFieldType() == MainetConstants.TEXT_FIELD)
                        || (map.getFieldType() == MainetConstants.TEXT_AREA) || (map.getFieldType() == MainetConstants.DROP_DOWN_BOX));
                final boolean isCkEditor = (map.getFieldType() == MainetConstants.TEXT_AREA_HTML);

                fieldName = map.getFiledNameMap().toLowerCase();
                if (isCkEditor) {
                    if ((null == fieldDetails.getTxta_03_ren_blob()) || (fieldDetails.getTxta_03_ren_blob().isEmpty())) {
                        validationContext.addOptionConstraint("section.add.english.validate");
                    }
                    if ((null == fieldDetails.getTxta_03_en_nnclob()) || fieldDetails.getTxta_03_en_nnclob().isEmpty()) {
                        validationContext.addOptionConstraint("section.add.hindi.validate");
                    }
                } else if (isInputField) {
                    fieldNameRg = fieldName + "_rg";
                    fieldNameEn = fieldName + "_en";

                    if (wrapper.isReadableProperty(fieldNameEn) || wrapper.isReadableProperty(fieldNameRg)) {
                        validationContext.rejectIfEmpty(wrapper.getPropertyValue(fieldNameEn).toString(),
                                map.getFieldNameEn());
                        validationContext.rejectIfEmpty(wrapper.getPropertyValue(fieldNameRg).toString(),
                                map.getFieldNameRg());
                    } else {
                        throw new FrameworkException(
                                "Unable to find readonly method for " + fieldNameEn + " or " + fieldNameRg);
                    }

                } else {
                    final Object val = wrapper.getPropertyValue(fieldName);

                    if (val instanceof Date) {
                        validationContext.rejectInvalidDate(Utility.converObjectToDate(val), map.getFieldNameEn());
                    }

                    validationContext.rejectIfEmpty(val, map.getFieldNameEn());
                }

            }

        }
    }

}
