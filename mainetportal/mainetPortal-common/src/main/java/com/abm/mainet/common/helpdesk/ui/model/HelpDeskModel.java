package com.abm.mainet.common.helpdesk.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.domain.AttachDocs;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.HelpDeskDTO;
import com.abm.mainet.common.ui.model.AbstractModel;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class HelpDeskModel extends AbstractModel {

    private static final long serialVersionUID = 3775845240846854728L;

    private HelpDeskDTO entity = new HelpDeskDTO();
    private String saveMode;
    private List<Long> fileCountUpload;
    private List<DocumentDetailsVO> attachments = new ArrayList<>();
    private List<AttachDocs> attachDocsList = new ArrayList<>();
    private String removeFileById;
    private Long organisationId;
    private String isIPRDHelpDeskUser;
    public HelpDeskDTO getEntity() {
        return entity;
    }

    public void setEntity(HelpDeskDTO entity) {
        this.entity = entity;
    }

    public List<Long> getFileCountUpload() {
        return fileCountUpload;
    }

    public void setFileCountUpload(List<Long> fileCountUpload) {
        this.fileCountUpload = fileCountUpload;
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

    public String getRemoveFileById() {
        return removeFileById;
    }

    public void setRemoveFileById(String removeFileById) {
        this.removeFileById = removeFileById;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

	public Long getOrganisationId() {
		return organisationId;
	}

	public void setOrganisationId(Long organisationId) {
		this.organisationId = organisationId;
	}

	public String getIsIPRDHelpDeskUser() {
		return isIPRDHelpDeskUser;
	}

	public void setIsIPRDHelpDeskUser(String isIPRDHelpDeskUser) {
		this.isIPRDHelpDeskUser = isIPRDHelpDeskUser;
	}


}
