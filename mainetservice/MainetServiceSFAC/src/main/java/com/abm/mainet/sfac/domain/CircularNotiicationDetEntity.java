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

@Entity
@Table(name = "Tb_SFAC_Circular_Notify_Det")
public class CircularNotiicationDetEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3399454155329142793L;
	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "CND_ID", unique = true, nullable = false)
	private Long cndId;
	
	@ManyToOne
	@JoinColumn(name = "CN_ID", referencedColumnName = "CN_ID")
	private CircularNotificationMasterEntity masterEntity;
	
	@Column(name = "ORG_TYPE")
	private Long orgType;
	
	@Column(name = "ORG_NAME")
	private String orgName;
	
	@Column(name = "SDB1")
	private Long sdb1;

	@Column(name = "SDB2")
	private Long sdb2;
	
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

	public Long getCndId() {
		return cndId;
	}

	public void setCndId(Long cndId) {
		this.cndId = cndId;
	}

	public CircularNotificationMasterEntity getMasterEntity() {
		return masterEntity;
	}

	public void setMasterEntity(CircularNotificationMasterEntity masterEntity) {
		this.masterEntity = masterEntity;
	}

	public Long getOrgType() {
		return orgType;
	}

	public void setOrgType(Long orgType) {
		this.orgType = orgType;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Long getSdb1() {
		return sdb1;
	}

	public void setSdb1(Long sdb1) {
		this.sdb1 = sdb1;
	}

	public Long getSdb2() {
		return sdb2;
	}

	public void setSdb2(Long sdb2) {
		this.sdb2 = sdb2;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
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
		return new String[] { "SFAC", "Tb_SFAC_Circular_Notify_Det", "CND_ID" };
	}

}
