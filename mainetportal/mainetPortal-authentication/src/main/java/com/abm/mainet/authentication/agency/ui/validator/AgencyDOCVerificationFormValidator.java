package com.abm.mainet.authentication.agency.ui.validator;

import java.util.List;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;

@Component
public class AgencyDOCVerificationFormValidator extends BaseEntityValidator<Employee> {

    @Override
    protected void performValidations(final Employee entity, final EntityValidationContext<Employee> entityValidationContext) {

        final List<LookUp> lookUps = getApplicationSession()
                .getHierarchicalLookUp(UserSession.getCurrent().getOrganisation(), MainetConstants.NEC.PARENT)
                .get(MainetConstants.NEC.PARENT);

        for (final LookUp lookUp : lookUps) {
            if (lookUp.getLookUpId() == entity.getEmplType()) {
                if (lookUp.getLookUpCode().equals(MainetConstants.NEC.ARCHITECT)
                        || lookUp.getLookUpCode().equals(MainetConstants.NEC.BUILDER)
                        || lookUp.getLookUpCode().equals(MainetConstants.NEC.ENGINEER)) {

                    if ((entity.getAgencyLocation() == null) || entity.getAgencyLocation().equals(MainetConstants.BLANK)) {
                        entityValidationContext
                                .addOptionConstraint(getApplicationSession().getMessage("eip.agency.location.error"));
                    }

                    if ((entity.getAgencyYearsOfExp() == null) || (entity.getAgencyYearsOfExp() == 0)) {
                        entityValidationContext.addOptionConstraint(getApplicationSession().getMessage("eip.agency.Exp.error"));
                    }
                    if ((entity.getAgencyQualification() == null)
                            || entity.getAgencyQualification().equals(MainetConstants.BLANK)) {
                        entityValidationContext
                                .addOptionConstraint(getApplicationSession().getMessage("eip.agency.qualification.error"));
                    }
                }

                if (lookUp.getLookUpCode().equals(MainetConstants.NEC.HOSPITAL)) {
                    if ((entity.getAgencyLocation() == null) || entity.getAgencyLocation().equals(MainetConstants.BLANK)) {
                        entityValidationContext
                                .addOptionConstraint(getApplicationSession().getMessage("eip.agency.location.error"));
                    }
                    if ((entity.getAgencyRegNo() == null) || (entity.getAgencyRegNo() == 0)) {
                        entityValidationContext.addOptionConstraint(getApplicationSession().getMessage("eip.agency.RegNo.error"));
                    }

                    if (entity.getHospitalType() == 0) {
                        entityValidationContext
                                .addOptionConstraint(getApplicationSession().getMessage("eip.agency.hospital.type.error"));
                    }

                    if ((entity.getHospitalNameInHindi() == null)
                            || entity.getHospitalNameInHindi().equals(MainetConstants.BLANK)) {
                        entityValidationContext
                                .addOptionConstraint(getApplicationSession().getMessage("eip.agency.hospital.name.hindi"));
                    }

                    if ((entity.getHospitalAddressInHindi() == null)
                            || entity.getHospitalAddressInHindi().equals(MainetConstants.BLANK)) {
                        entityValidationContext
                                .addOptionConstraint(getApplicationSession().getMessage("eip.agency.hospital.address.hindi"));
                    }

                    if ((entity.getHospitalAddressInHindi() != null)
                            || !entity.getHospitalAddressInHindi().equals(MainetConstants.BLANK)
                            || (entity.getHospitalNameInHindi() != null)
                            || !entity.getHospitalNameInHindi().equals(MainetConstants.BLANK)) {

                        final String str = entity.getHospitalAddressInHindi() + MainetConstants.BLANK
                                + entity.getHospitalNameInHindi();

                        for (final char c : str.toCharArray()) {
                            final int b = c;
                            if (((b >= 65) && (b <= 90)) || ((b >= 97) && (b <= 122))) {
                                entityValidationContext.addOptionConstraint(getApplicationSession().getMessage(
                                        " eip.agency.validation.fields.inhindi  "));
                                break;
                            }
                        }
                    }
                }

                if (lookUp.getLookUpCode().equals(MainetConstants.NEC.CYBER)
                        || lookUp.getLookUpCode().equals(MainetConstants.NEC.CENTER)) {
                    if ((entity.getAgencyLocation() == null) || entity.getAgencyLocation().equals(MainetConstants.BLANK)) {
                        entityValidationContext
                                .addOptionConstraint(getApplicationSession().getMessage("eip.agency.location.error"));
                    }
                }

                if (lookUp.getLookUpCode().equals(MainetConstants.NEC.CREMATORIA)) {
                    if ((entity.getAgencyLocation() == null) || entity.getAgencyLocation().equals(MainetConstants.BLANK)) {
                        entityValidationContext
                                .addOptionConstraint(getApplicationSession().getMessage("eip.agency.location.error"));
                    }
                }

            }
        }

    }
}
