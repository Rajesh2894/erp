package com.abm.mainet.care.ui.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.care.dto.CareApplicantDetailsDTO;
import com.abm.mainet.care.dto.CareFeedbackDTO;
import com.abm.mainet.care.dto.CareRequestDTO;
import com.abm.mainet.care.dto.ComplaintRegistrationAcknowledgementDTO;
import com.abm.mainet.care.dto.DepartmentComplaintDTO;
import com.abm.mainet.care.dto.ReopenComplaintDetailsDTO;
import com.abm.mainet.care.dto.RoleAllocationDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.ActionDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.OrganisationDTO;
import com.abm.mainet.common.dto.RequestDTO;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;

@SuppressWarnings("serial")
@Component
@Scope("session")
public class ComplaintRegistrationModel extends AbstractFormModel implements Serializable {

	private String complaintType;
	private String requestType;
	private String reopenedRequest;
	private Long hiddenTitle;
	private String hiddenFname;
	private String hiddenMname;
	private String hiddenLname;
	private String hiddenGender;
	private String hiddenMobile;
	private String hiddenEmail;
	private String applicantFullName;
	private String serviceName;
	private String title;
	private String gender;
	private CareApplicantDetailsDTO careApplicantDetails;
	private ReopenComplaintDetailsDTO reopenComplaintDetails;
	private RoleAllocationDTO roleAllocationDTO;
	private CareRequestDTO careRequestDto = new CareRequestDTO();
	private CareFeedbackDTO feedbackDetails = new CareFeedbackDTO();
	private RequestDTO applicantDetailDTO = new RequestDTO();
	private ActionDTO action = new ActionDTO();
	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	private Set<LookUp> complaintTypes = new HashSet<>();
	private Set<LookUp> locations = new HashSet<>();
	private String generatedMobileOTP;
	private String enteredMobileOTP;
	private List<OrganisationDTO> org = new ArrayList<>();
	List<LookUp> deptLookups = new ArrayList<>();
	List<LookUp> orgLookups = new ArrayList<>();
	private List<DepartmentComplaintDTO> dept = new ArrayList<>();
	private String loginFlag;
	private List<CareRequestDTO> careDTO = new ArrayList<>();
	private ComplaintRegistrationAcknowledgementDTO complaintAcknowledgementModel = new ComplaintRegistrationAcknowledgementDTO();
	private String saveMode;
	private String applicationType;
	private String labelType;
	private String kdmcEnv;
	private String prefixName;
	private List<String> flatNoList = new ArrayList<>();
	private String appStatus;
	private String externalStatus;
	private Map<Integer, List<LookUp>> lookupMap = new HashMap<>();
	

	public RequestDTO getApplicantDetailDTO() {
		return applicantDetailDTO;
	}

