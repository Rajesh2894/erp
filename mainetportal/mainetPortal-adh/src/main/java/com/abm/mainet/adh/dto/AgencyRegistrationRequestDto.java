package com.abm.mainet.adh.dto;

import java.util.ArrayList;
import java.util.List;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.RequestDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author cherupelli.srikanth
 * @since 17 October 2019
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgencyRegistrationRequestDto extends RequestDTO {

	private static final long serialVersionUID = 7997062942246432578L;

	private AdvertiserMasterDto masterDto = new AdvertiserMasterDto();

	private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();

	 private String status;
	 
	private List<AdvertiserMasterDto> masterDtolist = new ArrayList<>();
	 
	public AdvertiserMasterDto getMasterDto() {
		return masterDto;
	}

	public void setMasterDto(AdvertiserMasterDto masterDto) {
		this.masterDto = masterDto;
	}

	public ApplicantDetailDTO getApplicantDetailDto() {
		return applicantDetailDto;
	}

	public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
		this.applicantDetailDto = applicantDetailDto;
	}

	
	public String getStatus() {
		return status;
	}

	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public List<AdvertiserMasterDto> getMasterDtolist() {
		return masterDtolist;
	}

	public void setMasterDtolist(List<AdvertiserMasterDto> masterDtolist) {
		this.masterDtolist = masterDtolist;
	}

	

}
