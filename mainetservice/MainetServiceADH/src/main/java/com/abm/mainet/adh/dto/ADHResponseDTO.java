package com.abm.mainet.adh.dto;

import java.io.Serializable;

public class ADHResponseDTO implements Serializable {

    private static final long serialVersionUID = 5470455015712159097L;
    AgencyRegistrationRequestDto agencyRegistrationRequestDto = new AgencyRegistrationRequestDto();
    NewAdvertisementReqDto advertisementReqDto = new NewAdvertisementReqDto();

    public AgencyRegistrationRequestDto getAgencyRegistrationRequestDto() {
        return agencyRegistrationRequestDto;
    }

    public void setAgencyRegistrationRequestDto(AgencyRegistrationRequestDto agencyRegistrationRequestDto) {
        this.agencyRegistrationRequestDto = agencyRegistrationRequestDto;
    }

    public NewAdvertisementReqDto getAdvertisementReqDto() {
        return advertisementReqDto;
    }

    public void setAdvertisementReqDto(NewAdvertisementReqDto advertisementReqDto) {
        this.advertisementReqDto = advertisementReqDto;
    }

}
