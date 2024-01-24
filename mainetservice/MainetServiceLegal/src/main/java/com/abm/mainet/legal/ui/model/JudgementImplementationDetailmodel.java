/**
 * 
 */
package com.abm.mainet.legal.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.LocationDTO;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.legal.dto.AdvocateMasterDTO;
import com.abm.mainet.legal.dto.AttendeeDetailDto;
import com.abm.mainet.legal.dto.CaseEntryArbitoryFeeDto;
import com.abm.mainet.legal.dto.CaseEntryDTO;
import com.abm.mainet.legal.dto.CaseHearingDTO;
import com.abm.mainet.legal.dto.CourtMasterDTO;
import com.abm.mainet.legal.dto.JudgeMasterDTO;
import com.abm.mainet.legal.dto.JudgementDetailDTO;
import com.abm.mainet.legal.dto.OfficerInchargeDetailsDTO;
import com.abm.mainet.legal.service.IAdvocateMasterService;
import com.abm.mainet.legal.service.ICaseHearingService;
import com.abm.mainet.legal.service.ICourtMasterService;
import com.abm.mainet.legal.service.IJudgeMasterService;
import com.abm.mainet.legal.service.IJudgementDetailService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;


@Component
@Scope("session")
public class JudgementImplementationDetailmodel extends AbstractFormModel {

 
	private static final long serialVersionUID = -3402881795004973096L;

	@Autowired
    private DepartmentService departmentService;

    @Autowired
    private ICourtMasterService courtMasterService;

    @Autowired
    private ILocationMasService locationMasService;

    @Autowired
    private IAdvocateMasterService advocateMasterService;

    @Autowired
    IJudgeMasterService judgeMasterService;

    @Autowired
    ICaseHearingService caseHearingService;

    @Autowired
    IJudgementDetailService judgementDetailService;
    
    @Autowired
    private ISMSAndEmailService iSMSAndEmailService;
    
    @Autowired
   	TbOrganisationService  tbOrganisationService;

    private CaseEntryDTO caseEntryDTO = new CaseEntryDTO();

    private JudgementDetailDTO judgeDetailDto = new JudgementDetailDTO();

    private List<Department> departmentList = new ArrayList<>();

    private List<CaseEntryDTO> caseEntryList = new ArrayList<>();

    private List<CourtMasterDTO> courtNameList = new ArrayList<>();

    private List<LocationDTO> locationList = new ArrayList<>();

    private List<AdvocateMasterDTO> advocateList = new ArrayList<>();

    private List<EmployeeBean> employeeList = new ArrayList<>();

    private List<JudgeMasterDTO> judgeNameList = new ArrayList<>();

    private List<CaseEntryArbitoryFeeDto> arbitoryFeeList = new ArrayList<>();

    private List<CaseEntryDTO> caseEntryDTOList = new ArrayList<>();

    private CaseHearingDTO currentCaseHearingList = new CaseHearingDTO();

    private List<AttendeeDetailDto> attendeeList = new ArrayList<>();

    private List<AttachDocs> attachDocsList = new ArrayList<>();

    private List<CaseHearingDTO> caseHearingListHistory = new ArrayList<>();
    
    private List<OfficerInchargeDetailsDTO> officerInchargeDetailDTOList;

    private String saveMode;

    private String removeAttendeeId;
    
    private String orgFlag;
    
    private Long parentOrgid;
    
    public List<Department> getAllDepartmentList() {
	return departmentService.getDepartments(UserSession.getCurrent().getOrganisation().getOrgid(),
		MainetConstants.FlagA); // Active

    }

  
    public List<CourtMasterDTO> getAllCourtMasterList() {

	List<CourtMasterDTO> courtMasterDTOList = courtMasterService
		.getAllActiveCourtMaster(UserSession.getCurrent().getOrganisation().getOrgid());
	Map<Long, String> courtMap = courtMasterDTOList.stream()
		.collect(Collectors.toMap(CourtMasterDTO::getId, CourtMasterDTO::getCrtName));
	if (CollectionUtils.isNotEmpty(getCaseEntryList())) {
	    getCaseEntryList().forEach(master -> {
		master.setCrtName(courtMap.get(master.getCrtId()));
	    });
	}
	return courtMasterDTOList;
    }

