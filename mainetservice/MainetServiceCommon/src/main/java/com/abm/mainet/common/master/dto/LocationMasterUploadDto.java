package com.abm.mainet.common.master.dto;

import java.util.Date;

import javax.validation.constraints.Size;

public class LocationMasterUploadDto {

	private String locNameEng;
	private String locNameReg;
	private String locArea;
	private String locAreaReg;
	private String locAddress;
	private String locAddressReg;
	private Long pincode;
	private String landmark;
	private String deptLoc;
	private String locActive;
	private Long GISNo;
	private String locCategory;
	private String latitude;
	private String longitude;
	private String isAreaMappingElectoral;
	private String isAreaMappingRevenue;
	private String isAreaMappingOperational;
	private String codIdElecLevel1;
	private String codIdElecLevel2;
	private String codIdElecLevel3;
	private String codIdElecLevel4;
	private String codIdElecLevel5;
	private String codIdRevLevel1;
	private Long dpDeptId;
	private String dpDeptDesc;
	private String codIdOperLevel1;
	private String codIdOperLevel2;
	private String codIdOperLevel3;
	private String codIdOperLevel4;
	private String codIdOperLevel5;
	private boolean electoralChkBox;
	private boolean revenueChkBox;
	private boolean opertionalChkBox;
	private Long orgId;
	private Long userId;
	private Integer langId;
	private Date lmoddate;
	@Size(max = 100)
	private String lgIpMac;

	public String getLocNameEng() {
		return locNameEng;
	}

	public void setLocNameEng(String locNameEng) {
		this.locNameEng = locNameEng;
	}

	public String getLocNameReg() {
		return locNameReg;
	}

	public void setLocNameReg(String locNameReg) {
		this.locNameReg = locNameReg;
	}

	public String getLocArea() {
		return locArea;
	}

	public void setLocArea(String locArea) {
		this.locArea = locArea;
	}

	public String getLocAreaReg() {
		return locAreaReg;
	}

	public void setLocAreaReg(String locAreaReg) {
		this.locAreaReg = locAreaReg;
	}

	public String getLocAddress() {
		return locAddress;
	}

	public void setLocAddress(String locAddress) {
		this.locAddress = locAddress;
	}

	public String getLocAddressReg() {
		return locAddressReg;
	}

	public void setLocAddressReg(String locAddressReg) {
		this.locAddressReg = locAddressReg;
	}

	public Long getPincode() {
		return pincode;
	}

