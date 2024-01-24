package com.abm.mainet.agency.dto;

import com.abm.mainet.common.integration.dto.ResponseDTO;

public class TPAgencyResDTO extends ResponseDTO {

    private static final long serialVersionUID = -8857752438792074394L;

    private TPTechPersonLicMasDTO tpTechPersonLicMasDTO;

    private String authStatus;

    /**
     * @return the authStatus
     */
    public String getAuthStatus() {
        return authStatus;
    }

    /**
     * @param authStatus the authStatus to set
     */
    public void setAuthStatus(final String authStatus) {
        this.authStatus = authStatus;
    }

    /**
     * @return the tpTechPersonLicMasDTO
     */
    public TPTechPersonLicMasDTO getTpTechPersonLicMasDTO() {
        return tpTechPersonLicMasDTO;
    }

    /**
     * @param tpTechPersonLicMasDTO the tpTechPersonLicMasDTO to set
     */
    public void setTpTechPersonLicMasDTO(final TPTechPersonLicMasDTO tpTechPersonLicMasDTO) {
        this.tpTechPersonLicMasDTO = tpTechPersonLicMasDTO;
    }

}
