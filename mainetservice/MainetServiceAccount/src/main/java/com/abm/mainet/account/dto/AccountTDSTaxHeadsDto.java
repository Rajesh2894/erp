
package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author tejas.kotekar
 *
 */
public class AccountTDSTaxHeadsDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long tdsId;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------

    private Long orgid;

    private String tdsEntrydate;

    private Long fundId;

    private Long functionId;

    private Long fieldId;

    private Long pacHeadId;

    private Long sacHeadId;

    private Long cpdIdDeductionType;

    private String tdsDescription;

    private String tdsStatusFlg;

    private Long userId;

    private Long langId;

    private Date lmoddate;

    private Long updatedBy;

    private Date updatedDate;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMac;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMacUpd;

    private Long fi04N1;

    private String fi04V1;

    private Date fi04D1;

    private String fi04Lo1;

    private Long status;

    private String budgetCode;

    private String pacSacHeadDesc;

    private Long budgetCodeId;

    private String tdsTypeDesc;

    private String statusDesc;

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setTdsId(final Long tdsId) {
        this.tdsId = tdsId;
    }

    public Long getTdsId() {
        return tdsId;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setTdsEntrydate(final String tdsEntrydate) {
        this.tdsEntrydate = tdsEntrydate;
    }

    public String getTdsEntrydate() {
        return tdsEntrydate;
    }

    /**
     * @return the fundId
     */
    public Long getFundId() {
        return fundId;
    }

    /**
     * @param fundId the fundId to set
     */
    public void setFundId(final Long fundId) {
        this.fundId = fundId;
    }

    public void setFunctionId(final Long functionId) {
        this.functionId = functionId;
    }

    public Long getFunctionId() {
        return functionId;
    }

    public void setFieldId(final Long fieldId) {
        this.fieldId = fieldId;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setPacHeadId(final Long pacHeadId) {
        this.pacHeadId = pacHeadId;
    }

    public Long getPacHeadId() {
        return pacHeadId;
    }

    public void setSacHeadId(final Long sacHeadId) {
        this.sacHeadId = sacHeadId;
    }

    public Long getSacHeadId() {
        return sacHeadId;
    }

    public void setCpdIdDeductionType(
            final Long cpdIdDeductionType) {
        this.cpdIdDeductionType = cpdIdDeductionType;
    }

    public Long getCpdIdDeductionType() {
        return cpdIdDeductionType;
    }

    public void setTdsDescription(final String tdsDescription) {
        this.tdsDescription = tdsDescription;
    }

    public String getTdsDescription() {
        return tdsDescription;
    }

    public void setTdsStatusFlg(final String tdsStatusFlg) {
        this.tdsStatusFlg = tdsStatusFlg;
    }

    public String getTdsStatusFlg() {
        return tdsStatusFlg;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setFi04N1(final Long fi04N1) {
        this.fi04N1 = fi04N1;
    }

    public Long getFi04N1() {
        return fi04N1;
    }

    public void setFi04V1(final String fi04V1) {
        this.fi04V1 = fi04V1;
    }

    public String getFi04V1() {
        return fi04V1;
    }

    public void setFi04D1(final Date fi04D1) {
        this.fi04D1 = fi04D1;
    }

    public Date getFi04D1() {
        return fi04D1;
    }

    public void setFi04Lo1(final String fi04Lo1) {
        this.fi04Lo1 = fi04Lo1;
    }

    public String getFi04Lo1() {
        return fi04Lo1;
    }

    /**
     * @return the status
     */
    public Long getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(final Long status) {
        this.status = status;
    }

    /**
     * @return the budgetCode
     */
    public String getBudgetCode() {
        return budgetCode;
    }

    /**
     * @param budgetCode the budgetCode to set
     */
    public void setBudgetCode(final String budgetCode) {
        this.budgetCode = budgetCode;
    }

    /**
     * @return the pacSacHeadDesc
     */
    public String getPacSacHeadDesc() {
        return pacSacHeadDesc;
    }

    /**
     * @param pacSacHeadDesc the pacSacHeadDesc to set
     */
    public void setPacSacHeadDesc(final String pacSacHeadDesc) {
        this.pacSacHeadDesc = pacSacHeadDesc;
    }

    /**
     * @return the budgetCodeId
     */
    public Long getBudgetCodeId() {
        return budgetCodeId;
    }

    /**
     * @param budgetCodeId the budgetCodeId to set
     */
    public void setBudgetCodeId(final Long budgetCodeId) {
        this.budgetCodeId = budgetCodeId;
    }

    public String getTdsTypeDesc() {
        return tdsTypeDesc;
    }

    public void setTdsTypeDesc(final String tdsTypeDesc) {
        this.tdsTypeDesc = tdsTypeDesc;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(final String statusDesc) {
        this.statusDesc = statusDesc;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AccountTDSTaxHeadsDto [tdsId=" + tdsId + ", orgid=" + orgid
                + ", tdsEntrydate=" + tdsEntrydate + ", fundId=" + fundId
                + ", functionId=" + functionId + ", fieldId=" + fieldId
                + ", pacHeadId=" + pacHeadId + ", sacHeadId=" + sacHeadId
                + ", cpdIdDeductionType=" + cpdIdDeductionType
                + ", tdsDescription=" + tdsDescription + ", tdsStatusFlg="
                + tdsStatusFlg + ", userId=" + userId + ", langId=" + langId
                + ", lmoddate=" + lmoddate + ", updatedBy=" + updatedBy
                + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac
                + ", lgIpMacUpd=" + lgIpMacUpd + ", fi04N1=" + fi04N1
                + ", fi04V1=" + fi04V1 + ", fi04D1=" + fi04D1 + ", fi04Lo1="
                + fi04Lo1 + ", status=" + status + ", budgetCode=" + budgetCode
                + ", pacSacHeadDesc=" + pacSacHeadDesc + ", budgetCodeId="
                + budgetCodeId + "]";
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime
                * result)
                + ((cpdIdDeductionType == null) ? 0
                        : cpdIdDeductionType.hashCode());
        result = (prime
                * result)
                + ((fieldId == null) ? 0
                        : fieldId
                                .hashCode());
        result = (prime
                * result)
                + ((functionId == null) ? 0
                        : functionId
                                .hashCode());
        result = (prime
                * result)
                + ((fundId == null) ? 0 : fundId.hashCode());
        result = (prime
                * result)
                + ((pacHeadId == null) ? 0
                        : pacHeadId
                                .hashCode());
        result = (prime
                * result)
                + ((sacHeadId == null) ? 0
                        : sacHeadId
                                .hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AccountTDSTaxHeadsDto other = (AccountTDSTaxHeadsDto) obj;
        if (cpdIdDeductionType == null) {
            if (other.cpdIdDeductionType != null) {
                return false;
            }
        } else if (!cpdIdDeductionType
                .equals(other.cpdIdDeductionType)) {
            return false;
        }
        if (fieldId == null) {
            if (other.fieldId != null) {
                return false;
            }
        } else if (!fieldId.equals(other.fieldId)) {
            return false;
        }
        if (functionId == null) {
            if (other.functionId != null) {
                return false;
            }
        } else if (!functionId.equals(other.functionId)) {
            return false;
        }
        if (fundId == null) {
            if (other.fundId != null) {
                return false;
            }
        } else if (!fundId.equals(other.fundId)) {
            return false;
        }
        if (pacHeadId == null) {
            if (other.pacHeadId != null) {
                return false;
            }
        } else if (!pacHeadId.equals(other.pacHeadId)) {
            return false;
        }
        if (sacHeadId == null) {
            if (other.sacHeadId != null) {
                return false;
            }
        } else if (!sacHeadId.equals(other.sacHeadId)) {
            return false;
        }
        return true;
    }

}
