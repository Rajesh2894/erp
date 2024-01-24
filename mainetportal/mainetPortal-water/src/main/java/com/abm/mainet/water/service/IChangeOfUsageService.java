package com.abm.mainet.water.service;

import java.io.IOException;

import com.abm.mainet.water.dto.ChangeOfUsageRequestDTO;
import com.abm.mainet.water.dto.ChangeOfUsageResponseDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface IChangeOfUsageService {

    ChangeOfUsageResponseDTO fetchConnectionData(ChangeOfUsageRequestDTO requestVo)
            throws JsonParseException, JsonMappingException, IOException;

    ChangeOfUsageResponseDTO saveOrUpdateChangeUsage(ChangeOfUsageRequestDTO requestDTO)
            throws JsonParseException, JsonMappingException, IOException;

    ChangeOfUsageRequestDTO getAppicationDetails(Long applicationId, Long orgId);
            
}
