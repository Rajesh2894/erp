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
			entityValidationContext.addOptionConstraint("water.firstName");
		}

		if ((model.getApplicantDetailDto().getApplicantTitle() != null)
				&& (model.getApplicantDetailDto().getApplicantTitle() == 0L)) {
			entityValidationContext.addOptionConstraint("water.title");
		}
		if ((model.getApplicantDetailDto().getApplicantLastName() != null)
				&& model.getApplicantDetailDto().getApplicantLastName().isEmpty()) {
			entityValidationContext.addOptionConstraint("water.lastName");
		}
		if ((model.getApplicantDetailDto().getMobileNo() != null)
				&& model.getApplicantDetailDto().getMobileNo().isEmpty()) {
			entityValidationContext.addOptionConstraint("water.mobile");
		}
		if ((model.getApplicantDetailDto().getAreaName() != null)
				&& model.getApplicantDetailDto().getAreaName().isEmpty()) {
			entityValidationContext.addOptionConstraint("water.area");
		}
		
		  /*if ((model.getApplicantDetailDto().getVillageTownSub() != null) &&
		  model.getApplicantDetailDto().getVillageTownSub().isEmpty()) {
		  entityValidationContext.addOptionConstraint("water.vtc"); }*/
		 
		if ((model.getApplicantDetailDto().getPinCode() != null)
				&& model.getApplicantDetailDto().getPinCode().isEmpty()) {
			entityValidationContext.addOptionConstraint("water.pincode");
		}

		if ((model.getApplicantDetailDto().getIsBPL() != null) && model.getApplicantDetailDto().getIsBPL().isEmpty()) {
			entityValidationContext.addOptionConstraint("water.pline");
		}
		if ((model.getApplicantDetailDto().getIsBPL() != null)
				&& model.getApplicantDetailDto().getIsBPL().equals(MainetConstants.YES)) {
			if (model.getApplicantDetailDto().getBplNo().isEmpty()) {
				entityValidationContext.addOptionConstraint("water.bpl");
			}
		}

		if ((model.getReqDTO().getConsumerNo() != null) && model.getReqDTO().getConsumerNo().isEmpty()) {
			entityValidationContext.addOptionConstraint("water.consumer");
		}

		if ((model.getReqDTO().getConsumerName() != null)
				&& model.getReqDTO().getConsumerName().equals(MainetConstants.BLANK)) {
			entityValidationContext.addOptionConstraint("water.ConName");
		}
		if ((model.getReqDTO().getNoOfCopies() != null) && model.getReqDTO().getNoOfCopies().equals(0L)) {
			entityValidationContext.addOptionConstraint("water.copyNo");
		}
	}
}
