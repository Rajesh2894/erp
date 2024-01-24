package com.abm.mainet.workManagement.domain;

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

@Entity
@Table(name = "TB_WMS_GEN_RATE_MAST")
public class TbWmsMaterialMaster implements Serializable {

	/**
	 * 
	 */

	private static final long serialVersionUID = -5173794877462734625L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "MA_ID", nullable = false)
	private Long maId;

	@ManyToOne
	@JoinColumn(name = "SOR_ID", referencedColumnName = "SOR_ID", nullable = false)
	private ScheduleOfRateMstEntity sorId;

	@Column(name = "MA_ITEMNO", nullable = false)
	private String maItemNo;

	@Column(name = "MA_DESCRIPTION", nullable = false)
	private String maDescription;

	@Column(name = "MA_ITEM_UNIT", nullable = false)
	private Long maItemUnit;

	@Column(name = "MA_TYPEID", nullable = false)
	private Long maTypeId;

	@Column(name = "MA_RATE", nullable = false)
	private BigDecimal maRate;

	@Column(name = "MA_ACTIVE", nullable = false)
	private String maActive;

	@Column(name = "orgId", nullable = false)
	private Long orgId;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Column(name = "CREATED_DATE", nullable = true)
	private Date createdDate;

	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", nullable = true)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", nullable = true)
	private String lgIpMacUpd;

	public Long getMaId() {
		return maId;
	}

	public void setMaId(Long maId) {
		this.maId = maId;
	}

	public ScheduleOfRateMstEntity getSorId() {
		return sorId;
	}

	public void setSorId(ScheduleOfRateMstEntity sorId) {
		this.sorId = sorId;
	}

	public String getMaItemNo() {
		return maItemNo;
	}

	public void setMaItemNo(String maItemNo) {
		this.maItemNo = maItemNo;
	}

	public String getMaDescription() {
		return maDescription;
	}

	public void setMaDescription(String maDescription) {
		this.maDescription = maDescription;
	}

	public Long getMaItemUnit() {
		return maItemUnit;
	}

	public void setMaItemUnit(Long maItemUnit) {
		this.maItemUnit = maItemUnit;
	}

	public Long getMaTypeId() {
		return maTypeId;
	}

	public void setMaTypeId(Long maTypeId) {
		this.maTypeId = maTypeId;
	}

	public BigDecimal getMaRate() {
		return maRate;
	}

	public void setMaRate(BigDecimal maRate) {
		this.maRate = maRate;
	}

	public String getMaActive() {
		return maActive;
	}

	public void setMaActive(String maActive) {
		this.maActive = maActive;
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

	public String[] getPkValues() {
		return new String[] { "WMS", "TB_WMS_GEN_RATE_MAST", "MA_ID" };
	}

}
