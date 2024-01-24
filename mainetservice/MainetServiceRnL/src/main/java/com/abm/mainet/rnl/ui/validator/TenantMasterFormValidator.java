package com.abm.mainet.rnl.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.rnl.dto.TenantMaster;
import com.abm.mainet.rnl.ui.model.TenantMasterModel;

/**
 * @author ritesh.patil
 *
 */
@Component
public class TenantMasterFormValidator extends BaseEntityValidator<TenantMasterModel> {

    @Override
    protected void performValidations(
            final TenantMasterModel model,
            final EntityValidationContext<TenantMasterModel> entityValidationContext) {

        final ApplicationSession session = ApplicationSession
                .getInstance();
        final TenantMaster tenantMaster = model.getTenantMaster();

        if (tenantMaster.getType() == null) {
            entityValidationContext
                    .addOptionConstraint(session.getMessage(
                            "rnl.tenant.type"));
        }

        if ((tenantMaster.getTitle() == null) || (tenantMaster.getTitle() == '0')) {
            entityValidationContext
                    .addOptionConstraint(session.getMessage(
                            "rnl.tenant.title"));
        }
        if ((tenantMaster.getfName() == null) || tenantMaster.getfName().isEmpty()) {
            entityValidationContext
                    .addOptionConstraint(session.getMessage(
                            "rnl.tenant.fname"));
        }
        if ((tenantMaster.getlName() == null)
                || tenantMaster.getlName().isEmpty()) {
            entityValidationContext
                    .addOptionConstraint(session.getMessage(
                            "rnl.tenant.lname"));
        }
        if ((tenantMaster.getAddress1() == null) || tenantMaster.getAddress1().isEmpty()) {
            entityValidationContext
                    .addOptionConstraint(session.getMessage(
                            "rnl.tenant.address"));
        }

        if ((tenantMaster.getEmailId() == null) || tenantMaster.getEmailId().isEmpty()) {
            entityValidationContext
                    .addOptionConstraint(session.getMessage(
                            "rnl.tenant.emailid"));
        }

        if ((tenantMaster.getMobileNumber() == null) || tenantMaster.getMobileNumber().isEmpty()) {
            entityValidationContext
                    .addOptionConstraint(session.getMessage(
                            "rnl.tenant.mobilno"));
        }

        if (tenantMaster.getPinCode() == null) {
            entityValidationContext
                    .addOptionConstraint(session.getMessage(
                            "rnl.tenant.pincode"));
        }

    }

}
