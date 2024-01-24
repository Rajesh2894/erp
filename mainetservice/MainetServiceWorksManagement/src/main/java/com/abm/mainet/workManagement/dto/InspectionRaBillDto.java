package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.util.List;

import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;

public class InspectionRaBillDto implements Serializable {

    private static final long serialVersionUID = 2125338556978703108L;

    private VigilanceDto vigilanceDto;
    private RequestDTO requestDTO;
    private List<DocumentDetailsVO> attachments;
    
  
    
    private String status;

    public VigilanceDto getVigilanceDto() {
        return vigilanceDto;
    }

    public void setVigilanceDto(VigilanceDto vigilanceDto) {
        this.vigilanceDto = vigilanceDto;
    }

    public RequestDTO getRequestDTO() {
        return requestDTO;
    }

    public void setRequestDTO(RequestDTO requestDTO) {
        this.requestDTO = requestDTO;
    }

    public List<DocumentDetailsVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<DocumentDetailsVO> attachments) {
        this.attachments = attachments;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

   
}
