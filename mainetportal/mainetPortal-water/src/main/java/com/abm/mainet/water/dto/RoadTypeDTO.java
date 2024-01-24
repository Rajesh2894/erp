package com.abm.mainet.water.dto;

import java.io.Serializable;
import java.util.Date;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;

class RoadTypeDTO implements Serializable {

	private static final long serialVersionUID = -7519470383778641272L;

	private long crtId;

	private TbCsmrInfoDTO crtCsIdn;

	private Long crtRoadTypes;

	private Double crtRoadUnits;

	private Organisation orgId;

	private Employee userId;

	private int langId;

	private Date lmodDate;

	private Employee updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;

	private String crtGranted;

	private String crtLatest;

	private Long apmApplicationId;

	private Long smServiceId;

	private Long crtDiscType;

	private Long crtDiscRecnId;

	private Double crtRoadTrench;

	private String isDeleted;

	/**
	 * @return the crtId
	 */
	public long getCrtId() {
		return crtId;
	}

	/**
	 * @param crtId
	 *            the crtId to set
	 */
	public void setCrtId(final long crtId) {
		this.crtId = crtId;
	}

	/**
	 * @return the crtCsIdn
	 */

	/**
	 * @return the crtRoadTypes
	 */
	public Long getCrtRoadTypes() {
		return crtRoadTypes;
	}

	/**
	 * @param crtRoadTypes
	 *            the crtRoadTypes to set
	 */
	public void setCrtRoadTypes(final Long crtRoadTypes) {
		this.crtRoadTypes = crtRoadTypes;
	}

	/**
	 * @return the crtRoadUnits
	 */
	public Double getCrtRoadUnits() {
		return crtRoadUnits;
	}

	/**
	 * @param crtRoadUnits
	 *            the crtRoadUnits to set
	 */
	public void setCrtRoadUnits(final Double crtRoadUnits) {
		this.crtRoadUnits = crtRoadUnits;
	}

	/**
	 * @return the orgId
	 */
	public Organisation getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId
	 *            the orgId to set
	 */
	public void setOrgId(final Organisation orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the userId
	 */
	public Employee getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(final Employee userId) {
		this.userId = userId;
	}

	/**
	 * @return the langId
	 */
	public int getLangId() {
		return langId;
	}

	/**
	 * @param langId
	 *            the langId to set
	 */
	public void setLangId(final int langId) {
		this.langId = langId;
	}

	/**
	 * @return the lmodDate
	 */
	public Date getLmodDate() {
		return lmodDate;
	}

	/**
	 * @param lmodDate
	 *            the lmodDate to set
	 */
	public void setLmodDate(final Date lmodDate) {
		this.lmodDate = lmodDate;
	}

	/**
	 * @return the updatedBy
	 */
	public Employee getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy
	 *            the updatedBy to set
	 */
	public void setUpdatedBy(final Employee updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate
	 *            the updatedDate to set
	 */
	public void setUpdatedDate(final Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the lgIpMac
	 */
	public String getLgIpMac() {
		return lgIpMac;
	}

	/**
	 * @param lgIpMac
	 *            the lgIpMac to set
	 */
	public void setLgIpMac(final String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	/**
	 * @return the lgIpMacUpd
	 */
	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	/**
	 * @param lgIpMacUpd
	 *            the lgIpMacUpd to set
	 */
	public void setLgIpMacUpd(final String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	/**
	 * @return the crtGranted
	 */
	public String getCrtGranted() {
		return crtGranted;
	}

	/**
	 * @param crtGranted
	 *            the crtGranted to set
	 */
	public void setCrtGranted(final String crtGranted) {
		this.crtGranted = crtGranted;
	}

	/**
	 * @return the crtLatest
	 */
	public String getCrtLatest() {
		return crtLatest;
	}

	/**
	 * @param crtLatest
	 *            the crtLatest to set
	 */
	public void setCrtLatest(final String crtLatest) {
		this.crtLatest = crtLatest;
	}

	/**
	 * @return the apmApplicationId
	 */
	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	/**
	 * @param apmApplicationId
	 *            the apmApplicationId to set
	 */
	public void setApmApplicationId(final Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}

	/**
	 * @return the smServiceId
	 */
	public Long getSmServiceId() {
		return smServiceId;
	}

	/**
	 * @param smServiceId
	 *            the smServiceId to set
	 */
	public void setSmServiceId(final Long smServiceId) {
		this.smServiceId = smServiceId;
	}

	/**
	 * @return the crtDiscType
	 */
	public Long getCrtDiscType() {
		return crtDiscType;
	}

	/**
	 * @param crtDiscType
	 *            the crtDiscType to set
	 */
	public void setCrtDiscType(final Long crtDiscType) {
		this.crtDiscType = crtDiscType;
	}

	/**
	 * @return the crtCsIdn
	 */
	public TbCsmrInfoDTO getCrtCsIdn() {
		return crtCsIdn;
	}

	/**
	 * @param crtCsIdn
	 *            the crtCsIdn to set
	 */
	public void setCrtCsIdn(final TbCsmrInfoDTO crtCsIdn) {
		this.crtCsIdn = crtCsIdn;
	}

	/**
	 * @return the crtDiscRecnId
	 */
	public Long getCrtDiscRecnId() {
		return crtDiscRecnId;
	}

	/**
	 * @param crtDiscRecnId
	 *            the crtDiscRecnId to set
	 */
	public void setCrtDiscRecnId(final Long crtDiscRecnId) {
		this.crtDiscRecnId = crtDiscRecnId;
	}

	/**
	 * @return the crtRoadTrench
	 */
	public Double getCrtRoadTrench() {
		return crtRoadTrench;
	}

	/**
	 * @param crtRoadTrench
	 *            the crtRoadTrench to set
	 */
	public void setCrtRoadTrench(final Double crtRoadTrench) {
		this.crtRoadTrench = crtRoadTrench;
	}

	/**
	 * @return the isDeleted
	 */
	public String getIsDeleted() {
		return isDeleted;
	}

	/**
	 * @param isDeleted
	 *            the isDeleted to set
	 */
	public void setIsDeleted(final String isDeleted) {
		this.isDeleted = isDeleted;
	}

}
