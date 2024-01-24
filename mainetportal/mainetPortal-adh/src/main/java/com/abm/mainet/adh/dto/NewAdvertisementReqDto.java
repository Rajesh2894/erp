package com.abm.mainet.adh.dto;

import java.io.Serializable;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.RequestDTO;

/**
 * @author vishwajeet.kumar
 * @since 14 October 2019
 */
public class NewAdvertisementReqDto extends RequestDTO implements Serializable {

    private static final long serialVersionUID = -1461426011991369522L;
    private NewAdvertisementApplicationDto advertisementDto = new NewAdvertisementApplicationDto();
    private ApplicantDetailDTO applicantDetailDTO = new ApplicantDetailDTO();
    private String serviceName;
    private String applicantName;
    private String newOrRenewalApp;

    public NewAdvertisementApplicationDto getAdvertisementDto() {
        return advertisementDto;
    }

    public void setAdvertisementDto(NewAdvertisementApplicationDto advertisementDto) {
        this.advertisementDto = advertisementDto;
    }

    public ApplicantDetailDTO getApplicantDetailDTO() {
        return applicantDetailDTO;
    }

    public void setApplicantDetailDTO(ApplicantDetailDTO applicantDetailDTO) {
        this.applicantDetailDTO = applicantDetailDTO;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getNewOrRenewalApp() {
        return newOrRenewalApp;
    }

    public void setNewOrRenewalApp(String newOrRenewalApp) {
        this.newOrRenewalApp = newOrRenewalApp;
    }

}
