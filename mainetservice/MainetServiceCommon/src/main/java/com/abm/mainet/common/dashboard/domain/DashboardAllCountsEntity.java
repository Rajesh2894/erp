package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DashboardAllCountsEntity {

	@Id
	@Column(name = "num")
	private long id;

	@Column(name = "deptCount")
	private long deptCount;

	@Column(name = "serviceCount")
	private long serviceCount;

	@Column(name = "applReceivedCount")
	private long applReceivedCount;

	@Column(name = "applDisposedCount")
	private long applDisposedCount;

	@Column(name = "legalCount")
	private long legalCount;

	@Column(name = "grievancesCount")
	private long grievancesCount;

	@Column(name = "rtiCount")
	private long rtiCount;

	@Column(name = "solidWasteCount")
	private long solidWasteCount;

	@Column(name = "legisQueryCount")
	private long legisQueryCount;

	@Column(name = "councilMgmtCount")
	private long councilMgmtCount;

	@Column(name = "hrmsCount")
	private long hrmsCount;

	@Column(name = "storeMgmtCount")
	private long storeMgmtCount;

	@Column(name = "propertyCount")
	private long propertyCount;

	@Column(name = "waterCount")
	private long waterCount;

	@Column(name = "financialAccCount")
	private long financialAccCount;

	@Column(name = "advertiseTaxCount")
	private long advertiseTaxCount;

	@Column(name = "landEstateCount")
	private long landEstateCount;

	@Column(name = "birthDeathCount")
	private long birthDeathCount;

	@Column(name = "workMgmtSysCount")
	private long workMgmtSysCount;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getDeptCount() {
		return deptCount;
	}

	public void setDeptCount(long deptCount) {
		this.deptCount = deptCount;
	}

	public long getServiceCount() {
		return serviceCount;
	}

	public void setServiceCount(long serviceCount) {
		this.serviceCount = serviceCount;
	}

	public long getApplReceivedCount() {
		return applReceivedCount;
	}

	public void setApplReceivedCount(long applReceivedCount) {
		this.applReceivedCount = applReceivedCount;
	}

	public long getApplDisposedCount() {
		return applDisposedCount;
	}

	public void setApplDisposedCount(long applDisposedCount) {
		this.applDisposedCount = applDisposedCount;
	}

	public long getLegalCount() {
		return legalCount;
	}

	public void setLegalCount(long legalCount) {
		this.legalCount = legalCount;
	}

	public long getGrievancesCount() {
		return grievancesCount;
	}

	public void setGrievancesCount(long grievancesCount) {
		this.grievancesCount = grievancesCount;
	}

	public long getRtiCount() {
		return rtiCount;
	}

	public void setRtiCount(long rtiCount) {
		this.rtiCount = rtiCount;
	}

	public long getSolidWasteCount() {
		return solidWasteCount;
	}

	public void setSolidWasteCount(long solidWasteCount) {
		this.solidWasteCount = solidWasteCount;
	}

	public long getLegisQueryCount() {
		return legisQueryCount;
	}

	public void setLegisQueryCount(long legisQueryCount) {
		this.legisQueryCount = legisQueryCount;
	}

	public long getCouncilMgmtCount() {
		return councilMgmtCount;
	}

	public void setCouncilMgmtCount(long councilMgmtCount) {
		this.councilMgmtCount = councilMgmtCount;
	}

	public long getHrmsCount() {
		return hrmsCount;
	}

	public void setHrmsCount(long hrmsCount) {
		this.hrmsCount = hrmsCount;
	}

	public long getStoreMgmtCount() {
		return storeMgmtCount;
	}

	public void setStoreMgmtCount(long storeMgmtCount) {
		this.storeMgmtCount = storeMgmtCount;
	}

	public long getPropertyCount() {
		return propertyCount;
	}

	public void setPropertyCount(long propertyCount) {
		this.propertyCount = propertyCount;
	}

	public long getWaterCount() {
		return waterCount;
	}

	public void setWaterCount(long waterCount) {
		this.waterCount = waterCount;
	}

	public long getFinancialAccCount() {
		return financialAccCount;
	}

	public void setFinancialAccCount(long financialAccCount) {
		this.financialAccCount = financialAccCount;
	}

	public long getAdvertiseTaxCount() {
		return advertiseTaxCount;
	}

	public void setAdvertiseTaxCount(long advertiseTaxCount) {
		this.advertiseTaxCount = advertiseTaxCount;
	}

	public long getLandEstateCount() {
		return landEstateCount;
	}

	public void setLandEstateCount(long landEstateCount) {
		this.landEstateCount = landEstateCount;
	}

	public long getBirthDeathCount() {
		return birthDeathCount;
	}

	public void setBirthDeathCount(long birthDeathCount) {
		this.birthDeathCount = birthDeathCount;
	}

	public long getWorkMgmtSysCount() {
		return workMgmtSysCount;
	}

	public void setWorkMgmtSysCount(long workMgmtSysCount) {
		this.workMgmtSysCount = workMgmtSysCount;
	}

	@Override
	public String toString() {
		return "DashboardAllCountsEntity [id=" + id + ", deptCount=" + deptCount + ", serviceCount=" + serviceCount
				+ ", applReceivedCount=" + applReceivedCount + ", applDisposedCount=" + applDisposedCount
				+ ", legalCount=" + legalCount + ", grievancesCount=" + grievancesCount + ", rtiCount=" + rtiCount
				+ ", solidWasteCount=" + solidWasteCount + ", legisQueryCount=" + legisQueryCount
				+ ", councilMgmtCount=" + councilMgmtCount + ", hrmsCount=" + hrmsCount + ", storeMgmtCount="
				+ storeMgmtCount + ", propertyCount=" + propertyCount + ", waterCount=" + waterCount
				+ ", financialAccCount=" + financialAccCount + ", advertiseTaxCount=" + advertiseTaxCount
				+ ", landEstateCount=" + landEstateCount + ", birthDeathCount=" + birthDeathCount
				+ ", workMgmtSysCount=" + workMgmtSysCount + "]";
	}

}