    public List<LocationDTO> getAllLocationList() {
	List<LocationMasEntity> locationMasEntityList = locationMasService
		.getlocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
	List<LocationDTO> locationDTOList = new ArrayList<>();
	for (LocationMasEntity locationMasEntity : locationMasEntityList) {
	    LocationDTO locationDTO = new LocationDTO();

	    locationDTO.setLocId(locationMasEntity.getLocId());
	    locationDTO.setLocName(locationMasEntity.getLocNameEng());
	    locationDTO.setLocNameEng(locationMasEntity.getLocNameEng());
	    locationDTO.setLocNameReg(locationMasEntity.getLocNameReg());
	    locationDTO.setLandmark(locationMasEntity.getLandmark());
	    locationDTO.setPincode(locationMasEntity.getPincode());

	    locationDTOList.add(locationDTO);
	}
	return locationDTOList;
    }

    public List<AdvocateMasterDTO> getAllAdvocateMasterList() {
    	Organisation org = tbOrganisationService.findDefaultOrganisation();
    	Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
	List<AdvocateMasterDTO> advocateMasterDTOList = advocateMasterService
		.getAllAdvocateMasterByOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
	if(orgId != org.getOrgid()) {
		List<AdvocateMasterDTO> advocateMasterList = advocateMasterService
				.getAllAdvocateMasterByOrgid(org.getOrgid());
		advocateMasterDTOList.addAll(advocateMasterList);
	}
	Map<Long, String> advocateMap = advocateMasterDTOList.stream()
		.collect(Collectors.toMap(AdvocateMasterDTO::getAdvId, AdvocateMasterDTO::getAdvFirstNm));
	if (CollectionUtils.isNotEmpty(getCaseEntryList())) {
	    getCaseEntryList().forEach(master -> {
		master.setCrtName(advocateMap.get(master.getAdvId()));
	    });
	}
	return advocateMasterDTOList;
    }

    public List<EmployeeBean> getAllEmployee() {
	IEmployeeService employeeService = (IEmployeeService) ApplicationContextProvider.getApplicationContext()
		.getBean("employeeService");
	return employeeService.getAllEmployee(UserSession.getCurrent().getOrganisation().getOrgid());
    }

    public List<JudgeMasterDTO> getAllJudgeNames() {
	List<JudgeMasterDTO> allJudgeMaster = judgeMasterService
		.getAllJudgeMaster(UserSession.getCurrent().getOrganisation().getOrgid());

	if (CollectionUtils.isNotEmpty(allJudgeMaster)) {
	    allJudgeMaster.forEach(judgeDto -> {
		StringBuilder judgeName = new StringBuilder();
		if (StringUtils.isNotBlank(judgeDto.getJudgeFName())) {
		    judgeName.append(judgeDto.getJudgeFName());
		}
		if (StringUtils.isNotBlank(judgeDto.getJudgeMName())) {
		    judgeName.append(" " + judgeDto.getJudgeMName());
		}
		if (StringUtils.isNotBlank(judgeDto.getJudgeLName())) {
		    judgeName.append(" " + judgeDto.getJudgeLName());
		}
		judgeDto.setFulName(judgeName.toString());
	    });
	}

	return allJudgeMaster;
    }

    public List<CaseHearingDTO> getAllCaseHearingDetails(Long orgId, Long cseId) {

	List<CaseHearingDTO> hearingDetailList = caseHearingService.getHearingDetailByCaseId(orgId, cseId);
	return hearingDetailList;

    }
    
    public List<CaseHearingDTO> fetchAllCaseHearingDetails(Long cseId) {
    	List<CaseHearingDTO> hearingDetailList = caseHearingService.getHearingDetailsByCaseId(cseId);
    	return hearingDetailList;

        }
    

