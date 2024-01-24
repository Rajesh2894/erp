package com.abm.mainet.legal.ui.model;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.legal.dto.AdvocateMasterDTO;
import com.abm.mainet.legal.dto.CaseEntryDTO;
import com.abm.mainet.legal.dto.CaseHearingDTO;
import com.abm.mainet.legal.service.IAdvocateMasterService;
import com.abm.mainet.legal.service.ICaseHearingService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Component
@Scope("session")
public class HearingDateModel extends AbstractFormModel {

    private static final long serialVersionUID = 1L;

    @Autowired
    private ICaseHearingService caseHearingService;

    @Autowired
    private TbDepartmentService tbDepartmentService;

    @Autowired
    private IFileUploadService fileUpload;

    private String saveMode;
    
    @Autowired
    private IAdvocateMasterService advocateMasterService;
    
    @Autowired
    private ISMSAndEmailService iSMSAndEmailService;

    private List<CaseHearingDTO> hearingEntry = new ArrayList<>();
    private List<CaseHearingDTO> hearingEntryView = new ArrayList<>();
    private List<CaseEntryDTO> caseEntryDTOList;
    private CaseEntryDTO caseEntryDTO;
    private String orgFlag;
    private List<DocumentDetailsVO> documents = new ArrayList<>();
    private List<AttachDocs> attachDocsList = new ArrayList<>();
    private List<AttachDocs> caseAttachDocsList = new ArrayList<>();
    private Map<Long, AttachDocs> attachDocsMap = new HashMap<>();
    private Long parentOrgid;

    @Override
    public boolean saveForm() {
        Employee employee = getUserSession().getEmployee();
         hearingEntry.forEach(dto -> {
            if (null == dto.getHrId()) {
                dto.setCreatedBy(employee.getEmpId());
                dto.setCreatedDate(new Date());
                dto.setOrgid(getUserSession().getOrganisation().getOrgid());
                dto.setLgIpMac(employee.getEmppiservername());
                dto.setCseId(caseEntryDTO.getCseId());
                dto.setCseDeptid(caseEntryDTO.getCseDeptid());
            } else {
            	dto.setCseDeptid(caseEntryDTO.getCseDeptid());
                dto.setUpdatedBy(employee.getEmpId());
                dto.setUpdatedDate(new Date());
                dto.setLgIpMacUpd(employee.getEmppiservername());
            }
        });
         List<CaseHearingDTO> caseHearingDto= caseHearingService.saveAllCaseHearing(hearingEntry);
		/*
		 * Set<File> fileSet =
		 * FileUploadUtility.getCurrent().getFileMap().values().stream()
		 * .flatMap(Set::stream) // Flatten the Set<File> into a Stream<File>
		 * .collect(Collectors.toSet());
		 * 
		 * List<DocumentDetailsVO> documents = getDocuments(); if(!fileSet.isEmpty() &&
		 * fileSet != null)
		 */
         if(caseHearingDto != null) {
        	 caseHearingDto.get(caseHearingDto.size() - 1).setCseDeptid(caseEntryDTO.getCseDeptid());
         	prepareContractDocumentsData(caseHearingDto.get(caseHearingDto.size() - 1));
         }

        this.setSuccessMessage(getAppSession().getMessage("CaseHearingDTO.save.sucess"));
        sendSMSAndEmail();
        return true;
    }

    public void prepareContractDocumentsData(CaseHearingDTO hearingEntryDTO) {
        RequestDTO requestDTO = new RequestDTO();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        requestDTO.setOrgId(orgId);
        requestDTO.setStatus(MainetConstants.FlagA);
        requestDTO.setDepartmentName(
                tbDepartmentService.findDepartmentShortCodeByDeptId(hearingEntryDTO.getCseDeptid(), orgId));
        requestDTO.setIdfId(MainetConstants.Legal.HEARING_DATE + MainetConstants.FILE_PATH_SEPARATOR + hearingEntryDTO.getHrId());
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        	
        setDocuments(fileUpload.setFileUploadMethod(getDocuments()));
        fileUpload.doMasterFileUpload(getDocuments(), requestDTO);

    }
    

