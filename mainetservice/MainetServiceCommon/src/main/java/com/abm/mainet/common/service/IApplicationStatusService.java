package com.abm.mainet.common.service;

import java.util.List;

import com.abm.mainet.common.domain.ApplicationStatusEntity;
import com.abm.mainet.common.dto.ApplicationDetail;
import com.abm.mainet.common.dto.ApplicationStatusRequestVO;
import com.abm.mainet.common.integration.dto.WebServiceResponseDTO;

/**
 * @author vishnu.jagdale
 *
 */
public interface IApplicationStatusService {

    public List<ApplicationStatusEntity> getApplicationStatusList();

    public List<WebServiceResponseDTO> validateInput(ApplicationStatusRequestVO requestDTO, String flag) throws RuntimeException;

    public List<ApplicationDetail> getApplicationStatusListOpenForUser(ApplicationStatusRequestVO requestDTO)
            throws RuntimeException;

    public List<ApplicationDetail> getApplicationStatusDetail(ApplicationStatusRequestVO requestDTO)
            throws RuntimeException;

    List<WebServiceResponseDTO> validateInputWithoutOrganisation(ApplicationStatusRequestVO requestDTO, String flag)
            throws RuntimeException;

}