    @Override
    public boolean saveForm() {
	boolean status = false;
	
	judgeDetailDto.setCseId(caseEntryDTO.getCseId());
	judgeDetailDto.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
     if (hasValidationErrors()) {
         return false;
     }
     else {

	if (judgeDetailDto.getCjdId() == null) {
	    judgeDetailDto.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
	    judgeDetailDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
	    judgeDetailDto.setCreatedDate(new Date());
	    judgeDetailDto.setLgIpMac(getClientIpAddress());
	    judgeDetailDto.setCseId(caseEntryDTO.getCseId());

	    if (CollectionUtils.isNotEmpty(judgeDetailDto.getAttendeeDtoList())) {
		judgeDetailDto.getAttendeeDtoList().forEach(attendeList -> {
		    attendeList.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		    attendeList.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		    attendeList.setCreatedDate(new Date());
		    attendeList.setLgIpMac(getClientIpAddress());
		});
	    }
	    judgementDetailService.saveJudgementDetail(judgeDetailDto);
	    setSuccessMessage(
		    ApplicationSession.getInstance().getMessage("Successfully Added Judgement Implementation"));
	    status = true;
	 
	    
	} else {
	    judgeDetailDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
	    judgeDetailDto.setUpdatedDate(new Date());
	    judgeDetailDto.setLgIpMacUpd(getClientIpAddress());
	    if (CollectionUtils.isNotEmpty(judgeDetailDto.getAttendeeDtoList())) {
		judgeDetailDto.getAttendeeDtoList().forEach(attendeList -> {
		    if (attendeList.getAttendeeId() != null) {
			attendeList.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			attendeList.setUpdatedDate(new Date());
			attendeList.setLgIpMacUpd(getClientIpAddress());
		    } else {
			attendeList.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			attendeList.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			attendeList.setCreatedDate(new Date());
			attendeList.setLgIpMac(getClientIpAddress());
		    }

		});
	    }
	    List<Long> removeattendeeIds = new ArrayList<>();
	    String ids = getRemoveAttendeeId();
	    if (StringUtils.isNotBlank(ids)) {
		String wasteId[] = ids.split(MainetConstants.operator.COMMA);
		for (String id : wasteId) {
		    removeattendeeIds.add(Long.valueOf(id));
		}
	    }
	    judgementDetailService.updateJudgementDetail(judgeDetailDto, removeattendeeIds);
	    
	  
	    setSuccessMessage(
		    ApplicationSession.getInstance().getMessage("Judgement.Implementation.save"));
	    status = true;
	    
	    
	    
	}
     
	 if(status == true)
	 {
	  Organisation org = UserSession.getCurrent().getOrganisation();
      final SMSAndEmailDTO dto = new SMSAndEmailDTO();
      dto.setMobnumber(judgeDetailDto.getImplementerPhoneNo().toString());
      dto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
      dto.setAppName(judgeDetailDto.getImplementerName());
      dto.setDecision(judgeDetailDto.getDesigOfImplementer());
      dto.setAppDate(UtilityService.convertDateToDDMMYYYY(judgeDetailDto.getImplementationStartDate()));
      dto.setEmail(judgeDetailDto.getImplementerEmail());
      dto.setAppNo(caseEntryDTO.getCseSuitNo());
      dto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
      

      String menuUrl = "JudgementImplementationDetail.html";
      org.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
      int langId = Utility.getDefaultLanguageId(org);
      iSMSAndEmailService.sendEmailSMS("LEGL", menuUrl,
              PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, dto, org, langId);
	 }
	
  }
     
 
	return status;
    }

    public List<Department> getDepartmentList() {
	return departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
	this.departmentList = departmentList;
    }

    public List<CaseEntryDTO> getCaseEntryList() {
	return caseEntryList;
    }

    public void setCaseEntryList(List<CaseEntryDTO> caseEntryList) {
	this.caseEntryList = caseEntryList;
    }

    public List<CourtMasterDTO> getCourtNameList() {
	return courtNameList;
    }

    public void setCourtNameList(List<CourtMasterDTO> courtNameList) {
	this.courtNameList = courtNameList;
    }

