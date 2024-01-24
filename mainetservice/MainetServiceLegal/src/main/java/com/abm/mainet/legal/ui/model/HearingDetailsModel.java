package com.abm.mainet.legal.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.legal.dto.AdvocateMasterDTO;
import com.abm.mainet.legal.dto.CaseEntryDTO;
import com.abm.mainet.legal.dto.CaseHearingAttendeeDetailsDTO;
import com.abm.mainet.legal.dto.CaseHearingDTO;
import com.abm.mainet.legal.dto.JudgeMasterDTO;
import com.abm.mainet.legal.dto.TbLglComntRevwDtlDTO;
import com.abm.mainet.legal.service.IAdvocateMasterService;
import com.abm.mainet.legal.service.ICaseHearingService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Component
@Scope("session")
public class HearingDetailsModel extends AbstractFormModel {

    @Autowired
    private ICaseHearingService caseHearingService;
    
    @Autowired
    private IAdvocateMasterService advocateMasterService;
    
    @Autowired
    private ISMSAndEmailService iSMSAndEmailService;

    private static final long serialVersionUID = 1L;
    private String saveMode;
    private String hearingMode;
    private boolean hearing;
    private Long caseHearingId;
    private String orgFlag;
    private Long orgId;
    private List<CaseHearingDTO> hearingEntry;

    private List<CaseEntryDTO> caseEntryDTOList;
    private CaseEntryDTO caseEntryDTO;
    private CaseHearingDTO hearingEntity;
    private List<AttachDocs> attachDocsList = new ArrayList<>();
    private List<AttachDocs> caseAttachDocsList = new ArrayList<>();
    private List<CaseHearingAttendeeDetailsDTO> caseHearingAttendeeDetailsDTOList;
    private String removeAttendeeId;
    private JudgeMasterDTO judgeMasterDto;
    private Long parentOrgid;
    private Map<Long, AttachDocs> attachDocsMap;
    
    public Map<Long, AttachDocs> getAttachDocsMap() {
		return attachDocsMap;
	}

	public void setAttachDocsMap(Map<Long, AttachDocs> attachDocsMap) {
		this.attachDocsMap = attachDocsMap;
	}

	private String caseHearingFlag;
    
    private List<TbLglComntRevwDtlDTO> tbLglComntRevwDtlDTOList;
    private String removeComntId; 
    
    @Override
    public boolean saveForm() {
        Employee employee = getUserSession().getEmployee();

        hearingEntity.setUpdatedBy(employee.getEmpId());
        hearingEntity.setUpdatedDate(new Date());
        hearingEntity.setLgIpMacUpd(employee.getEmppiservername());

        caseEntryDTO.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        if (caseEntryDTO.getOfficeIncharge() == null || caseEntryDTO.getOfficeIncharge() == 0) {
            caseEntryDTO.setOfficeIncharge(UserSession.getCurrent().getEmployee().getEmpId());
        }

        caseHearingService.saveCaseHearing(hearingEntity);

        // insert in tb_lgl_hearingattendee_details and tb_lgl_hearing_hist table

        List<Long> removeAttendeeIds = new ArrayList<>();
        String ids = getRemoveAttendeeId();
        if (StringUtils.isNotBlank(ids)) {
            String arrayId[] = ids.split(MainetConstants.operator.COMMA);
            for (String id : arrayId) {
                removeAttendeeIds.add(Long.valueOf(id));
            }
        }

        caseHearingService.saveAndUpdateCaseHearingAttendee(caseHearingAttendeeDetailsDTOList, caseEntryDTO,
                removeAttendeeIds,UserSession.getCurrent().getOrganisation());
        
     // insert in TB_LGL_COMNT_REVW_DTL and TB_LGL_COMNT_REVW_DTL_HIST table

        List<Long> removeComntIds = new ArrayList<>();
        String comntids = getRemoveComntId();
        if (StringUtils.isNotBlank(comntids)) {
            String arrayId[] = comntids.split(MainetConstants.operator.COMMA);
            for (String id : arrayId) {
            	removeComntIds.add(Long.valueOf(id));
            }
        }

        caseHearingService.saveAndUpdtHearingComntAndRevw(tbLglComntRevwDtlDTOList, caseEntryDTO,
        		removeComntIds,UserSession.getCurrent().getOrganisation());
        
        this.setSuccessMessage(getAppSession().getMessage("CaseHearingDTO.save.details"));
        sendSMSAndEmail();
        return true;
    }
    
