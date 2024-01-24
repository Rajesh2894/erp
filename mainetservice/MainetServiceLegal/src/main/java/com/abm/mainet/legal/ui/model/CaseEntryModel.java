package com.abm.mainet.legal.ui.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.LocationDTO;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.legal.dto.AdvocateMasterDTO;
import com.abm.mainet.legal.dto.CaseEntryArbitoryFeeDto;
import com.abm.mainet.legal.dto.CaseEntryDTO;
import com.abm.mainet.legal.dto.CaseEntryDetailDTO;
import com.abm.mainet.legal.dto.CaseHearingAttendeeDetailsDTO;
import com.abm.mainet.legal.dto.CaseHearingDTO;
import com.abm.mainet.legal.dto.CourtMasterDTO;
import com.abm.mainet.legal.dto.JudgeMasterDTO;
import com.abm.mainet.legal.dto.OfficerInchargeDetailsDTO;
import com.abm.mainet.legal.dto.TbLglComntRevwDtlDTO;
import com.abm.mainet.legal.service.IAdvocateMasterService;
import com.abm.mainet.legal.service.ICaseEntryService;
import com.abm.mainet.legal.ui.validator.CaseEntryValidator;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Component
@Scope("session")
public class CaseEntryModel extends AbstractFormModel {

    private static final long serialVersionUID = 1L;
    private String saveMode;

    private CaseEntryDTO caseEntryDTO = new CaseEntryDTO();
    private List<CaseEntryDTO> caseEntryDTOList;
    private List<CaseEntryDetailDTO> caseEntryDetailDTO;
    private AdvocateMasterDTO advocateMasterDTO;
    private JudgeMasterDTO judgeMasterDto;
    private List<CaseEntryDetailDTO> plenfiffEntryDetailDTOList;
    private List<CaseEntryDetailDTO> defenderEntryDetailDTOList;
    private List<OfficerInchargeDetailsDTO> officerInchargeDetailDTOList;

    private List<DocumentDetailsVO> documents = new ArrayList<>();
    private List<DocumentDetailsVO> attachments = new ArrayList<>();
    private List<AttachDocs> attachDocsList = new ArrayList<>();
    private Map<Long, String> caseTypeList = new HashMap<>();

    private String removeCommonFileById;

    private List<JudgeMasterDTO> judgeNameList = new ArrayList<>();

    private List<CaseEntryArbitoryFeeDto> arbitoryFeeList = new ArrayList<>();

    private List<Department> departments = new ArrayList<>();

    private List<LocationDTO> locations = new ArrayList<>();

    private List<CourtMasterDTO> courtMasterDTOList = new ArrayList<>();

    private List<AdvocateMasterDTO> advocates = new ArrayList<>();

    private List<EmployeeBean> employee = new ArrayList<>();

    private EmployeeBean employeeBean;
    
    private List<Object[]> orgList;
    
    private Long parentOrgid;

    private String envFlag;
    
    private String dsclEnv;
    
    private boolean hearing;
    
    private List<CaseHearingDTO> hearingEntry;
    
    private List<CaseHearingAttendeeDetailsDTO> caseHearingAttendeeDetailsDTOList;
    
    private List<TbLglComntRevwDtlDTO> tbLglComntRevwDtlDTOList;
    
    private String hearingMode;
    
    private String orgFlag;
    
    private String caseHearingFlag;
    
    private List<String> suitNoList = new ArrayList<>();
    
    @Autowired
    private IFileUploadService fileUpload;

    @Autowired
    private ICaseEntryService caseEntryService;

    @Autowired
    private TbDepartmentService tbDepartmentService;

    @Autowired
    private IAdvocateMasterService advocateMasterService;
    
    @Autowired
	TbOrganisationService  tbOrganisationService;
    
    @Autowired
    private ISMSAndEmailService iSMSAndEmailService;

