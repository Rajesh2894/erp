package com.abm.mainet.additionalservices.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_cfc_tree_info")
public class TreeCuttingInfoEntity implements Serializable {

	private static final long serialVersionUID = 4865488858162165516L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "app_id", nullable = false)
	private Long appId;

	@Column(name = "apm_application_id", nullable = false)
	private Long apmApplicationId;

	@Column(name = "aplicant_name", nullable = false)
	private String appName;

	@Column(name = "mob_number", nullable = false)
	private Long mobNumber;

	@Column(name = "email_id", nullable = false)
	private String emailId;

	@Column(name = "address", nullable = false)
	private String address;

	@Column(name = "tree_desc", nullable = false)
	private String treeDesc;

	@Column(name = "tree_count", nullable = false)
	private Long count;

	@Column(name = "reason", nullable = false)
	private String reason;

	@Column(name = "location", nullable = false)
	private String location;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;
	
	@Column(name = "tree_cut_type", nullable = false)
	private Long treeCutType;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date creationDate;

	@Column(name = "CREATED_BY", updatable = false, nullable = false)
	private Long createdBy;

	@Column(name = "LG_IP_MAC", length = 100, nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	private String lgIpMacUpd;

	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;
	
	public static String[] getPkValues() {
		return new String[] { "CFC", "tb_cfc_tree_info", "app_id" };
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Long getMobNumber() {
		return mobNumber;
	}

	public void setMobNumber(Long mobNumber) {
		this.mobNumber = mobNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTreeDesc() {
		return treeDesc;
	}

	public void setTreeDesc(String treeDesc) {
		this.treeDesc = treeDesc;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
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

	public Long getTreeCutType() {
		return treeCutType;
	}

	public void setTreeCutType(Long treeCutType) {
		this.treeCutType = treeCutType;
	}

}
