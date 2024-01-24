package com.abm.mainet.common.dto;

public class TPAgencyReqDTO extends RequestDTO {

    private static final long serialVersionUID = 7056154998514358829L;

    private TPTechPersonLicMasDTO tpTechPersonLicMasDTO;

    private Long title;
    private String address;
    private int langaugeId;
    private String isDeleted;

    public TPTechPersonLicMasDTO getTpTechPersonLicMasDTO() {
        return tpTechPersonLicMasDTO;
    }

    public void setTpTechPersonLicMasDTO(final TPTechPersonLicMasDTO tpTechPersonLicMasDTO) {
        this.tpTechPersonLicMasDTO = tpTechPersonLicMasDTO;
    }

    public Long getTitle() {
        return title;
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
