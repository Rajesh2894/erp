package com.abm.mainet.swm.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.ui.model.AbstractFormModel;

@Component
@Scope("session")
public class FileDownLoadModel extends AbstractFormModel {
    private static final long serialVersionUID = 8891450974245930500L;

    private List<AttachDocs> listAttachDocs = new ArrayList<>();
    private String uploadedFile = MainetConstants.BLANK;
    private AttachDocs attachDocs = new AttachDocs();

    public List<AttachDocs> getListAttachDocs() {
        return listAttachDocs;
    }

    public void setListAttachDocs(List<AttachDocs> listAttachDocs) {
        this.listAttachDocs = listAttachDocs;
    }

    public String getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(String uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public AttachDocs getAttachDocs() {
        return attachDocs;
    }

    public void setAttachDocs(AttachDocs attachDocs) {
        this.attachDocs = attachDocs;
    }

}
