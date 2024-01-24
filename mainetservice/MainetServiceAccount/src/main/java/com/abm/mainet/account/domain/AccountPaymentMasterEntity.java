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

import com.abm.mainet.common.domain.BankAccountMasterEntity;
import com.abm.mainet.common.domain.TbAcVendormasterEntity;
import com.abm.mainet.common.domain.TbComparamDetEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author tejas.kotekar
 * @since 20 Jan 2017
 * @comment This table used for payment master entry.
 */
@Entity
@Table(name = "TB_AC_PAYMENT_MAS")
public class AccountPaymentMasterEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "PAYMENT_ID", precision = 12, scale = 0, nullable = false)
    private long paymentId;

    @Column(name = "PAYMENT_NO", length = 12, nullable = true)
    private String paymentNo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PAYMENT_DATE", nullable = true, columnDefinition = "DATETIME")
    private Date paymentDate;

    @Column(name = "INSTRUMENT_NUMBER", precision = 12, scale = 0, nullable = true)
    private Long instrumentNumber;

    @Column(name = "INSTRUMENT_DATE", nullable = true)
    private Date instrumentDate;

    @Column(name = "CHEQUE_CLEARANCE_DATE", nullable = true)
    private Date chequeClearanceDate;

    @Column(name = "PAYMENT_AMOUNT", precision = 15, scale = 2, nullable = true)
    private BigDecimal paymentAmount;

    @Column(name = "NARRATION", length = 1000, nullable = true)
    private String narration;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", precision = 7, scale = 0, nullable = false)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY", nullable = false, updatable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "PDM_ID", precision = 12, scale = 0, nullable = true)
    private Long pmdId;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "AUTHO_ID", precision = 7, scale = 0, nullable = true)
    private Long authoId;

    @Column(name = "AUTHO_DATE", nullable = true)
    private Date authoDate;

    @Column(name = "AUTHO_FLG", length = 1, nullable = true)
    private String authoFlg;

    @Column(name = "PAYMENT_TYPE_FLAG", length = 1)
    private Long paymentTypeFlag;

    @Column(name = "PAYMENT_DEL_FLAG ", length = 1)
    private String paymentDeletionFlag;

    @Temporal(TemporalType.DATE)
    @Column(name = "PAYMENT_DEL_DATE")
    private Date paymentDeletionDate;

    @Column(name = "PAYMENT_DEL_ORDER_NO", length = 20)
    private String paymentDeletionOrderNo;

    @Temporal(TemporalType.DATE)
    @Column(name = "PAYMENT_DEL_POSTING_DATE")
    private Date deletionPostingDate;

    @Column(name = "PAYMENT_DEL_AUTH_BY")
    private Long deletionAuthorizedBy;

    @Column(name = "PAYMENT_DEL_REMARK")
    private String deletionRemark;

    @Column(name = "DPS_SLIPID")
    private Long depositeSlipId;

    @Column(name = "TRAN_REFNO")
    private String utrNo;

    @Column(name = "DPAYBILLREF_NO", length = 30)
    private String billRefNo;
    
    @Column(name = "FIELDID", length = 20)
    private Long fieldId;

    @ManyToOne
    @JoinColumn(name = "CPD_ID_BILLTYPE", referencedColumnName = "CPD_ID")
    private TbComparamDetEntity billTypeId;

    @ManyToOne
    @JoinColumn(name = "CPD_ID_PAYMENT_MODE", referencedColumnName = "CPD_ID")
    private TbComparamDetEntity paymentModeId;

    @ManyToOne
    @JoinColumn(name = "BA_ACCOUNTID", referencedColumnName = "BA_ACCOUNTID")
    private BankAccountMasterEntity baBankAccountId;

    @ManyToOne
    @JoinColumn(name = "VM_VENDORID", referencedColumnName = "VM_VENDORID")
    private TbAcVendormasterEntity vmVendorId;

    @OneToMany(mappedBy = "paymentMasterId", targetEntity = AccountPaymentDetEntity.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<AccountPaymentDetEntity> paymentDetailList;

    public static String[] getPkValues() {
        return new String[] { "AC", "TB_AC_PAYMENT_MAS", "PAYMENT_ID" };
    }

    /**
     * @return the paymentId
     */
    public long getPaymentId() {
        return paymentId;
    }

    /**
     * @param paymentId the paymentId to set
     */
    public void setPaymentId(final long paymentId) {
        this.paymentId = paymentId;
    }

    /**
     * @return the paymentNo
     */
    public String getPaymentNo() {
        return paymentNo;
    }

    /**
     * @param paymentNo the paymentNo to set
     */
    public void setPaymentNo(final String paymentNo) {
        this.paymentNo = paymentNo;
    }

    /**
     * @return the paymentDate
     */
    public Date getPaymentDate() {
        return paymentDate;
    }

    /**
     * @param paymentDate the paymentDate to set
     */
    public void setPaymentDate(final Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    /**
     * @return the instrumentNumber
     */
    public Long getInstrumentNumber() {
        return instrumentNumber;
    }

    /**
     * @param instrumentNumber the instrumentNumber to set
     */
    public void setInstrumentNumber(final Long instrumentNumber) {
        this.instrumentNumber = instrumentNumber;
    }

    /**
     * @return the instrumentDate
     */
    public Date getInstrumentDate() {
        return instrumentDate;
    }

    /**
     * @param instrumentDate the instrumentDate to set
     */
    public void setInstrumentDate(final Date instrumentDate) {
        this.instrumentDate = instrumentDate;
    }

    /**
     * @return the paymentAmount
     */
    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * @param paymentAmount the paymentAmount to set
     */
    public void setPaymentAmount(final BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    /**
     * @return the narration
     */
    public String getNarration() {
        return narration;
    }

    /**
     * @param narration the narration to set
     */
    public void setNarration(final String narration) {
        this.narration = narration;
    }

    /**
     * @return the orgId
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    /**
     * @return the createdBy
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
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

    /**
     * @return the updatedDate
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getPmdId() {
        return pmdId;
    }

    public void setPmdId(Long pmdId) {
        this.pmdId = pmdId;
    }

    /**
     * @return the lgIpMac
     */
    public String getLgIpMac() {
        return lgIpMac;
    }

    /**
     * @param lgIpMac the lgIpMac to set
     */
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    /**
     * @return the lgIpMacUpd
     */
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    /**
     * @param lgIpMacUpd the lgIpMacUpd to set
     */
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    /**
     * @return the authoId
     */
    public Long getAuthoId() {
        return authoId;
    }

    /**
     * @param authoId the authoId to set
     */
    public void setAuthoId(final Long authoId) {
        this.authoId = authoId;
    }

    /**
     * @return the authoDate
     */
    public Date getAuthoDate() {
        return authoDate;
    }

    /**
     * @param authoDate the authoDate to set
     */
    public void setAuthoDate(final Date authoDate) {
        this.authoDate = authoDate;
    }

    /**
     * @return the authoFlg
     */
    public String getAuthoFlg() {
        return authoFlg;
    }

    /**
     * @param authoFlg the authoFlg to set
     */
    public void setAuthoFlg(final String authoFlg) {
        this.authoFlg = authoFlg;
    }

    public Long getPaymentTypeFlag() {
        return paymentTypeFlag;
    }

    public void setPaymentTypeFlag(final Long paymentTypeFlag) {
        this.paymentTypeFlag = paymentTypeFlag;
    }

    public String getPaymentDeletionFlag() {
        return paymentDeletionFlag;
    }

    public void setPaymentDeletionFlag(final String paymentDeletionFlag) {
        this.paymentDeletionFlag = paymentDeletionFlag;
    }

    public Date getPaymentDeletionDate() {
        return paymentDeletionDate;
    }

    public void setPaymentDeletionDate(final Date paymentDeletionDate) {
        this.paymentDeletionDate = paymentDeletionDate;
    }

    public String getPaymentDeletionOrderNo() {
        return paymentDeletionOrderNo;
    }

    public void setPaymentDeletionOrderNo(final String paymentDeletionOrderNo) {
        this.paymentDeletionOrderNo = paymentDeletionOrderNo;
    }

    public Date getDeletionPostingDate() {
        return deletionPostingDate;
    }

    public void setDeletionPostingDate(final Date deletionPostingDate) {
        this.deletionPostingDate = deletionPostingDate;
    }

    public Long getDeletionAuthorizedBy() {
        return deletionAuthorizedBy;
    }

    public void setDeletionAuthorizedBy(final Long deletionAuthorizedBy) {
        this.deletionAuthorizedBy = deletionAuthorizedBy;
    }

    public String getDeletionRemark() {
        return deletionRemark;
    }

    public void setDeletionRemark(final String deletionRemark) {
        this.deletionRemark = deletionRemark;
    }

    /**
     * @return the depositeSlipId
     */
    public Long getDepositeSlipId() {
        return depositeSlipId;
    }

    /**
     * @param depositeSlipId the depositeSlipId to set
     */
    public void setDepositeSlipId(final Long depositeSlipId) {
        this.depositeSlipId = depositeSlipId;
    }

    /**
     * @return the billTypeId
     */
    public TbComparamDetEntity getBillTypeId() {
        return billTypeId;
    }

    /**
     * @param billTypeId the billTypeId to set
     */
    public void setBillTypeId(final TbComparamDetEntity billTypeId) {
        this.billTypeId = billTypeId;
    }

    /**
     * @return the paymentModeId
     */
    public TbComparamDetEntity getPaymentModeId() {
        return paymentModeId;
    }

    /**
     * @param paymentModeId the paymentModeId to set
     */
    public void setPaymentModeId(final TbComparamDetEntity paymentModeId) {
        this.paymentModeId = paymentModeId;
    }

    /**
     * @return the baBankAccountId
     */
    public BankAccountMasterEntity getBaBankAccountId() {
        return baBankAccountId;
    }

    /**
     * @param baBankAccountId the baBankAccountId to set
     */
    public void setBaBankAccountId(final BankAccountMasterEntity baBankAccountId) {
        this.baBankAccountId = baBankAccountId;
    }

    /**
     * @return the vmVendorId
     */
    public TbAcVendormasterEntity getVmVendorId() {
        return vmVendorId;
    }

    /**
     * @param vmVendorId the vmVendorId to set
     */
    public void setVmVendorId(final TbAcVendormasterEntity vmVendorId) {
        this.vmVendorId = vmVendorId;
    }

    public List<AccountPaymentDetEntity> getPaymentDetailList() {
        return paymentDetailList;
    }

    public void setPaymentDetailList(final List<AccountPaymentDetEntity> paymentDetailList) {
        this.paymentDetailList = paymentDetailList;
    }

    public Date getChequeClearanceDate() {
        return chequeClearanceDate;
    }

    public void setChequeClearanceDate(final Date chequeClearanceDate) {
        this.chequeClearanceDate = chequeClearanceDate;
    }

    public String getUtrNo() {
        return utrNo;
    }

    public void setUtrNo(String utrNo) {
        this.utrNo = utrNo;
    }

    public String getBillRefNo() {
        return billRefNo;
    }

    public void setBillRefNo(String billRefNo) {
        this.billRefNo = billRefNo;
    }
    
    public Long getFieldId() {
		return fieldId;
	}

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}

	@Override
	public String toString() {
		return "AccountPaymentMasterEntity [paymentId=" + paymentId + ", paymentNo=" + paymentNo + ", paymentDate="
				+ paymentDate + ", instrumentNumber=" + instrumentNumber + ", instrumentDate=" + instrumentDate
				+ ", chequeClearanceDate=" + chequeClearanceDate + ", paymentAmount=" + paymentAmount + ", narration="
				+ narration + ", orgId=" + orgId + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", pmdId=" + pmdId + ", lgIpMac="
				+ lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + ", authoId=" + authoId + ", authoDate=" + authoDate
				+ ", authoFlg=" + authoFlg + ", paymentTypeFlag=" + paymentTypeFlag + ", paymentDeletionFlag="
				+ paymentDeletionFlag + ", paymentDeletionDate=" + paymentDeletionDate + ", paymentDeletionOrderNo="
				+ paymentDeletionOrderNo + ", deletionPostingDate=" + deletionPostingDate + ", deletionAuthorizedBy="
				+ deletionAuthorizedBy + ", deletionRemark=" + deletionRemark + ", depositeSlipId=" + depositeSlipId
				+ ", utrNo=" + utrNo + ", billRefNo=" + billRefNo + ", fieldId=" + fieldId + ", billTypeId="
				+ billTypeId + ", paymentModeId=" + paymentModeId + ", baBankAccountId=" + baBankAccountId
				+ ", vmVendorId=" + vmVendorId + ", paymentDetailList=" + paymentDetailList + "]";
	}

}