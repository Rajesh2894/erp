package com.abm.mainet.common.master.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Size;

public class BankMasterUploadDTO {

	private String bankName;
	private String bankBranch;
	private String ifscCode;
	private BigDecimal micrCode;
	private String city;
	private String district;
	private String state;
	private String branchAddr;
	private String status;
	private String contDet;
	private Long orgid;
	private Long userId;
	private Long langId;
	private Date lmoddate;
	@Size(max = 100)
    private String lgIpMac;
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankBranch() {
		return bankBranch;
	}
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	public BigDecimal getMicrCode() {
		return micrCode;
	}
	public void setMicrCode(BigDecimal micrCode) {
		this.micrCode = micrCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getBranchAddr() {
		return branchAddr;
	}
	public void setBranchAddr(String branchAddr) {
		this.branchAddr = branchAddr;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getContDet() {
		return contDet;
	}
	public void setContDet(String contDet) {
		this.contDet = contDet;
	}
	public Long getOrgid() {
		return orgid;
	}
	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getLangId() {
		return langId;
	}
	public void setLangId(Long langId) {
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
		result = prime * result + ((bankBranch == null) ? 0 : bankBranch.hashCode());
		result = prime * result + ((bankName == null) ? 0 : bankName.hashCode());
		result = prime * result + ((branchAddr == null) ? 0 : branchAddr.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((contDet == null) ? 0 : contDet.hashCode());
		result = prime * result + ((district == null) ? 0 : district.hashCode());
		result = prime * result + ((ifscCode == null) ? 0 : ifscCode.hashCode());
		result = prime * result + ((micrCode == null) ? 0 : micrCode.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		BankMasterUploadDTO other = (BankMasterUploadDTO) obj;
		if (bankBranch == null) {
			if (other.bankBranch != null)
				return false;
		} else if (!bankBranch.equals(other.bankBranch))
			return false;
		if (bankName == null) {
			if (other.bankName != null)
				return false;
		} else if (!bankName.equals(other.bankName))
			return false;
		if (branchAddr == null) {
			if (other.branchAddr != null)
				return false;
		} else if (!branchAddr.equals(other.branchAddr))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (contDet == null) {
			if (other.contDet != null)
				return false;
		} else if (!contDet.equals(other.contDet))
			return false;
		if (district == null) {
			if (other.district != null)
				return false;
		} else if (!district.equals(other.district))
			return false;
		if (ifscCode == null) {
			if (other.ifscCode != null)
				return false;
		} else if (!ifscCode.equals(other.ifscCode))
			return false;
		if (micrCode == null) {
			if (other.micrCode != null)
				return false;
		} else if (!micrCode.equals(other.micrCode))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
	
	
}
