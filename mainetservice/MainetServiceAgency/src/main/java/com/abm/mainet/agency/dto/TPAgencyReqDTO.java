package com.abm.mainet.agency.dto;

import com.abm.mainet.common.integration.dto.RequestDTO;

/**
 * @author Arun.Chavda
 *
 */
public class TPAgencyReqDTO extends RequestDTO {

    private static final long serialVersionUID = 6380103866577529526L;

    private TPTechPersonLicMasDTO tpTechPersonLicMasDTO;
    private Long title;
    private String address;
    private int langaugeId;
    private String isDeleted;
    public Long getTitle() {
        return title;
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

    public void setTitle(final Long title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public int getLangaugeId() {
        return langaugeId;
    }

    public void setLangaugeId(final int langaugeId) {
        this.langaugeId = langaugeId;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(final String isDeleted) {
        this.isDeleted = isDeleted;
    }

}
