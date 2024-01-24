/**
 * 
 */
package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.utility.LookUp;

/**
 * @author pooja.maske
 *
 */
public class BlockAllocationDetailDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7822978669435811294L;

	private Long bdId;

	private Long blockId; // fk

	private Long sdb1;

	private Long sdb2;

	private Long sdb3;

	private Long sdb4;

	private Long sdb5;

	private Long allocationCategory;

	private Long cbboId;

	private Long applicationId;

	private String status;

	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	private Long stateId;
	private Long distId;
	private Long blckId;

	private Long allcyrToCbbo;

	private String reason;

	private Long allocationSubCategory;

	private String cbboName;

	List<LookUp> districtList = new ArrayList<>();
	List<LookUp> blockList = new ArrayList<>();

	/**
	 * @return the bdId
	 */
	public Long getBdId() {
		return bdId;
	}

	/**
	 * @param bdId the bdId to set
	 */
	public void setBdId(Long bdId) {
		this.bdId = bdId;
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
	 * @return the cbboId
	 */
	public Long getCbboId() {
		return cbboId;
	}

	/**
	 * @param cbboId the cbboId to set
	 */
	public void setCbboId(Long cbboId) {
		this.cbboId = cbboId;
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
	 * @return the stateId
	 */
	public Long getStateId() {
		return stateId;
	}

	/**
	 * @param stateId the stateId to set
	 */
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	/**
	 * @return the distId
	 */
	public Long getDistId() {
		return distId;
	}

	/**
	 * @param distId the distId to set
	 */
	public void setDistId(Long distId) {
		this.distId = distId;
	}

	/**
	 * @return the blckId
	 */
	public Long getBlckId() {
		return blckId;
	}

	/**
	 * @param blckId the blckId to set
	 */
	public void setBlckId(Long blckId) {
		this.blckId = blckId;
	}

	/**
	 * @return the allcyrToCbbo
	 */
	public Long getAllcyrToCbbo() {
		return allcyrToCbbo;
	}

	/**
	 * @param allcyrToCbbo the allcyrToCbbo to set
	 */
	public void setAllcyrToCbbo(Long allcyrToCbbo) {
		this.allcyrToCbbo = allcyrToCbbo;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
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
	 * @return the cbboName
	 */
	public String getCbboName() {
		return cbboName;
	}

	/**
	 * @param cbboName the cbboName to set
	 */
	public void setCbboName(String cbboName) {
		this.cbboName = cbboName;
	}

	/**
	 * @return the districtList
	 */
	public List<LookUp> getDistrictList() {
		return districtList;
	}

	/**
	 * @param districtList the districtList to set
	 */
	public void setDistrictList(List<LookUp> districtList) {
		this.districtList = districtList;
	}

	/**
	 * @return the blockList
	 */
	public List<LookUp> getBlockList() {
		return blockList;
	}

	/**
	 * @param blockList the blockList to set
	 */
	public void setBlockList(List<LookUp> blockList) {
		this.blockList = blockList;
	}

}
