package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author hiren.poriya
 * @since 19 Jan 2017
 */
@Entity
@Table(name = "TB_BANK_ACCOUNT")
public class BankAccountMasterEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "BA_ACCOUNTID", precision = 12, scale = 0, nullable = false)
    private Long baAccountId;

    /*
     * @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
     * @JoinColumn(name = "ULB_BANKID", nullable = false, referencedColumnName = "ULB_BANKID") private UlbBankMasterEntity
     * ulbBankId;
     */

    @Column(name = "BA_ACCOUNT_NO", length = 25, nullable = false)
    private String baAccountNo;

    @Column(name = "CPD_ACCOUNTTYPE", precision = 12, scale = 0, nullable = false)
    private Long cpdAccountType;

    @Column(name = "BA_ACCOUNTNAME", length = 150, nullable = false)
    private String baAccountName;

    /*
     * @Column(name = "BA_OPENBAL_DATE", nullable = true) private Date baOpenbalDate;
     * @Column(name = "BA_OPEN_BAL_AMT", precision = 15, scale = 2, nullable = true) private BigDecimal baOpenBalAmt;
     */

    @Column(name = "FUND_ID", precision = 12, scale = 0, nullable = true)
    private Long fundId;

    @Column(name = "FIELD_ID", precision = 12, scale = 0, nullable = true)
    private Long fieldId;

    @Column(name = "PAC_HEAD_ID", precision = 12, scale = 0, nullable = true)
    private Long pacHeadId;

    @Column(name = "APP_CHALLAN_FLAG", length = 1, nullable = true)
    private String appChallanFlag;

    @Column(name = "AC_CPD_ID_STATUS", precision = 12, scale = 0, nullable = true)
    private Long acCpdIdStatus;

    @Column(name = "CREATED_BY", precision = 7, scale = 0, nullable = false)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = true)
    private Date createdDate;

    @Column(name = "UPDATED_BY", nullable = true)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    /*
     * @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = false) private Long langId;
     */

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "FUNCTION_ID", precision = 15, scale = 0, nullable = true)
    private Long functionId;

    @Column(name = "ORGID", nullable = false)
    private Long orgId;

    @Column(name = "BANK_TYPE", precision = 12, scale = 0, nullable = true)
    private Long bankType;

    @ManyToOne
    @JoinColumn(name = "BANKID", referencedColumnName = "BANKID")
    private BankMasterEntity bankId;

    @Column(name = "FI04_V1", length = 200, nullable = true)
    private String fi04V1;

    // @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // @JoinColumn(name = "BANKID", nullable = false, referencedColumnName = "BANKID")
    // private BankMasterEntity bankId;

    public Long getBaAccountId() {
        return baAccountId;
    }

    public void setBaAccountId(final Long baAccountId) {
        this.baAccountId = baAccountId;
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

    /**
     * @return the updatedBy
     */
    public Long getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
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

    public Long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getBankType() {
        return bankType;
    }

    public void setBankType(Long bankType) {
        this.bankType = bankType;
    }

    public String getFi04V1() {
        return fi04V1;
    }

    public void setFi04V1(String fi04v1) {
        fi04V1 = fi04v1;
    }

    public BankMasterEntity getBankId() {
        return bankId;
    }

    public void setBankId(BankMasterEntity bankId) {
        this.bankId = bankId;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("BankAccountMasterEntity [baAccountId=");
        builder.append(baAccountId);
        builder.append(", bankId=");
        builder.append(bankId);
        builder.append(", baAccountNo=");
        builder.append(baAccountNo);
        builder.append(", cpdAccountType=");
        builder.append(cpdAccountType);
        builder.append(", baAccountName=");
        builder.append(baAccountName);
        builder.append(", fundId=");
        builder.append(fundId);
        builder.append(", fieldId=");
        builder.append(fieldId);
        builder.append(", pacHeadId=");
        builder.append(pacHeadId);
        builder.append(", appChallanFlag=");
        builder.append(appChallanFlag);
        builder.append(", acCpdIdStatus=");
        builder.append(acCpdIdStatus);
        builder.append(", createdBy=");
        builder.append(createdBy);
        builder.append(", createdDate=");
        builder.append(createdDate);
        builder.append(", updatedBy=");
        builder.append(updatedBy);
        builder.append(", updatedDate=");
        builder.append(updatedDate);
        builder.append(", lgIpMac=");
        builder.append(lgIpMac);
        builder.append(", lgIpMacUpd=");
        builder.append(lgIpMacUpd);
        builder.append(", functionId=");
        builder.append(functionId);
        builder.append(", orgId=");
        builder.append(orgId);
        builder.append(", fi04V1=");
        builder.append(fi04V1);
        builder.append(", bankType=");
        builder.append(bankType);
        builder.append("]");
        return builder.toString();
    }

    public String[] getPkValues() {
        return new String[] { "AC", "TB_BANK_ACCOUNT", "BA_ACCOUNTID" };
    }

}