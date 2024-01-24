
package com.abm.mainet.account.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_AC_BUDGETALLOCATION_HIST")

public class AccountBudgetAllocationHistoryEntity implements Serializable {

    private static final long serialVersionUID = 8683880608834172574L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "H_BA_ID", nullable = false)
    private Long hBaId;

    @Column(name = "BA_ID", nullable = false)
    private Long baId;

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

    @Column(name = "CPD_BUGTYPE_ID")
    private Long cpdBugtypeId;

    @Column(name = "PR_EXPENDITUREID")
    private Long prExpenditureid;

    @Column(name = "PR_PROJECTIONID")
    private Long prProjectionid;

    @Column(name = "DP_DEPTID")
    private Long department;

    @Column(name = "FA_YEARID")
    private Long financialYear;

    @Column(name = "BUDGETCODE_ID")
    private Long prBudgetCodeid;

    @Column(name = "CPD_BUGSUBTYPE_ID", nullable = false)
    private Long cpdBugsubtypeId;

    public AccountBudgetAllocationHistoryEntity() {
        super();
    }

    public void setBaEntrydate(final Date baEntrydate) {
        this.baEntrydate = baEntrydate;
    }

    public Date getBaEntrydate() {
        return baEntrydate;
    }

    public void setValidtillDate(final Date validtillDate) {
        this.validtillDate = validtillDate;
    }

    public Date getValidtillDate() {
        return validtillDate;
    }

    public void setReleasePer(final BigDecimal releasePer) {
        this.releasePer = releasePer;
    }

    public BigDecimal getReleasePer() {
        return releasePer;
    }

    public void setAuthoFlg(final String authoFlg) {
        this.authoFlg = authoFlg;
    }

    public String getAuthoFlg() {
        return authoFlg;
    }

    public void setAuthoId(final BigDecimal authoId) {
        this.authoId = authoId;
    }

    public BigDecimal getAuthoId() {
        return authoId;
    }

    public void setAuthoDate(final Date authoDate) {
        this.authoDate = authoDate;
    }

    public Date getAuthoDate() {
        return authoDate;
    }

    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getCreatedDate() {
        return createdDate;
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

    public void setLangId(final int langId) {
        this.langId = langId;
    }

    public int getLangId() {
        return langId;
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
     * @return the hBaId
     */
    public Long gethBaId() {
        return hBaId;
    }

    /**
     * @param hBaId the hBaId to set
     */
    public void sethBaId(final Long hBaId) {
        this.hBaId = hBaId;
    }

    /**
     * @return the prExpenditureid
     */
    public Long getPrExpenditureid() {
        return prExpenditureid;
    }

    /**
     * @param prExpenditureid the prExpenditureid to set
     */
    public void setPrExpenditureid(final Long prExpenditureid) {
        this.prExpenditureid = prExpenditureid;
    }

    /**
     * @return the prProjectionid
     */
    public Long getPrProjectionid() {
        return prProjectionid;
    }

    /**
     * @param prProjectionid the prProjectionid to set
     */
    public void setPrProjectionid(final Long prProjectionid) {
        this.prProjectionid = prProjectionid;
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
     * @return the baId
     */
    public Long getBaId() {
        return baId;
    }

    /**
     * @param baId the baId to set
     */
    public void setBaId(final Long baId) {
        this.baId = baId;
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
        sb.append(hBaId);
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
        return new String[] { "AC", "TB_AC_BUDGETALLOCATION_HIST", "H_BA_ID" };
    }

}
