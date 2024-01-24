package com.abm.mainet.adh.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.integration.dto.RequestDTO;

/**
 * @author cherupelli.srikanth
 * @since 22 august 2019
 */
public class AgencyRegistrationRequestDto extends RequestDTO implements Serializable {

    private static final long serialVersionUID = -1003260014201967478L;

    private AdvertiserMasterDto masterDto = new AdvertiserMasterDto();

    private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
    
    
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

	public List<AdvertiserMasterDto> getMasterDtolist() {
		return masterDtolist;
	}

	public void setMasterDtolist(List<AdvertiserMasterDto> masterDtolist) {
		this.masterDtolist = masterDtolist;
	}

}
