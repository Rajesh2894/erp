package com.abm.mainet.account.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_AC_INVMST_HIST")
public class AccountInvestmentMasterHistEntity implements Serializable {
	private static final long serialVersionUID = -2255533555338259593L;
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "H_IN_INVID", precision = 12, scale = 0, nullable = false)
	private Long invsthId;

	@Column(name = "IN_INVID", length = 12, scale = 0, nullable = false)
	private Long invstId;

	@Column(name = "IN_INVNO", length = 10, nullable = false)
	private String invstNo;

	@Column(name = "IN_INVTYPE", precision = 7, scale = 0, nullable = false)
	private Long invstType;

	@Column(name = "IN_BANKID", precision = 7, scale = 0, nullable = false)
	private Long bankId;

	@Column(name = "IN_FDRNO", length = 20, nullable = false)
	private String inFdrNo;

	@Column(name = "IN_INVDT", nullable = true)
	private Date invstDate;

	@Column(name = "IN_INVAMT", precision = 12, scale = 2, nullable = false)
	private BigDecimal invstAmount;

	@Column(name = "IN_DUEDATE", nullable = true)
	private Date invstDueDate;

	@Column(name = "IN_INTRATE", precision = 12, scale = 2, nullable = false)
	private BigDecimal instRate;

	@Column(name = "IN_INTAMT", precision = 12, scale = 2, nullable = false)
	private BigDecimal instAmt;

	@Column(name = "IN_MATAMT", precision = 12, scale = 2, nullable = false)
	private BigDecimal maturityAmt;

	@Column(name = "IN_RESNO", precision = 12, scale = 0, nullable = false)
	private BigDecimal resNo;

	@Column(name = "IN_RESDT", nullable = true)
	private Date resDate;

	@Column(name = "FUND_ID", precision = 7, scale = 0, nullable = false)
	private Long fundId;

	@Column(name = "REMARKS", precision = 7, scale = 0, nullable = false)
	private Long remarks;

	@Column(name = "IN_STATUS", length = 1, nullable = false)
	private String status;

	@Column(name = "CLOS_DT", nullable = true)
	private Date closDate;

	@Column(name = "ORG_PRINCIPAL_AMT",precision = 12, scale = 2,  nullable = true)
	private BigDecimal originalPrincipalAmt;
	
	@Column(name = "NO_TIMES_RENEWAL")
	private Long noOfTimesRenewal;
	
	@Column(name = "LANG_ID", precision = 2, scale = 0, nullable = false)
	private Long langId;

	@Column(name = "ORGID", precision = 20, scale = 0, nullable = false)
	private Long orgId;

	@Column(name = "CREATED_BY", precision = 7, scale = 0, nullable = false)
	private Long createdBy;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "UPDATED_BY", nullable = false, updatable = true)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", length = 100, nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	private String lgIpMacUpd;

	@Column(name = "H_STATUS", length = 2)
	private Character hStatus;

	public static String[] getPkValues() {
		return new String[] { "AC", "TB_AC_INVMST_HIST", "H_IN_INVID" };
	}

	public Long getInvsthId() {
		return invsthId;
	}

	public void setInvsthId(Long invsthId) {
		this.invsthId = invsthId;
	}

	public Long getInvstId() {
		return invstId;
	}

	public void setInvstId(Long invstId) {
		this.invstId = invstId;
	}

	public String getInvstNo() {
		return invstNo;
	}

	public void setInvstNo(String invstNo) {
		this.invstNo = invstNo;
	}

	public Long getInvstType() {
		return invstType;
	}

	public void setInvstType(Long invstType) {
		this.invstType = invstType;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getInFdrNo() {
		return inFdrNo;
	}

	public void setInFdrNo(String inFdrNo) {
		this.inFdrNo = inFdrNo;
	}

	public Date getInvstDate() {
		return invstDate;
	}

	public void setInvstDate(Date invstDate) {
		this.invstDate = invstDate;
	}

	public BigDecimal getInvstAmount() {
		return invstAmount;
	}

	public void setInvstAmount(BigDecimal invstAmount) {
		this.invstAmount = invstAmount;
	}

	public Date getInvstDueDate() {
		return invstDueDate;
	}

	public void setInvstDueDate(Date invstDueDate) {
		this.invstDueDate = invstDueDate;
	}

	public BigDecimal getInstRate() {
		return instRate;
	}

	public void setInstRate(BigDecimal instRate) {
		this.instRate = instRate;
	}

	public BigDecimal getInstAmt() {
		return instAmt;
	}

	public void setInstAmt(BigDecimal instAmt) {
		this.instAmt = instAmt;
	}

	public BigDecimal getMaturityAmt() {
		return maturityAmt;
	}

	public void setMaturityAmt(BigDecimal maturityAmt) {
		this.maturityAmt = maturityAmt;
	}

	public BigDecimal getResNo() {
		return resNo;
	}

	public void setResNo(BigDecimal resNo) {
		this.resNo = resNo;
	}

	public Date getResDate() {
		return resDate;
	}

	public void setResDate(Date resDate) {
		this.resDate = resDate;
	}

	public Long getFundId() {
		return fundId;
	}

	public void setFundId(Long fundId) {
		this.fundId = fundId;
	}

	public Long getRemarks() {
		return remarks;
	}

	public void setRemarks(Long remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getClosDate() {
		return closDate;
	}

	public void setClosDate(Date closDate) {
		this.closDate = closDate;
	}

	public Long getLangId() {
		return langId;
	}

	public void setLangId(Long langId) {
		this.langId = langId;
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

	public Character gethStatus() {
		return hStatus;
	}

	public void sethStatus(Character hStatus) {
		this.hStatus = hStatus;
	}

	public BigDecimal getOriginalPrincipalAmt() {
		return originalPrincipalAmt;
	}

	public void setOriginalPrincipalAmt(BigDecimal originalPrincipalAmt) {
		this.originalPrincipalAmt = originalPrincipalAmt;
	}

	public Long getNoOfTimesRenewal() {
		return noOfTimesRenewal;
	}

	public void setNoOfTimesRenewal(Long noOfTimesRenewal) {
		this.noOfTimesRenewal = noOfTimesRenewal;
	}

	
}