    private void sendSMSAndEmail() {
		// SMS Email to Advocate 
		Organisation org = UserSession.getCurrent().getOrganisation();
		int langId = UserSession.getCurrent().getLanguageId();
		
		 String menuUrl = "HearingDetails.html";
		final SMSAndEmailDTO dto = new SMSAndEmailDTO();

		if (hearingEntity.getAdvId() != null) {
			AdvocateMasterDTO advocate = advocateMasterService.getAdvocateMasterById(hearingEntity.getAdvId());
			dto.setMobnumber(advocate.getAdvMobile());
			dto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
			dto.setAppName(String.join(" ", advocate.getAdvFirstNm(), advocate.getAdvLastNm()));
			dto.setAppDate(UtilityService.convertDateToDDMMYYYY(hearingEntity.getHrDate()));
			dto.setEmail(advocate.getAdvEmail());
			dto.setAppNo(caseEntryDTO.getCseSuitNo());
			dto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			org.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
			dto.setHearDate(UtilityService.convertDateToDDMMYYYY(hearingEntity.getHrDate()));
			//#122408
			dto.setCaseDate(UtilityService.convertDateToDDMMYYYY(caseEntryDTO.getCseDate()));
			LookUp lookup = CommonMasterUtility.getNonHierarchicalLookUpObject(caseEntryDTO.getCseTypId());

			if (langId == 1) {
				dto.setOrgName(org.getONlsOrgname());
				if (lookup != null && StringUtils.isNotEmpty(lookup.getDescLangFirst()))
					dto.setSubtype(lookup.getDescLangFirst());
			} else {
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
				    oicSMSdto.setHearDate(UtilityService.convertDateToDDMMYYYY(hearingEntry.get(0).getHrDate()));
					if (langId == 1) {
						dto.setOrgName(org.getONlsOrgname());}
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
        if (hearingEntry == null) {
            hearingEntry = new ArrayList<>(1);
            hearingEntry.add(new CaseHearingDTO());
        }
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

    public CaseHearingDTO getHearingEntity() {
        return hearingEntity;
    }

    public void setHearingEntity(CaseHearingDTO hearingEntity) {
        this.hearingEntity = hearingEntity;
    }

    public boolean isHearing() {
        return hearing;
    }

    public void setHearing(boolean hearing) {
        this.hearing = hearing;
    }

    public List<AttachDocs> getAttachDocsList() {
        return attachDocsList;
    }

    public void setAttachDocsList(List<AttachDocs> attachDocsList) {
        this.attachDocsList = attachDocsList;
    }

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
     * @return the hearingMode
     */
    public String getHearingMode() {
        return hearingMode;
    }

    /**
     * @param hearingMode the hearingMode to set
     */
    public void setHearingMode(String hearingMode) {
        this.hearingMode = hearingMode;
    }

    public List<CaseHearingAttendeeDetailsDTO> getCaseHearingAttendeeDetailsDTOList() {
        return caseHearingAttendeeDetailsDTOList;
    }

    public void setCaseHearingAttendeeDetailsDTOList(List<CaseHearingAttendeeDetailsDTO> caseHearingAttendeeDetailsDTOList) {
        this.caseHearingAttendeeDetailsDTOList = caseHearingAttendeeDetailsDTOList;
    }

    public String getRemoveAttendeeId() {
        return removeAttendeeId;
    }

    public void setRemoveAttendeeId(String removeAttendeeId) {
        this.removeAttendeeId = removeAttendeeId;
    }

	public Long getCaseHearingId() {
		return caseHearingId;
	}

	public void setCaseHearingId(Long caseHearingId) {
		this.caseHearingId = caseHearingId;
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

	/**
	 * @return the orgId
	 */
	public Long getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getParentOrgid() {
		return parentOrgid;
	}

	public void setParentOrgid(Long parentOrgid) {
		this.parentOrgid = parentOrgid;
	}

	public String getRemoveComntId() {
		return removeComntId;
	}

	public void setRemoveComntId(String removeComntId) {
		this.removeComntId = removeComntId;
	}

	public List<TbLglComntRevwDtlDTO> getTbLglComntRevwDtlDTOList() {
		return tbLglComntRevwDtlDTOList;
	}

	public void setTbLglComntRevwDtlDTOList(List<TbLglComntRevwDtlDTO> tbLglComntRevwDtlDTOList) {
		this.tbLglComntRevwDtlDTOList = tbLglComntRevwDtlDTOList;
	}

	public String getCaseHearingFlag() {
		return caseHearingFlag;
	}

	public void setCaseHearingFlag(String caseHearingFlag) {
		this.caseHearingFlag = caseHearingFlag;
	}

}
