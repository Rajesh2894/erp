package com.abm.mainet.water.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.water.ui.model.NoDuesCertificateModel;

@Component
public class WaterNoDuesCertificateValidator extends BaseEntityValidator<NoDuesCertificateModel> {

    @Override
    protected void performValidations(final NoDuesCertificateModel model,
            final EntityValidationContext<NoDuesCertificateModel> entityValidationContext) {
        if ((model.getApplicantDetailDto().getApplicantFirstName() != null)
                && model.getApplicantDetailDto().getApplicantFirstName().isEmpty()) {
            entityValidationContext.addOptionConstraint("water.validation.ApplicantFirstName");
        }

        if ((model.getApplicantDetailDto().getApplicantTitle() != null)
                && (model.getApplicantDetailDto().getApplicantTitle() == 0L)) {
            entityValidationContext.addOptionConstraint("water.validation.ApplicantNameTitle");
        }
        if ((model.getApplicantDetailDto().getApplicantLastName() != null)
                && model.getApplicantDetailDto().getApplicantLastName().isEmpty()) {
            entityValidationContext.addOptionConstraint("water.validation.ApplicantLastName");
        }
        if ((model.getApplicantDetailDto().getMobileNo() != null) && model.getApplicantDetailDto().getMobileNo().isEmpty()) {
            entityValidationContext.addOptionConstraint("water.validation.applicantMobileNo");
        }

        if ((model.getApplicantDetailDto().getPinCode() != null) && model.getApplicantDetailDto().getPinCode().isEmpty()) {
            entityValidationContext.addOptionConstraint("water.validation.applicantPinCode");
        }

        if ((model.getApplicantDetailDto().getIsBPL() != null) && model.getApplicantDetailDto().getIsBPL().isEmpty()) {
            entityValidationContext.addOptionConstraint("water.validation.isabovepovertyline");
        }
        if ((model.getApplicantDetailDto().getIsBPL() != null)
                && model.getApplicantDetailDto().getIsBPL().equals(MainetConstants.NewWaterServiceConstants.YES)) {
            if (model.getApplicantDetailDto().getBplNo().equals(MainetConstants.BLANK)) {
                entityValidationContext.addOptionConstraint("water.validation.bplnocantempty");
            }
        }

        if ((model.getReqDTO().getConsumerNo() != null) && model.getReqDTO().getConsumerNo().isEmpty()) {
            entityValidationContext.addOptionConstraint("nodue.consumerNo");
        }
        
        if(model.getApplicantDetailDto().getAreaName() != null && model.getApplicantDetailDto().getAreaName().isEmpty()) {
        	entityValidationContext.addOptionConstraint("water.validation.areaName");
        }
       
        /*if(model.getApplicantDetailDto().getVillageTownSub() != null && model.getApplicantDetailDto().getVillageTownSub().isEmpty()) {
        	entityValidationContext.addOptionConstraint("water.validation.village");
        }*/

        if ((model.getReqDTO().getNoOfCopies() != null) && model.getReqDTO().getNoOfCopies().equals(MainetConstants.BLANK)) {
            entityValidationContext.addOptionConstraint("nodue.noCopy");
        }

    }
}
