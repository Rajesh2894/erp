package com.abm.mainet.care.ui.validator;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abm.mainet.care.dto.GrievanceReqDTO;
import com.abm.mainet.care.utility.CareUtility;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ComplaintType;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.IComplaintTypeService;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;

@Component
public class GrievanceRequestValidator extends BaseEntityValidator<GrievanceReqDTO> {

    @Autowired
    private IComplaintTypeService iComplaintService;

    @Override
    protected void performValidations(GrievanceReqDTO entity,
            EntityValidationContext<GrievanceReqDTO> entityValidationContext) {

        entityValidationContext.rejectIfNotSelected(entity.getCareRequest().getDepartmentComplaint(),
                "departmentComplaint");
        entityValidationContext.rejectIfNotSelected(entity.getCareRequest().getComplaintType(), "complaintType");

        entityValidationContext.rejectIfNotSelected(entity.getCareRequest().getReferenceMode(), "referenceMode");
        entityValidationContext.rejectIfNotSelected(entity.getCareRequest().getReferenceCategory(),
                "referenceCategory");
        // D#119035
        /*
         * if (entity.getCareRequest().getWard1() != null) {
         * entityValidationContext.rejectIfNotSelected(entity.getCareRequest().getWard1(), "Ward"); entityValidationContext
         * .addOptionConstraint(ApplicationSession.getInstance().getMessage("care.validator.ward")); }
         */

        /* D#117996 */
        performGroupValidation("careRequest.ward");

        ComplaintType complaintType = null;

        complaintType = iComplaintService.findComplaintTypeById(entity.getCareRequest().getComplaintType());
        if (complaintType != null) {
            // D#128713
            if (StringUtils.isNotEmpty(complaintType.getAmtDues())
                    && complaintType.getAmtDues().equalsIgnoreCase(MainetConstants.FlagY)) {
                // check which department selected from UI

                String deptCode = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
                        .getDeptCode(entity.getCareRequest().getDepartmentComplaint());
                if (deptCode.equals(MainetConstants.DEPT_SHORT_NAME.PROPERTY)
                        && StringUtils.isEmpty(entity.getCareRequest().getExtReferNumber())) {
                    entityValidationContext
                            .addOptionConstraint(ApplicationSession.getInstance().getMessage("care.validator.propertyNo"));
                } else if (deptCode.equals(MainetConstants.DEPT_SHORT_NAME.WATER)
                        && StringUtils.isEmpty(entity.getCareRequest().getExtReferNumber())) {
                    entityValidationContext
                            .addOptionConstraint(ApplicationSession.getInstance().getMessage("care.validator.connectionNo"));
                } 
                //#138469
                /*else if (StringUtils.isEmpty(entity.getCareRequest().getExtReferNumber())) {
                    entityValidationContext.rejectIfEmpty(entity.getCareRequest().getExtReferNumber(), "extReferNumber");
                }*/

            }

            if (StringUtils.isNotEmpty(complaintType.getResidentId())
                    && complaintType.getResidentId().equalsIgnoreCase(MainetConstants.FlagY)) {
                entityValidationContext.rejectIfEmpty(entity.getCareRequest().getResidentId(), "residentId");
            }
        }

        entityValidationContext.rejectIfEmpty(entity.getCareRequest().getDescription(), "description");
        /*D#132587*/
        if(CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT, UserSession.getCurrent().getOrganisation().getOrgid())) {
        	if(StringUtils.isEmpty(entity.getCareRequest().getLandmark())) {
        	entityValidationContext
            .addOptionConstraint(ApplicationSession.getInstance().getMessage("care.validator.landmark"));
        	}
        }
        entityValidationContext.rejectIfNotSelected(entity.getCareRequest().getApplnType(), "applnType");
        entityValidationContext.rejectIfEmpty(entity.getApplicantDetailDto().getMobileNo(), "mobileNo");
        if (StringUtils.isNotEmpty(entity.getApplicantDetailDto().getMobileNo()) && entity.getApplicantDetailDto().getMobileNo().length() < 10) {
            entityValidationContext
                    .addOptionConstraint(ApplicationSession.getInstance().getMessage("care.validator.mobile.nolength"));
        }

