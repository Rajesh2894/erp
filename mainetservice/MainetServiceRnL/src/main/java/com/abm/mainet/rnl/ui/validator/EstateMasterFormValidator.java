package com.abm.mainet.rnl.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.rnl.dto.EstateMaster;
import com.abm.mainet.rnl.ui.model.EstateMasterModel;

/**
 * @author ritesh.patil
 *
 */
@Component
public class EstateMasterFormValidator
        extends BaseEntityValidator<EstateMasterModel> {

    @Override
    protected void performValidations(
            final EstateMasterModel model,
            final EntityValidationContext<EstateMasterModel> entityValidationContext) {

        final ApplicationSession session = ApplicationSession
                .getInstance();
        final EstateMaster estateMaster = model.getEstateMaster();

        if (estateMaster.getLocId() == null) {
            entityValidationContext
                    .addOptionConstraint(session.getMessage(
                            "estate.master.location.validate.msg"));
        }

        if ((estateMaster.getNameEng() == null)
                || estateMaster.getNameEng().isEmpty()) {
            entityValidationContext
                    .addOptionConstraint(session.getMessage(
                            "estate.master.name.eng.validate.msg"));
        }
        if ((estateMaster.getNameReg() == null)
                || estateMaster.getNameReg().isEmpty()) {
            entityValidationContext
                    .addOptionConstraint(session.getMessage(
                            "estate.master.name.reg.validate.msg"));
        }
        if ((estateMaster.getAddress() == null)
                || estateMaster.getAddress().isEmpty()) {
            entityValidationContext
                    .addOptionConstraint(session.getMessage(
                            "estate.master.address.validate.msg"));
        }
        if (estateMaster.getCategory() == null) {
            entityValidationContext
                    .addOptionConstraint(session.getMessage(
                            "estate.master.Category.validate.msg"));
        }

        if ((estateMaster.getType1() == null) || (estateMaster.getType1() == 0)) {
            entityValidationContext
                    .addOptionConstraint(session.getMessage(
                            "estate.master.type.validate.msg"));
        }

        if ((estateMaster.getType2() == null) || (estateMaster.getType2() == 0)) {
            entityValidationContext
                    .addOptionConstraint(session.getMessage(
                            "estate.master.subType.validate.msg"));
        }

    }

}
