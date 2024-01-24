package com.abm.mainet.water.ui.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.dto.TBWaterDisconnectionDTO;
import com.abm.mainet.water.ui.model.WaterDisconnectionModel;

@Component
public class WaterDisconnectionValidator extends
        BaseEntityValidator<WaterDisconnectionModel> {

    @Override
    protected void performValidations(
            final WaterDisconnectionModel model,
            final EntityValidationContext<WaterDisconnectionModel> entityValidationContext) {

        final TBWaterDisconnectionDTO entity = model
                .getDisconnectionEntity();
        final ApplicationSession session = ApplicationSession.getInstance();
        entityValidationContext.rejectIfEmpty(
                model.getConnectionNo(), "connectionNo");
        entityValidationContext.rejectIfNotSelected(
                entity.getDiscType(),
                "disconnectionEntity.discType");
        if ((entity.getDiscType() != null) && (entity.getDiscType() > 0d)) {
            final LookUp discon = CommonMasterUtility.getNonHierarchicalLookUpObject(entity.getDiscType());
            if ((discon != null) && MainetConstants.AccountConstants.CONTRA_TRANSFER.equals(discon.getLookUpCode())) {
                entityValidationContext.rejectCompareDate(entity.getDisconnectFromDate(), "disconnectFromDate",
                        entity.getDisconnectToDate(), "disconnectToDate");
            }
            
            if((discon != null) && StringUtils.equalsIgnoreCase(MainetConstants.NewWaterServiceConstants.APPLICATION_TYPE, discon.getLookUpCode())) {
        	
        	if(entity.getDisconnectFromDate() == null) {
                    entityValidationContext.addOptionConstraint(session.getMessage("water.disconnection.validate.from.date"));
                }
                if(entity.getDisconnectToDate() == null) {
                    entityValidationContext.addOptionConstraint(session.getMessage("water.disconnection.validate.to.date"));
                }
            }
        }
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL) && entity.getPlumId() == null || entity.getPlumId() == 0 ) {
            entityValidationContext.addOptionConstraint(session.getMessage("water.plumbername"));
        }
        entityValidationContext.rejectIfEmpty(
                entity.getDiscReason(), "discReason");
        entityValidationContext.rejectIfEmpty(
                model.getUlbPlumber(), "ulbPlumber");
        if (MainetConstants.NewWaterServiceConstants.NO.equals(model.getUlbPlumber())
                && ((model.getPlumberLicence() == null) || model.getPlumberLicence().isEmpty())) {
            entityValidationContext.addOptionConstraint("water.validation.plumberNo");
        }

    }

}