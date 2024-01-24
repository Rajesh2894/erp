package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SKDCLDashboardAllCountsEntity {

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

	@Column(name = "grievancesCount")
	private long grievancesCount;

	@Column(name = "propertyCount")
	private long propertyCount;

	@Column(name = "waterCount")
	private long waterCount;

	@Column(name = "birthDeathCount")
	private long birthDeathCount;

	@Column(name = "marriageRegistCount")
	private long marriageRegistCount;

	@Column(name = "tradeLicenCount")
	private long tradeLicenCount;

	@Column(name = "financialAccountCount")
	private long financialAccountCount;

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

	public long getGrievancesCount() {
		return grievancesCount;
	}

	public void setGrievancesCount(long grievancesCount) {
		this.grievancesCount = grievancesCount;
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

	public long getBirthDeathCount() {
		return birthDeathCount;
	}

	public void setBirthDeathCount(long birthDeathCount) {
		this.birthDeathCount = birthDeathCount;
	}

	public long getMarriageRegistCount() {
		return marriageRegistCount;
	}

	public void setMarriageRegistCount(long marriageRegistCount) {
		this.marriageRegistCount = marriageRegistCount;
	}

	public long getTradeLicenCount() {
		return tradeLicenCount;
	}

	public void setTradeLicenCount(long tradeLicenCount) {
		this.tradeLicenCount = tradeLicenCount;
	}

	public long getFinancialAccountCount() {
		return financialAccountCount;
	}

	public void setFinancialAccountCount(long financialAccountCount) {
		this.financialAccountCount = financialAccountCount;
	}

	@Override
	public String toString() {
		return "SKDCLDashboardAllCountsEntity [id=" + id + ", deptCount=" + deptCount + ", serviceCount=" + serviceCount
				+ ", applReceivedCount=" + applReceivedCount + ", applDisposedCount=" + applDisposedCount
				+ ", grievancesCount=" + grievancesCount + ", propertyCount=" + propertyCount + ", waterCount="
				+ waterCount + ", birthDeathCount=" + birthDeathCount + ", marriageRegistCount=" + marriageRegistCount
				+ ", tradeLicenCount=" + tradeLicenCount + ", financialAccountCount=" + financialAccountCount + "]";
	}

}
