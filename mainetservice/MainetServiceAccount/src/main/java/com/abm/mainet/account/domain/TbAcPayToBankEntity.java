package com.abm.mainet.account.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.BankMasterEntity;
import com.abm.mainet.common.domain.TbAcVendormasterEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Persistent class for entity stored in table "TB_AC_BANK_TDS_DETAILS"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name = "TB_AC_BANK_TDS_DETAILS")
// Define named queries here
@NamedQueries({ @NamedQuery(name = "TbAcPayToBankEntity.countAll", query = "SELECT COUNT(x) FROM TbAcPayToBankEntity x") })
public class TbAcPayToBankEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "PTB_ID", nullable = false)
    private Long ptbId;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------

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

    @Column(name = "PTB_BSRCODE", length = 15)
    private String ptbBsrcode;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @ManyToOne
    @JoinColumn(name = "BANKID", referencedColumnName = "BANKID")
    private BankMasterEntity bankMaster;

    @ManyToOne
    @JoinColumn(name = "VM_VENDORID", referencedColumnName = "VM_VENDORID")
    private TbAcVendormasterEntity tbAcVendormaster;

    @ManyToOne
    @JoinColumn(name = "SAC_HEAD_ID", referencedColumnName = "SAC_HEAD_ID")
    private AccountHeadSecondaryAccountCodeMasterEntity secondaryAccountCodeMaster;

    @Column(name = "PTB_TDS_TYPE")
    private Long ptbTdsType;

    @Column(name = "PTB_STATUS", length = 1)
    private String ptbStatus;

    @Column(name = "PTB_BAK_ACCOUNTNO")
    private String ptbBankAcNo;

    // ----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    // ----------------------------------------------------------------------

    // ----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    // ----------------------------------------------------------------------
    public TbAcPayToBankEntity() {
        super();
    }

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setPtbId(final Long ptbId) {
        this.ptbId = ptbId;
    }

    public Long getPtbId() {
        return ptbId;
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

    // --- DATABASE MAPPING : PTB_BSRCODE ( VARCHAR2 )
    public void setPtbBsrcode(final String ptbBsrcode) {
        this.ptbBsrcode = ptbBsrcode;
    }

    public String getPtbBsrcode() {
        return ptbBsrcode;
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

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    // ----------------------------------------------------------------------

    public BankMasterEntity getBankMaster() {
        return bankMaster;
    }

    public void setBankMaster(final BankMasterEntity bankMaster) {
        this.bankMaster = bankMaster;
    }

    public TbAcVendormasterEntity getTbAcVendormaster() {
        return tbAcVendormaster;
    }

    public void setTbAcVendormaster(final TbAcVendormasterEntity tbAcVendormaster) {
        this.tbAcVendormaster = tbAcVendormaster;
    }

    public AccountHeadSecondaryAccountCodeMasterEntity getSecondaryAccountCodeMaster() {
        return secondaryAccountCodeMaster;
    }

    public void setSecondaryAccountCodeMaster(final AccountHeadSecondaryAccountCodeMasterEntity secondaryAccountCodeMaster) {
        this.secondaryAccountCodeMaster = secondaryAccountCodeMaster;
    }

    public Long getPtbTdsType() {
        return ptbTdsType;
    }

    public void setPtbTdsType(final Long ptbTdsType) {
        this.ptbTdsType = ptbTdsType;
    }

    public String getPtbStatus() {
        return ptbStatus;
    }

    public void setPtbStatus(final String ptbStatus) {
        this.ptbStatus = ptbStatus;
    }

    public String getPtbBankAcNo() {
        return ptbBankAcNo;
    }

    public void setPtbBankAcNo(final String ptbBankAcNo) {
        this.ptbBankAcNo = ptbBankAcNo;
    }

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------

    @Override
    public String toString() {
        return "TbAcPayToBankEntity [ptbId=" + ptbId + ", orgid=" + orgid + ", userId=" + userId + ", lmoddate="
                + lmoddate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", ptbBsrcode=" + ptbBsrcode
                + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + ", bankMaster=" + bankMaster
                + ", tbAcVendormaster=" + tbAcVendormaster + ", secondaryAccountCodeMaster="
                + secondaryAccountCodeMaster + ", ptbTdsType=" + ptbTdsType + ", ptbStatus=" + ptbStatus
                + ", ptbBankAcNo=" + ptbBankAcNo + "]";
    }

    public String[] getPkValues() {
        return new String[] { "AC", "TB_AC_BANK_TDS_DETAILS",
                "PTB_ID" };
    }

}
