package com.abm.mainet.common.activity.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.domain.AttachDocs;
import com.abm.mainet.common.dto.ActivityManagementDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.ui.model.AbstractModel;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class ActivityManagementModel extends AbstractModel {

    private static final long serialVersionUID = 3775845240846854728L;

    private ActivityManagementDTO entity = new ActivityManagementDTO();

    private List<Long> fileCountUpload;
    private List<DocumentDetailsVO> attachments = new ArrayList<>();
    private List<AttachDocs> attachDocsList = new ArrayList<>();
    private String removeFileById;

    public ActivityManagementDTO getEntity() {
        return entity;
    }

    public void setEntity(ActivityManagementDTO entity) {
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

}
