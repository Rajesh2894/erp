package com.abm.mainet.cfc.loi.domain;

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

@Entity
@Table(name = "TB_DISHONUR_CHARGE_ENTITY")
public class DishonourChargeEntity implements Serializable {

	private static final long serialVersionUID = 4587806275885772695L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "DISHNOUR_CHG_ID", nullable = false)
	private Long disHonurChId;

	@Column(name = "APM_APPLICATION_ID")
	private Long apmApplicationId;

	@Column(name = "REF_ID")
	private String refId;

	@Column(name = "TAX_ID")
	private Long taxId;

	@Column(name = "DIS_HN_AMT")
	private BigDecimal disHnAm;

	@Column(name = "IS_DIS_HNR_CHR_PAID")
	private String isDishnrChgPaid;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", length = 100)
	private String lgIpMac;

	@Column(name = "DIS_HN_REMARKS", length = 200)
	private String disHnRemarks;

	@Column(name = "ORGID", nullable = false)
	private Long orgid;

	public String[] getPkValues() {
		return new String[] { "CFC", "TB_DISHONUR_CHARGE_ENTITY", "DISHNOUR_CHG_ID" };
	}

	public Long getDisHonurChId() {
		return disHonurChId;
	}

	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	public String getRefId() {
		return refId;
	}

	public Long getTaxId() {
		return taxId;
	}

	public BigDecimal getDisHnAm() {
		return disHnAm;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public String getLgIpMac() {
		return lgIpMac;
	}

	public String getDisHnRemarks() {
		return disHnRemarks;
	}

	public Long getOrgid() {
		return orgid;
	}

	public void setDisHonurChId(Long disHonurChId) {
		this.disHonurChId = disHonurChId;
	}

	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public void setTaxId(Long taxId) {
		this.taxId = taxId;
	}

	public void setDisHnAm(BigDecimal disHnAm) {
		this.disHnAm = disHnAm;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	public void setDisHnRemarks(String disHnRemarks) {
		this.disHnRemarks = disHnRemarks;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	public String getIsDishnrChgPaid() {
		return isDishnrChgPaid;
	}

	public void setIsDishnrChgPaid(String isDishnrChgPaid) {
		this.isDishnrChgPaid = isDishnrChgPaid;
	}

}
