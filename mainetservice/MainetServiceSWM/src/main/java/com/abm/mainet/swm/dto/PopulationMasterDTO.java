package com.abm.mainet.swm.dto;

import java.io.Serializable;
import java.util.Date;

public class PopulationMasterDTO implements Serializable {

    private static final long serialVersionUID = 5813367779693672312L;

    private Long popId;

    private Long codDwzid1;

    private Long codDwzid2;

    private Long codDwzid3;

    private Long codDwzid4;

    private Long codDwzid5;

    private String codDwzid1Str;

    private String codDwzid2Str;

    private String codDwzid3Str;

    private String codDwzid4Str;

    private String codDwzid5Str;

    private Long orgid;

    private Long popEst;

    private Long popYear;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long updatedBy;

    private Date updatedDate;

    private String searchMessage;

    private String popActive;

    private String populationfaYears;

    private String totalPopulation;
    
    private String delete;

    public String getPopulationfaYears() {
        return populationfaYears;
    }

    public void setPopulationfaYears(String populationfaYears) {
        this.populationfaYears = populationfaYears;
    }

    public Long getPopId() {
        return popId;
    }

    public void setPopId(Long popId) {
        this.popId = popId;
    }

    public Long getCodDwzid1() {
        return codDwzid1;
    }

    public void setCodDwzid1(Long codDwzid1) {
        this.codDwzid1 = codDwzid1;
    }

    public Long getCodDwzid2() {
        return codDwzid2;
    }

    public void setCodDwzid2(Long codDwzid2) {
        this.codDwzid2 = codDwzid2;
    }

    public Long getCodDwzid3() {
        return codDwzid3;
    }

    public void setCodDwzid3(Long codDwzid3) {
        this.codDwzid3 = codDwzid3;
    }

    public Long getCodDwzid4() {
        return codDwzid4;
    }

    public void setCodDwzid4(Long codDwzid4) {
        this.codDwzid4 = codDwzid4;
    }

    public Long getCodDwzid5() {
        return codDwzid5;
    }

    public void setCodDwzid5(Long codDwzid5) {
        this.codDwzid5 = codDwzid5;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getPopEst() {
        return popEst;
    }

    public void setPopEst(Long popEst) {
        this.popEst = popEst;
    }

    public String getPopActive() {
        return popActive;
    }

    public void setPopActive(String popActive) {
        this.popActive = popActive;
    }

    public Long getPopYear() {
        return popYear;
    }

    public void setPopYear(Long popYear) {
        this.popYear = popYear;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getSearchMessage() {
        return searchMessage;
    }

    public void setSearchMessage(String searchMessage) {
        this.searchMessage = searchMessage;
    }

    public String getCodDwzid1Str() {
        return codDwzid1Str;
    }

    public void setCodDwzid1Str(String codDwzid1Str) {
        this.codDwzid1Str = codDwzid1Str;
    }

    public String getCodDwzid2Str() {
        return codDwzid2Str;
    }

    public void setCodDwzid2Str(String codDwzid2Str) {
        this.codDwzid2Str = codDwzid2Str;
    }

    public String getCodDwzid3Str() {
        return codDwzid3Str;
    }

    public void setCodDwzid3Str(String codDwzid3Str) {
        this.codDwzid3Str = codDwzid3Str;
    }

    public String getCodDwzid4Str() {
        return codDwzid4Str;
    }

    public void setCodDwzid4Str(String codDwzid4Str) {
        this.codDwzid4Str = codDwzid4Str;
    }

    public String getCodDwzid5Str() {
        return codDwzid5Str;
    }

    public void setCodDwzid5Str(String codDwzid5Str) {
        this.codDwzid5Str = codDwzid5Str;
    }

    public String getTotalPopulation() {
        return totalPopulation;
    }

    public void setTotalPopulation(String totalPopulation) {
        this.totalPopulation = totalPopulation;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((popYear == null) ? 0 : popYear.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PopulationMasterDTO other = (PopulationMasterDTO) obj;
        if (popYear == null) {
            if (other.popYear != null)
                return false;
        } else if (!popYear.equals(other.popYear))
            return false;
        return true;
    }

	public String getDelete() {
		return delete;
	}

	public void setDelete(String delete) {
		this.delete = delete;
	}

}