    public List<AdvocateMasterDTO> getAdvocateMasterList() {
    	Organisation org = tbOrganisationService.findDefaultOrganisation();
    	Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
    	 List<AdvocateMasterDTO> advocateMasterDTOList = advocateMasterService
	                .getAllAdvocateMasterByOrgid(UserSession.getCurrent().getOrganisation().getOrgid());  
    	if(orgId != org.getOrgid()) {    		      
    	        List<AdvocateMasterDTO> allAdvocateMasterByOrgid = advocateMasterService
    	        .getAllAdvocateMasterByOrgid(org.getOrgid());
    	        advocateMasterDTOList.addAll(allAdvocateMasterByOrgid);
    	}       
        Map<Long, String> advocateMap = advocateMasterDTOList.stream()
                .collect(Collectors.toMap(AdvocateMasterDTO::getAdvId, AdvocateMasterDTO::getAdvFirstNm));
        if (CollectionUtils.isNotEmpty(getCaseEntryDTOList())) {
            getCaseEntryDTOList().forEach(master -> {
                master.setCrtName(advocateMap.get(master.getAdvId()));
            });
        }
     
        return advocateMasterDTOList;
    
    }

    @Override
    public boolean saveForm() {
        validateBean(caseEntryDTO, CaseEntryValidator.class);
        if (hasValidationErrors()) {
            return false;
        } else {

            if (CollectionUtils.isNotEmpty(getPlenfiffEntryDetailDTOList())) {
                getPlenfiffEntryDetailDTOList().forEach(dto -> {
                    if (dto.getCsedId() != null) {
                        dto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                        dto.setUpdatedDate(new Date());
                        dto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());

                    } else {
                        dto.setCsedStatus(MainetConstants.CommonConstants.Y);
                        dto.setTbLglCaseMa(caseEntryDTO);
                        dto.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                        dto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                        dto.setCreatedDate(new Date());
                        dto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                    }
                    dto.setCsedFlag("P");
                });
            }

            if (CollectionUtils.isNotEmpty(getDefenderEntryDetailDTOList())) {
                getDefenderEntryDetailDTOList().forEach(dto -> {
                    if (dto.getCsedId() != null) {
                        dto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                        dto.setUpdatedDate(new Date());
                        dto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
                        dto.setCsedPartyType(dto.getCsedParty());
                    } else {
                        dto.setCsedStatus(MainetConstants.CommonConstants.Y);
                        dto.setTbLglCaseMa(caseEntryDTO);
                        dto.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                        dto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                        dto.setCreatedDate(new Date());
                        dto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                        dto.setCsedPartyType(dto.getCsedParty());
                    }
                    dto.setCsedFlag("D");
                });
            }

            if (CollectionUtils.isNotEmpty(getOfficerInchargeDetailDTOList())) {
                getOfficerInchargeDetailDTOList().forEach(oicDTO -> {
                    if (oicDTO.getOicId() != null) {
                        oicDTO.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                        oicDTO.setUpdatedDate(new Date());
                        oicDTO.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());

                    } else {
                        oicDTO.setTbLglCaseMa(caseEntryDTO);
                        oicDTO.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                        oicDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                        oicDTO.setCreatedDate(new Date());
                        oicDTO.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                    }

                });
            }

            if (CollectionUtils.isNotEmpty(caseEntryDTO.getTbLglArbitoryFee())) {
                caseEntryDTO.getTbLglArbitoryFee().forEach(dto -> {
                    if (caseEntryDTO.getCseId() == null) {
                        dto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
                        dto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                        dto.setCreatedDate(new Date());
                        dto.setLpipmac(getClientIpAddress());
                    } else {
                        dto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                        dto.setUpdatedDate(new Date());
                        dto.setLgIpMacUpd(getClientIpAddress());
                    }

                });
            }

            List<CaseEntryDetailDTO> caseEntryDetailsDTOs = new ArrayList<>();
            caseEntryDetailsDTOs.addAll(getPlenfiffEntryDetailDTOList());
            caseEntryDetailsDTOs.addAll(getDefenderEntryDetailDTOList());
            caseEntryDTO.setTbLglCasePddetails(caseEntryDetailsDTOs);

            List<OfficerInchargeDetailsDTO> officeInchargeDetailDTOs = new ArrayList<>();
            officeInchargeDetailDTOs.addAll(getOfficerInchargeDetailDTOList());
            caseEntryDTO.setTbLglCaseOICdetails(officeInchargeDetailDTOs);

