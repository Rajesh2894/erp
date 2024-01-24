package com.abm.mainet.tradeLicense.dto;

public class LicenseValidityDto {
	private Long deptId;
	private Long serviceId;
	private Long orgId;
	private Long catId1;
	private Long licType;

	public Long getDeptId() {
		return deptId;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public Long getCatId1() {
		return catId1;
	}

	public Long getLicType() {
		return licType;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public void setCatId1(Long catId1) {
		this.catId1 = catId1;
	}

	public void setLicType(Long licType) {
		this.licType = licType;
	}
}
