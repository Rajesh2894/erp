package com.abm.mainet.property.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Arun Shinde
 *
 */
@Entity
@Table(name = "tb_as_assesment_factor_dtl_hist")
public class AssesmentFactorDetailHistEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "MN_fact_his_id")
	private long assAssfHisId;

	@Column(name = "MN_assd_id")
	private long mnAssdId;

	@Column(name = "MN_assf_factor")
	private Long assfFactor;

	@Column(name = "MN_assf_factor_id")
	private Long assfFactorId;

	@Column(name = "MN_assf_factor_value_id")
	private Long assfFactorValueId;

	@Column(name = "MN_assf_active")
	private String assfActive;

	@Column(name = "orgid")
	private Long orgId;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "updated_by")
	private Long updatedBy;

	@Column(name = "updated_date")
	private Date updatedDate;

	@Column(name = "lg_ip_mac")
	private String lgIpMac;

	@Column(name = "lg_ip_mac_upd")
	private String lgIpMacUpd;

	@Column(name = "MN_ASSO_START_DATE")
	private Date assfStartDate;

	@Column(name = "MN_ASSO_END_DATE")
	private Date assfEndDate;

	@Column(name = "MN_assf_factor_date")
	private Date factorDate;

	@Column(name = "MN_assf_factor_remark")
	private String factorRemark;
	
	@Column(name = "H_STATUS")
    private String hStatus;

	public long getAssAssfHisId() {
		return assAssfHisId;
	}

	public void setAssAssfHisId(long assAssfHisId) {
		this.assAssfHisId = assAssfHisId;
	}

	public Long getAssfFactor() {
		return assfFactor;
	}

	public void setAssfFactor(Long assfFactor) {
		this.assfFactor = assfFactor;
	}

	public Long getAssfFactorId() {
		return assfFactorId;
	}

	public void setAssfFactorId(Long assfFactorId) {
		this.assfFactorId = assfFactorId;
	}

	public Long getAssfFactorValueId() {
		return assfFactorValueId;
	}

	public void setAssfFactorValueId(Long assfFactorValueId) {
		this.assfFactorValueId = assfFactorValueId;
	}

	public String getAssfActive() {
		return assfActive;
	}

	public void setAssfActive(String assfActive) {
		this.assfActive = assfActive;
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

	public Date getAssfStartDate() {
		return assfStartDate;
	}

	public void setAssfStartDate(Date assfStartDate) {
		this.assfStartDate = assfStartDate;
	}

	public Date getAssfEndDate() {
		return assfEndDate;
	}

	public void setAssfEndDate(Date assfEndDate) {
		this.assfEndDate = assfEndDate;
	}

	public Date getFactorDate() {
		return factorDate;
	}

	public void setFactorDate(Date factorDate) {
		this.factorDate = factorDate;
	}

	public String getFactorRemark() {
		return factorRemark;
	}

	public void setFactorRemark(String factorRemark) {
		this.factorRemark = factorRemark;
	}

	public long getMnAssdId() {
		return mnAssdId;
	}

	public void setMnAssdId(long mnAssdId) {
		this.mnAssdId = mnAssdId;
	}

	public String gethStatus() {
		return hStatus;
	}

	public void sethStatus(String hStatus) {
		this.hStatus = hStatus;
	}

	public String[] getPkValues() {
		return new String[] { "AS", "tb_as_assesment_factor_dtl_hist", "MN_assd_fact_his_id" };
	}

}
