/**
 * 
 */
package com.abm.mainet.workManagement.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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

/**
 * @author Saiprasad.Vengurlekar
 *
 */

@Entity
@Table(name = "TB_WMS_MILESTONE_DET")
public class MilestoneDetail implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "MILED_ID", precision = 12, scale = 0, nullable = false)
	private Long miledId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MILE_ID", nullable = false)
	private MileStone milestoneEntity;

	@Column(name = "MILED_PROGRESSDATE", nullable = false)
	private Date proUpdateDate;

	@Column(name = "MILED_PHYSC_PERCENT", nullable = false)
	private BigDecimal phyPercent;

	@Column(name = "MILED_FINANC_PERCENT", nullable = false)
	private BigDecimal finPercent;

	@Column(name = "MILED_ACTIVE", nullable = false)
	private String miledActive;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", nullable = true)
	private String lgIpMacUpd;

	public Long getMiledId() {
		return miledId;
	}

	public void setMiledId(Long miledId) {
		this.miledId = miledId;
	}

	public MileStone getMilestoneEntity() {
		return milestoneEntity;
	}

	public void setMilestoneEntity(MileStone milestoneEntity) {
		this.milestoneEntity = milestoneEntity;
	}

	public Date getProUpdateDate() {
		return proUpdateDate;
	}

	public void setProUpdateDate(Date proUpdateDate) {
		this.proUpdateDate = proUpdateDate;
	}

	public BigDecimal getPhyPercent() {
		return phyPercent;
	}

	public void setPhyPercent(BigDecimal phyPercent) {
		this.phyPercent = phyPercent;
	}

	public BigDecimal getFinPercent() {
		return finPercent;
	}

	public void setFinPercent(BigDecimal finPercent) {
		this.finPercent = finPercent;
	}

	public String getMiledActive() {
		return miledActive;
	}

	public void setMiledActive(String miledActive) {
		this.miledActive = miledActive;
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
		return new String[] { "WMS", "TB_WMS_MILESTONE_DET", "MILED_ID" };
	}
}
