package com.abm.mainet.care.dto;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class LandInspectionReqDTO implements Serializable {

    private static final long serialVersionUID = 4324670955994557384L;
    private RequestDTO applicantDetailDto;
    private List<DocumentDetailsVO> attachments;
    private LandInspectionDto landInspectionDto;
    private WorkflowTaskAction action;

    public RequestDTO getApplicantDetailDto() {
        return applicantDetailDto;
    }

    public void setApplicantDetailDto(RequestDTO applicantDetailDto) {
        this.applicantDetailDto = applicantDetailDto;
    }

    public List<DocumentDetailsVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<DocumentDetailsVO> attachments) {
        this.attachments = attachments;
    }

    public LandInspectionDto getLandInspectionDto() {
        return landInspectionDto;
    }

    public void setLandInspectionDto(LandInspectionDto landInspectionDto) {
        this.landInspectionDto = landInspectionDto;
    }

    public WorkflowTaskAction getAction() {
        return action;
    }

    public void setAction(WorkflowTaskAction action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "LandInspectionReqDTO [applicantDetailDto=" + applicantDetailDto + ", attachments=" + attachments
                + ", landInspectionDto=" + landInspectionDto + ", action=" + action + "]";
    }

}
