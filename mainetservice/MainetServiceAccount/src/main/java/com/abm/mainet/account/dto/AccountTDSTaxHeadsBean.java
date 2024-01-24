package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author tejas.kotekar
 *
 */
public class AccountTDSTaxHeadsBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------

    private Long tdsId;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------
    private Long orgid;

    private Date tdsEntrydate;

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

    private String fundDesc;

    private String functionDesc;

    private String fieldDesc;

    private String pacHeadDesc;

    private String sacHeadDesc;

    private String hasError;

    private String successfulFlag;

    private Long status;

    private String statusDescription;

    private Long budgetCodeId;

    private String budgetHeadDesc;

    private List<AccountTDSTaxHeadsDto> taxHeadsDtoList = new ArrayList<>();

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

    public void setTdsEntrydate(final Date tdsEntrydate) {
        this.tdsEntrydate = tdsEntrydate;
    }

    public Date getTdsEntrydate() {
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
     * @return the taxHeadsDtoList
     */
    public List<AccountTDSTaxHeadsDto> getTaxHeadsDtoList() {
        return taxHeadsDtoList;
    }

    /**
     * @param taxHeadsDtoList the taxHeadsDtoList to set
     */
    public void setTaxHeadsDtoList(
            final List<AccountTDSTaxHeadsDto> taxHeadsDtoList) {
        this.taxHeadsDtoList = taxHeadsDtoList;
    }

    /**
     * @return the fundDesc
     */
    public String getFundDesc() {
        return fundDesc;
    }

    /**
     * @param fundDesc the fundDesc to set
     */
    public void setFundDesc(final String fundDesc) {
        this.fundDesc = fundDesc;
    }

    /**
     * @return the functionDesc
     */
    public String getFunctionDesc() {
        return functionDesc;
    }

    /**
     * @param functionDesc the functionDesc to set
     */
    public void setFunctionDesc(final String functionDesc) {
        this.functionDesc = functionDesc;
    }

    /**
     * @return the fieldDesc
     */
    public String getFieldDesc() {
        return fieldDesc;
    }

    /**
     * @param fieldDesc the fieldDesc to set
     */
    public void setFieldDesc(final String fieldDesc) {
        this.fieldDesc = fieldDesc;
    }

    /**
     * @return the pacHeadDesc
     */
    public String getPacHeadDesc() {
        return pacHeadDesc;
    }

    /**
     * @param pacHeadDesc the pacHeadDesc to set
     */
    public void setPacHeadDesc(final String pacHeadDesc) {
        this.pacHeadDesc = pacHeadDesc;
    }

    /**
     * @return the sacHeadDesc
     */
    public String getSacHeadDesc() {
        return sacHeadDesc;
    }

    /**
     * @param sacHeadDesc the sacHeadDesc to set
     */
    public void setSacHeadDesc(final String sacHeadDesc) {
        this.sacHeadDesc = sacHeadDesc;
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
     * @return the statusDescription
     */
    public String getStatusDescription() {
        return statusDescription;
    }

    /**
     * @param statusDescription the statusDescription to set
     */
    public void setStatusDescription(final String statusDescription) {
        this.statusDescription = statusDescription;
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
        final AccountTDSTaxHeadsBean other = (AccountTDSTaxHeadsBean) obj;
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

    /**
     * @return the hasError
     */
    public String getHasError() {
        return hasError;
    }

    /**
     * @param hasError the hasError to set
     */
    public void setHasError(final String hasError) {
        this.hasError = hasError;
    }

    /**
     * @return the successfulFlag
     */
    public String getSuccessfulFlag() {
        return successfulFlag;
    }

    /**
     * @param successfulFlag the successfulFlag to set
     */
    public void setSuccessfulFlag(final String successfulFlag) {
        this.successfulFlag = successfulFlag;
    }

    public String getBudgetHeadDesc() {
        return budgetHeadDesc;
    }

    public void setBudgetHeadDesc(final String budgetHeadDesc) {
        this.budgetHeadDesc = budgetHeadDesc;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AccountTDSTaxHeadsBean [tdsId=" + tdsId + ", orgid=" + orgid
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
                + fi04Lo1 + ", fundDesc=" + fundDesc + ", functionDesc="
                + functionDesc + ", fieldDesc=" + fieldDesc + ", pacHeadDesc="
                + pacHeadDesc + ", sacHeadDesc=" + sacHeadDesc + ", hasError="
                + hasError + ", successfulFlag=" + successfulFlag + ", status="
                + status + ", taxHeadsDtoList=" + taxHeadsDtoList + "]";
    }

}
