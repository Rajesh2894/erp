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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Ajay.Kumar
 *
 */
@Entity
@Table(name = "TB_AC_LNMST")
public class AccountLoanMasterEntity implements Serializable {
	private static final long serialVersionUID = 1302235418895250926L;
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "LN_LOANID", unique = true, nullable = false)
	private Long loanId;
	
	@Column(name = "LN_NO", length = 50 , nullable = true)
	private String lnNo;

	@Column(name = "LN_DEPTNAME", length = 50, nullable = false)
	private String lnDeptname;

	@Column(name = "LN_PURPOSE", length = 50, nullable = false)
	private String lnPurpose;

	@Column(name = "RES_NO", length = 12, nullable = true)
	private Long resNo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RES_DT", nullable = true)
	private Date resDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SANCTION_DT", nullable = true)
	private Date sanctionDate;

	@Column(name = "SANCTION_AMT", precision = 12, scale = 2, nullable = false)
	private BigDecimal santionAmount;

	@Column(name = "LN_INTRATE", precision = 16, scale = 2, nullable = false)
	private BigDecimal lnInrate;

	@Column(name = "INST_COUNT", precision = 2, scale = 0, nullable = false)
	private Long instCount;

	@Column(name = "INST_FREQ", precision = 7, scale = 0, nullable = false)
	private Long instFreq;

	@Column(name = "INST_AMT", precision = 12, scale = 2, nullable = false)
	private Long instAmt;

	@Column(name = "AUTH_BY", precision = 12, scale = 0, nullable = false)
	private Long authBy;

	@Column(name = "LN_REMARK", length = 100, nullable = true)
	private String lmRemark;

	@Column(name = "LN_STATUS", length = 1, nullable = false)
	private char lnStatus;

	@Column(name = "LANG_ID", precision = 2, scale = 0, nullable = false)
	private Long langId;

	@Column(name = "ORGID", nullable = false, updatable = false)
	private Long orgId;

	@Column(name = "CREATED_BY", precision = 7, scale = 0, nullable = false)
	private Long createdBy;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "UPDATED_BY", nullable = false, updatable = true)
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", length = 100, nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	private String lgIpMacUpd;

	// adding new columns

	@Column(name = "LOAN_NAME", length = 50, nullable = true)
	private String loanName;
	
	@Column(name = "LN_PERIOD_UNIT", length = 50, nullable = true)
	private String loanPeriodUnit;
	
	@Column(name = "SANCTION_NO", length = 20, nullable = true)
	private String SanctionNo;

	@Column(name = "VENDORID", nullable = true)
	private Long vendorId;

	@Column(name = "NO_OF_INSTALLMENTS", nullable = true)
	private Long noOfInstallments;

	@Column(name = "LOAN_PERIOD", nullable = true)
	private Long loanPeriod;
	
	

	public String getLoanName() {
		return loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	public String getSanctionNo() {
		return SanctionNo;
	}

	public void setSanctionNo(String sanctionNo) {
		SanctionNo = sanctionNo;
	}

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public Long getNoOfInstallments() {
		return noOfInstallments;
	}

	public void setNoOfInstallments(Long noOfInstallments) {
		this.noOfInstallments = noOfInstallments;
	}

	public Long getLoanPeriod() {
		return loanPeriod;
	}

	public void setLoanPeriod(Long loanPeriod) {
		this.loanPeriod = loanPeriod;
	}

	@OneToMany(mappedBy = "lnmas", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<AccountLoanDetEntity> accountLoanDetList;

	public static String[] getPkValues() {
		return new String[] { "AC", "TB_AC_LNMST", "LN_LOANID" };
	}

	/**
	 * @return loanId
	 */
	
	
	
	public Long getLoanId() {
		return loanId;
	}

	public String getLoanPeriodUnit() {
		return loanPeriodUnit;
	}

	public void setLoanPeriodUnit(String loanPeriodUnit) {
		this.loanPeriodUnit = loanPeriodUnit;
	}

	public String getLnNo() {
		return lnNo;
	}

	public void setLnNo(String lnNo) {
		this.lnNo = lnNo;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public String getLnDeptname() {
		return lnDeptname;
	}

	public void setLnDeptname(String lnDeptname) {
		this.lnDeptname = lnDeptname;
	}

	public String getLnPurpose() {
		return lnPurpose;
	}

	public void setLnPurpose(String lnPurpose) {
		this.lnPurpose = lnPurpose;
	}

	public Long getResNo() {
		return resNo;
	}

	public void setResNo(Long resNo) {
		this.resNo = resNo;
	}

	public Date getResDate() {
		return resDate;
	}

	public void setResDate(Date resDate) {
		this.resDate = resDate;
	}

	public Date getSanctionDate() {
		return sanctionDate;
	}

	public void setSanctionDate(Date sanctionDate) {
		this.sanctionDate = sanctionDate;
	}

	public BigDecimal getSantionAmount() {
		return santionAmount;
	}

	public void setSantionAmount(BigDecimal santionAmount) {
		this.santionAmount = santionAmount;
	}


	public BigDecimal getLnInrate() {
		return lnInrate;
	}

	public void setLnInrate(BigDecimal lnInrate) {
		this.lnInrate = lnInrate;
	}

	public Long getInstCount() {
		return instCount;
	}

	public void setInstCount(Long instCount) {
		this.instCount = instCount;
	}

	public Long getInstFreq() {
		return instFreq;
	}

	public void setInstFreq(Long instFreq) {
		this.instFreq = instFreq;
	}

	public Long getInstAmt() {
		return instAmt;
	}

	public void setInstAmt(Long instAmt) {
		this.instAmt = instAmt;
	}

	public Long getAuthBy() {
		return authBy;
	}

	public void setAuthBy(Long authBy) {
		this.authBy = authBy;
	}

	public String getLmRemark() {
		return lmRemark;
	}

	public void setLmRemark(String lmRemark) {
		this.lmRemark = lmRemark;
	}

	public char getLnStatus() {
		return lnStatus;
	}

	public void setLnStatus(char lnStatus) {
		this.lnStatus = lnStatus;
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

	public List<AccountLoanDetEntity> getAccountLoanDetList() {
		return accountLoanDetList;
	}

	public void setAccountLoanDetList(List<AccountLoanDetEntity> accountLoanDetList) {
		this.accountLoanDetList = accountLoanDetList;
	}

}
