package com.abm.mainet.care.dto;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.abm.mainet.common.dto.ActionDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.RequestDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class GrievanceReqDTO implements Serializable {

    private static final long serialVersionUID = 1082348551667448381L;

    private RequestDTO applicantDetailDto;
    private CareRequestDTO careRequest;
    private CareFeedbackDTO careFeedback;
    private ActionDTO action;
    private List<DocumentDetailsVO> attachments;
    private boolean reopen;

    public RequestDTO getApplicantDetailDto() {
        return applicantDetailDto;
    }

    public void setApplicantDetailDto(RequestDTO applicantDetailDto) {
        this.applicantDetailDto = applicantDetailDto;
    }

    public CareRequestDTO getCareRequest() {
        return careRequest;
    }

    public void setCareRequest(CareRequestDTO careRequest) {
        this.careRequest = careRequest;
    }

    public CareFeedbackDTO getCareFeedback() {
        return careFeedback;
    }

    public void setCareFeedback(CareFeedbackDTO careFeedback) {
        this.careFeedback = careFeedback;
    }

    public ActionDTO getAction() {
        return action;
    }

    public void setAction(ActionDTO action) {
        this.action = action;
    }

    public boolean isReopen() {
        return reopen;
    }

    public void setReopen(boolean reopen) {
        this.reopen = reopen;
    }

    public List<DocumentDetailsVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<DocumentDetailsVO> attachments) {
        this.attachments = attachments;
    }

}
