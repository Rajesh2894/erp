package com.abm.mainet.socialsecurity.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author rahul.chaubey
 * @since 11 Jan 2020
 */


@Entity
@Table(name = "TB_SWD_SCHEME_CONFIGURATION_HIST")
public class ConfigurationMasterHistoryEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3133277356807355172L;
	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "CONFID_H", nullable = false)
	private Long configurationHistoryId;
	
	@Column(name = "CONFID", nullable = false)
	private Long configurationId;
	
	@Column(name = "SDSCH_ID", nullable = false)
	private Long schemeMstId;
	
	@Column(name = "FROM_DT", nullable = false)
	private Date fromDate;
	
	@Column(name = "TO_DT", nullable = false)
	private Date toDate;
	
	@Column(name = "BENF_CNT", nullable = false)
	private Long beneficiaryCount;
	
	@Column(name = "ORGID", nullable = false)
	private Long orgId;
	
	@Column(name = "CREATION_DATE", nullable = true)
	private Date creationDate;

	@Column(name = "CREATED_BY", nullable = true)
	private Long createdBy;

	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", nullable = true)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", nullable = true)
	private String lgIpMacUpd;
	
	//H_STATUS
	
	@Column(name = "H_STATUS", nullable = true)
	private String hStatus;
	
	public Long getConfigurationHistoryId() {
		return configurationHistoryId;
	}

	public void setConfigurationHistoryId(Long configurationHistoryId) {
		this.configurationHistoryId = configurationHistoryId;
	}

	public Long getSchemeMstId() {
		return schemeMstId;
	}
	public void setSchemeMstId(Long schemeMstId) {
		this.schemeMstId = schemeMstId;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Long getBeneficiaryCount() {
		return beneficiaryCount;
	}

	public void setBeneficiaryCount(Long beneficiaryCount) {
		this.beneficiaryCount = beneficiaryCount;
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

	
	public String gethStatus() {
		return hStatus;
	}

	public void sethStatus(String hStatus) {
		this.hStatus = hStatus;
	}

	public Long getConfigurationId() {
		return configurationId;
	}

	public void setConfigurationId(Long configurationId) {
		this.configurationId = configurationId;
	}

	public static String[] getPkValues() {
		return new String[] { "SWD", "TB_SWD_SCHEME_CONFIGURATION_HIST", "CONFID_H" };
	}
	

}
