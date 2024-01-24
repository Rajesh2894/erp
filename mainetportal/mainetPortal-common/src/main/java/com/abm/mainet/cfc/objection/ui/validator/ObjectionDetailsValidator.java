package com.abm.mainet.cfc.objection.ui.validator;

import java.util.Date;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.cfc.objection.ui.model.ObjectionDetailsModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.util.ApplicationSession;

import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.UserSession;

@Component
public class ObjectionDetailsValidator extends BaseEntityValidator<ObjectionDetailsModel> {


    @Override
    protected void performValidations(ObjectionDetailsModel model,
            EntityValidationContext<ObjectionDetailsModel> entityValidationContext) {

        final ApplicationSession session = ApplicationSession.getInstance();
        ObjectionDetailsDto objDto = model.getObjectionDetailsDto();
   
            String objOn = CommonMasterUtility.getNonHierarchicalLookUpObject(objDto.getObjectionOn(),
                    UserSession.getCurrent().getOrganisation()).getLookUpCode();
            if (MainetConstants.OBJECTION_COMMON.ObjectionOn.BILL.equals(objOn)) {
                entityValidationContext.rejectIfEmpty(objDto.getBillNo(),
                        session.getMessage("obj.validation.billNo.empty"));
                entityValidationContext.rejectIfEmpty(objDto.getBillDueDate(),
                        session.getMessage("obj.validation.billDate.empty"));
                if (objDto.getDocs() == null) {
                    entityValidationContext.addOptionConstraint("Please upload Bill in case of Bill");
                }
                if (objDto.getBillNo() != null && objDto.getBillDueDate() != null) {
                    if (objDto.getBillDueDate().before(new Date())) {
                        entityValidationContext.addOptionConstraint(session.getMessage("obj.validation.billDate"));
                    }
                }

            } else if (MainetConstants.OBJECTION_COMMON.ObjectionOn.NOTICE.equals(objOn)) {
                entityValidationContext.rejectIfEmpty(objDto.getNoticeNo(), session.getMessage("obj.validation.noticeNo.empty"));

            } else if (MainetConstants.OBJECTION_COMMON.ObjectionOn.FIRST_APPEAL.equals(objOn)) {
         
        }
        
     

    }

}
