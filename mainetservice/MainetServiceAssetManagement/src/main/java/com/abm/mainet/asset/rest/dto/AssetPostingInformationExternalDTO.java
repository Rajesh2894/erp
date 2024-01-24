/**
 * 
 */
package com.abm.mainet.asset.rest.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO class for Asset Management Posting Information
 * 
 * @author sarojkumar.yadav
 *
 */
public class AssetPostingInformationExternalDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1502153034199328844L;

	private Long assetId;
	/*private Date creationDate;
	private Long createdBy;
	private Long updatedBy;
	private Date updatedDate;
	private String lgIpMac;
	private String lgIpMacUpd;*/
	private Date capitalizeOn;
	private Date firstAcquisitionOn;
	private String acquisitionYear;
	private Date orderOn;
	private String custodian;
	private String employee;
	private String location;

	/**
	 * @return the assetId
	 */
	public Long getAssetId() {
		return assetId;
	}

	/**
	 * @param assetId
	 *            the assetId to set
	 */
	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}

	/**
	 * @return the creationDate
	 */
	/*public Date getCreationDate() {
		return creationDate;
	}

	*//**
	 * @param creationDate
	 *            the creationDate to set
	 *//*
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	*//**
	 * @return the createdBy
	 *//*
	public Long getCreatedBy() {
		return createdBy;
	}

	*//**
	 * @param createdBy
	 *            the createdBy to set
	 *//*
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	*//**
	 * @return the updatedBy
	 *//*
	public Long getUpdatedBy() {
		return updatedBy;
	}

	*//**
	 * @param updatedBy
	 *            the updatedBy to set
	 *//*
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	*//**
	 * @return the updatedDate
	 *//*
	public Date getUpdatedDate() {
		return updatedDate;
	}

	*//**
	 * @param updatedDate
	 *            the updatedDate to set
	 *//*
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	*//**
	 * @return the lgIpMac
	 *//*
	public String getLgIpMac() {
		return lgIpMac;
	}

	*//**
	 * @param lgIpMac
	 *            the lgIpMac to set
	 *//*
	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	*//**
	 * @return the lgIpMacUpd
	 *//*
	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	*//**
	 * @param lgIpMacUpd
	 *            the lgIpMacUpd to set
	 *//*
	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}*/

	/**
	 * @return the capitalizeOn
	 */
	public Date getCapitalizeOn() {
		return capitalizeOn;
	}

	/**
	 * @param capitalizeOn
	 *            the capitalizeOn to set
	 */
	public void setCapitalizeOn(Date capitalizeOn) {
		this.capitalizeOn = capitalizeOn;
	}

	/**
	 * @return the firstAcquisitionOn
	 */
	public Date getFirstAcquisitionOn() {
		return firstAcquisitionOn;
	}

	/**
	 * @param firstAcquisitionOn
	 *            the firstAcquisitionOn to set
	 */
	public void setFirstAcquisitionOn(Date firstAcquisitionOn) {
		this.firstAcquisitionOn = firstAcquisitionOn;
	}

	
	public String getAcquisitionYear() {
		return acquisitionYear;
	}

	public void setAcquisitionYear(String acquisitionYear) {
		this.acquisitionYear = acquisitionYear;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the orderOn
	 */
	public Date getOrderOn() {
		return orderOn;
	}

	/**
	 * @param orderOn
	 *            the orderOn to set
	 */
	public void setOrderOn(Date orderOn) {
		this.orderOn = orderOn;
	}

	/**
	 * @return the custodian
	 */
	public String getCustodian() {
		return custodian;
	}

	/**
	 * @param custodian
	 *            the custodian to set
	 */
	public void setCustodian(String custodian) {
		this.custodian = custodian;
	}

	public String getLocation() {
		return location;
	}

	@Override
	public String toString() {
		return "AssetPostingInformationExternalDTO [assetId=" + assetId + ", capitalizeOn=" + capitalizeOn
				+ ", firstAcquisitionOn=" + firstAcquisitionOn + ", acquisitionYear=" + acquisitionYear + ", orderOn="
				+ orderOn + ", custodian=" + custodian + ", employee=" + employee + ", location=" + location + "]";
	}

	
}
