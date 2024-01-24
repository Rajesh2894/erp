
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
 * Persistent class for entity stored in table "TB_AC_PROJECTEDPROVISIONADJ_TR"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name = "TB_AC_PROJECTEDPROVISIONADJ_TR")

public class AccountBudgetReappropriationTrMasterEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "PA_ADJID_TR", nullable = false)
    private Long paAdjidTr;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------

    @Column(name = "PR_PROJECTIONID")
    private Long prProjectionid;

    @Column(name = "PR_EXPENDITUREID")
    private Long prExpenditureid;

    @Column(name = "PROVISION_OLDAMT")
    private BigDecimal provisionOldamt;

    @Column(name = "ORG_REV_BALAMT")
    private BigDecimal orgRevBalamt;

    @Column(name = "TRANSFER_AMOUNT")
    private BigDecimal transferAmount;

    @Column(name = "NEW_ORG_REV_AMOUNT")
    private BigDecimal newOrgRevAmount;

    @Column(name = "ORGID", nullable = false)
    private Long orgid;

    @Column(name = "CREATED_BY", nullable = false)
    private BigDecimal userId;

    @Column(name = "LANG_ID", nullable = false)
    private BigDecimal langId;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date lmoddate;

    @Column(name = "UPDATED_BY")
    private BigDecimal updatedBy;

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
    private BigDecimal fi04N1;

    @Column(name = "FI04_V1")
    private String fi04V1;

    @Temporal(TemporalType.DATE)
    @Column(name = "FI04_D1")
    private Date fi04D1;

    @Column(name = "AUTH_FLG", length = 1, nullable = false)
    private String authFlag;

    // ----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    // ----------------------------------------------------------------------
    @ManyToOne
    @JoinColumn(name = "PA_ADJID", referencedColumnName = "PA_ADJID")
    private AccountBudgetReappropriationMasterEntity tbAcProjectedprovisionadj;

    @ManyToOne
    @JoinColumn(name = "BUDGETCODE_ID", referencedColumnName = "BUDGETCODE_ID")
    private AccountBudgetCodeEntity tbAcBudgetCodeMaster;

    @Column(name = "DP_DEPTID")
    private Long department;

    // ----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    // ----------------------------------------------------------------------
    public AccountBudgetReappropriationTrMasterEntity() {
        super();
    }

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setPaAdjidTr(final Long paAdjidTr) {
        this.paAdjidTr = paAdjidTr;
    }

    public Long getPaAdjidTr() {
        return paAdjidTr;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    // --- DATABASE MAPPING : PR_PROJECTIONID ( NUMBER )
    public void setPrProjectionid(final Long prProjectionid) {
        this.prProjectionid = prProjectionid;
    }

    public Long getPrProjectionid() {
        return prProjectionid;
    }

    // --- DATABASE MAPPING : PR_EXPENDITUREID ( NUMBER )
    public void setPrExpenditureid(final Long prExpenditureid) {
        this.prExpenditureid = prExpenditureid;
    }

    public Long getPrExpenditureid() {
        return prExpenditureid;
    }

    // --- DATABASE MAPPING : PROVISION_OLDAMT ( NUMBER )
    public void setProvisionOldamt(final BigDecimal provisionOldamt) {
        this.provisionOldamt = provisionOldamt;
    }

    public BigDecimal getProvisionOldamt() {
        return provisionOldamt;
    }

    // --- DATABASE MAPPING : ORG_REV_BALAMT ( NUMBER )
    public void setOrgRevBalamt(final BigDecimal orgRevBalamt) {
        this.orgRevBalamt = orgRevBalamt;
    }

    public BigDecimal getOrgRevBalamt() {
        return orgRevBalamt;
    }

    // --- DATABASE MAPPING : TRANSFER_AMOUNT ( NUMBER )
    public void setTransferAmount(final BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    // --- DATABASE MAPPING : NEW_ORG_REV_AMOUNT ( NUMBER )
    public void setNewOrgRevAmount(final BigDecimal newOrgRevAmount) {
        this.newOrgRevAmount = newOrgRevAmount;
    }

    public BigDecimal getNewOrgRevAmount() {
        return newOrgRevAmount;
    }

    // --- DATABASE MAPPING : ORGID ( NUMBER )
    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getOrgid() {
        return orgid;
    }

    // --- DATABASE MAPPING : USER_ID ( NUMBER )
    public void setUserId(final BigDecimal userId) {
        this.userId = userId;
    }

    public BigDecimal getUserId() {
        return userId;
    }

    // --- DATABASE MAPPING : LANG_ID ( NUMBER )
    public void setLangId(final BigDecimal langId) {
        this.langId = langId;
    }

    public BigDecimal getLangId() {
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
    public void setUpdatedBy(final BigDecimal updatedBy) {
        this.updatedBy = updatedBy;
    }

    public BigDecimal getUpdatedBy() {
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
    public void setFi04N1(final BigDecimal fi04N1) {
        this.fi04N1 = fi04N1;
    }

    public BigDecimal getFi04N1() {
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

    public String getAuthFlag() {
        return authFlag;
    }

    public void setAuthFlag(String authFlag) {
        this.authFlag = authFlag;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    // ----------------------------------------------------------------------

    /**
     * @return the tbAcProjectedprovisionadj
     */
    public AccountBudgetReappropriationMasterEntity getTbAcProjectedprovisionadj() {
        return tbAcProjectedprovisionadj;
    }

    /**
     * @param tbAcProjectedprovisionadj the tbAcProjectedprovisionadj to set
     */
    public void setTbAcProjectedprovisionadj(
            final AccountBudgetReappropriationMasterEntity tbAcProjectedprovisionadj) {
        this.tbAcProjectedprovisionadj = tbAcProjectedprovisionadj;
    }

    public static String[] getPkValues() {
        return new String[] { "AC", "TB_AC_PROJECTEDPROVISIONADJ_TR", "PA_ADJID_TR" };
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

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(paAdjidTr);
        sb.append("]:");
        sb.append(prProjectionid);
        sb.append("|");
        sb.append(prExpenditureid);
        sb.append("|");
        sb.append(provisionOldamt);
        sb.append("|");
        sb.append(orgRevBalamt);
        sb.append("|");
        sb.append(transferAmount);
        sb.append("|");
        sb.append(newOrgRevAmount);
        sb.append("|");
        sb.append(orgid);
        sb.append("|");
        sb.append(userId);
        sb.append("|");
        sb.append(langId);
        sb.append("|");
        sb.append(lmoddate);
        sb.append("|");
        sb.append(updatedBy);
        sb.append("|");
        sb.append(updatedDate);
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
        sb.append(authFlag);
        return sb.toString();
    }

}