	public void setApplicantDetailDTO(RequestDTO applicantDetailDTO) {
		this.applicantDetailDTO = applicantDetailDTO;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ActionDTO getAction() {
		return action;
	}

	public void setAction(ActionDTO action) {
		this.action = action;
	}

	public CareApplicantDetailsDTO getCareApplicantDetails() {
		return careApplicantDetails;
	}

	public void setCareApplicantDetails(CareApplicantDetailsDTO careApplicantDetails) {
		this.careApplicantDetails = careApplicantDetails;
	}

	public ReopenComplaintDetailsDTO getReopenComplaintDetails() {
		return reopenComplaintDetails;
	}

	public void setReopenComplaintDetails(ReopenComplaintDetailsDTO reopenComplaintDetails) {
		this.reopenComplaintDetails = reopenComplaintDetails;
	}

	public String getComplaintType() {
		return complaintType;
	}

	public void setComplaintType(String complaintType) {
		this.complaintType = complaintType;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getReopenedRequest() {
		return reopenedRequest;
	}

	public void setReopenedRequest(String reopenedRequest) {
		this.reopenedRequest = reopenedRequest;
	}

	public RoleAllocationDTO getRoleAllocationDTO() {
		return roleAllocationDTO;
	}

	public void setRoleAllocationDTO(RoleAllocationDTO roleAllocationDTO) {
		this.roleAllocationDTO = roleAllocationDTO;
	}

	public CareRequestDTO getCareRequestDto() {
		return careRequestDto;
	}

	public void setCareRequestDto(CareRequestDTO careRequestDto) {
		this.careRequestDto = careRequestDto;
	}

	public Long getHiddenTitle() {
		return hiddenTitle;
	}

	public void setHiddenTitle(Long hiddenTitle) {
		this.hiddenTitle = hiddenTitle;
	}

	public String getHiddenFname() {
		return hiddenFname;
	}

	public void setHiddenFname(String hiddenFname) {
		this.hiddenFname = hiddenFname;
	}

	public String getHiddenMname() {
		return hiddenMname;
	}

	public void setHiddenMname(String hiddenMname) {
		this.hiddenMname = hiddenMname;
	}

	public String getHiddenLname() {
		return hiddenLname;
	}

	public void setHiddenLname(String hiddenLname) {
		this.hiddenLname = hiddenLname;
	}

	public String getHiddenGender() {
		return hiddenGender;
	}

	public void setHiddenGender(String hiddenGender) {
		this.hiddenGender = hiddenGender;
	}

	public String getHiddenMobile() {
		return hiddenMobile;
	}

	public void setHiddenMobile(String hiddenMobile) {
		this.hiddenMobile = hiddenMobile;
	}

	public String getHiddenEmail() {
		return hiddenEmail;
	}

	public void setHiddenEmail(String hiddenEmail) {
		this.hiddenEmail = hiddenEmail;
	}

	public String getApplicantFullName() {
		return applicantFullName;
	}

	public void setApplicantFullName(String applicantFullName) {
		this.applicantFullName = applicantFullName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public CareFeedbackDTO getFeedbackDetails() {
		return feedbackDetails;
	}

	public void setFeedbackDetails(CareFeedbackDTO feedbackDetails) {
		this.feedbackDetails = feedbackDetails;
	}

	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}

	public Set<LookUp> getComplaintTypes() {
		return complaintTypes;
	}

	public void setComplaintTypes(Set<LookUp> complaintTypes) {
		this.complaintTypes = complaintTypes;
	}

	public Set<LookUp> getLocations() {
		return locations;
	}

	public void setLocations(Set<LookUp> locations) {
		this.locations = locations;
	}

	public String getGeneratedMobileOTP() {
		return generatedMobileOTP;
	}

	public void setGeneratedMobileOTP(String generatedMobileOTP) {
		this.generatedMobileOTP = generatedMobileOTP;
	}

	public String getEnteredMobileOTP() {
		return enteredMobileOTP;
	}

	public void setEnteredMobileOTP(String enteredMobileOTP) {
		this.enteredMobileOTP = enteredMobileOTP;
	}

	public List<OrganisationDTO> getOrg() {
		return org;
	}

	public void setOrg(List<OrganisationDTO> org) {
		this.org = org;
	}

	public List<DepartmentComplaintDTO> getDept() {
		return dept;
	}

	public void setDept(List<DepartmentComplaintDTO> dept) {
		this.dept = dept;
	}

	public String getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}

	public List<CareRequestDTO> getCareDTO() {
		return careDTO;
	}

	public void setCareDTO(List<CareRequestDTO> careDTO) {
		this.careDTO = careDTO;
	}

	public ComplaintRegistrationAcknowledgementDTO getComplaintAcknowledgementModel() {
		return complaintAcknowledgementModel;
	}

	public void setComplaintAcknowledgementModel(
			ComplaintRegistrationAcknowledgementDTO complaintAcknowledgementModel) {
		this.complaintAcknowledgementModel = complaintAcknowledgementModel;
	}

	public List<LookUp> getDeptLookups() {
		return deptLookups;
	}

	public void setDeptLookups(List<LookUp> deptLookups) {
		this.deptLookups = deptLookups;
	}

	public List<LookUp> getOrgLookups() {
		return orgLookups;
	}

	public void setOrgLookups(List<LookUp> orgLookups) {
		this.orgLookups = orgLookups;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}
	
	@Autowired
	IOrganisationService organisatonService;

	public long getEmplType() {
		long emplType = 0;
		Employee emp = UserSession.getCurrent().getEmployee();
		if (emp != null)
			if (emp.getEmploginname().equalsIgnoreCase("NOUSER")) {
				Organisation org = organisatonService.getOrganisationById(Utility.getOrgId());

				List<LookUp> lookUps = CommonMasterUtility.getLookUps(PrefixConstants.Prefix.NEC,org);
				Optional<LookUp> lookup = lookUps.stream()
						.filter(l -> l.getLookUpCode().equals(MainetConstants.NEC.CITIZEN)).findFirst();
				if (lookup.isPresent())
					emplType = lookup.get().getLookUpId();

			} else {
				emplType = emp.getEmplType();
			}

		return emplType;
	}

	public String getReferenceMode() {
		String referenceMode = null;
		Organisation org = organisatonService.getOrganisationById(Utility.getOrgId());
		List<LookUp> lookUps = CommonMasterUtility.getLookUps(PrefixConstants.Prefix.RFM,org);
		if (lookUps != null && !lookUps.isEmpty()) {
			Optional<LookUp> lookup = lookUps.stream()
					.filter(l -> l.getLookUpCode().equals(MainetConstants.RFM.WEB_BROWSER)).findFirst();
			if (lookup.isPresent())
				referenceMode = String.valueOf(lookup.get().getLookUpId());
		}
		return referenceMode;
	}

	public String getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}

	public String getLabelType() {
		return labelType;
	}

	public void setLabelType(String labelType) {
		this.labelType = labelType;
	}

	public String getKdmcEnv() {
		return kdmcEnv;
	}

	public void setKdmcEnv(String kdmcEnv) {
		this.kdmcEnv = kdmcEnv;
	}

	public List<String> getFlatNoList() {
		return flatNoList;
	}

	public void setFlatNoList(List<String> flatNoList) {
		this.flatNoList = flatNoList;
	}

	public String getAppStatus() {
		return appStatus;
	}

	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}

	public String getExternalStatus() {
		return externalStatus;
	}

	public void setExternalStatus(String externalStatus) {
		this.externalStatus = externalStatus;
	}
	
	
	public Map<Integer, List<LookUp>> getLookupMap() {
		return lookupMap;
	}

	public void setLookupMap(Map<Integer, List<LookUp>> lookupMap) {
		this.lookupMap = lookupMap;
	}

	
	
}
