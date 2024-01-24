package com.abm.mainet.care.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.care.dto.report.ComplaintReportRequestDTO;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;

@Component
public class GrievanceReportValidator extends BaseEntityValidator<ComplaintReportRequestDTO> {

    @Override
    protected void performValidations(ComplaintReportRequestDTO entity,
            EntityValidationContext<ComplaintReportRequestDTO> entityValidationContext) {

        if (entity.getFromDate() != null && entity.getToDate() != null)
            entityValidationContext.rejectCompareDate(entity.getFromDate(), "fromDate", entity.getToDate(), "toDate");
        if (entity.getDepartment() != null)
            entityValidationContext.rejectIfNotSelected(entity.getDepartment(), "department");
        if (entity.getStatus() != null)
            entityValidationContext.rejectIfNotSelected(entity.getStatus(), "status");
        if (entity.getComplaintType() != null)
            entityValidationContext.rejectIfNotSelected(entity.getComplaintType(), "complaintType");
        if (entity.getReportType() != null)
            entityValidationContext.rejectIfNotSelected(entity.getReportType(), "reportType");
        if (entity.getSlaStatus() != null)
            entityValidationContext.rejectIfNotSelected(entity.getSlaStatus(), "slaStatus");
        if (entity.getReferenceMode() != null)
        entityValidationContext.rejectIfNotSelected(entity.getReferenceMode(), "modeType");
        
    }

}
