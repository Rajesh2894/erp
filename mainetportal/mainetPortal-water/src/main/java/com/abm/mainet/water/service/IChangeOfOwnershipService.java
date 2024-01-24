package com.abm.mainet.water.service;

import java.io.IOException;
import java.util.List;

import org.springframework.validation.BindingResult;

import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.water.dto.ChangeOfOwnerRequestDTO;
import com.abm.mainet.water.dto.ChangeOfOwnerResponseDTO;
import com.abm.mainet.water.dto.ChangeOfUsageRequestDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface IChangeOfOwnershipService {

    ChangeOfOwnerRequestDTO mapCheckList(ChangeOfOwnerRequestDTO requestObject, List<DocumentDetailsVO> docs,
            BindingResult bindingResult);

    ChangeOfOwnerResponseDTO fetchOldConnectionData(ChangeOfOwnerRequestDTO requestVo)
            throws JsonParseException, JsonMappingException, IOException;

    ChangeOfOwnerResponseDTO saveOrUpdateChangeOfOwnerForm(ChangeOfOwnerRequestDTO changeOwnerMaster)
            throws JsonParseException, JsonMappingException, IOException;
    
    ChangeOfOwnerRequestDTO getAppicationDetails(Long applicationId, Long orgId);

}
