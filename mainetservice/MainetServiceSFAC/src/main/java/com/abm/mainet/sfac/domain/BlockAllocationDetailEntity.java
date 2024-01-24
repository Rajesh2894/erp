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
@Table(name = "TB_SFAC_BLOCK_ALLOCATION_DETAIL")
public class BlockAllocationDetailEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5206396347757759699L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "BD_ID")
	private Long bdId;

	@ManyToOne
	@JoinColumn(name = "BLCK_ID", referencedColumnName = "BLCK_ID")
	private BlockAllocationEntity blockMasterEntity;

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

	@Column(name = "ALLOCATION_CATEGORY", nullable = true)
	private Long allocationCategory;

	@Column(name = "CBBO_ID", nullable = true)
	private Long cbboId;

	@Column(name = "ALLOCATION_YEAR_TO_CBBO", nullable = true)
	private Long allcyrToCbbo;

	@Column(name = "APPLICATION_ID")
	private Long applicationId;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "REASON", nullable = true)
	private String reason;

	@Column(name = "ALLOCATION_SUB_CATEGORY", nullable = true)
	private Long allocationSubCategory;

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

	public String[] getPkValues() {
		return new String[] { "SFAC", "TB_SFAC_BLOCK_ALLOCATION_DETAIL", "BD_ID" };
	}

}
