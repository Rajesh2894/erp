/**
 * 
 */
package com.abm.mainet.sfac.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author pooja.maske
 *
 */

@Entity
@Table(name = "Tb_Sfac_Fpo_Profile_Mgmt_Mast")
public class FPOProfileManagemntMaster implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6925900306527732284L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "FPM_ID", nullable = false)
	private Long fpmId;

	@Column(name = "OVERALL_TURNOVER")
	private BigDecimal overallTurnOver;

	@Column(name = "ADD_SHARES_ISSUED")
	private BigDecimal additionalSharesIssued;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_ISSUED")
	private Date dateIssued;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Column(name = "LG_IP_MAC", nullable = false, length = 100)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100)
	private String lgIpMacUpd;

	/**
	 * @return the fpmId
	 */
	public Long getFpmId() {
		return fpmId;
	}

	/**
	 * @param fpmId the fpmId to set
	 */
	public void setFpmId(Long fpmId) {
		this.fpmId = fpmId;
	}

	/**
	 * @return the overallTurnOver
	 */
	public BigDecimal getOverallTurnOver() {
		return overallTurnOver;
	}

	/**
	 * @param overallTurnOver the overallTurnOver to set
	 */
	public void setOverallTurnOver(BigDecimal overallTurnOver) {
		this.overallTurnOver = overallTurnOver;
	}

	/**
	 * @return the additionalSharesIssued
	 */
	public BigDecimal getAdditionalSharesIssued() {
		return additionalSharesIssued;
	}

	/**
	 * @param additionalSharesIssued the additionalSharesIssued to set
	 */
	public void setAdditionalSharesIssued(BigDecimal additionalSharesIssued) {
		this.additionalSharesIssued = additionalSharesIssued;
	}

	/**
	 * @return the dateIssued
	 */
	public Date getDateIssued() {
		return dateIssued;
	}

	/**
	 * @param dateIssued the dateIssued to set
	 */
	public void setDateIssued(Date dateIssued) {
		this.dateIssued = dateIssued;
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
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
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
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
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
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
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
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
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
	public void setLgIpMac(String lgIpMac) {
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
	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	public String[] getPkValues() {
		return new String[] { "SFAC", "Tb_Sfac_Fpo_Profile_Mgmt_Mast", "FPM_ID" };
	}

}
