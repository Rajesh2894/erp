package com.abm.mainet.adh.dto;

import java.io.Serializable;
import java.util.Date;

public class HoardingBookingDto implements Serializable{


	private static final long serialVersionUID = -6000088223002906015L;
	
	private Long adhHrdBKId;
	private Long orgId;
	private Long adhId;
	private Long hoardingId;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

	/**
	 * @return the adhHrdBKId
	 */
	public Long getAdhHrdBKId() {
		return adhHrdBKId;
	}

	/**
	 * @param adhHrdBKId the adhHrdBKId to set
	 */
	public void setAdhHrdBKId(Long adhHrdBKId) {
		this.adhHrdBKId = adhHrdBKId;
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
	 * @return the adhId
	 */
	public Long getAdhId() {
		return adhId;
	}

	/**
	 * @param adhId the adhId to set
	 */
	public void setAdhId(Long adhId) {
		this.adhId = adhId;
	}

	/**
	 * @return the hoardingId
	 */
	public Long getHoardingId() {
		return hoardingId;
	}

	/**
	 * @param hoardingId the hoardingId to set
	 */
	public void setHoardingId(Long hoardingId) {
		this.hoardingId = hoardingId;
	}


    




}
