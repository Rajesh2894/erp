package com.abm.mainet.account.domain;

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

import com.abm.mainet.common.domain.TbComparamDetEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountBudgetCodeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Persistent class for entity stored in table "TB_AC_TDS_TAXHEADS"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name = "TB_AC_TDS_TAXHEADS")
public class AccountTDSTaxHeadsEntity implements
        Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "TDS_ID", nullable = false)
    private Long tdsId;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------
    @Column(name = "ORGID", nullable = false)
    private Long orgid;

    @Temporal(TemporalType.DATE)
    @Column(name = "TDS_ENTRYDATE", nullable = false)
    private Date tdsEntrydate;

    @Column(name = "TDS_DESCRIPTION", nullable = false)
    private String tdsDescription;

    @Column(name = "TDS_STATUS_FLG", nullable = false)
    private String tdsStatusFlg;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "LANG_ID", nullable = false)
    private Long langId;

    @Temporal(TemporalType.DATE)
    @Column(name = "LMODDATE", nullable = false)
    private Date lmoddate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100)
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

    @ManyToOne
    @JoinColumn(name = "CPD_ID_DEDUCTION_TYPE", referencedColumnName = "CPD_ID")
    private TbComparamDetEntity tbComparamDet;

    @ManyToOne
    @JoinColumn(name = "BUDGETCODE_ID", referencedColumnName = "BUDGETCODE_ID")
    private AccountBudgetCodeEntity budgetCode;

    // ----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    // ----------------------------------------------------------------------
    public AccountTDSTaxHeadsEntity() {
        super();
    }

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
    // --- DATABASE MAPPING : ORGID ( NUMBER )
    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getOrgid() {
        return orgid;
    }

    // --- DATABASE MAPPING : TDS_ENTRYDATE ( DATE )
    public void setTdsEntrydate(final Date tdsEntrydate) {
        this.tdsEntrydate = tdsEntrydate;
    }

    public Date getTdsEntrydate() {
        return tdsEntrydate;
    }

    // --- DATABASE MAPPING : TDS_DESCRIPTION ( NVARCHAR2 )
    public void setTdsDescription(final String tdsDescription) {
        this.tdsDescription = tdsDescription;
    }

    public String getTdsDescription() {
        return tdsDescription;
    }

    // --- DATABASE MAPPING : TDS_STATUS_FLG ( NVARCHAR2 )
    public void setTdsStatusFlg(final String tdsStatusFlg) {
        this.tdsStatusFlg = tdsStatusFlg;
    }

    public String getTdsStatusFlg() {
        return tdsStatusFlg;
    }

    // --- DATABASE MAPPING : USER_ID ( NUMBER )
    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    // --- DATABASE MAPPING : LANG_ID ( NUMBER )
    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    public Long getLangId() {
        return langId;
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

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    // ----------------------------------------------------------------------

    public void setTbComparamDet(
            final TbComparamDetEntity tbComparamDet) {
        this.tbComparamDet = tbComparamDet;
    }

    public TbComparamDetEntity getTbComparamDet() {
        return tbComparamDet;
    }

    /**
     * @return the budgetCode
     */
    public AccountBudgetCodeEntity getBudgetCode() {
        return budgetCode;
    }

    /**
     * @param budgetCode the budgetCode to set
     */
    public void setBudgetCode(final AccountBudgetCodeEntity budgetCode) {
        this.budgetCode = budgetCode;
    }

    public static String[] getPkValues() {
        return new String[] { "AC", "TB_AC_TDS_TAXHEADS",
                "TDS_ID" };
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AccountTDSTaxHeadsEntity [tdsId=" + tdsId + ", orgid=" + orgid
                + ", tdsEntrydate=" + tdsEntrydate + ", tdsDescription="
                + tdsDescription + ", tdsStatusFlg=" + tdsStatusFlg
                + ", userId=" + userId + ", langId=" + langId + ", lmoddate="
                + lmoddate + ", updatedBy=" + updatedBy + ", updatedDate="
                + updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd="
                + lgIpMacUpd + ", fi04N1=" + fi04N1 + ", fi04V1=" + fi04V1
                + ", fi04D1=" + fi04D1 + ", fi04Lo1=" + fi04Lo1
                + ", tbComparamDet=" + tbComparamDet + ", budgetCodeId="
                + budgetCode + "]";
    }

}
