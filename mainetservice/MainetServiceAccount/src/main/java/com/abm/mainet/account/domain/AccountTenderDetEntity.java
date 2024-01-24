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
 * Persistent class for entity stored in table "TB_AC_TENDER_DET"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name = "TB_AC_TENDER_DET")
public class AccountTenderDetEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "TR_TENDERID_DET", nullable = false)
    private Long trTenderidDet;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------
    @Column(name = "BUDGETARY_PROV")
    private BigDecimal budgetaryProv;

    @Column(name = "BALANCE_PROV")
    private BigDecimal balanceProv;

    @Column(name = "ORGID", nullable = false)
    private Long orgid;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LANG_ID", nullable = false)
    private Long langId;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "TR_TENDERDET_AMT")
    private BigDecimal tenderDetailAmt;

    @Column(name = "SAC_HEAD_ID")
    private Long sacHeadId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FI04_D1")
    private Date fi04D1;

    @Column(name = "FI04_LO1", length = 1)
    private String fi04Lo1;

    // ----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    // ----------------------------------------------------------------------

    @ManyToOne
    @JoinColumn(name = "TR_TENDER_ID", referencedColumnName = "TR_TENDER_ID")
    private AccountTenderEntryEntity tbAcTenderMaster;

    @ManyToOne
    @JoinColumn(name = "BUDGETCODE_ID", referencedColumnName = "BUDGETCODE_ID")
    private AccountBudgetCodeEntity tbAcBudgetCodeMaster;

    public static String[] getPkValues() {
        return new String[] { "AC", "TB_AC_TENDER_DET", "TR_TENDERID_DET" };
    }

    // ----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    // ----------------------------------------------------------------------
    public AccountTenderDetEntity() {
        super();
    }

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setTrTenderidDet(final Long trTenderidDet) {
        this.trTenderidDet = trTenderidDet;
    }

    public Long getTrTenderidDet() {
        return trTenderidDet;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    // --- DATABASE MAPPING : BUDGETARY_PROV ( NUMBER )
    public void setBudgetaryProv(final BigDecimal budgetaryProv) {
        this.budgetaryProv = budgetaryProv;
    }

    public BigDecimal getBudgetaryProv() {
        return budgetaryProv;
    }

    // --- DATABASE MAPPING : BALANCE_PROV ( NUMBER )
    public void setBalanceProv(final BigDecimal balanceProv) {
        this.balanceProv = balanceProv;
    }

    public BigDecimal getBalanceProv() {
        return balanceProv;
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
    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    public Long getLangId() {
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

    // --- DATABASE MAPPING : TR_TENDERDET_AMT ( NUMBER )
    public BigDecimal getTenderDetailAmt() {
        return tenderDetailAmt;
    }

    public void setTenderDetailAmt(final BigDecimal tenderDetailAmt) {
        this.tenderDetailAmt = tenderDetailAmt;
    }

    // --- DATABASE MAPPING : FI04_V1 ( NVARCHAR2 )
    public Long getSacHeadId() {
        return sacHeadId;
    }

    public void setSacHeadId(Long sacHeadId) {
        this.sacHeadId = sacHeadId;
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

    public AccountBudgetCodeEntity getTbAcBudgetCodeMaster() {
        return tbAcBudgetCodeMaster;
    }

    public void setTbAcBudgetCodeMaster(final AccountBudgetCodeEntity tbAcBudgetCodeMaster) {
        this.tbAcBudgetCodeMaster = tbAcBudgetCodeMaster;
    }

    public void setTbAcTenderMaster(final AccountTenderEntryEntity tbAcTenderMaster) {
        this.tbAcTenderMaster = tbAcTenderMaster;
    }

    public AccountTenderEntryEntity getTbAcTenderMaster() {
        return tbAcTenderMaster;
    }

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(trTenderidDet);
        sb.append("]:");
        sb.append(budgetaryProv);
        sb.append("|");
        sb.append(balanceProv);
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
        sb.append(tenderDetailAmt);
        sb.append("|");
        sb.append(sacHeadId);
        sb.append("|");
        sb.append(fi04D1);
        sb.append("|");
        sb.append(fi04Lo1);
        return sb.toString();
    }

}
