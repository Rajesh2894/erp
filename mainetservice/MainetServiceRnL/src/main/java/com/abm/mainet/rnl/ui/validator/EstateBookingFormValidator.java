package com.abm.mainet.rnl.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.rnl.ui.model.EstateBookingModel;

/**
 * @author ritesh.patil
 *
 */
@Component
public class EstateBookingFormValidator extends BaseEntityValidator<EstateBookingModel> {

    @Override
    protected void performValidations(
            final EstateBookingModel model,
            final EntityValidationContext<EstateBookingModel> entityValidationContext) {

        final ApplicationSession session = ApplicationSession.getInstance();

        if ((model.getBookingReqDTO().getApplicantDetailDto().getApplicantTitle() == null)
                || (model.getBookingReqDTO().getApplicantDetailDto().getApplicantTitle() == 0L)) {
            entityValidationContext.addOptionConstraint(session.getMessage("water.validation.ApplicantNameTitle"));
        }
        if ((model.getBookingReqDTO().getApplicantDetailDto().getApplicantFirstName() == null)
                || model.getBookingReqDTO().getApplicantDetailDto().getApplicantFirstName().isEmpty()) {
            entityValidationContext.addOptionConstraint(session.getMessage("water.validation.ApplicantFirstName"));
        }
        if ((model.getBookingReqDTO().getApplicantDetailDto().getApplicantLastName() == null)
                || model.getBookingReqDTO().getApplicantDetailDto().getApplicantLastName().isEmpty()) {
            entityValidationContext.addOptionConstraint(session.getMessage("water.validation.ApplicantLastName"));
        }
        if ((model.getBookingReqDTO().getApplicantDetailDto().getGender() == null)
                || model.getBookingReqDTO().getApplicantDetailDto().getGender().equals("0")) {
            entityValidationContext.addOptionConstraint(session.getMessage("water.validation.ApplicantGender"));
        }
        if ((model.getBookingReqDTO().getApplicantDetailDto().getMobileNo() == null)
                || model.getBookingReqDTO().getApplicantDetailDto().getMobileNo().isEmpty()) {
            entityValidationContext.addOptionConstraint(session.getMessage("water.validation.Applicantentermobileno"));
        }

        if ((model.getBookingReqDTO().getApplicantDetailDto().getAreaName() == null)
                || model.getBookingReqDTO().getApplicantDetailDto().getAreaName().isEmpty()) {
            entityValidationContext.addOptionConstraint(session.getMessage("water.validation.applicantarea"));
        }
        if ((model.getBookingReqDTO().getApplicantDetailDto().getVillageTownSub() == null)
                || model.getBookingReqDTO().getApplicantDetailDto().getVillageTownSub().isEmpty()) {
            entityValidationContext.addOptionConstraint(session.getMessage("water.validation.ApplicantcityVill"));
        }
        if ((model.getBookingReqDTO().getApplicantDetailDto().getPinCode() == null)
                || model.getBookingReqDTO().getApplicantDetailDto().getPinCode().isEmpty()) {
            entityValidationContext.addOptionConstraint(session.getMessage("water.validation.ApplicantnterPincode"));
        }

        if (model.getBookingReqDTO().getEstateBookingDTO().getFromDate() == null) {
            entityValidationContext.addOptionConstraint(session.getMessage("rnl.estate.from.date"));
        }
        if (model.getBookingReqDTO().getEstateBookingDTO().getToDate() == null) {
            entityValidationContext.addOptionConstraint(session.getMessage("rnl.estate.to.date"));
        }
        if ((model.getBookingReqDTO().getEstateBookingDTO().getShiftId() == null)
                || (model.getBookingReqDTO().getEstateBookingDTO().getShiftId() == 0L)) {
            entityValidationContext.addOptionConstraint(session.getMessage("rnl.estate.shift.name"));
        }
        if ((model.getBookingReqDTO().getEstateBookingDTO().getPurpose() == null)) {
            entityValidationContext.addOptionConstraint(session.getMessage("rnl.estate.purpose"));
        }

        /*if ((model.getBookingReqDTO().getDocumentList() != null) && !model.getBookingReqDTO().getDocumentList().isEmpty()) {
            for (final DocumentDetailsVO doc : model.getBookingReqDTO().getDocumentList()) {
                if (doc.getCheckkMANDATORY().equals(MainetConstants.CommonConstants.Y)) {
                    if (doc.getDocumentByteCode() == null) {
                        entityValidationContext.addOptionConstraint(session.getMessage("rnl.estate.mandtory.docs"));
                        break;
                    }
                }
            }
        }*/

    }
}
