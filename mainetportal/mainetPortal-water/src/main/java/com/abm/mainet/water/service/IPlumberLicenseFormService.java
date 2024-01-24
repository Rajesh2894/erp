package com.abm.mainet.water.service;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.abm.mainet.water.dto.PlumberLicenseRequestDTO;
import com.abm.mainet.water.dto.PlumberLicenseResponseDTO;

public interface IPlumberLicenseFormService {

	PlumberLicenseResponseDTO saveOrUpdatePlumberLicense(PlumberLicenseRequestDTO requestDTO)
			throws JsonParseException, JsonMappingException, IOException;

	PlumberLicenseRequestDTO getApplicationDetails(Long applicationId, Long orgId);

	PlumberLicenseRequestDTO getPlumberDetailsByLicenseNumber(Long orgId, String plumberLicenceNo);

	PlumberLicenseResponseDTO savePlumberRenewalData(PlumberLicenseRequestDTO requestDTO)
			throws JsonParseException, JsonMappingException, IOException;

	PlumberLicenseResponseDTO saveDuplicatePlumberData(PlumberLicenseRequestDTO plumDto)
			throws JsonParseException, JsonMappingException, IOException;

}
