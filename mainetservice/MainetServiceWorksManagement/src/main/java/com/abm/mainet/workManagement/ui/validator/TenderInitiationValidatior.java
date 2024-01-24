package com.abm.mainet.workManagement.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.workManagement.dto.TenderMasterDto;

/**
 * @author hiren.poriya
 * @Since 19-Apr-2018
 */
@Component
public class TenderInitiationValidatior extends BaseEntityValidator<TenderMasterDto> {

    @Override
    protected void performValidations(TenderMasterDto dto,
            EntityValidationContext<TenderMasterDto> validationContext) {

        if (dto.getDeptId() == null || dto.getDeptId() == 0) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("tender.select.dept"));
        }

        if(!MainetConstants.DEPT_SHORT_NAME.STORE.equalsIgnoreCase(dto.getDeptCode())) {
	        if (dto.getProjId() == null || dto.getProjId() == 0) {
	            validationContext.addOptionConstraint(getApplicationSession().getMessage("work.Def.valid.select.projName"));
	        }
        } 

        if (dto.getTenderCategory() == null || dto.getTenderCategory() == 0) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("tender.select.category"));
        }

        if (dto.getResolutionDate() != null && !dto.getResolutionDate().toString().isEmpty()) {
			/*
			 * if (dto.getResolutionNo() == null || dto.getResolutionNo().isEmpty()) {
			 * validationContext.addOptionConstraint(getApplicationSession().getMessage(
			 * "tender.enter.resolutionNo")); }
			 */
        }

        dto.getWorkDto().forEach(work -> {
            if (work.isInitiated()) {
                if (work.getTenderFeeAmt() == null) {
                    validationContext.addOptionConstraint(getApplicationSession().getMessage("wms.tender.enter.fees.amount"));
                }
                if (work.getVenderClassId() == null) {
                    validationContext.addOptionConstraint(getApplicationSession().getMessage("wms.tender.select.vendor.classid"));
                }

                if (work.getTenderSecAmt() == null) {
                    validationContext
                            .addOptionConstraint(getApplicationSession().getMessage("wms.tender.enter.securitydeposite.amount"));
                }
                if (work.getVendorWorkPeriodUnit() == null) {
                    validationContext
                            .addOptionConstraint(getApplicationSession().getMessage("wms.tender.select.workduration.period.unit"));
                }

                if (work.getVendorWorkPeriod() == null) {
                    validationContext
                            .addOptionConstraint(getApplicationSession().getMessage("wms.tender.select.workduration.period"));
                }
            }
        });
    }

}
