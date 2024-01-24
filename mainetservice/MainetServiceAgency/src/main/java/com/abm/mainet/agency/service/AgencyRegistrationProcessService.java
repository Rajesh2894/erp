package com.abm.mainet.agency.service;

import com.abm.mainet.agency.authentication.dto.AgencyEmployeeReqDTO;
import com.abm.mainet.agency.authentication.dto.AgencyEmployeeResDTO;
import com.abm.mainet.agency.dto.TPAgencyReqDTO;
import com.abm.mainet.agency.dto.TPAgencyResDTO;

/**
 * @author Arun.Chavda
 *
 */
public interface AgencyRegistrationProcessService {

    AgencyEmployeeResDTO saveAgnEmployeeDetails(AgencyEmployeeReqDTO requestDto);
    TPAgencyResDTO getAuthStatus(TPAgencyReqDTO requestDTO);
    TPAgencyResDTO saveReUploadDocuments(TPAgencyReqDTO requestDTO);

}
