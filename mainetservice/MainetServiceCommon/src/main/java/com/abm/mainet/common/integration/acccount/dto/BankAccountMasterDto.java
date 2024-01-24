/**
 *
 */
package com.abm.mainet.common.integration.acccount.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author hiren.poriya
 *
 */
public class BankAccountMasterDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long baAccountId;
    private Long ulbBankId;
    private String baAccountNo;
    private Long cpdAccountType;
    private String baAccountName;
    // private String baOpenbalDate;
    // private Double baOpenBalAmt;
    private Long fundId;
    private Long fieldId;
    private Long pacHeadId;
    private String appChallanFlag;
    private Long acCpdIdStatus;
    private Long createdBy;
    private Date createdDate;
    private Long updatedBy;
    private Date updatedDate;
    private Long langId;
    private Long orgId;
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
    private Long functionId;
    private Long bankId;
    private String bankName;

    private BigDecimal baOpenBalAmt;
    // private String secHeadCode;

    private String sacHeadDesc;
    private Long bankBranchId;

    private String bankTypeDesc;
    private String accOldHeadCode;
    public Long getBaAccountId() {
        return baAccountId;
    }

    public void setBaAccountId(final Long baAccountId) {
        this.baAccountId = baAccountId;
    }

    public Long getUlbBankId() {
        return ulbBankId;
    }

    public void setUlbBankId(final Long ulbBankId) {
        this.ulbBankId = ulbBankId;
    }

    public String getBaAccountNo() {
        return baAccountNo;
    }

    public void setBaAccountNo(final String baAccountNo) {
        this.baAccountNo = baAccountNo;
    }

    public Long getCpdAccountType() {
        return cpdAccountType;
    }

    public void setCpdAccountType(final Long cpdAccountType) {
        this.cpdAccountType = cpdAccountType;
    }

    public String getBaAccountName() {
        return baAccountName;
    }

    public void setBaAccountName(final String baAccountName) {
        this.baAccountName = baAccountName;
    }

    /*
     * public Double getBaOpenBalAmt() { return baOpenBalAmt; } public void setBaOpenBalAmt(Double baOpenBalAmt) {
     * this.baOpenBalAmt = baOpenBalAmt; }
     */
    public Long getFundId() {
        return fundId;
    }

    public void setFundId(final Long fundId) {
        this.fundId = fundId;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(final Long fieldId) {
        this.fieldId = fieldId;
    }

    public Long getPacHeadId() {
        return pacHeadId;
    }

    public void setPacHeadId(final Long pacHeadId) {
        this.pacHeadId = pacHeadId;
    }

    public String getAppChallanFlag() {
        return appChallanFlag;
    }

    public void setAppChallanFlag(final String appChallanFlag) {
        this.appChallanFlag = appChallanFlag;
    }

    public Long getAcCpdIdStatus() {
        return acCpdIdStatus;
    }

    public void setAcCpdIdStatus(final Long acCpdIdStatus) {
        this.acCpdIdStatus = acCpdIdStatus;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long getFi04N1() {
        return fi04N1;
    }

    public void setFi04N1(final Long fi04n1) {
        fi04N1 = fi04n1;
    }

    public String getFi04V1() {
        return fi04V1;
    }

    public void setFi04V1(final String fi04v1) {
        fi04V1 = fi04v1;
    }

    public Date getFi04D1() {
        return fi04D1;
    }

    public void setFi04D1(final Date fi04d1) {
        fi04D1 = fi04d1;
    }

    public String getFi04Lo1() {
        return fi04Lo1;
    }

    public void setFi04Lo1(final String fi04Lo1) {
        this.fi04Lo1 = fi04Lo1;
    }

    public String getSacHeadDesc() {
        return sacHeadDesc;
    }

    public void setSacHeadDesc(final String sacHeadDesc) {
        this.sacHeadDesc = sacHeadDesc;
    }

    /**
     * @return the bankBranchId
     */
    public Long getBankBranchId() {
        return bankBranchId;
    }

    /**
     * @param bankBranchId the bankBranchId to set
     */
    public void setBankBranchId(final Long bankBranchId) {
        this.bankBranchId = bankBranchId;
    }

    /*
     * public String getBaOpenbalDate() { return baOpenbalDate; } public void setBaOpenbalDate(final String baOpenbalDate) {
     * this.baOpenbalDate = baOpenbalDate; }
     */

    public String getBankTypeDesc() {
        return bankTypeDesc;
    }

    public void setBankTypeDesc(final String bankTypeDesc) {
        this.bankTypeDesc = bankTypeDesc;
    }

    public BigDecimal getBaOpenBalAmt() {
        return baOpenBalAmt;
    }

    public void setBaOpenBalAmt(final BigDecimal baOpenBalAmt) {
        this.baOpenBalAmt = baOpenBalAmt;
    }

    public Long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(final Long functionId) {
        this.functionId = functionId;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccOldHeadCode() {
		return accOldHeadCode;
	}

	public void setAccOldHeadCode(String accOldHeadCode) {
		this.accOldHeadCode = accOldHeadCode;
	}
    
}
