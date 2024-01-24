package com.abm.mainet.swm.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.swm.dto.WasteCollectorDTO;
import com.abm.mainet.swm.ui.model.WasteCollectorModel;

/**
 * @author sarojkumar.yadav
 *
 */
@Component
public class WasteCollectorValidator extends BaseEntityValidator<WasteCollectorModel> {

    @Override
    protected void performValidations(final WasteCollectorModel model,
            final EntityValidationContext<WasteCollectorModel> entityValidationContext) {

        final ApplicationSession session = ApplicationSession.getInstance();
        final ApplicantDetailDTO applicantDetailDTO = model.getCollectorReqDTO().getApplicantDetailDto();
        final WasteCollectorDTO collectorDTO = model.getCollectorReqDTO().getCollectorDTO();

        if ((applicantDetailDTO.getApplicantTitle() == null) || (applicantDetailDTO.getApplicantTitle() == 0L)) {
            entityValidationContext
                    .addOptionConstraint(session.getMessage("construct.demolition.validation.ApplicantNameTitle"));
        }
        if ((applicantDetailDTO.getApplicantFirstName() == null)
                || applicantDetailDTO.getApplicantFirstName().isEmpty()) {
            entityValidationContext
                    .addOptionConstraint(session.getMessage("construct.demolition.validation.ApplicantFirstName"));
        }
        if ((applicantDetailDTO.getApplicantLastName() == null)
                || applicantDetailDTO.getApplicantLastName().isEmpty()) {
            entityValidationContext
                    .addOptionConstraint(session.getMessage("construct.demolition.validation.ApplicantLastName"));
        }
        if ((applicantDetailDTO.getGender() == null) || applicantDetailDTO.getGender().equals("0")) {
            entityValidationContext
                    .addOptionConstraint(session.getMessage("construct.demolition.validation.ApplicantGender"));
        }
        if ((applicantDetailDTO.getMobileNo() == null) || applicantDetailDTO.getMobileNo().isEmpty()) {
            entityValidationContext
                    .addOptionConstraint(session.getMessage("construct.demolition.validation.Applicantentermobileno"));
        }

        if ((applicantDetailDTO.getAreaName() == null) || applicantDetailDTO.getAreaName().isEmpty()) {
            entityValidationContext
                    .addOptionConstraint(session.getMessage("construct.demolition.validation.applicantarea"));
        }
        if ((applicantDetailDTO.getPinCode() == null) || applicantDetailDTO.getPinCode().isEmpty()) {
            entityValidationContext
                    .addOptionConstraint(session.getMessage("construct.demolition.validation.ApplicantnterPincode"));
        }
        if ((collectorDTO.getCapacity() == null) || collectorDTO.getCapacity() == 0d) {
            entityValidationContext.addOptionConstraint(session.getMessage("construct.demolition.validation.capacity"));
        }
        /*
         * if ((collectorDTO.getBldgPermission() == null) || collectorDTO.getBldgPermission().isEmpty() ||
         * collectorDTO.getBldgPermission().equals("N")) { entityValidationContext
         * .addOptionConstraint(session.getMessage("construct.demolition.validation.permission")); }
         */
        if ((collectorDTO.getVehicleType() == null) || collectorDTO.getVehicleType() == 0l) {
            entityValidationContext.addOptionConstraint(session.getMessage("construct.demolition.validation.vehicleId"));
        }
        if ((collectorDTO.getLocationId() == null) || collectorDTO.getLocationId() == 0l) {
            entityValidationContext.addOptionConstraint(session.getMessage("construct.demolition.validation.locationId"));
        }
        if ((collectorDTO.getNoTrip() == null) || collectorDTO.getNoTrip() == 0l) {
            entityValidationContext.addOptionConstraint(session.getMessage("construct.demolition.validation.trip"));
        }

        if ((collectorDTO.getDocumentList() != null) && !collectorDTO.getDocumentList().isEmpty()) {
            for (final DocumentDetailsVO doc : collectorDTO.getDocumentList()) {
                if (doc.getCheckkMANDATORY().equals("Y")) {
                    if (doc.getDocumentByteCode() == null) {
                        entityValidationContext.addOptionConstraint(session.getMessage("rnl.estate.mandtory.docs"));
                        break;
                    }
                }
            }
        }

    }
}
