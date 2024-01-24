/**
 * 
 */
package com.abm.mainet.sfac.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author pooja.maske
 *
 */

@Entity
@Table(name = "Tb_SFAC_Block_Allocation")
public class BlockAllocationEntity implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 8076145183983437564L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "BLCK_ID", nullable = false)
	private Long blockId;

	@Column(name = "ORG_TYPE_ID", nullable = false)
	private Long orgTypeId;

	@Column(name = "MAS_ID", nullable = false)
	private Long organizationNameId;

	@Column(name = "ALLOCATION_YEAR_ID")
	private Long allocationYearId;

	@Column(name = "REASON", nullable = true)
	private String reasonForBlockChange;

	@Column(name = "APPLICATION_ID")
	private Long applicationId;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "AUTH_REMARK")
	private String authRemark;

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

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "blockMasterEntity", cascade = CascadeType.ALL)
	private List<BlockTargetDetEntity> targetDetEntity = new ArrayList<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "blockMasterEntity", cascade = CascadeType.ALL)
	private List<BlockAllocationDetailEntity> detailEntity = new ArrayList<>();

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

	/**
	 * @return the allocationTarget
	 */
	/*
	 * public Long getAllocationTarget() { return allocationTarget; }
	 * 
	 *//**
		 * @param allocationTarget the allocationTarget to set
		 *//*
			 * public void setAllocationTarget(Long allocationTarget) {
			 * this.allocationTarget = allocationTarget; }
			 */

	/**
	 * @return the authRemark
	 */
	public String getAuthRemark() {
		return authRemark;
	}

	/**
	 * @param authRemark the authRemark to set
	 */
	public void setAuthRemark(String authRemark) {
		this.authRemark = authRemark;
	}

	/**
	 * @return the targetDetEntity
	 */
	public List<BlockTargetDetEntity> getTargetDetEntity() {
		return targetDetEntity;
	}

	/**
	 * @param targetDetEntity the targetDetEntity to set
	 */
	public void setTargetDetEntity(List<BlockTargetDetEntity> targetDetEntity) {
		this.targetDetEntity = targetDetEntity;
	}

	/**
	 * @return the detailEntity
	 */
	public List<BlockAllocationDetailEntity> getDetailEntity() {
		return detailEntity;
	}

	/**
	 * @param detailEntity the detailEntity to set
	 */
	public void setDetailEntity(List<BlockAllocationDetailEntity> detailEntity) {
		this.detailEntity = detailEntity;
	}

	public String[] getPkValues() {
		return new String[] { "SFAC", "Tb_SFAC_Block_Allocation", "BLCK_ID" };
	}

}
