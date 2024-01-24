package com.abm.mainet.account.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.TbAcVendormasterEntity;
import com.abm.mainet.common.domain.TbComparamDetEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Persistent class for entity stored in table "TB_AC_TENDER_MASTER"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name = "TB_AC_TENDER_MASTER")
public class AccountTenderEntryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "TR_TENDER_ID", nullable = false)
    private Long trTenderId;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TR_ENTRY_DATE", nullable = false)
    private Date trEntryDate;

    @Column(name = "TR_TENDER_NO", nullable = false)
    private String trTenderNo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TR_TENDER_DATE", nullable = false)
    private Date trTenderDate;

    @Column(name = "TR_NAMEOFWORK")
    private String trNameofwork;

    @Column(name = "TR_EMD_AMT")
    private Long trEmdAmt;

    @Column(name = "SPECIALCONDITIONS")
    private String specialconditions;

    @Column(name = "TR_TENDER_AMOUNT", nullable = false)
    private BigDecimal trTenderAmount;

    @Column(name = "TR_PROPOSAL_NO", nullable = false)
    private String trProposalNo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TR_PROPOSAL_DATE", nullable = false)
    private Date trProposalDate;

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

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "AUTH_BY")
    private Long authorisedBy;

    @Column(name = "AUTH_REMARK")
    private String authRemark;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "AUTH_DATE")
    private Date authDate;

    @Column(name = "AUTH_STATUS", length = 1)
    private String authStatus;

    // ----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    // ----------------------------------------------------------------------
    @OneToMany(mappedBy = "tbAcTenderMaster", targetEntity = AccountTenderDetEntity.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<AccountTenderDetEntity> listOfTbAcTenderDet;

    @ManyToOne
    @JoinColumn(name = "DP_DEPTID", referencedColumnName = "DP_DEPTID")
    private Department tbDepartment;

    @ManyToOne
    @JoinColumn(name = "VM_VENDORID", referencedColumnName = "VM_VENDORID")
    private TbAcVendormasterEntity tbVendormaster;

    @ManyToOne
    @JoinColumn(name = "TR_TYPE_CPD_ID", referencedColumnName = "CPD_ID")
    private TbComparamDetEntity tbComparamDet;

    @ManyToOne
    @JoinColumn(name = "VOU_ID", referencedColumnName = "VOU_ID")
    private AccountVoucherEntryEntity tbVoucherEntry;

    public static String[] getPkValues() {
        return new String[] { "AC", "TB_AC_TENDER_MASTER", "TR_TENDER_ID" };
    }

    // ----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    // ----------------------------------------------------------------------
    public AccountTenderEntryEntity() {
        super();
    }

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setTrTenderId(final Long trTenderId) {
        this.trTenderId = trTenderId;
    }

    public Long getTrTenderId() {
        return trTenderId;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    // --- DATABASE MAPPING : TR_ENTRY_DATE ( DATE )
    public void setTrEntryDate(final Date trEntryDate) {
        this.trEntryDate = trEntryDate;
    }

    public Date getTrEntryDate() {
        return trEntryDate;
    }

    // --- DATABASE MAPPING : TR_TENDER_NO ( NVARCHAR2 )
    public void setTrTenderNo(final String trTenderNo) {
        this.trTenderNo = trTenderNo;
    }

    public String getTrTenderNo() {
        return trTenderNo;
    }

    // --- DATABASE MAPPING : TR_TENDER_DATE ( DATE )
    public void setTrTenderDate(final Date trTenderDate) {
        this.trTenderDate = trTenderDate;
    }

    public Date getTrTenderDate() {
        return trTenderDate;
    }

    // --- DATABASE MAPPING : TR_NAMEOFWORK ( NVARCHAR2 )
    public void setTrNameofwork(final String trNameofwork) {
        this.trNameofwork = trNameofwork;
    }

    public String getTrNameofwork() {
        return trNameofwork;
    }

    // --- DATABASE MAPPING : TR_EMD_AMT ( NUMBER )
    public void setTrEmdAmt(final Long trEmdAmt) {
        this.trEmdAmt = trEmdAmt;
    }

    public Long getTrEmdAmt() {
        return trEmdAmt;
    }

    // --- DATABASE MAPPING : SPECIALCONDITIONS ( NVARCHAR2 )
    public void setSpecialconditions(final String specialconditions) {
        this.specialconditions = specialconditions;
    }

    public String getSpecialconditions() {
        return specialconditions;
    }

    // --- DATABASE MAPPING : TR_TENDER_AMOUNT ( NUMBER )
    public void setTrTenderAmount(final BigDecimal trTenderAmount) {
        this.trTenderAmount = trTenderAmount;
    }

    public BigDecimal getTrTenderAmount() {
        return trTenderAmount;
    }

    // --- DATABASE MAPPING : TR_PROPOSAL_NO ( NVARCHAR2 )
    public void setTrProposalNo(final String trProposalNo) {
        this.trProposalNo = trProposalNo;
    }

    public String getTrProposalNo() {
        return trProposalNo;
    }

    // --- DATABASE MAPPING : TR_PROPOSAL_DATE ( DATE )
    public void setTrProposalDate(final Date trProposalDate) {
        this.trProposalDate = trProposalDate;
    }

    public Date getTrProposalDate() {
        return trProposalDate;
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
    public void setListOfTbAcTenderDet(final List<AccountTenderDetEntity> listOfTbAcTenderDet) {
        this.listOfTbAcTenderDet = listOfTbAcTenderDet;
    }

    public Long getAuthorisedBy() {
        return authorisedBy;
    }

    public void setAuthorisedBy(final Long authorisedBy) {
        this.authorisedBy = authorisedBy;
    }

    public String getAuthRemark() {
        return authRemark;
    }

    public void setAuthRemark(final String authRemark) {
        this.authRemark = authRemark;
    }

    public Date getAuthDate() {
        return authDate;
    }

    public void setAuthDate(final Date authDate) {
        this.authDate = authDate;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(final String authStatus) {
        this.authStatus = authStatus;
    }

    public List<AccountTenderDetEntity> getListOfTbAcTenderDet() {
        return listOfTbAcTenderDet;
    }

    public void setTbDepartment(final Department tbDepartment) {
        this.tbDepartment = tbDepartment;
    }

    public Department getTbDepartment() {
        return tbDepartment;
    }

    public void setTbVendormaster(final TbAcVendormasterEntity tbVendormaster) {
        this.tbVendormaster = tbVendormaster;
    }

    public TbAcVendormasterEntity getTbVendormaster() {
        return tbVendormaster;
    }

    public void setTbComparamDet(final TbComparamDetEntity tbComparamDet) {
        this.tbComparamDet = tbComparamDet;
    }

    public TbComparamDetEntity getTbComparamDet() {
        return tbComparamDet;
    }

    public AccountVoucherEntryEntity getTbVoucherEntry() {
        return tbVoucherEntry;
    }

    public void setTbVoucherEntry(final AccountVoucherEntryEntity tbVoucherEntry) {
        this.tbVoucherEntry = tbVoucherEntry;
    }

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(trTenderId);
        sb.append("]:");
        sb.append(trEntryDate);
        sb.append("|");
        sb.append(trTenderNo);
        sb.append("|");
        sb.append(trTenderDate);
        sb.append("|");
        sb.append(trNameofwork);
        sb.append("|");
        sb.append(trEmdAmt);
        sb.append("|");
        sb.append(specialconditions);
        sb.append("|");
        sb.append(trTenderAmount);
        sb.append("|");
        sb.append(trProposalNo);
        sb.append("|");
        sb.append(trProposalDate);
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
        sb.append(lgIpMac);
        sb.append("|");
        sb.append(lgIpMacUpd);
        sb.append("|");
        sb.append(authorisedBy);
        sb.append("|");
        sb.append(authRemark);
        sb.append("|");
        sb.append(authDate);
        sb.append("|");
        sb.append(authStatus);
        return sb.toString();
    }

}
