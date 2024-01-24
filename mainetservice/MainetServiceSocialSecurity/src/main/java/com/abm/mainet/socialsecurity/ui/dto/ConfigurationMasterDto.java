package com.abm.mainet.socialsecurity.ui.dto;

import java.io.Serializable;
import java.util.Date;

public class ConfigurationMasterDto implements Serializable {

	/**
	 * @author rahul.chaubey
	 * @since 11 Jan 2020
	 */
	private static final long serialVersionUID = 2007703049568964699L;

	private Long configurationId;

	private Long schemeMstId;

	private String schemeCode;

	private String schemeName;

	private Date fromDate;

	private Date toDate;

	private Long beneficiaryCount;

	private Long orgId;

	private Date creationDate;

	private Long createdBy;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;
	
	private Long typeOfScheme;

	public String getSchemeCode() {
		return schemeCode;
	}

	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
	}

	public Long getConfigurationId() {
		return configurationId;
	}

	public void setConfigurationId(Long configurationId) {
		this.configurationId = configurationId;
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

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public Long getTypeOfScheme() {
		return typeOfScheme;
	}

	public void setTypeOfScheme(Long typeOfScheme) {
		this.typeOfScheme = typeOfScheme;
	}

}
