package com.abm.mainet.care.ui.validator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.abm.mainet.care.dto.ActionResponseDTO;
import com.abm.mainet.care.dto.CareRequestDTO;
import com.abm.mainet.care.dto.DepartmentComplaintDTO;
import com.abm.mainet.care.dto.DepartmentComplaintTypeDTO;
import com.abm.mainet.care.dto.ResponseType;
import com.abm.mainet.care.service.IDepartmentComplaintService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.RequestDTO;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.dms.utility.FileUploadUtility;

public class GrievanceBeanValidator {
	

	public static void onError(final ActionResponseDTO response, final Errors errors) {

		response.setErrorList(errors.getAllErrors());
	}

	public static ActionResponseDTO doSubmitValidation(RequestDTO applicantDetailDto, CareRequestDTO careRequest,
			Errors errors, ActionResponseDTO actResponse, String kdmcENV,Organisation org,DepartmentComplaintTypeDTO complaintType) {

		ApplicationSession applicationSession = ApplicationSession.getInstance();
		
		if (applicantDetailDto.getMobileNo() == null || applicantDetailDto.getMobileNo().isEmpty()) {
			errors.reject(
					applicationSession.getMessage(MainetConstants.GrievanceConstants.ValidationMessage.MOBILENUMBER));
		} else if (applicantDetailDto.getMobileNo().length() < 10) {
			errors.reject(applicationSession
					.getMessage(MainetConstants.GrievanceConstants.ValidationMessage.MOBILENUMBER_LENGTH));

		}

		if (applicantDetailDto.getTitleId() == null || applicantDetailDto.getTitleId() == 0) {
			errors.reject(applicationSession.getMessage(MainetConstants.GrievanceConstants.ValidationMessage.TITLE));
		}
		
		if (applicantDetailDto.getfName() == null || applicantDetailDto.getfName().isEmpty()) {
			errors.reject(
					applicationSession.getMessage(MainetConstants.GrievanceConstants.ValidationMessage.FIRSTNAME));
		}
		if (applicantDetailDto.getlName() == null || applicantDetailDto.getlName().isEmpty()) {
			errors.reject(applicationSession.getMessage(MainetConstants.GrievanceConstants.ValidationMessage.LASTNAME));
		}
		if (applicantDetailDto.getGender() == null || applicantDetailDto.getGender().isEmpty()
				|| (applicantDetailDto.getGender() != null && applicantDetailDto.getGender().equals("0"))) {
			errors.reject(applicationSession.getMessage(MainetConstants.GrievanceConstants.ValidationMessage.GENDER));
		}
		
		if (applicantDetailDto.getAreaName() == null || applicantDetailDto.getAreaName().isEmpty()) {
			errors.reject(applicationSession.getMessage(MainetConstants.GrievanceConstants.ValidationMessage.AREANAME));
		}
		/*
		 * if (applicantDetailDto.getPincodeNo() == null ||
		 * applicantDetailDto.getPincodeNo() <= 0) {
		 * errors.reject(applicationSession.getMessage(MainetConstants.
		 * GrievanceConstants.ValidationMessage.PINCODE)); }
		 */

		if (org != null) {
			if (org.getDefaultStatus() != null && !org.getDefaultStatus().isEmpty()) {
				//District field is not present on SKDCL ENV
				if (!Utility.isEnvPrefixAvailable(org, MainetConstants.ENVIRNMENT_VARIABLE.ENV_PRODUCT)) {
				if (careRequest.getDistrict() == null || careRequest.getDistrict() == 0) {
					errors.reject(applicationSession
							.getMessage(MainetConstants.GrievanceConstants.ValidationMessage.DISTRICT));
				}
				if (careRequest.getOrgId() == null || careRequest.getOrgId() == 0) {
					errors.reject(
							applicationSession.getMessage(MainetConstants.GrievanceConstants.ValidationMessage.ORG));
				}
				}
			}
		}

		if (StringUtils.isNotEmpty(kdmcENV) && kdmcENV.equals("Y")) {
			if (careRequest.getDepartmentComplaint() == null || careRequest.getDepartmentComplaint() == 0) {
				errors.reject(
						applicationSession.getMessage(MainetConstants.GrievanceConstants.ValidationMessage.SKDCLCOMTYPE));	
			}
			
		}else if (careRequest.getDepartmentComplaint() == null || careRequest.getDepartmentComplaint() == 0) {
			errors.reject(
					applicationSession.getMessage(MainetConstants.GrievanceConstants.ValidationMessage.DEPARTMENT));
		}
		
		
		if (StringUtils.isNotEmpty(kdmcENV) && kdmcENV.equals("Y")) {
			if (careRequest.getComplaintType() == null || careRequest.getComplaintType() == 0) {
				errors.reject(
						applicationSession.getMessage(MainetConstants.GrievanceConstants.ValidationMessage.SKDCLCOMSUBTYPE));	
			}
			
		}else if (careRequest.getComplaintType() == null || careRequest.getComplaintType() == 0) {
			errors.reject(
					applicationSession.getMessage(MainetConstants.GrievanceConstants.ValidationMessage.COMPLAINTTYPE));
		}
		
		//D#128713
		List<DepartmentComplaintDTO> deptList = new ArrayList<>();
		try {
			deptList = ApplicationContextProvider.getApplicationContext().getBean(IDepartmentComplaintService.class).getCareWorkflowMasterDefinedDepartmentsListByOrgId(careRequest.getOrgId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		DepartmentComplaintDTO dept= deptList.stream().filter(dep->dep.getDepartment().getDpDeptid()==careRequest.getDepartmentComplaint())
				.findFirst().orElse(null);
		String deptCode="";
		if(dept!= null) {
			deptCode=dept.getDepartment().getDpDeptcode();
		}
		if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENVIRNMENT_VARIABLE.ENV_SKDCL)) {
			/*
			 * if (StringUtils.isNotEmpty(complaintType.getAmtDues()) &&
			 * complaintType.getAmtDues().equalsIgnoreCase(MainetConstants.FlagY
			 * )) {
			 */
			// check which department selected from UI
			if (deptCode.equals(MainetConstants.DEPT_SHORT_NAME.PROP_TAX)
					&& StringUtils.isEmpty(careRequest.getExtReferNumber())) {
				errors.reject(ApplicationSession.getInstance().getMessage("care.validator.propertyNo"));
			}
			// #161766
			else if (deptCode.equals(MainetConstants.DEPT_SHORT_NAME.PROP_TAX) && !careRequest.isFlatListEmpty()
					&& StringUtils.isEmpty(careRequest.getPropFlatNo())) {
				errors.reject(ApplicationSession.getInstance().getMessage("care.validator.propFlatNo"));
			}

			else if (deptCode.equals(MainetConstants.DEPT_SHORT_NAME.WATER)
					&& StringUtils.isEmpty(careRequest.getExtReferNumber())) {
				errors.reject(ApplicationSession.getInstance().getMessage("care.validator.connectionNo"));
			} /*
				 * else
				 * if(StringUtils.isEmpty(careRequest.getExtReferNumber())){
				 * errors.reject(ApplicationSession.getInstance().getMessage("s"
				 * )); } }
				 */
		} else {
			if (StringUtils.isNotEmpty(complaintType.getAmtDues())
					&& complaintType.getAmtDues().equalsIgnoreCase(MainetConstants.FlagY)) {
				// check which department selected from UI
				if (deptCode.equals(MainetConstants.DEPT_SHORT_NAME.PROP_TAX)
						&& StringUtils.isEmpty(careRequest.getExtReferNumber())) {
					errors.reject(ApplicationSession.getInstance().getMessage("care.validator.propertyNo"));
				}
				// #161766
				else if (deptCode.equals(MainetConstants.DEPT_SHORT_NAME.PROP_TAX) && !careRequest.isFlatListEmpty()
						&& StringUtils.isEmpty(careRequest.getPropFlatNo())) {
					errors.reject(ApplicationSession.getInstance().getMessage("care.validator.propFlatNo"));
				}

				else if (deptCode.equals(MainetConstants.DEPT_SHORT_NAME.WATER)
						&& StringUtils.isEmpty(careRequest.getExtReferNumber())) {
					errors.reject(ApplicationSession.getInstance().getMessage("care.validator.connectionNo"));
				} else if (StringUtils.isEmpty(careRequest.getExtReferNumber())) {
					errors.reject(ApplicationSession.getInstance().getMessage("s"));
				}
			}
		}
            
		

		if (!deptCode.equals(MainetConstants.DEPT_SHORT_NAME.WATER)) {
			if (careRequest.getWard1() != null) {
				if (careRequest.getWard1() == 0) {
					errors.reject(applicationSession.getMessage(MainetConstants.GrievanceConstants.ValidationMessage.ZONE));
				}

			}

			if (careRequest.getWard2() != null) {
				if (careRequest.getWard2() == 0) {
					errors.reject(applicationSession.getMessage(MainetConstants.GrievanceConstants.ValidationMessage.ZONE2));
				}

			}
		}
		
		if (careRequest.getDescription() == null || careRequest.getDescription().trim().isEmpty()) {
			errors.reject(
					applicationSession.getMessage(MainetConstants.GrievanceConstants.ValidationMessage.DESCRIPTION));
		}
		
		

		// here set location/Landmark validation
		if (StringUtils.isNotEmpty(kdmcENV) && kdmcENV.equals("Y")) {
			if (StringUtils.isEmpty(careRequest.getLandmark())) {
				errors.reject(applicationSession
						.getMessage(MainetConstants.GrievanceConstants.ValidationMessage.LOCATION_LANDMARK));
			}
		} else {
			if (careRequest.getLocation() == null || careRequest.getLocation() == 0) {
				errors.reject(
						applicationSession.getMessage(MainetConstants.GrievanceConstants.ValidationMessage.LOCATION));
			}
		}
		
		if (complaintType!= null && 
				StringUtils.isNotEmpty(complaintType.getResidentId()) && complaintType.getResidentId().equalsIgnoreCase(MainetConstants.FlagY)) {
            if(StringUtils.isEmpty(careRequest.getResidentId())) {
            	errors.reject(ApplicationSession.getInstance().getMessage("care.validation.error.residentId"));	
            }
			
        }

		if (careRequest.getReferenceMode() == null || careRequest.getReferenceMode().isEmpty()
				|| careRequest.getReferenceMode().equalsIgnoreCase("0")) {
			errors.reject(applicationSession.getMessage(MainetConstants.GrievanceConstants.ValidationMessage.MODE));
		}
		if (careRequest.getReferenceCategory() == null || careRequest.getReferenceCategory().isEmpty()
				|| careRequest.getReferenceCategory().equalsIgnoreCase("0")) {
			errors.reject(
					applicationSession.getMessage(MainetConstants.GrievanceConstants.ValidationMessage.REFCATAGORY));
		}
		

		/*if (careRequest.getWard2() == null || careRequest.getWard2() == 0) {
			errors.reject(applicationSession.getMessage(MainetConstants.GrievanceConstants.ValidationMessage.ZONE2));
		}*/
		
		if (deptCode.equals(MainetConstants.DEPT_SHORT_NAME.WATER)) {
			if (careRequest.getWard1() == null || careRequest.getWard1() == 0) {
				errors.reject(applicationSession.getMessage(MainetConstants.GrievanceConstants.ValidationMessage.ZONE3));
			}

			if (careRequest.getWard2() == null || careRequest.getWard2() == 0) {
				errors.reject(applicationSession.getMessage(MainetConstants.GrievanceConstants.ValidationMessage.ZONE));
			}

			if (careRequest.getWard3() == null || careRequest.getWard3() == 0) {
				errors.reject(applicationSession.getMessage(MainetConstants.GrievanceConstants.ValidationMessage.ZONE2));
			}

		}
		
		
		//U#110065 document check
        if (complaintType!= null && StringUtils.isNotEmpty(complaintType.getDocumentReq()) && complaintType.getDocumentReq().equalsIgnoreCase(MainetConstants.FlagY)) {
            
            if ((FileUploadUtility.getCurrent().getFileMap() != null)
                    && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
            } else {
            	errors.reject(ApplicationSession.getInstance()
						.getMessage("care.validation.error.document"));
            }
        }

		if (errors.hasErrors()) {
			actResponse.setResponse(ResponseType.Fail.toString());
			GrievanceBeanValidator.onError(actResponse, errors);
		}
		return actResponse;

	}

}
