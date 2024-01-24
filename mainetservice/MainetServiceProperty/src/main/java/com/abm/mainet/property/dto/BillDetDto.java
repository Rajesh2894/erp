package com.abm.mainet.property.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BillDetDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6320165839727079408L;

	private long bdBilldetid;
	
	private BillMasDto bmIdno;
	
	private Long taxId;
	
	private Long rebateId;

	private Long adjustmentId;

	private BigDecimal bdCurTaxamt;
	
	private BigDecimal bdCurBalTaxamt;

	private BigDecimal bdCurBalArramt;	

	private BigDecimal bdCurArramt;

	private BigDecimal bdPrvArramt;

	private BigDecimal bdCurTaxamtPrint;

	private BigDecimal bdPrvBalArramt;
	
	private String bdBillflag;
	
	private Long orgId;

	private Long createdBy;

	private Date createdDate;

	private Long updatedBy;

	private Date updatedDate;
	
	private String lgIpMac;

	private String lgIpMacUpd;
	
	private Long taxCategory;

	private Long collSeq;

	public long getBdBilldetid() {
		return bdBilldetid;
	}

	public void setBdBilldetid(long bdBilldetid) {
		this.bdBilldetid = bdBilldetid;
	}

	public BillMasDto getBmIdno() {
		return bmIdno;
	}

	public void setBmIdno(BillMasDto bmIdno) {
		this.bmIdno = bmIdno;
	}

	public Long getTaxId() {
		return taxId;
	}

	public void setTaxId(Long taxId) {
		this.taxId = taxId;
	}

	public Long getRebateId() {
		return rebateId;
	}

	public void setRebateId(Long rebateId) {
		this.rebateId = rebateId;
	}

	public Long getAdjustmentId() {
		return adjustmentId;
	}

	public void setAdjustmentId(Long adjustmentId) {
		this.adjustmentId = adjustmentId;
	}

	public BigDecimal getBdCurTaxamt() {
		return bdCurTaxamt;
	}

	public void setBdCurTaxamt(BigDecimal bdCurTaxamt) {
		this.bdCurTaxamt = bdCurTaxamt;
	}

	public BigDecimal getBdCurBalTaxamt() {
		return bdCurBalTaxamt;
	}

	public void setBdCurBalTaxamt(BigDecimal bdCurBalTaxamt) {
		this.bdCurBalTaxamt = bdCurBalTaxamt;
	}

	public BigDecimal getBdCurBalArramt() {
		return bdCurBalArramt;
	}

	public void setBdCurBalArramt(BigDecimal bdCurBalArramt) {
		this.bdCurBalArramt = bdCurBalArramt;
	}

	public BigDecimal getBdCurArramt() {
		return bdCurArramt;
	}

	public void setBdCurArramt(BigDecimal bdCurArramt) {
		this.bdCurArramt = bdCurArramt;
	}

	public BigDecimal getBdPrvArramt() {
		return bdPrvArramt;
	}

	public void setBdPrvArramt(BigDecimal bdPrvArramt) {
		this.bdPrvArramt = bdPrvArramt;
	}

	public BigDecimal getBdCurTaxamtPrint() {
		return bdCurTaxamtPrint;
	}

	public void setBdCurTaxamtPrint(BigDecimal bdCurTaxamtPrint) {
		this.bdCurTaxamtPrint = bdCurTaxamtPrint;
	}

	public BigDecimal getBdPrvBalArramt() {
		return bdPrvBalArramt;
	}

	public void setBdPrvBalArramt(BigDecimal bdPrvBalArramt) {
		this.bdPrvBalArramt = bdPrvBalArramt;
	}

	public String getBdBillflag() {
		return bdBillflag;
	}

	public void setBdBillflag(String bdBillflag) {
		this.bdBillflag = bdBillflag;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getLgIpMac() {
		return lgIpMac;
	}

	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	public Long getTaxCategory() {
		return taxCategory;
	}

	public void setTaxCategory(Long taxCategory) {
		this.taxCategory = taxCategory;
	}

	public Long getCollSeq() {
		return collSeq;
	}

	public void setCollSeq(Long collSeq) {
		this.collSeq = collSeq;
	}

}
