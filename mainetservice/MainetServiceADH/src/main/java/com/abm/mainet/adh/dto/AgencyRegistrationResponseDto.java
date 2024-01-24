package com.abm.mainet.adh.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.abm.mainet.common.dto.ApplicantDetailDTO;

/**
 * @author cherupelli.srikanth
 * @since 22 august 2019
 */
public class AgencyRegistrationResponseDto implements Serializable {

    private static final long serialVersionUID = -4183197379596283499L;

    private AdvertiserMasterDto masterDto = new AdvertiserMasterDto();

    private ApplicantDetailDTO applicantDetailDTO = new ApplicantDetailDTO();

    private Long applicationId;

    private String status;

    private List<AdvertiserMasterDto> masterDtolist = new ArrayList<>();

    private String errorMsg;

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public AdvertiserMasterDto getMasterDto() {
        return masterDto;
    }

    public void setMasterDto(AdvertiserMasterDto masterDto) {
        this.masterDto = masterDto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ApplicantDetailDTO getApplicantDetailDTO() {
        return applicantDetailDTO;
    }

    public void setApplicantDetailDTO(ApplicantDetailDTO applicantDetailDTO) {
        this.applicantDetailDTO = applicantDetailDTO;
    }

    public List<AdvertiserMasterDto> getMasterDtolist() {
        return masterDtolist;
    }

    public void setMasterDtolist(List<AdvertiserMasterDto> masterDtolist) {
        this.masterDtolist = masterDtolist;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
