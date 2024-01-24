package com.abm.mainet.legal.ui.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.StringUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.legal.dto.AdvocateMasterDTO;
import com.abm.mainet.legal.dto.CaseEntryDTO;
import com.abm.mainet.legal.dto.JudgementMasterDto;
import com.abm.mainet.legal.service.CaseEntryService;
import com.abm.mainet.legal.service.IAdvocateMasterService;
import com.abm.mainet.legal.service.ICaseEntryService;
import com.abm.mainet.legal.service.IJudgementMasterService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Component
@Scope("session")
public class JudgementMasterModel extends AbstractFormModel {

    private static final long serialVersionUID = -7820206330837819278L;

    private List<TbDepartment> departmentsList;
    private List<CaseEntryDTO> caseEntryDtoList = new ArrayList<>();
    private List<JudgementMasterDto> judgementMasterDtoList = new ArrayList<>();
    private JudgementMasterDto judgementMasterDto = new JudgementMasterDto();
    private CaseEntryDTO caseEntryDTO = new CaseEntryDTO();
    List<LookUp> caseNoList = new ArrayList<>();
    private List<AttachDocs> attachDocsList = new ArrayList<>();
    private List<DocumentDetailsVO> attachments = new ArrayList<>();
    private String saveMode;
    private String removedIds;
    private List<Long> fileCountUpload;
    private Long length;
    private Long parentOrgid;
    private String caseDate;

    private String orgFlag;
    
    @Autowired
    IFileUploadService fileUpload;

    @Autowired
    IJudgementMasterService judgementMasterService;
    
    @Autowired
    private IAdvocateMasterService advocateMasterService;
    
    @Autowired
    private ISMSAndEmailService iSMSAndEmailService;
    
    @Autowired
    private ICaseEntryService caseEntryService;

    public List<TbDepartment> getDepartmentsList() {
        return departmentsList;
    }

    public void setDepartmentsList(List<TbDepartment> departmentsList) {
        this.departmentsList = departmentsList;
    }

    public JudgementMasterDto getJudgementMasterDto() {
        return judgementMasterDto;
    }

    public void setJudgementMasterDto(JudgementMasterDto judgementMasterDto) {
        this.judgementMasterDto = judgementMasterDto;
    }

    public List<LookUp> getCaseNoList() {
        return caseNoList;
    }

    public void setCaseNoList(List<LookUp> caseNoList) {
        this.caseNoList = caseNoList;
    }

    public List<CaseEntryDTO> getCaseEntryDtoList() {
        return caseEntryDtoList;
    }

    public void setCaseEntryDtoList(List<CaseEntryDTO> caseEntryDtoList) {
        this.caseEntryDtoList = caseEntryDtoList;
    }

    public List<JudgementMasterDto> getJudgementMasterDtoList() {
        return judgementMasterDtoList;
    }

    public void setJudgementMasterDtoList(List<JudgementMasterDto> judgementMasterDtoList) {
        this.judgementMasterDtoList = judgementMasterDtoList;
    }

    public CaseEntryDTO getCaseEntryDTO() {
        return caseEntryDTO;
    }

    public void setCaseEntryDTO(CaseEntryDTO caseEntryDTO) {
        this.caseEntryDTO = caseEntryDTO;
    }

    public List<AttachDocs> getAttachDocsList() {
        return attachDocsList;
    }

    public void setAttachDocsList(List<AttachDocs> attachDocsList) {
        this.attachDocsList = attachDocsList;
    }

