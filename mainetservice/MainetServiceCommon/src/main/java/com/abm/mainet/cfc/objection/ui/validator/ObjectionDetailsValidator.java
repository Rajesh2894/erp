package com.abm.mainet.cfc.objection.ui.validator;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.cfc.objection.service.IObjectionDetailsService;
import com.abm.mainet.cfc.objection.service.NoticeMasterService;
import com.abm.mainet.cfc.objection.ui.model.ObjectionDetailsModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;

@Component
public class ObjectionDetailsValidator extends BaseEntityValidator<ObjectionDetailsModel> {

    @Resource
    IObjectionDetailsService iObjectionDetailsService;

    @Autowired
    private NoticeMasterService noticeMasterService;

    @Override
    protected void performValidations(ObjectionDetailsModel model,
            EntityValidationContext<ObjectionDetailsModel> entityValidationContext) {

        final ApplicationSession session = ApplicationSession.getInstance();
        ObjectionDetailsDto objDto = model.getObjectionDetailsDto();
        String objOn = CommonMasterUtility.getNonHierarchicalLookUpObject(objDto.getObjectionOn(),
                UserSession.getCurrent().getOrganisation()).getLookUpCode();
        if (MainetConstants.Objection.ObjectionOn.BILL.equals(objOn)) {
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
                boolean result = iObjectionDetailsService.isValidBillNoForObjection(objDto);
                if (!result) {
                    entityValidationContext.addOptionConstraint(session.getMessage("obj.validation.billNo"));
                }
            }
        }

        if (objDto.getApmApplicationId() != null) {
            entityValidationContext.rejectIfEmpty(objDto.getApmApplicationId(), session.getMessage("obj.validation.refNo"));
            if (MainetConstants.Objection.ObjectionOn.NOTICE.equals(objOn)) {
                entityValidationContext.rejectIfEmpty(objDto.getNoticeNo(), session.getMessage("obj.validation.noticeNo.empty"));
                entityValidationContext.rejectIfEmpty(objDto.getLocId(), session.getMessage("obj.validation.location.empty"));
                if (objDto.getNoticeNo() != null && !objDto.getNoticeNo().isEmpty()) {
                    boolean validNoticeNo = true;
                    int notCount =0;
                    notCount=noticeMasterService.getCountOfNotByRefNoAndNotNo(objDto.getOrgId(),
                            objDto.getObjectionReferenceNumber(),
                            objDto.getNoticeNo());
                    
                    //120052 - In case of individual property where prop no is not unique.
                    if (notCount <= 0) {
                    	notCount = noticeMasterService.getCountOfNotByApplNoAndNotNo(objDto.getOrgId(),
								objDto.getApmApplicationId(), objDto.getNoticeNo());
                    }
                    if (notCount <= 0) {
                        entityValidationContext.addOptionConstraint(session.getMessage("obj.validation.noticeNo.valid"));
                        validNoticeNo = false;
                    }
                    if (validNoticeNo) {
                        int count =0;
                        count=noticeMasterService.getCountOfNotBeforeDueDateByRefNoAndNotNo(objDto.getOrgId(),
                                objDto.getObjectionReferenceNumber(),
                                objDto.getNoticeNo());
                        //120052 - In case of individual property where prop no is not unique.
						if (count <= 0) {
							count = noticeMasterService.getCountOfNotBeforeDueDateByApplNoAndNotNo(objDto.getOrgId(),
									objDto.getApmApplicationId(), objDto.getNoticeNo());
						}
                        if (count <= 0) {
                            entityValidationContext.addOptionConstraint(session.getMessage("obj.validation.noticeNo"));
                        }
                    }
                }
            } /*
               * else if (MainetConstants.Objection.ObjectionOn.FIRST_APPEAL.equals(objOn)) { if (objDto.getServiceId() != null) {
               * boolean isRTSService = serviceMasterService.isServiceRTS(objDto.getServiceId(), objDto.getOrgId()); if
               * (!isRTSService) { entityValidationContext.addOptionConstraint(session.getMessage("obj.validation.rts")); } } }
               */
        }
        /* end of check */

    }

}
