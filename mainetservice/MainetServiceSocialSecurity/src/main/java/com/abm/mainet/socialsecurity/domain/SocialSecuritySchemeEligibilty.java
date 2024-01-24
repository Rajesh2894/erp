/**
 * 
 */
package com.abm.mainet.socialsecurity.domain;

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
 * @author satish.rathore
 *
 */

@Entity
@Table(name = "TB_SWD_SCHEME_ElIGIBILITY")
public class SocialSecuritySchemeEligibilty implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8965922995476353053L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "SDSCHE_ID", nullable = false)
	private Long schmeEligibiltyId;

	@ManyToOne
	@JoinColumn(name = "SDSCH_ID", nullable = false)
	private SocialSecuritySchemeMaster sosecuritySchmMasEligibility;

	@Column(name = "SDSCHE_FACT_APL", nullable = false)
	private Long factorApplicableId;

	@Column(name = "SDSCHE_CRITERIA", nullable = false)
	private Long criteriaId;

	@Column(name = "SDSCHE_RENGFROM", nullable = false)
	private String rangeFrom;

	@Column(name = "SDSCHE_RENGTO", nullable = false)
	private String rangeTo;

	@Column(name = "SDSCHE_GRID", nullable = false)
	private Long groupID;

	@Column(name = "SDSCHE_PAYSCH", nullable = false)
	private Long paySchedule;

	@Column(name = "SDSCHE_AMT", nullable = false)
	private BigDecimal amount;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "CREATED_DATE", nullable = true)
	private Date createdDate;

	@Column(name = "CREATED_BY", nullable = true)
	private Long createdBy;

	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", nullable = true)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", nullable = true)
	private String lgIpMacUpd;

	public Long getSchmeEligibiltyId() {
		return schmeEligibiltyId;
	}

	public void setSchmeEligibiltyId(Long schmeEligibiltyId) {
		this.schmeEligibiltyId = schmeEligibiltyId;
	}

	

	public SocialSecuritySchemeMaster getSosecuritySchmMasEligibility() {
		return sosecuritySchmMasEligibility;
	}

	public void setSosecuritySchmMasEligibility(SocialSecuritySchemeMaster sosecuritySchmMasEligibility) {
		this.sosecuritySchmMasEligibility = sosecuritySchmMasEligibility;
	}

	public Long getFactorApplicableId() {
		return factorApplicableId;
	}

	public void setFactorApplicableId(Long factorApplicableId) {
		this.factorApplicableId = factorApplicableId;
	}

	public Long getCriteriaId() {
		return criteriaId;
	}

	public void setCriteriaId(Long criteriaId) {
		this.criteriaId = criteriaId;
	}

	public String getRangeFrom() {
		return rangeFrom;
	}

	public void setRangeFrom(String rangeFrom) {
		this.rangeFrom = rangeFrom;
	}

	public String getRangeTo() {
		return rangeTo;
	}

	public void setRangeTo(String rangeTo) {
		this.rangeTo = rangeTo;
	}

	public Long getGroupID() {
		return groupID;
	}

	public void setGroupID(Long groupID) {
		this.groupID = groupID;
	}

	public Long getPaySchedule() {
		return paySchedule;
	}

	public void setPaySchedule(Long paySchedule) {
		this.paySchedule = paySchedule;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
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

	public static String[] getPkValues() {
		return new String[] { "SWD", "TB_SWD_SCHEME_ElIGIBILITY", "SDSCHE_ID" };
	}

}
