/**
 *
 */
package com.abm.mainet.common.integration.acccount.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.Department;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author prasad.kancharla
 *
 */

@Entity
@Table(name = "TB_AC_BUDGETCODE_MAS")

public class AccountBudgetCodeEntity implements Serializable {

    private static final long serialVersionUID = 8683880608834172574L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "BUDGETCODE_ID", nullable = false)
    private Long prBudgetCodeid;

    @Column(name = "BUDGET_CODE", nullable = false)
    private String prBudgetCode;
    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------

    @Column(name = "CPD_ID_STATUS_FLG", length = 1)
    private String cpdIdStatusFlag;

    @Column(name = "ORGID", nullable = false)
    private Long orgid;

    @Column(name = "CREATED_BY", nullable = false)
    private Long userId;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date lmoddate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "FI04_V1")
    private String fi04V1;

    @Temporal(TemporalType.DATE)
    @Column(name = "FI04_D1")
    private Date fi04D1;

    @Column(name = "FI04_LO1", length = 1)
    private String fi04Lo1;

    // "faYearid" (column "FA_YEARID") is not defined by itself because used as FK in a link
    // "fundId" (column "FUND_ID") is not defined by itself because used as FK in a link
    // "functionId" (column "FUNCTION_ID") is not defined by itself because used as FK in a link
    // "fieldId" (column "FIELD_ID") is not defined by itself because used as FK in a link
    // "pacHeadId" (column "PAC_HEAD_ID") is not defined by itself because used as FK in a link
    // "sacHeadId" (column "SAC_HEAD_ID") is not defined by itself because used as FK in a link

    // ----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    // ----------------------------------------------------------------------
    @ManyToOne
    @JoinColumn(name = "FIELD_ID", referencedColumnName = "FIELD_ID")
    private AccountFieldMasterEntity tbAcFieldMaster;

    @ManyToOne
    @JoinColumn(name = "FUND_ID", referencedColumnName = "FUND_ID")
    private AccountFundMasterEntity tbAcFundMaster;

    @ManyToOne
    @JoinColumn(name = "SAC_HEAD_ID", referencedColumnName = "SAC_HEAD_ID")
    private AccountHeadSecondaryAccountCodeMasterEntity tbAcSecondaryheadMaster;

    @ManyToOne
    @JoinColumn(name = "FUNCTION_ID", referencedColumnName = "FUNCTION_ID")
    private AccountFunctionMasterEntity tbAcFunctionMaster;

    @Column(name = "CPD_ID_BUDGET_TYPE", nullable = false)
    private Long cpdBugtypeId;

    @Column(name = "CPD_ID_BUDGET_SUBTYPE", nullable = false)
    private Long cpdBugsubtypeId;

    @ManyToOne
    @JoinColumn(name = "PAC_HEAD_ID", referencedColumnName = "PAC_HEAD_ID")
    private AccountHeadPrimaryAccountCodeMasterEntity tbAcPrimaryheadMaster;

    // ----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    // ----------------------------------------------------------------------
    public AccountBudgetCodeEntity() {
        super();
    }

    // @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "DP_DEPTID", referencedColumnName = "DP_DEPTID")
    private Department tbDepartment;

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setPrProjectionid(final Long prBudgetCodeid) {
        this.prBudgetCodeid = prBudgetCodeid;
    }

    public Long getPrProjectionid() {
        return prBudgetCodeid;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------

    // --- DATABASE MAPPING : ORGID ( NUMBER )
    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getOrgid() {
        return orgid;
    }

    // --- DATABASE MAPPING : USER_ID ( NUMBER )
    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    // --- DATABASE MAPPING : LMODDATE ( DATE )
    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    // --- DATABASE MAPPING : UPDATED_BY ( NUMBER )
    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    // --- DATABASE MAPPING : UPDATED_DATE ( DATE )
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    // --- DATABASE MAPPING : LG_IP_MAC ( VARCHAR2 )
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    // --- DATABASE MAPPING : LG_IP_MAC_UPD ( VARCHAR2 )
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    // --- DATABASE MAPPING : FI04_V1 ( NVARCHAR2 )
    public void setFi04V1(final String fi04V1) {
        this.fi04V1 = fi04V1;
    }

    public String getFi04V1() {
        return fi04V1;
    }

    // --- DATABASE MAPPING : FI04_D1 ( DATE )
    public void setFi04D1(final Date fi04D1) {
        this.fi04D1 = fi04D1;
    }

    public Date getFi04D1() {
        return fi04D1;
    }

    // --- DATABASE MAPPING : FI04_LO1 ( CHAR )
    public void setFi04Lo1(final String fi04Lo1) {
        this.fi04Lo1 = fi04Lo1;
    }

    public String getFi04Lo1() {
        return fi04Lo1;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    // ----------------------------------------------------------------------

    /**
     * @return the prBudgetCodeid
     */
    public Long getprBudgetCodeid() {
        return prBudgetCodeid;
    }

    /**
     * @param prBudgetCodeid the prBudgetCodeid to set
     */
    public void setprBudgetCodeid(final Long prBudgetCodeid) {
        this.prBudgetCodeid = prBudgetCodeid;
    }

    /**
     * @return the tbAcFieldMaster
     */
    public AccountFieldMasterEntity getTbAcFieldMaster() {
        return tbAcFieldMaster;
    }

    /**
     * @param tbAcFieldMaster the tbAcFieldMaster to set
     */
    public void setTbAcFieldMaster(
            final AccountFieldMasterEntity tbAcFieldMaster) {
        this.tbAcFieldMaster = tbAcFieldMaster;
    }

    /**
     * @return the tbAcFundMaster
     */
    public AccountFundMasterEntity getTbAcFundMaster() {
        return tbAcFundMaster;
    }

    /**
     * @param tbAcFundMaster the tbAcFundMaster to set
     */
    public void setTbAcFundMaster(
            final AccountFundMasterEntity tbAcFundMaster) {
        this.tbAcFundMaster = tbAcFundMaster;
    }

    /**
     * @return the tbAcSecondaryheadMaster
     */
    public AccountHeadSecondaryAccountCodeMasterEntity getTbAcSecondaryheadMaster() {
        return tbAcSecondaryheadMaster;
    }

    /**
     * @param tbAcSecondaryheadMaster the tbAcSecondaryheadMaster to set
     */
    public void setTbAcSecondaryheadMaster(
            final AccountHeadSecondaryAccountCodeMasterEntity tbAcSecondaryheadMaster) {
        this.tbAcSecondaryheadMaster = tbAcSecondaryheadMaster;
    }

    /**
     * @return the tbAcFunctionMaster
     */
    public AccountFunctionMasterEntity getTbAcFunctionMaster() {
        return tbAcFunctionMaster;
    }

    /**
     * @param tbAcFunctionMaster the tbAcFunctionMaster to set
     */
    public void setTbAcFunctionMaster(
            final AccountFunctionMasterEntity tbAcFunctionMaster) {
        this.tbAcFunctionMaster = tbAcFunctionMaster;
    }

    /**
     * @return the prBudgetCode
     */
    public String getprBudgetCode() {
        return prBudgetCode;
    }

    /**
     * @param prBudgetCode the prBudgetCode to set
     */
    public void setprBudgetCode(final String prBudgetCode) {
        this.prBudgetCode = prBudgetCode;
    }

    /**
     * @return the cpdBugsubtypeId
     */
    public Long getcpdBugsubtypeId() {
        return cpdBugsubtypeId;
    }

    /**
     * @param cpdBugsubtypeId the cpdBugsubtypeId to set
     */
    public void setcpdBugsubtypeId(final Long cpdBugsubtypeId) {
        this.cpdBugsubtypeId = cpdBugsubtypeId;
    }

    /**
     * @return the prBudgetCodeid
     */
    public Long getPrBudgetCodeid() {
        return prBudgetCodeid;
    }

    /**
     * @param prBudgetCodeid the prBudgetCodeid to set
     */
    public void setPrBudgetCodeid(final Long prBudgetCodeid) {
        this.prBudgetCodeid = prBudgetCodeid;
    }

    /**
     * @return the prBudgetCode
     */
    public String getPrBudgetCode() {
        return prBudgetCode;
    }

    /**
     * @param prBudgetCode the prBudgetCode to set
     */
    public void setPrBudgetCode(final String prBudgetCode) {
        this.prBudgetCode = prBudgetCode;
    }

    /**
     * @return the cpdIdStatusFlag
     */
    public String getCpdIdStatusFlag() {
        return cpdIdStatusFlag;
    }

    /**
     * @param cpdIdStatusFlag the cpdIdStatusFlag to set
     */
    public void setCpdIdStatusFlag(final String cpdIdStatusFlag) {
        this.cpdIdStatusFlag = cpdIdStatusFlag;
    }

    /**
     * @return the cpdBugtypeId
     */
    public Long getCpdBugtypeId() {
        return cpdBugtypeId;
    }

    /**
     * @param cpdBugtypeId the cpdBugtypeId to set
     */
    public void setCpdBugtypeId(final Long cpdBugtypeId) {
        this.cpdBugtypeId = cpdBugtypeId;
    }

    /**
     * @return the cpdBugsubtypeId
     */
    public Long getCpdBugsubtypeId() {
        return cpdBugsubtypeId;
    }

    /**
     * @param cpdBugsubtypeId the cpdBugsubtypeId to set
     */
    public void setCpdBugsubtypeId(final Long cpdBugsubtypeId) {
        this.cpdBugsubtypeId = cpdBugsubtypeId;
    }

    /**
     * @return the tbDepartment
     */
    public Department getTbDepartment() {
        return tbDepartment;
    }

    /**
     * @param tbDepartment the tbDepartment to set
     */
    public void setTbDepartment(final Department tbDepartment) {
        this.tbDepartment = tbDepartment;
    }

    public AccountHeadPrimaryAccountCodeMasterEntity getTbAcPrimaryheadMaster() {
        return tbAcPrimaryheadMaster;
    }

    public void setTbAcPrimaryheadMaster(final AccountHeadPrimaryAccountCodeMasterEntity tbAcPrimaryheadMaster) {
        this.tbAcPrimaryheadMaster = tbAcPrimaryheadMaster;
    }

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------

    public static String[] getPkValues() {
        return new String[] { "AC", "TB_AC_BUDGETCODE_MAS", "BUDGETCODE_ID" };
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("AccountBudgetCodeEntity [prBudgetCodeid=");
        builder.append(prBudgetCodeid);
        builder.append(", prBudgetCode=");
        builder.append(prBudgetCode);
        builder.append(", cpdIdStatusFlag=");
        builder.append(cpdIdStatusFlag);
        builder.append(", orgid=");
        builder.append(orgid);
        builder.append(", userId=");
        builder.append(userId);
        builder.append(", lmoddate=");
        builder.append(lmoddate);
        builder.append(", updatedBy=");
        builder.append(updatedBy);
        builder.append(", updatedDate=");
        builder.append(updatedDate);
        builder.append(", lgIpMac=");
        builder.append(lgIpMac);
        builder.append(", lgIpMacUpd=");
        builder.append(lgIpMacUpd);
        builder.append(", fi04V1=");
        builder.append(fi04V1);
        builder.append(", fi04D1=");
        builder.append(fi04D1);
        builder.append(", fi04Lo1=");
        builder.append(fi04Lo1);
        builder.append(", tbAcFieldMaster=");
        builder.append(tbAcFieldMaster);
        builder.append(", tbAcFundMaster=");
        builder.append(tbAcFundMaster);
        builder.append(", tbAcSecondaryheadMaster=");
        builder.append(tbAcSecondaryheadMaster);
        builder.append(", tbAcFunctionMaster=");
        builder.append(tbAcFunctionMaster);
        builder.append(", cpdBugtypeId=");
        builder.append(cpdBugtypeId);
        builder.append(", cpdBugsubtypeId=");
        builder.append(cpdBugsubtypeId);
        builder.append(", tbAcPrimaryheadMaster=");
        builder.append(tbAcPrimaryheadMaster);
        builder.append(", tbDepartment=");
        builder.append(tbDepartment);
        builder.append("]");
        return builder.toString();
    }

}