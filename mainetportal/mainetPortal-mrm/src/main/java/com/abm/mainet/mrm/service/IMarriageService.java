package com.abm.mainet.mrm.service;

import java.io.IOException;
import java.util.List;

import com.abm.mainet.mrm.dto.MarriageDTO;
import com.abm.mainet.mrm.dto.MarriageRequest;
import com.abm.mainet.mrm.dto.MarriageResponse;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface IMarriageService {
	
	
	MarriageResponse getMarriageData(MarriageRequest marriageRequest)throws JsonParseException, JsonMappingException, IOException;
	
	List<MarriageDTO> fetchMarriageData(MarriageRequest marriageRequest);
	
	MarriageDTO saveMarriageRegInDraftMode(MarriageDTO marriageDTO)throws JsonParseException, JsonMappingException, IOException;
	
	MarriageDTO saveWitnessDetails(MarriageDTO marriageDTO)throws JsonParseException, JsonMappingException, IOException;
    

}
