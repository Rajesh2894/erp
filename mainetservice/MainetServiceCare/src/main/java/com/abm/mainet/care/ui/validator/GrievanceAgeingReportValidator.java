package com.abm.mainet.care.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.care.dto.report.ComplaintReportRequestDTO;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;

@Component
public class GrievanceAgeingReportValidator extends BaseEntityValidator<ComplaintReportRequestDTO> {

    @Override
    protected void performValidations(ComplaintReportRequestDTO entity,
            EntityValidationContext<ComplaintReportRequestDTO> entityValidationContext) {

        if (entity.getDepartment() != null)
            entityValidationContext.rejectIfNotSelected(entity.getDepartment(), "department");
        if (entity.getStatus() != null)
            entityValidationContext.rejectIfNotSelected(entity.getStatus(), "status");
        if (entity.getComplaintType() != null)
            entityValidationContext.rejectIfNotSelected(entity.getComplaintType(), "complaintType");
        if (entity.getReportType() != null)
            entityValidationContext.rejectIfNotSelected(entity.getReportType(), "reportType");

        // entityValidationContext.rejectIfEmpty(entity.getFromSlab(), "fromSlab");
        // entityValidationContext.rejectIfEmpty(entity.getToSlab(), "toSlab");
        // entityValidationContext.rejectNotInRangeFromZero(entity.getFromSlab(), "fromSlab", entity.getToSlab(), "toSlab");
        entityValidationContext.rejectIfEmpty(entity.getSlabLevels(), "slabLevels");
    }

}