	@SuppressWarnings("deprecation")
	private void sendSMSAndEmail() {
			// SMS Email to Advocate 
			Organisation org = UserSession.getCurrent().getOrganisation();
			int langId = UserSession.getCurrent().getLanguageId();
			
			 String menuUrl = "HearingDate.html";
			final SMSAndEmailDTO dto = new SMSAndEmailDTO();

			if (caseEntryDTO.getAdvId() != null) {
				AdvocateMasterDTO advocate = advocateMasterService.getAdvocateMasterById(caseEntryDTO.getAdvId());
				dto.setMobnumber(advocate.getAdvMobile());
				dto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
				dto.setAppName(String.join(" ", advocate.getAdvFirstNm(), advocate.getAdvLastNm()));
				dto.setAppDate(UtilityService.convertDateToDDMMYYYY(hearingEntry.get(0).getCreatedDate()));
				dto.setEmail(advocate.getAdvEmail());
				dto.setAppNo(caseEntryDTO.getCseSuitNo());
				dto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
				org.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
				//D#147168
				hearingEntry.forEach(hrdto ->{
					Long lookUpId = CommonMasterUtility.getValueFromPrefixLookUp("SH", "HSC", UserSession.getCurrent().getOrganisation()).getLookUpId();
					if (lookUpId.equals(hrdto.getHrStatus()))
				    dto.setHearDate(UtilityService.convertDateToDDMMYYYY(hrdto.getHrDate()));
				});
				
				//#122408
				dto.setCaseDate(UtilityService.convertDateToDDMMYYYY(caseEntryDTO.getCseDate()));
				LookUp lookup = CommonMasterUtility.getNonHierarchicalLookUpObject(caseEntryDTO.getCseTypId());
				
				if (langId == 1) {
				dto.setOrgName(org.getONlsOrgname());
				if (lookup != null && StringUtils.isNotEmpty(lookup.getDescLangFirst()))
				dto.setSubtype(lookup.getDescLangFirst());
				}
				else {
					dto.setOrgName(org.getONlsOrgnameMar());
					if (lookup != null && StringUtils.isNotEmpty(lookup.getDescLangSecond()))
					dto.setSubtype(lookup.getDescLangSecond());
				}
				iSMSAndEmailService.sendEmailSMS("LEGL", menuUrl, PrefixConstants.SMS_EMAIL_ALERT_TYPE.SCHEDULED_MESSAGE,
						dto, org, langId);
			}

			// SMS  Email to OIC
			final SMSAndEmailDTO oicSMSdto = new SMSAndEmailDTO();
			if (CollectionUtils.isNotEmpty(caseEntryDTO.getTbLglCaseOICdetails())) {
				caseEntryDTO.getTbLglCaseOICdetails().forEach(oicDTO -> {
						oicSMSdto.setMobnumber(oicDTO.getOicPhoneNo());
						oicSMSdto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
						oicSMSdto.setAppName(oicDTO.getOicName());
						oicSMSdto.setAppDate(UtilityService.convertDateToDDMMYYYY(caseEntryDTO.getCseDate()));
						oicSMSdto.setEmail(oicDTO.getOicEmailId());
						oicSMSdto.setAppNo(caseEntryDTO.getCseSuitNo());
						oicSMSdto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
					    org.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
					    //D#147168
						hearingEntry.forEach(hrdto ->{
							Long lookUpId = CommonMasterUtility.getValueFromPrefixLookUp("SH", "HSC", UserSession.getCurrent().getOrganisation()).getLookUpId();
							if (lookUpId.equals(hrdto.getHrStatus()))
							  oicSMSdto.setHearDate(UtilityService.convertDateToDDMMYYYY(hrdto.getHrDate()));
						});
						if (langId == 1) {
							dto.setOrgName(org.getONlsOrgname());
							}
							else {
								dto.setOrgName(org.getONlsOrgnameMar());
							}
						iSMSAndEmailService.sendEmailSMS("LEGL", menuUrl,
								PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG, oicSMSdto, org, langId);
				});
			}

		
	}

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public List<CaseHearingDTO> getHearingEntry() {
        return hearingEntry;
    }

    public void setHearingEntry(List<CaseHearingDTO> hearingEntry) {
        this.hearingEntry = hearingEntry;
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

    public List<DocumentDetailsVO> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentDetailsVO> documents) {
        this.documents = documents;
    }

    public List<AttachDocs> getAttachDocsList() {
        return attachDocsList;
    }

    public void setAttachDocsList(List<AttachDocs> attachDocsList) {
        this.attachDocsList = attachDocsList;
    }

    /**
     * @return the caseAttachDocsList
     */
    public List<AttachDocs> getCaseAttachDocsList() {
        return caseAttachDocsList;
    }

    /**
     * @param caseAttachDocsList the caseAttachDocsList to set
     */
    public void setCaseAttachDocsList(List<AttachDocs> caseAttachDocsList) {
        this.caseAttachDocsList = caseAttachDocsList;
    }

	/**
	 * @return the hearingEntryView
	 */
	public List<CaseHearingDTO> getHearingEntryView() {
		return hearingEntryView;
	}

	/**
	 * @param hearingEntryView the hearingEntryView to set
	 */
	public void setHearingEntryView(List<CaseHearingDTO> hearingEntryView) {
		this.hearingEntryView = hearingEntryView;
	}

	/**
	 * @return the orgFlag
	 */
	public String getOrgFlag() {
		return orgFlag;
	}

	/**
	 * @param orgFlag the orgFlag to set
	 */
	public void setOrgFlag(String orgFlag) {
		this.orgFlag = orgFlag;
	}

	public Long getParentOrgid() {
		return parentOrgid;
	}

	public void setParentOrgid(Long parentOrgid) {
		this.parentOrgid = parentOrgid;
	}

	public Map<Long, AttachDocs> getAttachDocsMap() {
		return attachDocsMap;
	}

	public void setAttachDocsMap(Map<Long, AttachDocs> attachDocsMap) {
		this.attachDocsMap = attachDocsMap;
	}
    

}
