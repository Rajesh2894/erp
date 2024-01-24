package com.abm.mainet.swm.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.swm.dto.SanitationStaffTargetDTO;
import com.abm.mainet.swm.dto.SanitationStaffTargetDetDTO;

@Component
public class SanitationStaffTargetValidator extends BaseEntityValidator<SanitationStaffTargetDTO> {

    @Override
    protected void performValidations(SanitationStaffTargetDTO entity,
            EntityValidationContext<SanitationStaffTargetDTO> entityValidationContext) {
        long count = 0;
        for (SanitationStaffTargetDTO pastchk : entity.getTargetDto()) {

            for (SanitationStaffTargetDetDTO chk1 : pastchk.getSanitationStaffTargetDet()) {
                Long prsntvalue = chk1.getVehicleId();
                Long prsntRoValue = chk1.getRoId();
                for (SanitationStaffTargetDetDTO chk2 : entity.getSanitationStaffTargetDet()) {
                    if (chk2.getVehicleId().equals(prsntvalue)) {
                        if (chk2.getRoId().equals(prsntRoValue)) {

                            if (entity.getSanId() == null) {
                                boolean chkbfrdate1 = ((entity.getSanTgfromdt().before(pastchk.getSanTgfromdt()))
                                        && (entity.getSanTgtodt().before(pastchk.getSanTgfromdt())));
                                boolean chkaftdate1 = ((entity.getSanTgfromdt().before(pastchk.getSanTgtodt()))
                                        && (entity.getSanTgtodt().before(pastchk.getSanTgtodt())));
                                boolean chkbfrdate2 = ((entity.getSanTgfromdt().after(pastchk.getSanTgfromdt()))
                                        && (entity.getSanTgtodt().after(pastchk.getSanTgfromdt())));
                                boolean chkaftdate2 = ((entity.getSanTgfromdt().after(pastchk.getSanTgtodt()))
                                        && (entity.getSanTgtodt().after(pastchk.getSanTgtodt())));

                                if (!((chkbfrdate1 && chkaftdate1) || (chkbfrdate2 && chkaftdate2))) {

                                    if (count == 0) {
                                        entityValidationContext.addOptionConstraint("swm.validation.timeoverlapping");
                                    }
                                    count++;

                                }

                            }
                        }
                    }
                }

            }
        }

    }

}
