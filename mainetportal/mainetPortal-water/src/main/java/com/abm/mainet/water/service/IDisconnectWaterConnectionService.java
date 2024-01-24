package com.abm.mainet.water.service;

import java.io.IOException;

import com.abm.mainet.water.dto.WaterDeconnectionRequestDTO;
import com.abm.mainet.water.dto.WaterDisconnectionResponseDTO;
import com.abm.mainet.water.dto.WaterReconnectionRequestDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface IDisconnectWaterConnectionService {

    WaterDisconnectionResponseDTO fetchConnectionDetails(WaterDeconnectionRequestDTO waterRequestDto)
            throws JsonParseException, JsonMappingException, IOException;

    Object validatePlumbetValidity(String plumberLicence);

    WaterDisconnectionResponseDTO saveOrUpdateDisconnection(WaterDeconnectionRequestDTO waterRequestDto)
            throws JsonParseException, JsonMappingException, IOException;
    
    WaterDeconnectionRequestDTO getWaterDisconnecDetails(Long applicationId, Long orgId);

}