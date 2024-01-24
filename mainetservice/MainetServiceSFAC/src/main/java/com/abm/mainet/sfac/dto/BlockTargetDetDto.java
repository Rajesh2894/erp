/**
 * 
 */
package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author pooja.maske
 *
 */
public class BlockTargetDetDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9027306842924668235L;

	private Long btId;

	private Long blockId; // fk

	private Long finYrId;

	private Long allocationCategory;

	private Long allocationSubCategory;

	private Long allocationTarget;

	private String status;

	private Long applicationId;

	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	private Date targetDate;

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
	 * @return the blockId
	 */
	public Long getBlockId() {
		return blockId;
	}

	/**
	 * @param blockId the blockId to set
	 */
	public void setBlockId(Long blockId) {
		this.blockId = blockId;
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

}