        if(CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT, UserSession.getCurrent().getOrganisation().getOrgid())){
        if(entity.getApplicantDetailDto().getMobileNo()!= null && !entity.getApplicantDetailDto().getMobileNo().isEmpty())	
        if(entity.getApplicantDetailDto().getMobileNo().charAt(0)== '0'){
        	entityValidationContext.addOptionConstraint(ApplicationSession.getInstance().getMessage("care.validator.mobile.no.valid"));
        }
        String strPinCodePattern = "^[1-9][0-9]{5}$";
        if(entity.getApplicantDetailDto().getPincodeNum()!= null && !entity.getApplicantDetailDto().getPincodeNum().isEmpty()){
        if (!entity.getApplicantDetailDto().getPincodeNum().matches(strPinCodePattern)) {
            entityValidationContext
                    .addOptionConstraint(ApplicationSession.getInstance().getMessage("care.validator.pincode.valid"));
        }
        }
        
        String s1= "^[A-Za-z0-9!@#$%^&*()-_{},'|~` "+'"'+"\u0900-\u097F]*$"; 
        if(null != entity.getCareRequest().getDescription() && !entity.getApplicantDetailDto().getPincodeNum().isEmpty()){
        	if(!entity.getCareRequest().getDescription().matches(s1)){
        		 entityValidationContext
                 .addOptionConstraint(ApplicationSession.getInstance().getMessage("care.validator.description"));
        	}
        }
        
        }
        
        if(CareUtility.isENVCodePresent(MainetConstants.ENV_ASCL, UserSession.getCurrent().getOrganisation().getOrgid())){
        	
        	if(null != entity.getApplicantDetailDto().getMobileNo() && !entity.getApplicantDetailDto().getMobileNo().isEmpty())	
                if(entity.getApplicantDetailDto().getMobileNo().charAt(0)== '0'){
                	entityValidationContext.addOptionConstraint(ApplicationSession.getInstance().getMessage("care.validator.mobile.no.valid"));
                }
                String strPinCodePattern = "^[1-9][0-9]{5}$";
                String pincode= String.valueOf(entity.getApplicantDetailDto().getPincodeNo());
                if(null != pincode && !pincode.isEmpty()){
                if (!pincode.matches(strPinCodePattern)) {
                    entityValidationContext
                            .addOptionConstraint(ApplicationSession.getInstance().getMessage("care.validator.pincode.valid"));
                }
                }
        	
        }
        
        //143584
        /*String pincode= String.valueOf(entity.getApplicantDetailDto().getPincodeNo());
        if (pincode!=null && pincode.length() < 6) {
            entityValidationContext
                    .addOptionConstraint(ApplicationSession.getInstance().getMessage("care.validator.pincode.pincodelength"));
        }*/
        

        //entityValidationContext.rejectIfEmpty(entity.getApplicantDetailDto().getTitleId(), "titleId");
        //entityValidationContext.rejectIfNotSelected(entity.getApplicantDetailDto().getTitleId(), "titleId");
        if(null==entity.getApplicantDetailDto().getTitleId()){
        	entityValidationContext
            .addOptionConstraint(ApplicationSession.getInstance().getMessage("care.validator.title"));
        }
        entityValidationContext.rejectIfNotSelected(entity.getApplicantDetailDto().getTitleId(), "titleId");
        entityValidationContext.rejectIfEmpty(entity.getApplicantDetailDto().getfName(), "fName");
        entityValidationContext.rejectIfEmpty(entity.getApplicantDetailDto().getlName(), "lName");
        entityValidationContext.rejectIfNotSelected(entity.getApplicantDetailDto().getGender(), "gender");

        entityValidationContext.rejectIfEmpty(entity.getApplicantDetailDto().getAreaName(), "areaName");
        // entityValidationContext.rejectIfEmpty(entity.getApplicantDetailDto().getCityName(),
        // "cityName");
        if(CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT, UserSession.getCurrent().getOrganisation().getOrgid())){
        entityValidationContext.rejectIfEmpty(entity.getApplicantDetailDto().getPincodeNum(),"pincodeNo");
        }
        
        if(!CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT, UserSession.getCurrent().getOrganisation().getOrgid())){
        	entityValidationContext.rejectIfNotSelected(entity.getCareRequest().getDistrict(), "district");
        }

     entityValidationContext.rejectIfNotSelected(entity.getCareRequest().getOrgId(), "orgId");

        entityValidationContext.rejectIfNotSelected(entity.getCareRequest().getLocation(), "location");

        entityValidationContext.rejectInvalidDate(entity.getCareRequest().getReferenceDate(), "referenceDate");
        
        if(null != entity.getCareRequest().getReferenceDate()){
        Date referDate= entity.getCareRequest().getReferenceDate();
        Date todayDate = new Date();
        if (referDate.after(todayDate)) {
            entityValidationContext
                    .addOptionConstraint(ApplicationSession.getInstance().getMessage("care.validator.msg.fromDate"));
        }
        }
        
        if(CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT, UserSession.getCurrent().getOrganisation().getOrgid())){
		String pincode = entity.getApplicantDetailDto().getPincodeNum();
		if (StringUtils.isNotEmpty(pincode) && pincode != null) {
			if (pincode.length() < 6) {
				entityValidationContext.addOptionConstraint(
						ApplicationSession.getInstance().getMessage("care.validator.pincode.pincodelength"));
			}
		}
        }
        
        // document check
        if (complaintType != null) {
            if (StringUtils.isNotEmpty(complaintType.getDocumentReq())
                    && complaintType.getDocumentReq().equalsIgnoreCase(MainetConstants.FlagY)) {
                if ((FileUploadUtility.getCurrent().getFileMap() != null)
                        && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
                } else {
                    entityValidationContext
                            .addOptionConstraint(ApplicationSession.getInstance().getMessage("care.validation.error.document"));
                }
            }
        }

    }

}