	public void setPincode(Long pincode) {
		this.pincode = pincode;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getDeptLoc() {
		return deptLoc;
	}

	public void setDeptLoc(String deptLoc) {
		this.deptLoc = deptLoc;
	}

	public String getLocActive() {
		return locActive;
	}

	public void setLocActive(String locActive) {
		this.locActive = locActive;
	}

	public Long getGISNo() {
		return GISNo;
	}

	public void setGISNo(Long gISNo) {
		GISNo = gISNo;
	}

	public String getLocCategory() {
		return locCategory;
	}

	public void setLocCategory(String locCategory) {
		this.locCategory = locCategory;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getIsAreaMappingElectoral() {
		return isAreaMappingElectoral;
	}

	public void setIsAreaMappingElectoral(String isAreaMappingElectoral) {
		this.isAreaMappingElectoral = isAreaMappingElectoral;
	}

	public String getIsAreaMappingRevenue() {
		return isAreaMappingRevenue;
	}

	public void setIsAreaMappingRevenue(String isAreaMappingRevenue) {
		this.isAreaMappingRevenue = isAreaMappingRevenue;
	}

	public String getIsAreaMappingOperational() {
		return isAreaMappingOperational;
	}

	public void setIsAreaMappingOperational(String isAreaMappingOperational) {
		this.isAreaMappingOperational = isAreaMappingOperational;
	}

	public String getCodIdElecLevel1() {
		return codIdElecLevel1;
	}

	public void setCodIdElecLevel1(String codIdElecLevel1) {
		this.codIdElecLevel1 = codIdElecLevel1;
	}

	public String getCodIdElecLevel2() {
		return codIdElecLevel2;
	}

	public void setCodIdElecLevel2(String codIdElecLevel2) {
		this.codIdElecLevel2 = codIdElecLevel2;
	}

	public String getCodIdElecLevel3() {
		return codIdElecLevel3;
	}

	public void setCodIdElecLevel3(String codIdElecLevel3) {
		this.codIdElecLevel3 = codIdElecLevel3;
	}

	public String getCodIdElecLevel4() {
		return codIdElecLevel4;
	}

	public void setCodIdElecLevel4(String codIdElecLevel4) {
		this.codIdElecLevel4 = codIdElecLevel4;
	}

	public String getCodIdElecLevel5() {
		return codIdElecLevel5;
	}

	public void setCodIdElecLevel5(String codIdElecLevel5) {
		this.codIdElecLevel5 = codIdElecLevel5;
	}

	public String getCodIdRevLevel1() {
		return codIdRevLevel1;
	}

	public void setCodIdRevLevel1(String codIdRevLevel1) {
		this.codIdRevLevel1 = codIdRevLevel1;
	}

	public Long getDpDeptId() {
		return dpDeptId;
	}

	public void setDpDeptId(Long dpDeptId) {
		this.dpDeptId = dpDeptId;
	}

	public String getDpDeptDesc() {
		return dpDeptDesc;
	}

	public void setDpDeptDesc(String dpDeptDesc) {
		this.dpDeptDesc = dpDeptDesc;
	}

	public String getCodIdOperLevel1() {
		return codIdOperLevel1;
	}

	public void setCodIdOperLevel1(String codIdOperLevel1) {
		this.codIdOperLevel1 = codIdOperLevel1;
	}

	public String getCodIdOperLevel2() {
		return codIdOperLevel2;
	}

	public void setCodIdOperLevel2(String codIdOperLevel2) {
		this.codIdOperLevel2 = codIdOperLevel2;
	}

	public String getCodIdOperLevel3() {
		return codIdOperLevel3;
	}

	public void setCodIdOperLevel3(String codIdOperLevel3) {
		this.codIdOperLevel3 = codIdOperLevel3;
	}

	public String getCodIdOperLevel4() {
		return codIdOperLevel4;
	}

	public void setCodIdOperLevel4(String codIdOperLevel4) {
		this.codIdOperLevel4 = codIdOperLevel4;
	}

	public String getCodIdOperLevel5() {
		return codIdOperLevel5;
	}

	public void setCodIdOperLevel5(String codIdOperLevel5) {
		this.codIdOperLevel5 = codIdOperLevel5;
	}

	public boolean isElectoralChkBox() {
		return electoralChkBox;
	}

	public void setElectoralChkBox(boolean electoralChkBox) {
		this.electoralChkBox = electoralChkBox;
	}

	public boolean isRevenueChkBox() {
		return revenueChkBox;
	}

	public void setRevenueChkBox(boolean revenueChkBox) {
		this.revenueChkBox = revenueChkBox;
	}

	public boolean isOpertionalChkBox() {
		return opertionalChkBox;
	}

	public void setOpertionalChkBox(boolean opertionalChkBox) {
		this.opertionalChkBox = opertionalChkBox;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getLangId() {
		return langId;
	}

	public void setLangId(Integer langId) {
		this.langId = langId;
	}

	public Date getLmoddate() {
		return lmoddate;
	}

	public void setLmoddate(Date lmoddate) {
		this.lmoddate = lmoddate;
	}

	public String getLgIpMac() {
		return lgIpMac;
	}

	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((locNameEng == null) ? 0 : locNameEng.hashCode());
		result = prime * result + ((locArea == null) ? 0 : locArea.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocationMasterUploadDto other = (LocationMasterUploadDto) obj;
		if (locNameEng == null) {
			if (other.locNameEng != null) {
				return false;
			}
		} else if (locArea == null) {
			if (other.locArea != null) {
				return false;
			}
		} else if (!(locNameEng.equals(other.locNameEng) && locArea.equals(other.locArea))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "LocationMasterUploadDto [locNameEng=" + locNameEng + ", locNameReg=" + locNameReg + ", locArea="
				+ locArea + ", locAreaReg=" + locAreaReg + ", locAddress=" + locAddress + ", locAddressReg="
				+ locAddressReg + ", pincode=" + pincode + ", landmark=" + landmark + ", deptLoc=" + deptLoc
				+ ", locActive=" + locActive + ", GISNo=" + GISNo + ", locCategory=" + locCategory + ", latitude="
				+ latitude + ", longitude=" + longitude + ", isAreaMappingElectoral=" + isAreaMappingElectoral
				+ ", isAreaMappingRevenue=" + isAreaMappingRevenue + ", isAreaMappingOperational="
				+ isAreaMappingOperational + ", codIdElecLevel1=" + codIdElecLevel1 + ", codIdElecLevel2="
				+ codIdElecLevel2 + ", codIdElecLevel3=" + codIdElecLevel3 + ", codIdElecLevel4=" + codIdElecLevel4
				+ ", codIdElecLevel5=" + codIdElecLevel5 + ", codIdRevLevel1=" + codIdRevLevel1 + ", dpDeptId="
				+ dpDeptId + ", dpDeptDesc=" + dpDeptDesc + ", codIdOperLevel1=" + codIdOperLevel1
				+ ", codIdOperLevel2=" + codIdOperLevel2 + ", codIdOperLevel3=" + codIdOperLevel3 + ", codIdOperLevel4="
				+ codIdOperLevel4 + ", codIdOperLevel5=" + codIdOperLevel5 + ", electoralChkBox=" + electoralChkBox
				+ ", revenueChkBox=" + revenueChkBox + ", opertionalChkBox=" + opertionalChkBox + ", orgId=" + orgId
				+ ", userId=" + userId + ", langId=" + langId + ", lmoddate=" + lmoddate + ", lgIpMac=" + lgIpMac + "]";
	}

}
