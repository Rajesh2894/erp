package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.constant.MainetConstants;

/**
 * @author apurva.salgaonkar
 * @since 19 Jan 2017
 */
@Entity
@Table(name = "TB_CONTRACT_INSTALMENT_DETAIL")
public class ContractInstalmentDetailEntity implements Serializable {
	private static final long serialVersionUID = 8502674303697583964L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "CONIT_ID", precision = 12, scale = 0, nullable = false)
	private long conitId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONT_ID", nullable = false)
	private ContractMastEntity contId;

	@Column(name = "CONIT_AMT_TYPE", precision = 12, scale = 0, nullable = false)
	private Long conitAmtType;

	@Column(name = "CONIT_VALUE", precision = 15, scale = 2, nullable = false)
	private Double conitValue;

	@Column(name = "CONIT_DUE_DATE", nullable = false)
	private Date conitDueDate;

	@Column(name = "CONIT_MILESTONE", length = 500, nullable = true)
	private String conitMilestone;

	@Column(name = "CONTT_ACTIVE", length = 1, nullable = false)
	private String conttActive;

	@Column(name = "CONIT_PR_FLAG", length = 1, nullable = true)
	private String conitPrFlag;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "CREATED_BY", precision = 12, scale = 0, nullable = false)
	private Long createdBy;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date lmodDate;

	@Column(name = "UPDATED_BY", nullable = false)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", length = 100, nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	private String lgIpMacUpd;

	@Column(name = "CONIT_AMT", precision = 15, scale = 2, nullable = false)
	private Double conitAmount;

	@Column(name = "TAX_ID", nullable = false)
	private Long taxId;

	@Column(name = "CONIT_START_DATE", nullable = false)
	private Date conitStartDate;
	
	@Column(name = "REMARK", nullable = true)
	private String remark;

	public String[] getPkValues() {
		return new String[] { MainetConstants.CommonConstants.COM, MainetConstants.RnLDetailEntity.TB_CONTRACT_DETAIL,"CONIT_ID" };
	}

	/**
	 * @return the conitId
	 */
	public long getConitId() {
		return conitId;
	}

	/**
	 * @param conitId
	 *            the conitId to set
	 */
	public void setConitId(final long conitId) {
		this.conitId = conitId;
	}

	/**
	 * @return the contId
	 */
	public ContractMastEntity getContId() {
		return contId;
	}

	/**
	 * @param contId
	 *            the contId to set
	 */
	public void setContId(final ContractMastEntity contId) {
		this.contId = contId;
	}

	/**
	 * @return the conitAmtType
	 */
	public Long getConitAmtType() {
		return conitAmtType;
	}

	/**
	 * @param conitAmtType
	 *            the conitAmtType to set
	 */
	public void setConitAmtType(final Long conitAmtType) {
		this.conitAmtType = conitAmtType;
	}

	/**
	 * @return the conitValue
	 */
	public Double getConitValue() {
		return conitValue;
	}

	/**
	 * @param conitValue
	 *            the conitValue to set
	 */
	public void setConitValue(final Double conitValue) {
		this.conitValue = conitValue;
	}

	/**
	 * @return the conitMilestone
	 */
	public String getConitMilestone() {
		return conitMilestone;
	}

	/**
	 * @param conitMilestone
	 *            the conitMilestone to set
	 */
	public void setConitMilestone(final String conitMilestone) {
		this.conitMilestone = conitMilestone;
	}

	/**
	 * @return the conttActive
	 */
	public String getConttActive() {
		return conttActive;
	}

	/**
	 * @param conttActive
	 *            the conttActive to set
	 */
	public void setConttActive(final String conttActive) {
		this.conttActive = conttActive;
	}

	/**
	 * @return the conitPrFlag
	 */
	public String getConitPrFlag() {
		return conitPrFlag;
	}

	/**
	 * @param conitPrFlag
	 *            the conitPrFlag to set
	 */
	public void setConitPrFlag(final String conitPrFlag) {
		this.conitPrFlag = conitPrFlag;
	}

	/**
	 * @return the orgId
	 */
	public Long getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId
	 *            the orgId to set
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
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(final Long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the lmodDate
	 */
	public Date getLmodDate() {
		return lmodDate;
	}

	/**
	 * @param lmodDate
	 *            the lmodDate to set
	 */
	public void setLmodDate(final Date lmodDate) {
		this.lmodDate = lmodDate;
	}

	/**
	 * @return the updatedBy
	 */
	public Long getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy
	 *            the updatedBy to set
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
	 * @param updatedDate
	 *            the updatedDate to set
	 */
	public void setUpdatedDate(final Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the lgIpMac
	 */
	public String getLgIpMac() {
		return lgIpMac;
	}

	/**
	 * @param lgIpMac
	 *            the lgIpMac to set
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
	 * @param lgIpMacUpd
	 *            the lgIpMacUpd to set
	 */
	public void setLgIpMacUpd(final String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	public Double getConitAmount() {
		return conitAmount;
	}

	public void setConitAmount(final Double conitAmount) {
		this.conitAmount = conitAmount;
	}

	public Long getTaxId() {
		return taxId;
	}

	public void setTaxId(final Long taxId) {
		this.taxId = taxId;
	}

	public Date getConitDueDate() {
		return conitDueDate;
	}

	public void setConitDueDate(final Date conitDueDate) {
		this.conitDueDate = conitDueDate;
	}

	public Date getConitStartDate() {
		return conitStartDate;
	}

	public void setConitStartDate(final Date conitStartDate) {
		this.conitStartDate = conitStartDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}