    public CaseEntryDTO getCaseEntryDTO() {
	return caseEntryDTO;
    }

    public void setCaseEntryDTO(CaseEntryDTO caseEntryDTO) {
	this.caseEntryDTO = caseEntryDTO;
    }

    public String getSaveMode() {
	return saveMode;
    }

    public void setSaveMode(String saveMode) {
	this.saveMode = saveMode;
    }

    public List<LocationDTO> getLocationList() {
	return locationList;
    }

    public void setLocationList(List<LocationDTO> locationList) {
	this.locationList = locationList;
    }

    public List<AdvocateMasterDTO> getAdvocateList() {
	return advocateList;
    }

    public void setAdvocateList(List<AdvocateMasterDTO> advocateList) {
	this.advocateList = advocateList;
    }

    public List<EmployeeBean> getEmployeeList() {
	return employeeList;
    }

    public void setEmployeeList(List<EmployeeBean> employeeList) {
	this.employeeList = employeeList;
    }

    public List<JudgeMasterDTO> getJudgeNameList() {
	return judgeNameList;
    }

    public void setJudgeNameList(List<JudgeMasterDTO> judgeNameList) {
	this.judgeNameList = judgeNameList;
    }

    public List<CaseEntryArbitoryFeeDto> getArbitoryFeeList() {
	return arbitoryFeeList;
    }

    public void setArbitoryFeeList(List<CaseEntryArbitoryFeeDto> arbitoryFeeList) {
	this.arbitoryFeeList = arbitoryFeeList;
    }

    public List<CaseEntryDTO> getCaseEntryDTOList() {
	return caseEntryDTOList;
    }

    public void setCaseEntryDTOList(List<CaseEntryDTO> caseEntryDTOList) {
	this.caseEntryDTOList = caseEntryDTOList;
    }

    public CaseHearingDTO getCurrentCaseHearingList() {
	return currentCaseHearingList;
    }

    public void setCurrentCaseHearingList(CaseHearingDTO currentCaseHearingList) {
	this.currentCaseHearingList = currentCaseHearingList;
    }

    public JudgementDetailDTO getJudgeDetailDto() {
	return judgeDetailDto;
    }

    public void setJudgeDetailDto(JudgementDetailDTO judgeDetailDto) {
	this.judgeDetailDto = judgeDetailDto;
    }

    public List<AttendeeDetailDto> getAttendeeList() {
	return attendeeList;
    }

    public void setAttendeeList(List<AttendeeDetailDto> attendeeList) {
	this.attendeeList = attendeeList;
    }

    public List<AttachDocs> getAttachDocsList() {
	return attachDocsList;
    }

    public void setAttachDocsList(List<AttachDocs> attachDocsList) {
	this.attachDocsList = attachDocsList;
    }

    public List<CaseHearingDTO> getCaseHearingListHistory() {
	return caseHearingListHistory;
    }

    public void setCaseHearingListHistory(List<CaseHearingDTO> caseHearingListHistory) {
	this.caseHearingListHistory = caseHearingListHistory;
    }

    public String getRemoveAttendeeId() {
	return removeAttendeeId;
    }

    public void setRemoveAttendeeId(String removeAttendeeId) {
	this.removeAttendeeId = removeAttendeeId;
    }

	public List<OfficerInchargeDetailsDTO> getOfficerInchargeDetailDTOList() {
		return officerInchargeDetailDTOList;
	}

	public void setOfficerInchargeDetailDTOList(List<OfficerInchargeDetailsDTO> officerInchargeDetailDTOList) {
		this.officerInchargeDetailDTOList = officerInchargeDetailDTOList;
	}


	public String getOrgFlag() {
		return orgFlag;
	}

	public void setOrgFlag(String orgFlag) {
		this.orgFlag = orgFlag;
	}


	public Long getParentOrgid() {
		return parentOrgid;
	}


	public void setParentOrgid(Long parentOrgid) {
		this.parentOrgid = parentOrgid;
	}


	
    
}
