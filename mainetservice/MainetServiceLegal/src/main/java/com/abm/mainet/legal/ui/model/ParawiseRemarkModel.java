package com.abm.mainet.legal.ui.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.legal.dto.CaseEntryDTO;
import com.abm.mainet.legal.dto.CaseEntryDetailDTO;
import com.abm.mainet.legal.dto.ParawiseRemarkDTO;
import com.abm.mainet.legal.service.IParawiseRemarkService;

@Component
@Scope("session")
public class ParawiseRemarkModel extends AbstractFormModel {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IParawiseRemarkService parawiseRemarkService;

    @Resource
    IFileUploadService fileUpload;

    private String saveMode;
    private CaseEntryDTO caseEntryDTO = new CaseEntryDTO();
    private List<CaseEntryDTO> caseEntryDTOList;
    private List<CaseEntryDetailDTO> caseEntryDetailDTO;
    private List<ParawiseRemarkDTO> parawiseRemarkDTOList = new ArrayList<>();
    private List<AttachDocs> attachDocsList = new ArrayList<>();
    private List<AttachDocs> caseAttachDocsList = new ArrayList<>();
    private List<DocumentDetailsVO> attachments = new ArrayList<>();
    private List<ParawiseRemarkDTO> parawiseRemarkDTOListView = new ArrayList<>();
    private Long parentOrgid;
    private String removeParawiseIds;
    public List<AttachDocs> getAttachDocsList() {
        return attachDocsList;
    }

    public void setAttachDocsList(List<AttachDocs> attachDocsList) {
        this.attachDocsList = attachDocsList;
    }

    public List<ParawiseRemarkDTO> getParawiseRemarkDTOList() {
        return parawiseRemarkDTOList;
    }

    public void setParawiseRemarkDTOList(List<ParawiseRemarkDTO> parawiseRemarkDTOList) {
        this.parawiseRemarkDTOList = parawiseRemarkDTOList;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public CaseEntryDTO getCaseEntryDTO() {
        return caseEntryDTO;
    }

    public void setCaseEntryDTO(CaseEntryDTO caseEntryDTO) {
        this.caseEntryDTO = caseEntryDTO;
    }

    public List<CaseEntryDTO> getCaseEntryDTOList() {
        return caseEntryDTOList;
    }

    public void setCaseEntryDTOList(List<CaseEntryDTO> caseEntryDTOList) {
        this.caseEntryDTOList = caseEntryDTOList;
    }

    public List<CaseEntryDetailDTO> getCaseEntryDetailDTO() {
        return caseEntryDetailDTO;
    }

    public void setCaseEntryDetailDTO(List<CaseEntryDetailDTO> caseEntryDetailDTO) {
        this.caseEntryDetailDTO = caseEntryDetailDTO;
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

    public List<DocumentDetailsVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<DocumentDetailsVO> attachments) {
        this.attachments = attachments;
    }


	@Override
    public boolean saveForm() {

        Employee employee = getUserSession().getEmployee();
    	List<Long> removeParawiseIdsList = new ArrayList<>();
        parawiseRemarkDTOList.forEach(dto -> {
            if (null == dto.getParId()) {
            	dto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                //dto.setCreatedBy(employee.getEmpId());
                dto.setCreatedDate(new Date());
                dto.setOrgid(getUserSession().getOrganisation().getOrgid());
                dto.setLgIpMac(employee.getEmppiservername());
                dto.setCaseId(caseEntryDTO.getCseId());
                dto.setAtdId(1l);
            } else {

                dto.setUpdatedBy(employee.getEmpId());
                dto.setUpdatedDate(new Date());
                dto.setLgIpMacUpd(employee.getEmppiservername());
                dto.setAtdId(1l);

            }
        });
        final String parawiseIds = getRemoveParawiseIds();
		if (null != parawiseIds && !parawiseIds.isEmpty()) {
			final String array[] = parawiseIds.split(MainetConstants.operator.COMMA);
			for (final String string : array)
				removeParawiseIdsList.add(Long.valueOf(string));
		}
        Long count = 0l;
        for (ParawiseRemarkDTO dto : parawiseRemarkDTOList) {
            if (CollectionUtils.isNotEmpty(dto.getAttachments())) {
                dto.setParentOrgId(getUserSession().getOrganisation().getOrgid());
                for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                    if (count == entry.getKey()) {
                        dto.setAttachments(setFileUploadMethod1(entry.getValue()));
                    }
                }
                count++;
            } else {
                count++;
            }
        }
        parawiseRemarkService.saveAllParawiseRemark(parawiseRemarkDTOList, removeParawiseIdsList);
        this.setSuccessMessage(ApplicationSession.getInstance().getMessage("lgl.editparawiseRemark"));
        return true;

    }

    public List<DocumentDetailsVO> setFileUploadMethod1(Set<File> list) {
        List<DocumentDetailsVO> docs = new ArrayList<>();
        Base64 base64 = null;
        // List<File> list = null;
        for (final File file : list) {
            try {
                base64 = new Base64();
                final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));

                DocumentDetailsVO d = new DocumentDetailsVO();
                d.setDocumentName(file.getName());
                d.setDocumentByteCode(bytestring);
                docs.add(d);

            } catch (final IOException e) {
                // LOGGER.error("Exception has been occurred in file byte to string conversions", e);
            }
        }

        return docs;
    }

	public List<ParawiseRemarkDTO> getParawiseRemarkDTOListView() {
		return parawiseRemarkDTOListView;
	}

	public void setParawiseRemarkDTOListView(List<ParawiseRemarkDTO> parawiseRemarkDTOListView) {
		this.parawiseRemarkDTOListView = parawiseRemarkDTOListView;
	}

	public Long getParentOrgid() {
		return parentOrgid;
	}

	public void setParentOrgid(Long parentOrgid) {
		this.parentOrgid = parentOrgid;
	}

	public String getRemoveParawiseIds() {
		return removeParawiseIds;
	}

	public void setRemoveParawiseIds(String removeParawiseIds) {
		this.removeParawiseIds = removeParawiseIds;
	}

    
}
