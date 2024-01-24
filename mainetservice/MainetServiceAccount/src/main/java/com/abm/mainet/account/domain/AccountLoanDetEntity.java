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
import org.hibernate.annotations.GenericGenerator;

/**
 * @author Ajay.Kumar
 *
 */
@Entity
@Table(name = "TB_AC_LNDET")
public class AccountLoanDetEntity implements Serializable {
	private static final long serialVersionUID = 5114659852323391035L;
	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "LNDET_ID", unique = true, nullable = false)
	private Long lnDetId;
	
	@ManyToOne
	@JoinColumn(name = "LN_LOANID", nullable = false)
	private AccountLoanMasterEntity lnmas;

	@Column(name = "INST_SEQNO", precision = 2, scale = 0, nullable = false)
	private Long instSeqno;

	@Column(name = "PRNPAL_AMT", precision = 12, scale = 2, nullable = false)
	private BigDecimal prnpalAmount;

	@Column(name = "INT_AMT", precision = 12, scale = 2, nullable = false)
	private BigDecimal intAmount;

	@Column(name = "INST_DUEDT", precision = 12, scale = 0, nullable = false)
	private Date instDueDate;

	@Column(name = "BAL_PRNPALAMT", precision = 12, scale = 2, nullable = false)
	private BigDecimal balPrnpalamt;

	@Column(name = "BAL_INTAMT", precision = 12, scale = 2, nullable = false)
	private Long balIntAmt;

	@Column(name = "INST_STATUS", length = 1, nullable = false)
	private String instStatus;

	@Column(name = "LANG_ID", precision = 2, scale = 0, nullable = false)
	private Long langId;

	@Column(name = "ORGID", precision = 20, scale = 0, nullable = false)
	private Long orgId;

	@Column(name = "CREATED_BY", precision = 20, scale = 0, nullable = false)
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

	public static String[] getPkValues() {
		return new String[] { "AC", "TB_AC_LNDET", "LNDET_ID" };
	}

	/**
	 * @return lnDetId
	 */
	public Long getLnDetId() {
		return lnDetId;
	}

	public void setLnDetId(Long lnDetId) {
		this.lnDetId = lnDetId;
	}

	public AccountLoanMasterEntity getLnmas() {
		return lnmas;
	}

	public void setLnmas(AccountLoanMasterEntity lnmas) {
		this.lnmas = lnmas;
	}

	public Long getInstSeqno() {
		return instSeqno;
	}

	public void setInstSeqno(Long instSeqno) {
		this.instSeqno = instSeqno;
	}

	public BigDecimal getPrnpalAmount() {
		return prnpalAmount;
	}

	public void setPrnpalAmount(BigDecimal prnpalAmount) {
		this.prnpalAmount = prnpalAmount;
	}

	public BigDecimal getIntAmount() {
		return intAmount;
	}

	public void setIntAmount(BigDecimal intAmount) {
		this.intAmount = intAmount;
	}

	public Date getInstDueDate() {
		return instDueDate;
	}

	public void setInstDueDate(Date instDueDate) {
		this.instDueDate = instDueDate;
	}

	public BigDecimal getBalPrnpalamt() {
		return balPrnpalamt;
	}

	public void setBalPrnpalamt(BigDecimal balPrnpalamt) {
		this.balPrnpalamt = balPrnpalamt;
	}

	public Long getBalIntAmt() {
		return balIntAmt;
	}

	public void setBalIntAmt(Long balIntAmt) {
		this.balIntAmt = balIntAmt;
	}

	public String getInstStatus() {
		return instStatus;
	}

	public void setInstStatus(String instStatus) {
		this.instStatus = instStatus;
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

}