    public List<DocumentDetailsVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<DocumentDetailsVO> attachments) {
        this.attachments = attachments;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public String getRemovedIds() {
        return removedIds;
    }

    public void setRemovedIds(String removedIds) {
        this.removedIds = removedIds;
    }

    public List<Long> getFileCountUpload() {
        return fileCountUpload;
    }

    public void setFileCountUpload(List<Long> fileCountUpload) {
        this.fileCountUpload = fileCountUpload;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
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


	public String getCaseDate() {
		return caseDate;
	}

	public void setCaseDate(String caseDate) {
		this.caseDate = caseDate;
	}

	@Override
    public boolean saveForm() {
        Long empId = UserSession.getCurrent().getEmployee().getEmpId();
        Date todayDate = new Date();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

        if (!validateList(judgementMasterDtoList)) {
            return false;
        }

        for (JudgementMasterDto dto : judgementMasterDtoList) {
            if (dto.getJudId() == null) {
                dto.setOrgId(orgId);
                dto.setCreatedBy(empId);
                dto.setCreatedDate(todayDate);
                dto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                dto.setJudgementStatus(MainetConstants.Common_Constant.ACTIVE_FLAG);
            } else {
                dto.setUpdatedBy(empId);
                dto.setUpdatedDate(todayDate);
                dto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
                dto.setJudgementStatus(MainetConstants.Common_Constant.ACTIVE_FLAG);
            }
        }
        setAttachments(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
        if (judgementMasterDtoList.get(0).getJudId() == null) {
            judgementMasterService.saveJudgementData(judgementMasterDtoList, getAttachments());
            setSuccessMessage(ApplicationSession.getInstance().getMessage("lgl.case.judgement.details.save"));
        } else {
            List<Long> removeIds = new ArrayList<>();
            if (removedIds != null && !removedIds.isEmpty()) {
                String array[] = removedIds.split(MainetConstants.operator.COMMA);
                for (String id : array) {
                    removeIds.add(Long.valueOf(id));
                }
            }
            judgementMasterService.updateJudgementData(judgementMasterDtoList, getAttachments(),
                    getAttachDocsList(), removeIds);
            setSuccessMessage(ApplicationSession.getInstance().getMessage("lgl.case.judgement.details.update"));
        }
        sendSMSAndEmail();
        return true;
    }

    public boolean validateList(List<JudgementMasterDto> detDtos) {
        boolean isCheck = true;
        for (int value = 0; value <= detDtos.size() - 1; value++) {
            if (detDtos.get(value).getJudDate() == null) {
                addValidationError(ApplicationSession.getInstance()
                        .getMessage("legal.case.judgement.validation.select.date") + (value + 1));
                isCheck = false;
            }
            if (detDtos.get(value).getJudSummaryDetail() == null) {
                addValidationError(ApplicationSession.getInstance()
                        .getMessage("legal.case.judgement.validation.details") + (value + 1));
                isCheck = false;
            }
        }
        if (isCheck)
            isCheck = checkDocumentList();
        return isCheck;

    }

    public boolean checkDocumentList() {
        boolean flag = true;
        final List<DocumentDetailsVO> docList = fileUpload.prepareFileUpload(getAttachments());
         Map<Long, Set<File>> fileMap = FileUploadUtility.getCurrent().getFileMap();
        if ((docList != null) && !docList.isEmpty()) {
            for (int i = 0; i < docList.size(); i++) {
                final DocumentDetailsVO doc = docList.get(i);
                if ((doc.getDocumentByteCode() == null) || doc.getDocumentByteCode().isEmpty()) {
                    addValidationError(
                            ApplicationSession.getInstance().getMessage("legal.case.judgement.validation.document")
                                    + (i + 1));
                    flag = false;
                }
            }
        }
        return flag;
    }

    public Map<Long, Set<File>> getUploadedFileList(List<AttachDocs> attachDocs,
            FileNetApplicationClient fileNetApplicationClient) {
        Set<File> fileList = null;
        Long x = 0L;
        Map<Long, Set<File>> fileMap = new HashMap<>();
        for (AttachDocs doc : attachDocs) {
            fileList = new HashSet<>();
            final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
                    + MainetConstants.FILE_PATH_SEPARATOR + MainetConstants.CommonMasterUi.SHOW_DOCS;
            String existingPath = doc.getAttPath() + MainetConstants.FILE_PATH_SEPARATOR + doc.getAttFname();
            final String fileName = StringUtility.staticStringAfterChar(MainetConstants.FILE_PATH_SEPARATOR,
                    existingPath);

            String directoryPath = StringUtility.staticStringBeforeChar(MainetConstants.FILE_PATH_SEPARATOR,
                    existingPath);

            directoryPath = directoryPath.replace(MainetConstants.FILE_PATH_SEPARATOR, MainetConstants.operator.COMMA);
            FileOutputStream fos = null;
            File file = null;
            try {
                final byte[] image = fileNetApplicationClient.getFileByte(fileName, directoryPath);

                Utility.createDirectory(Filepaths.getfilepath() + outputPath + MainetConstants.FILE_PATH_SEPARATOR);

                file = new File(Filepaths.getfilepath() + outputPath + MainetConstants.FILE_PATH_SEPARATOR + fileName);

                fos = new FileOutputStream(file);

                fos.write(image);

                fos.close();

            } catch (final Exception e) {
                throw new FrameworkException("Exception in getting getUploadedFileList", e);
            } finally {
                try {

                    if (fos != null) {
                        fos.close();
                    }

                } catch (final IOException e) {
                    throw new FrameworkException("Exception in getting getUploadedFileList", e);
                }
            }
            fileList.add(file);
            fileMap.put(x, fileList);
            x++;
        }

        return fileMap;
    }
    

	private void sendSMSAndEmail() {
			// SMS Email to Advocate 
			Organisation org = UserSession.getCurrent().getOrganisation();
			int langId = UserSession.getCurrent().getLanguageId();
			
			 String menuUrl = "JudgementMaster.html";
			final SMSAndEmailDTO dto = new SMSAndEmailDTO();
			CaseEntryDTO entryDTO = caseEntryService.getCaseEntryById(judgementMasterDtoList.get(0).getCseId());

			if (entryDTO.getAdvId() != null) {
				AdvocateMasterDTO advocate = advocateMasterService.getAdvocateMasterById(entryDTO.getAdvId());
				dto.setMobnumber(advocate.getAdvMobile());
				dto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
				dto.setAppName(String.join(" ", advocate.getAdvFirstNm(), advocate.getAdvLastNm()));
				dto.setAppDate(UtilityService.convertDateToDDMMYYYY(judgementMasterDtoList.get(0).getJudDate()));
				dto.setEmail(advocate.getAdvEmail());
				dto.setAppNo(entryDTO.getCseSuitNo());
				dto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
				org.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
				if (langId == 1) {
					dto.setOrgName(org.getONlsOrgname());
				} else {
					dto.setOrgName(org.getONlsOrgnameMar());
				}
				iSMSAndEmailService.sendEmailSMS("LEGL", menuUrl, PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG,
						dto, org, langId);
			}

			// SMS  Email to OIC
			final SMSAndEmailDTO oicSMSdto = new SMSAndEmailDTO();
			if (CollectionUtils.isNotEmpty(entryDTO.getTbLglCaseOICdetails())) {
				entryDTO.getTbLglCaseOICdetails().forEach(oicDTO -> {
						oicSMSdto.setMobnumber(oicDTO.getOicPhoneNo());
						oicSMSdto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
						oicSMSdto.setAppName(oicDTO.getOicName());
						oicSMSdto.setAppDate(UtilityService.convertDateToDDMMYYYY(judgementMasterDtoList.get(0).getJudDate()));
						oicSMSdto.setEmail(oicDTO.getOicEmailId());
						oicSMSdto.setAppNo(entryDTO.getCseSuitNo());
						oicSMSdto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
					    org.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
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
}
