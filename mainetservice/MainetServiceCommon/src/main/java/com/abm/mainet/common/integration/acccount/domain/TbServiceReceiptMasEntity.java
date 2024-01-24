package com.abm.mainet.common.integration.acccount.domain;

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

import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Rahul.Yadav
 * @since 22 Mar 2016
 */
@Entity
@Table(name = "TB_RECEIPT_MAS")
public class TbServiceReceiptMasEntity implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -9038205557221109375L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "RM_RCPTID", precision = 12, scale = 0, nullable = false)
    private long rmRcptid;

    @Column(name = "RM_RCPTNO", precision = 19, scale = 0, nullable = true)
    private Long rmRcptno;

    @Temporal(TemporalType.DATE)
    @Column(name = "RM_DATE", nullable = true)
    private Date rmDate;

    @Column(name = "APM_APPLICATION_ID", precision = 16, scale = 0, nullable = true)
    private Long apmApplicationId;

    @Column(name = "RM_RECEIPTCATEGORY_ID", precision = 12, scale = 0, nullable = true)
    private Long rmReceiptcategoryId;

    @Column(name = "RM_AMOUNT", precision = 12, scale = 2, nullable = true)
    private BigDecimal rmAmount;

    @Column(name = "CM_COLLNID", precision = 12, scale = 0, nullable = true)
    private Long cmCollnid;

    @Column(name = "CU_COUNTERID", precision = 12, scale = 0, nullable = true)
    private Long cuCounterid;

    @Column(name = "RM_RECEIVEDFROM", length = 100, nullable = true)
    private String rmReceivedfrom;

    @Column(name = "RM_NARRATION", length = 200, nullable = true)
    private String rmNarration;

    @Column(name = "RM_COLNCNT_RCPTNO", precision = 12, scale = 0, nullable = true)
    private Long rmColncntRcptno;

    @Column(name = "RM_COUNTER_RCPTNO", precision = 12, scale = 0, nullable = true)
    private Long rmCounterRcptno;

    @Column(name = "SM_SERVICE_ID", precision = 12, scale = 0, nullable = true)
    private Long smServiceId;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;

    // @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = true)
    // private int langId;

    @Column(name = "CREATED_DATE", nullable = true)
    private Date createdDate;

    @Column(name = "UPDATED_BY", nullable = false, updatable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    // @Column(name = "RM_V1", length = 100, nullable = true)
    // private String rmV1;

    /*
     * @Column(name = "RM_V2", length = 100, nullable = true) private String rmV2;
     */

    @Column(name = "PG_REFID", precision = 12, nullable = true)
    private Long pgRefId;

    @Column(name = "RM_V3", length = 100, nullable = true)
    private String rmV3;

    @Column(name = "RM_V4", length = 100, nullable = true)
    private String rmV4;

    @Column(name = "RM_V5", length = 100, nullable = true)
    private String rmV5;

    // @Column(name = "RM_N1", precision = 15, scale = 0, nullable = true)
    // private Long rmN1;

    @Column(name = "RM_DEMAND", precision = 12, scale = 2, nullable = true)
    private BigDecimal rmDemand;

    @Column(name = "RM_N3", precision = 15, scale = 0, nullable = true)
    private Long rmN3;

    @Column(name = "RM_N4", precision = 15, scale = 0, nullable = true)
    private Long rmN4;

    @Column(name = "RM_N5", precision = 15, scale = 0, nullable = true)
    private Long rmN5;

    @Column(name = "RM_D1", nullable = true)
    private Date rmD1;

    @Column(name = "RM_D2", nullable = true)
    private Date rmD2;

    @Column(name = "RM_D3", nullable = true)
    private Date rmD3;

    @Column(name = "RM_LO1", length = 1, nullable = true)
    private String rmLo1;

    @Column(name = "RM_LO2", length = 1, nullable = true)
    private String rmLo2;

    @Column(name = "RM_LO3", length = 1, nullable = true)
    private String rmLo3;

    @Column(name = "RM_LOI_NO", length = 16, nullable = true)
    private String rmLoiNo;

    @Column(name = "COD_DWZID1", precision = 12, scale = 0, nullable = true)
    private Long coddwzId1;

    @Column(name = "COD_DWZID2", precision = 12, scale = 0, nullable = true)
    private Long coddwzId2;

    @Column(name = "COD_DWZID3", precision = 12, scale = 0, nullable = true)
    private Long coddwzId3;

    @Column(name = "COD_DWZID4", precision = 12, scale = 0, nullable = true)
    private Long coddwzId4;

    @Column(name = "COD_DWZID5", precision = 12, scale = 0, nullable = true)
    private Long coddwzId5;

    @Column(name = "DP_DEPTID", precision = 12, scale = 0, nullable = true)
    private Long dpDeptId;

    @Column(name = "MANUALRECEIPTNO", length = 50, nullable = true)
    private String manualReceiptNo;

    @Column(name = "VM_VENDORID", precision = 12, scale = 0, nullable = true)
    private Long vmVendorId;

    @Column(name = "MOBILE_NUMBER", length = 10, nullable = true)
    private String mobileNumber;

    @Column(name = "EMAIL_ID", length = 100, nullable = true)
    private String emailId;

    @Column(name = "RECEIPT_DEL_FLAG", length = 1)
    private String receiptDelFlag;

    @Column(name = "RECEIPT_DEL_DATE")
    private Date receiptDelDate;

    @Column(name = "RECEIPT_DEL_POSTING_DATE")
    private Date receiptDelPostingDate;

    @Column(name = "RECEIPT_DEL_REMARK", length = 500)
    private String receiptDelRemark;

    @Column(name = "REF_ID", precision = 12, scale = 0, nullable = true)
    private Long refId;

    @Column(name = "RECEIPT_TYPE_FLAG", length = 1)
    private String receiptTypeFlag;

    @Column(name = "ADDITIONAL_REF_NO", length = 25, nullable = true)
    private String additionalRefNo;
    
    @Column(name = "CFC_COLCNTR_NO", length =50, nullable = true)
    private String cfcColCenterNo;
    
    @Column(name = "CFC_COUNTER_NO", length =50, nullable = true)
    private String cfcColCounterNo;
    
    @Column(name = "RM_ADDRESS", length = 200, nullable = true)
    private String rmAddress;

	/*
	 * @OneToOne(fetch = FetchType.LAZY, mappedBy = "rmRcptid", cascade =
	 * CascadeType.ALL) private TbSrcptModesDetEntity receiptModeDetail = null;
	 */
    
    @OneToMany(mappedBy = "rmRcptid", targetEntity = TbSrcptModesDetEntity.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<TbSrcptModesDetEntity> receiptModeDetail;

    @OneToMany(mappedBy = "rmRcptid", targetEntity = TbSrcptFeesDetEntity.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<TbSrcptFeesDetEntity> receiptFeeDetail;

    /*
     * @ManyToOne
     * @JoinColumn(name="FIELD_ID", referencedColumnName="FIELD_ID") private AccountFieldMasterEntity tbAcFieldMaster;
     */

    @Column(name = "FIELD_ID")
    private Long fieldId;

    @ManyToOne
    @JoinColumn(name = "CHALLAN_ID", referencedColumnName = "CHALLAN_ID")
    private ChallanMaster challanMaster;

    @Column(name = "RECEIPT_DEL_ORDER_NO")
    private String receipt_del_order_no;

    @Column(name = "RECEIPT_DEL_AUTH_BY")
    private Long receipt_del_auth_by;
    
    @Column(name = "flat_no")
    private String flatNo;
    
    @Column(name = "RM_CFC_CNTRID", precision = 12, scale = 0, nullable = true)
    private Long rmCFfcCntrNo;
     //User Story #147721 for setting POS Payment mode
    @Column(name = "POS_PAY_MODE")
    private String posPayMode;
    
    @Column(name = "POS_PAY_TXNID")
    private String posTxnId;
    
    @Column(name = "gst_No")
    private String gstNo;

    public long getRmRcptid() {
        return rmRcptid;
    }

    public void setRmRcptid(final long rmRcptid) {
        this.rmRcptid = rmRcptid;
    }

    public Long getRmRcptno() {
        return rmRcptno;
    }

    public void setRmRcptno(final Long rmRcptno) {
        this.rmRcptno = rmRcptno;
    }

    public Date getRmDate() {
        return rmDate;
    }

    public void setRmDate(final Date rmDate) {
        this.rmDate = rmDate;
    }

    public Long getRmReceiptcategoryId() {
        return rmReceiptcategoryId;
    }

    public void setRmReceiptcategoryId(final Long rmReceiptcategoryId) {
        this.rmReceiptcategoryId = rmReceiptcategoryId;
    }

    public BigDecimal getRmAmount() {
        return rmAmount;
    }

    public void setRmAmount(final BigDecimal rmAmount) {
        this.rmAmount = rmAmount;
    }

    public Long getCmCollnid() {
        return cmCollnid;
    }

    public void setCmCollnid(final Long cmCollnid) {
        this.cmCollnid = cmCollnid;
    }

    public Long getCuCounterid() {
        return cuCounterid;
    }

    public void setCuCounterid(final Long cuCounterid) {
        this.cuCounterid = cuCounterid;
    }

    public String getRmReceivedfrom() {
        return rmReceivedfrom;
    }

    public void setRmReceivedfrom(final String rmReceivedfrom) {
        this.rmReceivedfrom = rmReceivedfrom;
    }

    public String getRmNarration() {
        return rmNarration;
    }

    public void setRmNarration(final String rmNarration) {
        this.rmNarration = rmNarration;
    }

    public Long getRmColncntRcptno() {
        return rmColncntRcptno;
    }

    public void setRmColncntRcptno(final Long rmColncntRcptno) {
        this.rmColncntRcptno = rmColncntRcptno;
    }

    public Long getRmCounterRcptno() {
        return rmCounterRcptno;
    }

    public void setRmCounterRcptno(final Long rmCounterRcptno) {
        this.rmCounterRcptno = rmCounterRcptno;
    }

    public Long getSmServiceId() {
        return smServiceId;
    }

    public void setSmServiceId(final Long smServiceId) {
        this.smServiceId = smServiceId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    /*
     * public int getLangId() { return langId; } public void setLangId(int langId) { this.langId = langId; }
     */

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getRmLoiNo() {
        return rmLoiNo;
    }

    public void setRmLoiNo(final String rmLoiNo) {
        this.rmLoiNo = rmLoiNo;
    }

	/*
	 * public TbSrcptModesDetEntity getReceiptModeDetail() { return
	 * receiptModeDetail; }
	 * 
	 * public void setReceiptModeDetail(final TbSrcptModesDetEntity
	 * receiptModeDetail) { this.receiptModeDetail = receiptModeDetail; }
	 */
    

    public List<TbSrcptFeesDetEntity> getReceiptFeeDetail() {
        return receiptFeeDetail;
    }

    public List<TbSrcptModesDetEntity> getReceiptModeDetail() {
		return receiptModeDetail;
	}

	public void setReceiptModeDetail(List<TbSrcptModesDetEntity> receiptModeDetail) {
		this.receiptModeDetail = receiptModeDetail;
	}

	public void setReceiptFeeDetail(final List<TbSrcptFeesDetEntity> receiptFeeDetail) {
        this.receiptFeeDetail = receiptFeeDetail;
    }

    public Long getCoddwzId1() {
        return coddwzId1;
    }

    public void setCoddwzId1(final Long coddwzId1) {
        this.coddwzId1 = coddwzId1;
    }

    public Long getCoddwzId2() {
        return coddwzId2;
    }

    public void setCoddwzId2(final Long coddwzId2) {
        this.coddwzId2 = coddwzId2;
    }

    public Long getCoddwzId3() {
        return coddwzId3;
    }

    public void setCoddwzId3(final Long coddwzId3) {
        this.coddwzId3 = coddwzId3;
    }

    public Long getCoddwzId4() {
        return coddwzId4;
    }

    public void setCoddwzId4(final Long coddwzId4) {
        this.coddwzId4 = coddwzId4;
    }

    public Long getCoddwzId5() {
        return coddwzId5;
    }

    public void setCoddwzId5(final Long coddwzId5) {
        this.coddwzId5 = coddwzId5;
    }

    public Long getDpDeptId() {
        return dpDeptId;
    }

    public void setDpDeptId(final Long dpDeptId) {
        this.dpDeptId = dpDeptId;
    }

    /**
     * @return the manualReceiptNo
     */
    public String getManualReceiptNo() {
        return manualReceiptNo;
    }

    /**
     * @param manualReceiptNo the manualReceiptNo to set
     */
    public void setManualReceiptNo(final String manualReceiptNo) {
        this.manualReceiptNo = manualReceiptNo;
    }

    /**
     * @return the vmVendorId
     */
    public Long getVmVendorId() {
        return vmVendorId;
    }

    /**
     * @param vmVendorId the vmVendorId to set
     */
    public void setVmVendorId(final Long vmVendorId) {
        this.vmVendorId = vmVendorId;
    }

    /**
     * @return the mobileNumber
     */
    public String getMobileNumber() {
        return mobileNumber;
    }

    /**
     * @param mobileNumber the mobileNumber to set
     */
    public void setMobileNumber(final String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    /**
     * @return the emailId
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     * @param emailId the emailId to set
     */
    public void setEmailId(final String emailId) {
        this.emailId = emailId;
    }

    // --- DATABASE MAPPING : RECEIPT_DEL_FLAG ( CHAR )
    public void setReceiptDelFlag(final String receiptDelFlag) {
        this.receiptDelFlag = receiptDelFlag;
    }

    public String getReceiptDelFlag() {
        return receiptDelFlag;
    }

    // --- DATABASE MAPPING : RECEIPT_DEL_DATE ( DATE )
    public void setReceiptDelDate(final Date receiptDelDate) {
        this.receiptDelDate = receiptDelDate;
    }

    public Date getReceiptDelDate() {
        return receiptDelDate;
    }

    // --- DATABASE MAPPING : RECEIPT_DEL_POSTING_DATE ( DATE )
    public void setReceiptDelPostingDate(final Date receiptDelPostingDate) {
        this.receiptDelPostingDate = receiptDelPostingDate;
    }

    public Date getReceiptDelPostingDate() {
        return receiptDelPostingDate;
    }

    // --- DATABASE MAPPING : RECEIPT_DEL_REMARK ( VARCHAR2 )
    public void setReceiptDelRemark(final String receiptDelRemark) {
        this.receiptDelRemark = receiptDelRemark;
    }

    public String getReceiptDelRemark() {
        return receiptDelRemark;
    }

    /**
     * @return the tbAcFieldMaster
     */
    /*
     * public AccountFieldMasterEntity getTbAcFieldMaster() { return tbAcFieldMaster; }
     */

    /**
     * @param tbAcFieldMaster the tbAcFieldMaster to set
     */
    /*
     * public void setTbAcFieldMaster(AccountFieldMasterEntity tbAcFieldMaster) { this.tbAcFieldMaster = tbAcFieldMaster; }
     */
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
     * @return the rmV1
     */
    /*
     * public String getRmV1() { return rmV1; }
     *//**
        * @param rmV1 the rmV1 to set
        *//*
           * public void setRmV1(String rmV1) { this.rmV1 = rmV1; }
           */

    /**
     * @return the rmV3
     */
    public String getRmV3() {
        return rmV3;
    }

    public Long getPgRefId() {
        return pgRefId;
    }

    public void setPgRefId(Long pgRefId) {
        this.pgRefId = pgRefId;
    }

    /**
     * @param rmV3 the rmV3 to set
     */
    public void setRmV3(final String rmV3) {
        this.rmV3 = rmV3;
    }

    /**
     * @return the rmV4
     */
    public String getRmV4() {
        return rmV4;
    }

    /**
     * @param rmV4 the rmV4 to set
     */
    public void setRmV4(final String rmV4) {
        this.rmV4 = rmV4;
    }

    /**
     * @return the rmV5
     */
    public String getRmV5() {
        return rmV5;
    }

    /**
     * @param rmV5 the rmV5 to set
     */
    public void setRmV5(final String rmV5) {
        this.rmV5 = rmV5;
    }

    /**
     * @return the rmN1
     */
    /*
     * public Long getRmN1() { return rmN1; }
     *//**
        * @param rmN1 the rmN1 to set
        *//*
           * public void setRmN1(Long rmN1) { this.rmN1 = rmN1; }
           */

    /**
     * @return the rmN3
     */
    public Long getRmN3() {
        return rmN3;
    }

    /**
     * @param rmN3 the rmN3 to set
     */
    public void setRmN3(final Long rmN3) {
        this.rmN3 = rmN3;
    }

    /**
     * @return the rmN4
     */
    public Long getRmN4() {
        return rmN4;
    }

    /**
     * @param rmN4 the rmN4 to set
     */
    public void setRmN4(final Long rmN4) {
        this.rmN4 = rmN4;
    }

    /**
     * @return the rmN5
     */
    public Long getRmN5() {
        return rmN5;
    }

    /**
     * @param rmN5 the rmN5 to set
     */
    public void setRmN5(final Long rmN5) {
        this.rmN5 = rmN5;
    }

    /**
     * @return the rmD1
     */
    public Date getRmD1() {
        return rmD1;
    }

    /**
     * @param rmD1 the rmD1 to set
     */
    public void setRmD1(final Date rmD1) {
        this.rmD1 = rmD1;
    }

    /**
     * @return the rmD2
     */
    public Date getRmD2() {
        return rmD2;
    }

    /**
     * @param rmD2 the rmD2 to set
     */
    public void setRmD2(final Date rmD2) {
        this.rmD2 = rmD2;
    }

    /**
     * @return the rmD3
     */
    public Date getRmD3() {
        return rmD3;
    }

    /**
     * @param rmD3 the rmD3 to set
     */
    public void setRmD3(final Date rmD3) {
        this.rmD3 = rmD3;
    }

    /**
     * @return the rmLo1
     */
    public String getRmLo1() {
        return rmLo1;
    }

    /**
     * @param rmLo1 the rmLo1 to set
     */
    public void setRmLo1(final String rmLo1) {
        this.rmLo1 = rmLo1;
    }

    /**
     * @return the rmLo2
     */
    public String getRmLo2() {
        return rmLo2;
    }

    /**
     * @param rmLo2 the rmLo2 to set
     */
    public void setRmLo2(final String rmLo2) {
        this.rmLo2 = rmLo2;
    }

    /**
     * @return the rmLo3
     */
    public String getRmLo3() {
        return rmLo3;
    }

    /**
     * @param rmLo3 the rmLo3 to set
     */
    public void setRmLo3(final String rmLo3) {
        this.rmLo3 = rmLo3;
    }

    /**
     * @return the challanMaster
     */
    public ChallanMaster getChallanMaster() {
        return challanMaster;
    }

    /**
     * @param challanMaster the challanMaster to set
     */
    public void setChallanMaster(final ChallanMaster challanMaster) {
        this.challanMaster = challanMaster;
    }

    /**
     * @return the apmApplicationId
     */
    public Long getApmApplicationId() {
        return apmApplicationId;
    }

    /**
     * @param apmApplicationId the apmApplicationId to set
     */
    public void setApmApplicationId(final Long apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    /**
     * @return the refId
     */
    public Long getRefId() {
        return refId;
    }

    /**
     * @param refId the refId to set
     */
    public void setRefId(final Long refId) {
        this.refId = refId;
    }

    /**
     * @return the receiptTypeFlag
     */
    public String getReceiptTypeFlag() {
        return receiptTypeFlag;
    }

    /**
     * @param receiptTypeFlag the receiptTypeFlag to set
     */
    public void setReceiptTypeFlag(final String receiptTypeFlag) {
        this.receiptTypeFlag = receiptTypeFlag;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(final Long fieldId) {
        this.fieldId = fieldId;
    }

    public String getAdditionalRefNo() {
        return additionalRefNo;
    }

    public void setAdditionalRefNo(final String additionalRefNo) {
        this.additionalRefNo = additionalRefNo;
    }

    public String getReceipt_del_order_no() {
        return receipt_del_order_no;
    }

    public void setReceipt_del_order_no(final String receipt_del_order_no) {
        this.receipt_del_order_no = receipt_del_order_no;
    }

    public Long getReceipt_del_auth_by() {
        return receipt_del_auth_by;
    }

    public void setReceipt_del_auth_by(final Long receipt_del_auth_by) {
        this.receipt_del_auth_by = receipt_del_auth_by;
    }

    public BigDecimal getRmDemand() {
        return rmDemand;
    }

    public void setRmDemand(BigDecimal rmDemand) {
        this.rmDemand = rmDemand;
    }

    
    public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}

	public Long getRmCFfcCntrNo() {
		return rmCFfcCntrNo;
	}

	public void setRmCFfcCntrNo(Long rmCFfcCntrNo) {
		this.rmCFfcCntrNo = rmCFfcCntrNo;
	}

	public String getCfcColCenterNo() {
		return cfcColCenterNo;
	}

	public void setCfcColCenterNo(String cfcColCenterNo) {
		this.cfcColCenterNo = cfcColCenterNo;
	}


	public String getCfcColCounterNo() {
		return cfcColCounterNo;
	}

	public void setCfcColCounterNo(String cfcColCounterNo) {
		this.cfcColCounterNo = cfcColCounterNo;
	}

	public String getPosPayMode() {
		return posPayMode;
	}

	public void setPosPayMode(String posPayMode) {
		this.posPayMode = posPayMode;
	}

	public String getPosTxnId() {
		return posTxnId;
	}

	public void setPosTxnId(String posTxnId) {
		this.posTxnId = posTxnId;
	}

	public String getRmAddress() {
		return rmAddress;
	}

	public void setRmAddress(String rmAddress) {
		this.rmAddress = rmAddress;
	}

	public String[] getPkValues() {
        return new String[] { "AC", "TB_RECEIPT_MAS", "RM_RCPTID" };
    }

    public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	@Override
    public String toString() {
        return "TbServiceReceiptMasEntity [rmRcptid=" + rmRcptid + ", rmRcptno=" + rmRcptno + ", rmDate=" + rmDate
                + ", apmApplicationId=" + apmApplicationId + ", rmReceiptcategoryId=" + rmReceiptcategoryId
                + ", rmAmount=" + rmAmount + ", cmCollnid=" + cmCollnid + ", cuCounterid=" + cuCounterid
                + ", rmReceivedfrom=" + rmReceivedfrom + ", rmNarration=" + rmNarration + ", rmColncntRcptno="
                + rmColncntRcptno + ", rmCounterRcptno=" + rmCounterRcptno + ", smServiceId=" + smServiceId + ", orgId="
                + orgId + ", createdBy=" + createdBy + ", createdDate=" + createdDate
                + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd="
                + lgIpMacUpd + ", pgRefId=" + pgRefId + ", rmV3=" + rmV3 + ", rmV4=" + rmV4 + ", rmV5=" + rmV5 + ", rmDemand="
                + rmDemand + ", rmN3=" + rmN3 + ", rmN4=" + rmN4 + ", rmN5=" + rmN5 + ", rmD1=" + rmD1 + ", rmD2=" + rmD2
                + ", rmD3=" + rmD3 + ", rmLo1=" + rmLo1 + ", rmLo2=" + rmLo2 + ", rmLo3=" + rmLo3 + ", rmLoiNo="
                + rmLoiNo + ", coddwzId1=" + coddwzId1 + ", coddwzId2=" + coddwzId2 + ", coddwzId3=" + coddwzId3
                + ", coddwzId4=" + coddwzId4 + ", coddwzId5=" + coddwzId5 + ", dpDeptId=" + dpDeptId
                + ", manualReceiptNo=" + manualReceiptNo + ", vmVendorId=" + vmVendorId + ", mobileNumber="
                + mobileNumber + ", emailId=" + emailId + ", receiptDelFlag=" + receiptDelFlag + ", receiptDelDate="
                + receiptDelDate + ", receiptDelPostingDate=" + receiptDelPostingDate + ", receiptDelRemark="
                + receiptDelRemark + ", refId=" + refId + ", receiptTypeFlag=" + receiptTypeFlag + ", additionalRefNo="
                + additionalRefNo + ", receiptModeDetail=" + receiptModeDetail + ", receiptFeeDetail="
                + receiptFeeDetail + ", fieldId=" + fieldId + ", challanMaster=" + challanMaster
                + ", receipt_del_order_no=" + receipt_del_order_no + ", receipt_del_auth_by=" + receipt_del_auth_by + ", cfcColCenterNo=" + cfcColCenterNo
                + ", cfcColCounterNo=" + cfcColCounterNo
                + "]";
    }

}