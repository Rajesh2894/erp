package com.abm.mainet.water.service;

import java.io.IOException;

import com.abm.mainet.water.dto.WaterReconnectionRequestDTO;
import com.abm.mainet.water.dto.WaterReconnectionResponseDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface IWaterReconnectionFormService {

    WaterReconnectionRequestDTO searchConnectionDetails(WaterReconnectionRequestDTO requestDTO)
            throws JsonParseException, JsonMappingException, IOException;

    WaterReconnectionResponseDTO serachPlumerLicense(WaterReconnectionRequestDTO requestDTO)
            throws JsonParseException, JsonMappingException, IOException;

    WaterReconnectionResponseDTO saveOrUpdateReconnection(WaterReconnectionRequestDTO reconnectionRequestDTO)
            throws JsonParseException, JsonMappingException, IOException;
    
    WaterReconnectionRequestDTO getAppicationDetails(Long applicationId, Long orgId);

}
