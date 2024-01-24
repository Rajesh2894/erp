/**
 * 
 */
package com.abm.mainet.sfac.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author pooja.maske
 *
 */
@Entity
@Table(name = "TB_SFAC_BLOCK_TARGET_DETAIL")
public class BlockTargetDetEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -732067415369851599L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "BT_ID")
	private Long btId;

	@ManyToOne
	@JoinColumn(name = "BLCK_ID", referencedColumnName = "BLCK_ID")
	private BlockAllocationEntity blockMasterEntity;

	@Column(name = "FIN_YR_ID", nullable = true)
	private Long finYrId;

	@Column(name = "ALLOCATION_CATEGORY", nullable = true)
	private Long allocationCategory;

	@Column(name = "ALLOCATION_SUB_CATEGORY", nullable = true)
	private Long allocationSubCategory;

	@Column(name = "ALLOCATION_TARGET", nullable = true)
	private Long allocationTarget;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "APPLICATION_ID")
	private Long applicationId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TARGET_DATE")
	private Date targetDate;

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
	 * @return the btId
	 */
	public Long getBtId() {
		return btId;
	}

	/**
	 * @param btId the btId to set
	 */
	public void setBtId(Long btId) {
		this.btId = btId;
	}

	/**
	 * @return the blockMasterEntity
	 */
	public BlockAllocationEntity getBlockMasterEntity() {
		return blockMasterEntity;
	}

	/**
	 * @param blockMasterEntity the blockMasterEntity to set
	 */
	public void setBlockMasterEntity(BlockAllocationEntity blockMasterEntity) {
		this.blockMasterEntity = blockMasterEntity;
	}

	/**
	 * @return the finYrId
	 */
	public Long getFinYrId() {
		return finYrId;
	}

	/**
	 * @param finYrId the finYrId to set
	 */
	public void setFinYrId(Long finYrId) {
		this.finYrId = finYrId;
	}

	/**
	 * @return the allocationCategory
	 */
	public Long getAllocationCategory() {
		return allocationCategory;
	}

	/**
	 * @param allocationCategory the allocationCategory to set
	 */
	public void setAllocationCategory(Long allocationCategory) {
		this.allocationCategory = allocationCategory;
	}

	/**
	 * @return the allocationTarget
	 */
	public Long getAllocationTarget() {
		return allocationTarget;
	}

	/**
	 * @param allocationTarget the allocationTarget to set
	 */
	public void setAllocationTarget(Long allocationTarget) {
		this.allocationTarget = allocationTarget;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the applicationId
	 */
	public Long getApplicationId() {
		return applicationId;
	}

	/**
	 * @param applicationId the applicationId to set
	 */
	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
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

	/**
	 * @return the allocationSubCategory
	 */
	public Long getAllocationSubCategory() {
		return allocationSubCategory;
	}

	/**
	 * @param allocationSubCategory the allocationSubCategory to set
	 */
	public void setAllocationSubCategory(Long allocationSubCategory) {
		this.allocationSubCategory = allocationSubCategory;
	}

	/**
	 * @return the targetDate
	 */
	public Date getTargetDate() {
		return targetDate;
	}

	/**
	 * @param targetDate the targetDate to set
	 */
	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}

	public String[] getPkValues() {
		return new String[] { "SFAC", "TB_SFAC_BLOCK_TARGET_DETAIL", "BT_ID" };
	}
}
