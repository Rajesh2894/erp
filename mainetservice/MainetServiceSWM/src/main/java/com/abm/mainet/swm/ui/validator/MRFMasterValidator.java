package com.abm.mainet.swm.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.swm.dto.MRFMasterDto;

@Component
public class MRFMasterValidator extends BaseEntityValidator<MRFMasterDto> {

    @Override
    protected void performValidations(MRFMasterDto entity,
            EntityValidationContext<MRFMasterDto> entityValidationContext) {
        /*
         * entityValidationContext.rejectIfNull(entity.getMrfPlantId(), "mrfPlantId");
         * entityValidationContext.rejectIfNull(entity.getMrfPlantName(), "mrfPlantName");
         * entityValidationContext.rejectIfNotSelected(entity.getMrfCategory(), "mrfCategory");
         * entityValidationContext.rejectInvalidDate(entity.getMrfDateOpen(), "mrfDateOpen");
         * entityValidationContext.rejectIfNotSelected(entity.getMrfDecentralised(), "mrfDecentralised");
         * entityValidationContext.rejectIfNotSelected(entity.getMrfOwnerShip(), "mrfOwnerShip");
         * entityValidationContext.rejectIfNotSelected(entity.getLocId(), "locId");
         * entityValidationContext.rejectIfNull(entity.getMrfPlantCap(), "mrfPlantCap");
         * entity.getTbSwMrfEmployeeDet().forEach(emp->{
         * entityValidationContext.rejectIfNotSelected(emp.getMrfEId(),"Mrf.MrfEId");
         * entityValidationContext.rejectIfNull(emp.getMrfeAvalCnt(),"Mrf.MrfeAvalCnt");
         * entityValidationContext.rejectIfNull(emp.getMrfeReqCnt(),"Mrf.MrfeReqCnt"); });
         * entity.getTbSwMrfVechicleDet().forEach(veh->{
         * entityValidationContext.rejectIfNotSelected(veh.getMrfvId(),"Mrf.MrfvId");
         * entityValidationContext.rejectIfNull(veh.getMrfvAvalCnt(),"Mrf.MrfvAvalCnt");
         * entityValidationContext.rejectIfNull(veh.getMrfvReqCnt(),"Mrf.MrfvReqCnt"); });
         */

    }

}
