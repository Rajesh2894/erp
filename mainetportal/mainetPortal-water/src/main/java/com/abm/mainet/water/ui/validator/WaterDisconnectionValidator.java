package com.abm.mainet.water.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.water.dto.TBWaterDisconnectionDTO;
import com.abm.mainet.water.ui.model.WaterDisconnectionFormModel;

@Component
public class WaterDisconnectionValidator extends
        BaseEntityValidator<WaterDisconnectionFormModel> {

    @Override
    protected void performValidations(
            final WaterDisconnectionFormModel model,
            final EntityValidationContext<WaterDisconnectionFormModel> entityValidationContext) {

        final TBWaterDisconnectionDTO entity = model
                .getDisconnectionEntity();
        performGroupValidation("applicantDetailDto.dwzid");

        entityValidationContext.rejectIfEmpty(
                model.getConnectionNo(), "connectionNo");
        if ((model.getConsumerName() == null) || model.getConsumerName().isEmpty()) {
            entityValidationContext.addOptionConstraint("water.conNUmber.serach");
        }
        entityValidationContext.rejectIfNotSelected(
                entity.getDiscType(),
                "disconnectionEntity.discType");
        if ((entity.getDiscType() != null) && (entity.getDiscType() > 0d)) {
            final LookUp discon = CommonMasterUtility.getNonHierarchicalLookUpObject(entity.getDiscType());
            if ((discon != null) && MainetConstants.NewWaterServiceConstants.APPLICATION_TYPE.equals(discon.getLookUpCode())) {
            	entityValidationContext.rejectCompareDate(entity.getDisconnectFromDate(), "disconnectFromDate", 
						entity.getDisconnectToDate(), "disconnectToDate");
            }
        }
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "PSCL") && entity.getPlumId() == null || entity.getPlumId() == 0 ) {
            entityValidationContext.addOptionConstraint("water.plumbername");
        }
        entityValidationContext.rejectIfEmpty(
                entity.getDiscReason(), "discReason");
        entityValidationContext.rejectIfEmpty(
                model.getUlbPlumber(), "ulbPlumber");
        if (MainetConstants.NO.equals(model.getUlbPlumber()) && (model.getPlumberLicence() == null || model.getPlumberLicence().isEmpty())) {
            entityValidationContext.addOptionConstraint("water.plumber.notEmpty");
        }

    }

}