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
 * @author prasad.kancharla
 *
 */
@Entity
@Table(name = "TB_AC_BILL_EXP_DETAIL_HIST")
public class AccountBillEntryExpenditureDetHistEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "BCH_HIST_ID", nullable = false)
    private Long hBchId;

    @Column(name = "BCH_ID", nullable = false)
    private Long id;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------
    @Column(name = "BCH_CHARGES_AMT", nullable = false)
    private BigDecimal billChargesAmount;

    @Column(name = "DISALLOWED_AMT")
    private BigDecimal disallowedAmount;

    @Column(name = "DISALLOWED_REMARK")
    private String disallowedRemark;

    @Column(name = "ACT_AMT")
    private BigDecimal actualAmount;

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

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMacAddress;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacAddressUpdated;

    @Column(name = "SAC_HEAD_ID", precision = 12, scale = 0, nullable = true)
    private Long sacHeadId;

    /*
     * @ManyToOne
     * @JoinColumn(name = "SAC_HEAD_ID", referencedColumnName = "SAC_HEAD_ID") private AccountHeadSecondaryAccountCodeMasterEntity
     * sacHeadId;
     */

    @Column(name = "FI04_V1")
    private String fi04V1;

    @Temporal(TemporalType.DATE)
    @Column(name = "FI04_D1")
    private Date fi04D1;

    @Column(name = "FI04_LO1", length = 1)
    private String fi04Lo1;

    @Column(name = "H_STATUS", length = 1)
    private Character hStatus;

    // ----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    // ----------------------------------------------------------------------
    @ManyToOne
    @JoinColumn(name = "BM_ID", referencedColumnName = "BM_ID")
    private AccountBillEntryMasterEnitity billMasterId;

    @ManyToOne
    @JoinColumn(name = "BUDGETCODE_ID", referencedColumnName = "BUDGETCODE_ID")
    private AccountBudgetCodeEntity budgetCodeId;

    public static String[] getPkValues() {
        return new String[] { "AC", "TB_AC_BILL_EXP_DETAIL_HIST", "BCH_HIST_ID" };
    }

    // ----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    // ----------------------------------------------------------------------
    public AccountBillEntryExpenditureDetHistEntity() {
        super();
    }

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------

    public Long gethBchId() {
        return hBchId;
    }

    public void sethBchId(Long hBchId) {
        this.hBchId = hBchId;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    // --- DATABASE MAPPING : BCH_CHARGES_AMT ( NUMBER )
    public void setBillChargesAmount(final BigDecimal billChargesAmount) {
        this.billChargesAmount = billChargesAmount;
    }

    public BigDecimal getBillChargesAmount() {
        return billChargesAmount;
    }

    // --- DATABASE MAPPING : DISALLOWED_AMT ( NUMBER )
    public void setDisallowedAmount(final BigDecimal disallowedAmount) {
        this.disallowedAmount = disallowedAmount;
    }

    public BigDecimal getDisallowedAmount() {
        return disallowedAmount;
    }

    /**
     * @return the disallowedRemark
     */
    public String getDisallowedRemark() {
        return disallowedRemark;
    }

    /**
     * @param disallowedRemark the disallowedRemark to set
     */
    public void setDisallowedRemark(final String disallowedRemark) {
        this.disallowedRemark = disallowedRemark;
    }

    // --- DATABASE MAPPING : ACT_AMT ( NUMBER )
    public void setActualAmount(final BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    // --- DATABASE MAPPING : ORGID ( NUMBER )
    public void setOrgid(final Long orgId) {
        orgid = orgId;
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

    // --- DATABASE MAPPING : LG_IP_MAC ( VARCHAR2 )
    public void setLgIpMacAddress(final String lgIpMacAddress) {
        this.lgIpMacAddress = lgIpMacAddress;
    }

    public String getLgIpMacAddress() {
        return lgIpMacAddress;
    }

    // --- DATABASE MAPPING : LG_IP_MAC_UPD ( VARCHAR2 )
    public void setLgIpMacAddressUpdated(final String lgIpMacAddressUpdated) {
        this.lgIpMacAddressUpdated = lgIpMacAddressUpdated;
    }

    public String getLgIpMacAddressUpdated() {
        return lgIpMacAddressUpdated;
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
    public void setBillMasterId(final AccountBillEntryMasterEnitity billMasterId) {
        this.billMasterId = billMasterId;
    }

    public AccountBillEntryMasterEnitity getBillMasterId() {
        return billMasterId;
    }

    /**
     * @return the budgetCodeId
     */
    public AccountBudgetCodeEntity getBudgetCodeId() {
        return budgetCodeId;
    }

    /**
     * @param budgetCodeId the budgetCodeId to set
     */
    public void setBudgetCodeId(final AccountBudgetCodeEntity budgetCodeId) {
        this.budgetCodeId = budgetCodeId;
    }

    public Long getSacHeadId() {
        return sacHeadId;
    }

    public void setSacHeadId(Long sacHeadId) {
        this.sacHeadId = sacHeadId;
    }

    public Character gethStatus() {
        return hStatus;
    }

    public void sethStatus(Character hStatus) {
        this.hStatus = hStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AccountBillEntryExpenditureDetHistEntity [hBchId=" + hBchId + ", id=" + id + ", billChargesAmount="
                + billChargesAmount + ", disallowedAmount=" + disallowedAmount + ", disallowedRemark="
                + disallowedRemark + ", actualAmount=" + actualAmount + ", orgid=" + orgid + ", createdBy=" + createdBy
                + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate
                + ", lgIpMacAddress=" + lgIpMacAddress + ", lgIpMacAddressUpdated=" + lgIpMacAddressUpdated
                + ", sacHeadId=" + sacHeadId + ", fi04V1=" + fi04V1 + ", fi04D1=" + fi04D1 + ", fi04Lo1=" + fi04Lo1
                + ", hStatus=" + hStatus + ", billMasterId=" + billMasterId + ", budgetCodeId=" + budgetCodeId + "]";
    }

}
