
package com.abm.mainet.account.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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

import com.abm.mainet.common.integration.acccount.domain.AccountBudgetCodeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Persistent class for entity stored in table "TB_AC_BUDGETALLOCATION"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name = "TB_AC_BUDGETALLOCATION")

public class AccountBudgetAllocationEntity implements Serializable {

    private static final long serialVersionUID = 8683880608834172574L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "BA_ID", nullable = false)
    private Long baId;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------
    @Temporal(TemporalType.DATE)
    @Column(name = "BA_ENTRYDATE", nullable = false)
    private Date baEntrydate;

    @Temporal(TemporalType.DATE)
    @Column(name = "VALIDTILL_DATE")
    private Date validtillDate;

    @Column(name = "RELEASE_PER")
    private BigDecimal releasePer;

    @Column(name = "AUTHO_FLG", length = 1)
    private String authoFlg;

    @Column(name = "AUTHO_ID")
    private BigDecimal authoId;

    @Temporal(TemporalType.DATE)
    @Column(name = "AUTHO_DATE")
    private Date authoDate;

    @Column(name = "ORGID", nullable = false)
    private Long orgid;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LANG_ID", nullable = false)
    private int langId;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "FI04_N1")
    private Long fi04N1;

    @Column(name = "FI04_V1")
    private String fi04V1;

    @Temporal(TemporalType.DATE)
    @Column(name = "FI04_D1")
    private Date fi04D1;

    @Column(name = "FI04_LO1", length = 1)
    private String fi04Lo1;

    // ----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    // ----------------------------------------------------------------------
    @Column(name = "CPD_BUGTYPE_ID")
    private Long cpdBugtypeId;

    @ManyToOne
    @JoinColumn(name = "PR_EXPENDITUREID", referencedColumnName = "PR_EXPENDITUREID")
    private AccountBudgetProjectedExpenditureEntity tbAcProjectedExpenditure;

    @ManyToOne
    @JoinColumn(name = "PR_PROJECTIONID", referencedColumnName = "PR_PROJECTIONID")
    private AccountBudgetProjectedRevenueEntryEntity tbAcProjectedrevenue;

    @Column(name = "DP_DEPTID")
    private Long department;

    @Column(name = "FA_YEARID")
    private Long financialYear;

    @ManyToOne
    @JoinColumn(name = "BUDGETCODE_ID", referencedColumnName = "BUDGETCODE_ID")
    private AccountBudgetCodeEntity tbAcBudgetCodeMaster;

    @Column(name = "CPD_BUGSUBTYPE_ID", nullable = false)
    private Long cpdBugsubtypeId;

    // ----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    // ----------------------------------------------------------------------
    public AccountBudgetAllocationEntity() {
        super();
    }

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setBaId(final Long baId) {
        this.baId = baId;
    }

    public Long getBaId() {
        return baId;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    // --- DATABASE MAPPING : BA_ENTRYDATE ( DATE )
    public void setBaEntrydate(final Date baEntrydate) {
        this.baEntrydate = baEntrydate;
    }

    public Date getBaEntrydate() {
        return baEntrydate;
    }

    // --- DATABASE MAPPING : VALIDTILL_DATE ( DATE )
    public void setValidtillDate(final Date validtillDate) {
        this.validtillDate = validtillDate;
    }

    public Date getValidtillDate() {
        return validtillDate;
    }

    // --- DATABASE MAPPING : RELEASE_PER ( NUMBER )
    public void setReleasePer(final BigDecimal releasePer) {
        this.releasePer = releasePer;
    }

    public BigDecimal getReleasePer() {
        return releasePer;
    }

    // --- DATABASE MAPPING : AUTHO_FLG ( CHAR )
    public void setAuthoFlg(final String authoFlg) {
        this.authoFlg = authoFlg;
    }

    public String getAuthoFlg() {
        return authoFlg;
    }

    // --- DATABASE MAPPING : AUTHO_ID ( NUMBER )
    public void setAuthoId(final BigDecimal authoId) {
        this.authoId = authoId;
    }

    public BigDecimal getAuthoId() {
        return authoId;
    }

    // --- DATABASE MAPPING : AUTHO_DATE ( DATE )
    public void setAuthoDate(final Date authoDate) {
        this.authoDate = authoDate;
    }

    public Date getAuthoDate() {
        return authoDate;
    }

    // --- DATABASE MAPPING : ORGID ( NUMBER )
    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getOrgid() {
        return orgid;
    }

    // --- DATABASE MAPPING : CREATED_BY ( NUMBER )
    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    // --- DATABASE MAPPING : CREATED_DATE ( DATE )
    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getCreatedDate() {
        return createdDate;
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

    // --- DATABASE MAPPING : LANG_ID ( NUMBER )
    public void setLangId(final int langId) {
        this.langId = langId;
    }

    public int getLangId() {
        return langId;
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

    // --- DATABASE MAPPING : FI04_N1 ( NUMBER )
    public void setFi04N1(final Long fi04N1) {
        this.fi04N1 = fi04N1;
    }

    public Long getFi04N1() {
        return fi04N1;
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

    /**
     * @return the tbAcProjectedExpenditure
     */
    public AccountBudgetProjectedExpenditureEntity getTbAcProjectedExpenditure() {
        return tbAcProjectedExpenditure;
    }

    /**
     * @param tbAcProjectedExpenditure the tbAcProjectedExpenditure to set
     */
    public void setTbAcProjectedExpenditure(
            final AccountBudgetProjectedExpenditureEntity tbAcProjectedExpenditure) {
        this.tbAcProjectedExpenditure = tbAcProjectedExpenditure;
    }

    /**
     * @return the tbAcProjectedrevenue
     */
    public AccountBudgetProjectedRevenueEntryEntity getTbAcProjectedrevenue() {
        return tbAcProjectedrevenue;
    }

    /**
     * @param tbAcProjectedrevenue the tbAcProjectedrevenue to set
     */
    public void setTbAcProjectedrevenue(
            final AccountBudgetProjectedRevenueEntryEntity tbAcProjectedrevenue) {
        this.tbAcProjectedrevenue = tbAcProjectedrevenue;
    }

    /**
     * @return the department
     */
    public Long getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(final Long department) {
        this.department = department;
    }

    /**
     * @return the financialYear
     */
    public Long getFinancialYear() {
        return financialYear;
    }

    /**
     * @param financialYear the financialYear to set
     */
    public void setFinancialYear(final Long financialYear) {
        this.financialYear = financialYear;
    }

    /**
     * @return the tbAcBudgetCodeMaster
     */
    public AccountBudgetCodeEntity getTbAcBudgetCodeMaster() {
        return tbAcBudgetCodeMaster;
    }

    /**
     * @param tbAcBudgetCodeMaster the tbAcBudgetCodeMaster to set
     */
    public void setTbAcBudgetCodeMaster(
            final AccountBudgetCodeEntity tbAcBudgetCodeMaster) {
        this.tbAcBudgetCodeMaster = tbAcBudgetCodeMaster;
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

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(baId);
        sb.append("]:");
        sb.append(baEntrydate);
        sb.append("|");
        sb.append(validtillDate);
        sb.append("|");
        sb.append(releasePer);
        sb.append("|");
        sb.append(authoFlg);
        sb.append("|");
        sb.append(authoId);
        sb.append("|");
        sb.append(authoDate);
        sb.append("|");
        sb.append(orgid);
        sb.append("|");
        sb.append(createdBy);
        sb.append("|");
        sb.append(createdDate);
        sb.append("|");
        sb.append(updatedBy);
        sb.append("|");
        sb.append(updatedDate);
        sb.append("|");
        sb.append(langId);
        sb.append("|");
        sb.append(lgIpMac);
        sb.append("|");
        sb.append(lgIpMacUpd);
        sb.append("|");
        sb.append(fi04N1);
        sb.append("|");
        sb.append(fi04V1);
        sb.append("|");
        sb.append(fi04D1);
        sb.append("|");
        sb.append(fi04Lo1);
        return sb.toString();
    }

    public static String[] getPkValues() {
        return new String[] { "AC", "TB_AC_BUDGETALLOCATION", "BA_ID" };
    }

}
