package com.abm.mainet.cfc.checklist.dto;

import java.util.List;

import com.abm.mainet.common.integration.dms.dto.CFCAttachmentsDTO;
import com.abm.mainet.common.integration.dto.ResponseDTO;

public class DocumentResubmissionResponseDTO extends ResponseDTO {

    private static final long serialVersionUID = 1544691764082683349L;

    private ChecklistServiceDTO checklistDetail;
    private List<CFCAttachmentsDTO> attachmentList;

    public ChecklistServiceDTO getChecklistDetail() {
        return checklistDetail;
    }

    public void setChecklistDetail(final ChecklistServiceDTO checklistDetail) {
        this.checklistDetail = checklistDetail;
    }

    public List<CFCAttachmentsDTO> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(final List<CFCAttachmentsDTO> attachmentList) {
        this.attachmentList = attachmentList;
    }

}
