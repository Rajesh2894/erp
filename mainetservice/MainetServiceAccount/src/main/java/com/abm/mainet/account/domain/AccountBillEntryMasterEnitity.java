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
import com.abm.mainet.common.integration.acccount.domain.AccountFundMasterEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author tejas.kotekar
 *
 */
@Entity
@Table(name = "TB_AC_BILL_MAS")
public class AccountBillEntryMasterEnitity implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "BM_ID", nullable = false)
    private Long id;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------
    @Column(name = "BM_BILLNO", nullable = false)
    private String billNo;

    @Temporal(TemporalType.DATE)
    @Column(name = "BM_ENTRYDATE")
    private Date billEntryDate;

    @Column(name = "VM_VENDORNAME")
    private String vendorName;

    @Column(name = "BM_INVOICENUMBER")
    private String invoiceNumber;

    @Temporal(TemporalType.DATE)
    @Column(name = "BM_INVOICEDATE")
    private Date invoiceDate;

    @Column(name = "BM_INVOICEVALUE")
    private BigDecimal invoiceValue;

    @Column(name = "BM_W_P_ORDER_NUMBER")
    private String workOrPurchaseOrderNumber;

    @Temporal(TemporalType.DATE)
    @Column(name = "BM_W_P_ORDER_DATE")
    private Date workOrPurchaseOrderDate;

    @Column(name = "BM_RESOLUTION_NUMBER")
    private String resolutionNumber;

    @Temporal(TemporalType.DATE)
    @Column(name = "BM_RESOLUTION_DATE")
    private Date resolutionDate;

    @Column(name = "BM_NARRATION", nullable = false)
    private String narration;

    @Column(name = "BM_TOT_AMT", nullable = false)
    private BigDecimal billTotalAmount;

    @Column(name = "BM_BAL_AMT", nullable = false)
    private BigDecimal balanceAmount;

    @Column(name = "INT_REF_ID")
    private Long billIntRefId;

    @Column(name = "LPS_LOANID")
    private Long loanId;

    @Column(name = "ADVTYPE_CPD_ID")
    private Long advanceTypeId;

    @Column(name = "CHECKER_AUTHO", length = 1)
    private Character checkerAuthorization;

    @Column(name = "CHECKER_REMARKS")
    private String checkerRemarks;

    @Column(name = "CHECKER_USER")
    private Long checkerUser;

    @Temporal(TemporalType.DATE)
    @Column(name = "CHECKER_DATE")
    private Date checkerDate;

    @Column(name = "ORGID", nullable = false)
    private Long orgId;

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
    private Long languageId;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMacAddress;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacAddressUpdated;

    @Column(name = "FI04_N1")
    private Long fi04N1;

    @Column(name = "BM_DEL_FLAG", length = 1)
    private String billDeletionFlag;

    @Temporal(TemporalType.DATE)
    @Column(name = "BM_DEL_DATE")
    private Date billDeletionDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "BM_DEL_POSTING_DATE")
    private Date deletionPostingDate;

    @Column(name = "BM_DEL_ORDER_NO")
    private String billDeletionOrderNo;

    @Column(name = "BM_DEL_AUTH_BY")
    private Long billDeletionAuthorizedBy;

    @Column(name = "BM_DEL_REMARK")
    private String billDeletionRemark;

    @Column(name = "BM_PAY_STATUS", length = 1)
    private Character payStatus;
    
    //added by rahul.chaubey User Story #40685
    @Column(name = "BM_TAXABLE_VAL", precision = 15, scale = 2, nullable = true)
    private BigDecimal bmTaxableValue;

    // ----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    // ----------------------------------------------------------------------
    @ManyToOne
    @JoinColumn(name = "LB_LIABILITY_ID", referencedColumnName = "LB_LIABILITY_ID")
    private AccountLiabilityBookingEntity liabilityId;

    @ManyToOne
    @JoinColumn(name = "DP_DEPTID", referencedColumnName = "DP_DEPTID")
    private Department departmentId;

    @ManyToOne
    @JoinColumn(name = "VM_VENDORID", referencedColumnName = "VM_VENDORID")
    private TbAcVendormasterEntity vendorId;

    @ManyToOne
    @JoinColumn(name = "BM_BILLTYPE_CPD_ID", referencedColumnName = "CPD_ID")
    private TbComparamDetEntity billTypeId;

    @Column(name = "FIELD_ID")
    private Long fieldId;
    
    @Column(name = "DUE_DATE")
    private Date dueDate;

    @OneToMany(mappedBy = "billMasterId", targetEntity = AccountBillEntryExpenditureDetEntity.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<AccountBillEntryExpenditureDetEntity> expenditureDetailList;

    @OneToMany(mappedBy = "billMasterId", targetEntity = AccountBillEntryDeductionDetEntity.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<AccountBillEntryDeductionDetEntity> deductionDetailList;
    
    @OneToMany(mappedBy = "billMasterId", targetEntity = AccountBillEntryMeasurementDetEntity.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<AccountBillEntryMeasurementDetEntity> measuremetDetailList;

  //added by rahul.chaubey User Story #40685
    @ManyToOne
    @JoinColumn(name = "FUND_ID", referencedColumnName = "FUND_ID")
    private AccountFundMasterEntity fundId;
    
    
    public static String[] getPkValues() {
        return new String[] { "AC", "TB_AC_BILL_MAS", "BM_ID" };
    }

    // ----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    // ----------------------------------------------------------------------
    public AccountBillEntryMasterEnitity() {
        super();
    }

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setId(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    // --- DATABASE MAPPING : BM_BILLNO ( NVARCHAR2 )
    public void setBillNo(final String billNo) {
        this.billNo = billNo;
    }

    public String getBillNo() {
        return billNo;
    }

    // --- DATABASE MAPPING : BM_ENTRYDATE ( DATE )
    public void setBillEntryDate(final Date billEntryDate) {
        this.billEntryDate = billEntryDate;
    }

    public Date getBillEntryDate() {
        return billEntryDate;
    }

    // --- DATABASE MAPPING : VM_VENDORNAME ( NVARCHAR2 )
    public void setVendorName(final String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorName() {
        return vendorName;
    }

    // --- DATABASE MAPPING : BM_INVOICENUMBER ( NVARCHAR2 )
    public void setInvoiceNumber(final String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    // --- DATABASE MAPPING : BM_INVOICEDATE ( DATE )
    public void setInvoiceDate(final Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    // --- DATABASE MAPPING : BM_INVOICEVALUE ( NUMBER )
    public void setInvoiceValue(final BigDecimal invoiceValue) {
        this.invoiceValue = invoiceValue;
    }

    public BigDecimal getInvoiceValue() {
        return invoiceValue;
    }

    // --- DATABASE MAPPING : BM_W_P_ORDER_NUMBER ( NVARCHAR2 )
    public void setWorkOrPurchaseOrderNumber(final String workOrPurchaseOrderNumber) {
        this.workOrPurchaseOrderNumber = workOrPurchaseOrderNumber;
    }

    public String getWorkOrPurchaseOrderNumber() {
        return workOrPurchaseOrderNumber;
    }

    // --- DATABASE MAPPING : BM_W_P_ORDER_DATE ( DATE )
    public void setWorkOrPurchaseOrderDate(final Date workOrPurchaseOrderDate) {
        this.workOrPurchaseOrderDate = workOrPurchaseOrderDate;
    }

    public Date getWorkOrPurchaseOrderDate() {
        return workOrPurchaseOrderDate;
    }

    // --- DATABASE MAPPING : BM_RESOLUTION_NUMBER ( NVARCHAR2 )
    public void setResolutionNumber(final String resolutionNumber) {
        this.resolutionNumber = resolutionNumber;
    }

    public String getResolutionNumber() {
        return resolutionNumber;
    }

    // --- DATABASE MAPPING : BM_RESOLUTION_DATE ( DATE )
    public void setResolutionDate(final Date resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    public Date getResolutionDate() {
        return resolutionDate;
    }

    // --- DATABASE MAPPING : BM_NARRATION ( NVARCHAR2 )
    public void setNarration(final String narration) {
        this.narration = narration;
    }

    public String getNarration() {
        return narration;
    }

    // --- DATABASE MAPPING : BM_TOT_AMT ( NUMBER )
    public void setBillTotalAmount(final BigDecimal billTotalAmount) {
        this.billTotalAmount = billTotalAmount;
    }

    public BigDecimal getBillTotalAmount() {
        return billTotalAmount;
    }

    // --- DATABASE MAPPING : BM_BAL_AMT ( NUMBER )
    public void setBalanceAmount(final BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    // --- DATABASE MAPPING : DEP_ID ( NUMBER )
    public Long getBillIntRefId() {
        return billIntRefId;
    }

    public void setBillIntRefId(Long billIntRefId) {
        this.billIntRefId = billIntRefId;
    }

    // --- DATABASE MAPPING : LPS_LOANID ( NUMBER )
    public void setLoanId(final Long loanId) {
        this.loanId = loanId;
    }

    public Long getLoanId() {
        return loanId;
    }

    // --- DATABASE MAPPING : ADVTYPE_CPD_ID ( NUMBER )
    public void setAdvanceTypeId(final Long advanceTypeId) {
        this.advanceTypeId = advanceTypeId;
    }

    public Long getAdvanceTypeId() {
        return advanceTypeId;
    }

    // --- DATABASE MAPPING : CHECKER_AUTHO ( CHAR )
    public void setCheckerAuthorization(final Character checkerAuthorization) {
        this.checkerAuthorization = checkerAuthorization;
    }

    public Character getCheckerAuthorization() {
        return checkerAuthorization;
    }

    // --- DATABASE MAPPING : CHECKER_REMARKS ( NVARCHAR2 )
    public void setCheckerRemarks(final String checkerRemarks) {
        this.checkerRemarks = checkerRemarks;
    }

    public String getCheckerRemarks() {
        return checkerRemarks;
    }

    // --- DATABASE MAPPING : CHECKER_USER ( NVARCHAR2 )
    public void setCheckerUser(final Long checkerUser) {
        this.checkerUser = checkerUser;
    }

    public Long getCheckerUser() {
        return checkerUser;
    }

    // --- DATABASE MAPPING : CHECKER_DATE ( DATE )
    public void setCheckerDate(final Date checkerDate) {
        this.checkerDate = checkerDate;
    }

    public Date getCheckerDate() {
        return checkerDate;
    }

    // --- DATABASE MAPPING : ORGID ( NUMBER )
    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getOrgId() {
        return orgId;
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
    public void setLanguageId(final Long languageId) {
        this.languageId = languageId;
    }

    public Long getLanguageId() {
        return languageId;
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

    // --- DATABASE MAPPING : FI04_N1 ( NUMBER )
    public void setFi04N1(final Long fi04N1) {
        this.fi04N1 = fi04N1;
    }

    public Long getFi04N1() {
        return fi04N1;
    }

    public String getBillDeletionFlag() {
        return billDeletionFlag;
    }

    public void setBillDeletionFlag(final String billDeletionFlag) {
        this.billDeletionFlag = billDeletionFlag;
    }

    public Date getBillDeletionDate() {
        return billDeletionDate;
    }

    public void setBillDeletionDate(final Date billDeletionDate) {
        this.billDeletionDate = billDeletionDate;
    }

    public Date getDeletionPostingDate() {
        return deletionPostingDate;
    }

    public void setDeletionPostingDate(final Date deletionPostingDate) {
        this.deletionPostingDate = deletionPostingDate;
    }

    public String getBillDeletionOrderNo() {
        return billDeletionOrderNo;
    }

    public void setBillDeletionOrderNo(final String billDeletionOrderNo) {
        this.billDeletionOrderNo = billDeletionOrderNo;
    }

    public Long getBillDeletionAuthorizedBy() {
        return billDeletionAuthorizedBy;
    }

    public void setBillDeletionAuthorizedBy(final Long billDeletionAuthorizedBy) {
        this.billDeletionAuthorizedBy = billDeletionAuthorizedBy;
    }

    public String getBillDeletionRemark() {
        return billDeletionRemark;
    }

    public void setBillDeletionRemark(final String billDeletionRemark) {
        this.billDeletionRemark = billDeletionRemark;
    }

    public Character getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(final Character payStatus) {
        this.payStatus = payStatus;
    }

    public BigDecimal getBmTaxableValue() {
		return bmTaxableValue;
	}

	public void setBmTaxableValue(BigDecimal bmTaxableValue) {
		this.bmTaxableValue = bmTaxableValue;
	}
    
    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    // ----------------------------------------------------------------------
    public void setLiabilityId(final AccountLiabilityBookingEntity liabilityId) {
        this.liabilityId = liabilityId;
    }

    public AccountLiabilityBookingEntity getLiabilityId() {
        return liabilityId;
    }

    public void setDepartmentId(final Department departmentId) {
        this.departmentId = departmentId;
    }

    public Department getDepartmentId() {
        return departmentId;
    }

    public void setVendorId(final TbAcVendormasterEntity vendorId) {
        this.vendorId = vendorId;
    }

    public TbAcVendormasterEntity getVendorId() {
        return vendorId;
    }

    public void setBillTypeId(final TbComparamDetEntity billTypeId) {
        this.billTypeId = billTypeId;
    }

    public TbComparamDetEntity getBillTypeId() {
        return billTypeId;
    }

    /**
     * @return the expenditureDetList
     */
    public List<AccountBillEntryExpenditureDetEntity> getExpenditureDetailList() {
        return expenditureDetailList;
    }

    /**
     * @param expenditureDetList the expenditureDetList to set
     */
    public void setExpenditureDetailList(final List<AccountBillEntryExpenditureDetEntity> expenditureDetailList) {
        this.expenditureDetailList = expenditureDetailList;
    }

    /**
     * @return the deductionDetList
     */
    public List<AccountBillEntryDeductionDetEntity> getDeductionDetailList() {
        return deductionDetailList;
    }

    /**
     * @param deductionDetList the deductionDetList to set
     */
    public void setDeductionDetailList(final List<AccountBillEntryDeductionDetEntity> deductionDetailList) {
        this.deductionDetailList = deductionDetailList;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(final Long fieldId) {
        this.fieldId = fieldId;
    }

	public AccountFundMasterEntity getFundId() {
		return fundId;
	}

	public void setFundId(AccountFundMasterEntity fundId) {
		this.fundId = fundId;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public List<AccountBillEntryMeasurementDetEntity> getMeasuremetDetailList() {
		return measuremetDetailList;
	}

	public void setMeasuremetDetailList(List<AccountBillEntryMeasurementDetEntity> measuremetDetailList) {
		this.measuremetDetailList = measuremetDetailList;
	}

	@Override
	public String toString() {
		return "AccountBillEntryMasterEnitity [id=" + id + ", billNo=" + billNo + ", billEntryDate=" + billEntryDate
				+ ", vendorName=" + vendorName + ", invoiceNumber=" + invoiceNumber + ", invoiceDate=" + invoiceDate
				+ ", invoiceValue=" + invoiceValue + ", workOrPurchaseOrderNumber=" + workOrPurchaseOrderNumber
				+ ", workOrPurchaseOrderDate=" + workOrPurchaseOrderDate + ", resolutionNumber=" + resolutionNumber
				+ ", resolutionDate=" + resolutionDate + ", narration=" + narration + ", billTotalAmount="
				+ billTotalAmount + ", balanceAmount=" + balanceAmount + ", billIntRefId=" + billIntRefId + ", loanId="
				+ loanId + ", advanceTypeId=" + advanceTypeId + ", checkerAuthorization=" + checkerAuthorization
				+ ", checkerRemarks=" + checkerRemarks + ", checkerUser=" + checkerUser + ", checkerDate=" + checkerDate
				+ ", orgId=" + orgId + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", updatedBy="
				+ updatedBy + ", updatedDate=" + updatedDate + ", languageId=" + languageId + ", lgIpMacAddress="
				+ lgIpMacAddress + ", lgIpMacAddressUpdated=" + lgIpMacAddressUpdated + ", fi04N1=" + fi04N1
				+ ", billDeletionFlag=" + billDeletionFlag + ", billDeletionDate=" + billDeletionDate
				+ ", deletionPostingDate=" + deletionPostingDate + ", billDeletionOrderNo=" + billDeletionOrderNo
				+ ", billDeletionAuthorizedBy=" + billDeletionAuthorizedBy + ", billDeletionRemark="
				+ billDeletionRemark + ", payStatus=" + payStatus + ", bmTaxableValue=" + bmTaxableValue
				+ ", liabilityId=" + liabilityId + ", departmentId=" + departmentId + ", vendorId=" + vendorId
				+ ", billTypeId=" + billTypeId + ", fieldId=" + fieldId + ", expenditureDetailList="
				+ expenditureDetailList + ", deductionDetailList=" + deductionDetailList + ", fundId=" + fundId + "]";
	}

    
    
}