            if (caseEntryDTO.getCseId() != null) {
                caseEntryDTO.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                caseEntryDTO.setUpdatedDate(new Date());
                caseEntryDTO.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
                caseEntryService.updateCaseEntry(caseEntryDTO);
                setSuccessMessage(ApplicationSession.getInstance().getMessage("lgl.editCaseEntry"));
            } else {
                caseEntryDTO.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                caseEntryDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                caseEntryDTO.setCreatedDate(new Date());
                caseEntryDTO.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                caseEntryService.saveCaseEntry(caseEntryDTO);
                /*setSuccessMessage(ApplicationSession.getInstance().getMessage("lgl.saveCaseEntry"));*/
                setSuccessMessage(getAppSession().getMessage("lgl.saveCaseEntry",
                        new Object[] {caseEntryDTO.getCaseNo()} ));
                sendSMSAndEmail();
                
      
            }
            prepareContractDocumentsData(caseEntryDTO);
         
            return true;
        }

    }

	private void sendSMSAndEmail() {
		//SMS and Email
			// SMS Email to Advocate

			Organisation org = UserSession.getCurrent().getOrganisation();
			int langId =  UserSession.getCurrent().getLanguageId();
			
			 String menuUrl = "CaseEntry.html";
			final SMSAndEmailDTO dto = new SMSAndEmailDTO();

			if (caseEntryDTO.getAdvId() != null) {
				AdvocateMasterDTO advocate = advocateMasterService.getAdvocateMasterById(caseEntryDTO.getAdvId());

				dto.setMobnumber(advocate.getAdvMobile());
				dto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
				dto.setAppName(String.join(" ", advocate.getAdvFirstNm(), advocate.getAdvLastNm()));
				dto.setAppDate(UtilityService.convertDateToDDMMYYYY(caseEntryDTO.getCseDate()));
				dto.setEmail(advocate.getAdvEmail());
				dto.setAppNo(caseEntryDTO.getCseSuitNo());
				dto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
				org.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
				if (langId == 1) {
					dto.setOrgName(org.getONlsOrgname());}
					else {
						dto.setOrgName(org.getONlsOrgnameMar());
					}
				iSMSAndEmailService.sendEmailSMS("LEGL", menuUrl, PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED,
						dto, org, langId);
			}

			// SMS  Email to OIC

			final SMSAndEmailDTO oicSMSdto = new SMSAndEmailDTO();

			if (CollectionUtils.isNotEmpty(getOfficerInchargeDetailDTOList())) {
				getOfficerInchargeDetailDTOList().forEach(oicDTO -> {
						oicSMSdto.setMobnumber(oicDTO.getOicPhoneNo());
						oicSMSdto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
						oicSMSdto.setAppName(oicDTO.getOicName());
						oicSMSdto.setAppDate(UtilityService.convertDateToDDMMYYYY(caseEntryDTO.getCseDate()));
						oicSMSdto.setEmail(oicDTO.getOicEmailId());
						oicSMSdto.setAppNo(caseEntryDTO.getCseSuitNo());
						oicSMSdto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
						org.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
						if (langId == 1) {
							dto.setOrgName(org.getONlsOrgname());}
							else {
								dto.setOrgName(org.getONlsOrgnameMar());
							}
						iSMSAndEmailService.sendEmailSMS("LEGL", menuUrl,
								PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, oicSMSdto, org, langId);

				});
			}

		
	}

    public void prepareContractDocumentsData(CaseEntryDTO caseEntryDTO) {
        RequestDTO requestDTO = new RequestDTO();
        Organisation org = tbOrganisationService.findDefaultOrganisation();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        if(caseEntryDTO.getOrgid().equals(org.getOrgid())) {      	
        	requestDTO.setOrgId(caseEntryDTO.getOrgid());
       }else {
        requestDTO.setOrgId(orgId);
        }
        requestDTO.setStatus(MainetConstants.FlagA);
        requestDTO.setDepartmentName(
                tbDepartmentService.findDepartmentShortCodeByDeptId(caseEntryDTO.getCseDeptid(), orgId));
        requestDTO.setIdfId(
                MainetConstants.Legal.CASE_ENTRY + MainetConstants.FILE_PATH_SEPARATOR + caseEntryDTO.getCseId());
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        List<DocumentDetailsVO> dto = getCommonFileAttachment();

        setCommonFileAttachment(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
        int i = 0;
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            getCommonFileAttachment().get(i).setDoc_DESC_ENGL(dto.get(entry.getKey().intValue()).getDoc_DESC_ENGL());
            i++;
        }
        fileUpload.doMasterFileUpload(getCommonFileAttachment(), requestDTO);
        List<Long> enclosureRemoveById = null;
        String fileId = getRemoveCommonFileById();
        if (fileId != null && !fileId.isEmpty()) {
            enclosureRemoveById = new ArrayList<>();
            String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
            for (String fields : fileArray) {
                enclosureRemoveById.add(Long.valueOf(fields));
            }
        }
        if (enclosureRemoveById != null && !enclosureRemoveById.isEmpty())
            caseEntryService.deleteContractDocFileById(enclosureRemoveById,
                    UserSession.getCurrent().getEmployee().getEmpId());
    }

    public List<TbDepartment> getMapDeptList() {
        final List<TbDepartment> mapDeptList = tbDepartmentService
                .findAllMappedDepartments(UserSession.getCurrent().getOrganisation().getOrgid());
        return mapDeptList;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public List<CaseEntryDTO> getCaseEntryDTOList() {
        return caseEntryDTOList;
    }

    public void setCaseEntryDTOList(List<CaseEntryDTO> caseEntryDTOList) {
        this.caseEntryDTOList = caseEntryDTOList;
    }

    public CaseEntryDTO getCaseEntryDTO() {
        return caseEntryDTO;
    }

    public void setCaseEntryDTO(CaseEntryDTO caseEntryDTO) {
        this.caseEntryDTO = caseEntryDTO;
    }

    public List<CaseEntryDetailDTO> getCaseEntryDetailDTO() {
        return caseEntryDetailDTO;
    }

    public void setCaseEntryDetailDTO(List<CaseEntryDetailDTO> caseEntryDetailDTO) {
        this.caseEntryDetailDTO = caseEntryDetailDTO;
    }

    public List<CaseEntryDetailDTO> getPlenfiffEntryDetailDTOList() {
        return plenfiffEntryDetailDTOList;
    }

    public void setPlenfiffEntryDetailDTOList(List<CaseEntryDetailDTO> plenfiffEntryDetailDTOList) {
        this.plenfiffEntryDetailDTOList = plenfiffEntryDetailDTOList;
    }

    public List<CaseEntryDetailDTO> getDefenderEntryDetailDTOList() {
        return defenderEntryDetailDTOList;
    }

    public void setDefenderEntryDetailDTOList(List<CaseEntryDetailDTO> defenderEntryDetailDTOList) {
        this.defenderEntryDetailDTOList = defenderEntryDetailDTOList;
    }

    public List<OfficerInchargeDetailsDTO> getOfficerInchargeDetailDTOList() {
        return officerInchargeDetailDTOList;
    }

    public void setOfficerInchargeDetailDTOList(List<OfficerInchargeDetailsDTO> officerInchargeDetailDTOList) {
        this.officerInchargeDetailDTOList = officerInchargeDetailDTOList;
    }

    public List<DocumentDetailsVO> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentDetailsVO> documents) {
        this.documents = documents;
    }

    public List<DocumentDetailsVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<DocumentDetailsVO> attachments) {
        this.attachments = attachments;
    }

    public List<AttachDocs> getAttachDocsList() {
        return attachDocsList;
    }

    public void setAttachDocsList(List<AttachDocs> attachDocsList) {
        this.attachDocsList = attachDocsList;
    }

    public String getRemoveCommonFileById() {
        return removeCommonFileById;
    }

    public void setRemoveCommonFileById(String removeCommonFileById) {
        this.removeCommonFileById = removeCommonFileById;
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

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public List<LocationDTO> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationDTO> locations) {
        this.locations = locations;
    }

    public List<CourtMasterDTO> getCourtMasterDTOList() {
        return courtMasterDTOList;
    }

    public void setCourtMasterDTOList(List<CourtMasterDTO> courtMasterDTOList) {
        this.courtMasterDTOList = courtMasterDTOList;
    }

    public List<AdvocateMasterDTO> getAdvocates() {
        return advocates;
    }

    public void setAdvocates(List<AdvocateMasterDTO> advocates) {
        this.advocates = advocates;
    }

    public List<EmployeeBean> getEmployee() {
        return employee;
    }

    public void setEmployee(List<EmployeeBean> employee) {
        this.employee = employee;
    }

    public AdvocateMasterDTO getAdvocateMasterDTO() {
        return advocateMasterDTO;
    }

    public void setAdvocateMasterDTO(AdvocateMasterDTO advocateMasterDTO) {
        this.advocateMasterDTO = advocateMasterDTO;
    }

    public JudgeMasterDTO getJudgeMasterDto() {
        return judgeMasterDto;
    }

    /**
	 * @return the orgList
	 */
	public List<Object[]> getOrgList() {
		return orgList;
	}

	/**
	 * @param orgList the orgList to set
	 */
	public void setOrgList(List<Object[]> orgList) {
		this.orgList = orgList;
	}

	public void setJudgeMasterDto(JudgeMasterDTO judgeMasterDto) {
        this.judgeMasterDto = judgeMasterDto;
    }

    public EmployeeBean getEmployeeBean() {
        return employeeBean;
    }

    public void setEmployeeBean(EmployeeBean employeeBean) {
        this.employeeBean = employeeBean;
    }

	public Long getParentOrgid() {
		return parentOrgid;
	}

	public void setParentOrgid(Long parentOrgid) {
		this.parentOrgid = parentOrgid;
	}

	public String getEnvFlag() {
		return envFlag;
	}

	public void setEnvFlag(String envFlag) {
		this.envFlag = envFlag;
	}

	public String getDsclEnv() {
		return dsclEnv;
	}

	public void setDsclEnv(String dsclEnv) {
		this.dsclEnv = dsclEnv;
	}

	public boolean isHearing() {
		return hearing;
	}

	public void setHearing(boolean hearing) {
		this.hearing = hearing;
	}

	public List<CaseHearingDTO> getHearingEntry() {
		return hearingEntry;
	}

	public void setHearingEntry(List<CaseHearingDTO> hearingEntry) {
		this.hearingEntry = hearingEntry;
	}

	public List<CaseHearingAttendeeDetailsDTO> getCaseHearingAttendeeDetailsDTOList() {
		return caseHearingAttendeeDetailsDTOList;
	}

	public void setCaseHearingAttendeeDetailsDTOList(
			List<CaseHearingAttendeeDetailsDTO> caseHearingAttendeeDetailsDTOList) {
		this.caseHearingAttendeeDetailsDTOList = caseHearingAttendeeDetailsDTOList;
	}

	public List<TbLglComntRevwDtlDTO> getTbLglComntRevwDtlDTOList() {
		return tbLglComntRevwDtlDTOList;
	}

	public void setTbLglComntRevwDtlDTOList(List<TbLglComntRevwDtlDTO> tbLglComntRevwDtlDTOList) {
		this.tbLglComntRevwDtlDTOList = tbLglComntRevwDtlDTOList;
	}

	public String getHearingMode() {
		return hearingMode;
	}

	public void setHearingMode(String hearingMode) {
		this.hearingMode = hearingMode;
	}

	public String getOrgFlag() {
		return orgFlag;
	}

	public void setOrgFlag(String orgFlag) {
		this.orgFlag = orgFlag;
	}

	public String getCaseHearingFlag() {
		return caseHearingFlag;
	}

	public void setCaseHearingFlag(String caseHearingFlag) {
		this.caseHearingFlag = caseHearingFlag;
	}

	public Map<Long, String> getCaseTypeList() {
		return caseTypeList;
	}

	public void setCaseTypeList(Map<Long, String> caseTypeList) {
		this.caseTypeList = caseTypeList;
	}

	public List<String> getSuitNoList() {
		return suitNoList;
	}

	public void setSuitNoList(List<String> suitNoList) {
		this.suitNoList = suitNoList;
	}
	
	
   
}
