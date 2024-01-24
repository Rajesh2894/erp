package com.abm.mainet.common.dto;

public class TPAgencyResDTO extends ResponseDTO {

    private static final long serialVersionUID = -8857752438792074394L;

    private String authStatus;

    private TPTechPersonLicMasDTO tpTechPersonLicMasDTO;

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(final String authStatus) {
        this.authStatus = authStatus;
    }

    public TPTechPersonLicMasDTO getTpTechPersonLicMasDTO() {
        return tpTechPersonLicMasDTO;
    }

    public void setTpTechPersonLicMasDTO(final TPTechPersonLicMasDTO tpTechPersonLicMasDTO) {
        this.tpTechPersonLicMasDTO = tpTechPersonLicMasDTO;
    }

}
