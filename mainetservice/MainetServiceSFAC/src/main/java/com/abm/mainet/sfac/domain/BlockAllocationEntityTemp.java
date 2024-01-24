/**
 * 
 */
package com.abm.mainet.sfac.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author pooja.maske
 *
 */
@Entity
@Table(name = "Tb_SFAC_Block_Allocation_Temp")
public class BlockAllocationEntityTemp implements Serializable{


	private static final long serialVersionUID = 8804486719179641841L;
	
	@Id
	@Column(name = "BLCK_ID", nullable = false)
	private Long blockId;

	@Column(name = "ORG_TYPE_ID", nullable = false)
	private Long orgTypeId;

	@Column(name = "ORG_NAME_ID", nullable = false)
	private Long organizationNameId;

	@Column(name = "ALLOCATION_YEAR_ID", nullable = false)
	private Long allocationYearId;

	@Column(name = "SDB1", nullable = false)
	private Long sdb1;

	@Column(name = "SDB2", nullable = false)
	private Long sdb2;

	@Column(name = "SDB3", nullable = false)
	private Long sdb3;

	@Column(name = "SDB4", nullable = true)
	private Long sdb4;

	@Column(name = "SDB5", nullable = true)
	private Long sdb5;

	@Column(name = "NO_OF_FPO_ALLOCATED", nullable = true)
	private Long noOfFPOAllocated;

	@Column(name = "ALLOCATION_CATEGORY", nullable = true)
	private Long allocationCategory;
	
	@Column(name = "CHANGED_BLOCK", nullable = true)
	private Long changedBlock; 
	
	@Column(name = "COMMODITY_TYPE", nullable = true)
	private Long commodityType; 
	
	@Column(name = "REASON", nullable = true)
	private String reasonForBlockChange;
	
	@Column(name = "APPLICATION_ID")
	private Long applicationId;
	
	@Column(name = "STATUS")
	private String status;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Column(name = "LG_IP_MAC", nullable = false, length = 100)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100)
	private String lgIpMacUpd;

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
	 * @return the organizationNameId
	 */
	public Long getOrganizationNameId() {
		return organizationNameId;
	}

	/**
	 * @param organizationNameId the organizationNameId to set
	 */
	public void setOrganizationNameId(Long organizationNameId) {
		this.organizationNameId = organizationNameId;
	}

	/**
	 * @return the allocationYearId
	 */
	public Long getAllocationYearId() {
		return allocationYearId;
	}

	/**
	 * @param allocationYearId the allocationYearId to set
	 */
	public void setAllocationYearId(Long allocationYearId) {
		this.allocationYearId = allocationYearId;
	}

	/**
	 * @return the sdb1
	 */
	public Long getSdb1() {
		return sdb1;
	}

	/**
	 * @param sdb1 the sdb1 to set
	 */
	public void setSdb1(Long sdb1) {
		this.sdb1 = sdb1;
	}

	/**
	 * @return the sdb2
	 */
	public Long getSdb2() {
		return sdb2;
	}

	/**
	 * @param sdb2 the sdb2 to set
	 */
	public void setSdb2(Long sdb2) {
		this.sdb2 = sdb2;
	}

	/**
	 * @return the sdb3
	 */
	public Long getSdb3() {
		return sdb3;
	}

	/**
	 * @param sdb3 the sdb3 to set
	 */
	public void setSdb3(Long sdb3) {
		this.sdb3 = sdb3;
	}

	/**
	 * @return the sdb4
	 */
	public Long getSdb4() {
		return sdb4;
	}

	/**
	 * @param sdb4 the sdb4 to set
	 */
	public void setSdb4(Long sdb4) {
		this.sdb4 = sdb4;
	}

	/**
	 * @return the sdb5
	 */
	public Long getSdb5() {
		return sdb5;
	}

	/**
	 * @param sdb5 the sdb5 to set
	 */
	public void setSdb5(Long sdb5) {
		this.sdb5 = sdb5;
	}

	/**
	 * @return the noOfFPOAllocated
	 */
	public Long getNoOfFPOAllocated() {
		return noOfFPOAllocated;
	}

	/**
	 * @param noOfFPOAllocated the noOfFPOAllocated to set
	 */
	public void setNoOfFPOAllocated(Long noOfFPOAllocated) {
		this.noOfFPOAllocated = noOfFPOAllocated;
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
	 * @return the orgTypeId
	 */
	public Long getOrgTypeId() {
		return orgTypeId;
	}

	/**
	 * @param orgTypeId the orgTypeId to set
	 */
	public void setOrgTypeId(Long orgTypeId) {
		this.orgTypeId = orgTypeId;
	}
	
	

	/**
	 * @return the changedBlock
	 */
	public Long getChangedBlock() {
		return changedBlock;
	}

	/**
	 * @param changedBlock the changedBlock to set
	 */
	public void setChangedBlock(Long changedBlock) {
		this.changedBlock = changedBlock;
	}

	/**
	 * @return the commodityType
	 */
	public Long getCommodityType() {
		return commodityType;
	}

	/**
	 * @param commodityType the commodityType to set
	 */
	public void setCommodityType(Long commodityType) {
		this.commodityType = commodityType;
	}

	/**
	 * @return the reasonForBlockChange
	 */
	public String getReasonForBlockChange() {
		return reasonForBlockChange;
	}

	/**
	 * @param reasonForBlockChange the reasonForBlockChange to set
	 */
	public void setReasonForBlockChange(String reasonForBlockChange) {
		this.reasonForBlockChange = reasonForBlockChange;
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

	public String[] getPkValues() {
		return new String[] { "SFAC", "Tb_SFAC_Block_Allocation_Temp", "BLCK_ID" };
	}


}
