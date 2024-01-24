package com.abm.mainet.common.dto;

import java.util.Date;

import javax.validation.constraints.Size;

import org.springframework.validation.BindingResult;

public class VendorMasterUploadDto {

    private String type;
    private String name;
    private Long mobileNum;
    private Long uidNum;
    private String vatNum;
    private String bankBranchIfsc;
    private String function;
    private String address;
    private String subType;
    private String payTo;
    private String emailId;
    private String gstNum;
    private String panNum;
    private String bankAcNum;
    private String primaryAccountHead;
    private Long orgid;
    private Long userId;
    private Long langId;
    private Date lmoddate;
    @Size(max = 100)
    private String lgIpMac;
    private String sliMode;
    private String vendorClassName;
    private Long bankId;
    private String accOldHeadCode;

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Long getMobileNum() {
	return mobileNum;
    }

    public void setMobileNum(Long mobileNum) {
	this.mobileNum = mobileNum;
    }

    public Long getUidNum() {
	return uidNum;
    }

    public void setUidNum(Long uidNum) {
	this.uidNum = uidNum;
    }

    public String getVatNum() {
	return vatNum;
    }

    public void setVatNum(String vatNum) {
	this.vatNum = vatNum;
    }

    public String getBankBranchIfsc() {
	return bankBranchIfsc;
    }

    public void setBankBranchIfsc(String bankBranchIfsc) {
	this.bankBranchIfsc = bankBranchIfsc;
    }

    public String getFunction() {
	return function;
    }

    public void setFunction(String function) {
	this.function = function;
    }

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public String getSubType() {
	return subType;
    }

    public void setSubType(String subType) {
	this.subType = subType;
    }

    public String getPayTo() {
	return payTo;
    }

    public void setPayTo(String payTo) {
	this.payTo = payTo;
    }

    public String getEmailId() {
	return emailId;
    }

    public void setEmailId(String emailId) {
	this.emailId = emailId;
    }

    public String getGstNum() {
	return gstNum;
    }

    public void setGstNum(String gstNum) {
	this.gstNum = gstNum;
    }

    public String getPanNum() {
	return panNum;
    }

    public void setPanNum(String panNum) {
	this.panNum = panNum;
    }

    public String getBankAcNum() {
	return bankAcNum;
    }

    public void setBankAcNum(String bankAcNum) {
	this.bankAcNum = bankAcNum;
    }

    public String getPrimaryAccountHead() {
	return primaryAccountHead;
    }

    public void setPrimaryAccountHead(String primaryAccountHead) {
	this.primaryAccountHead = primaryAccountHead;
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

    public String getSliMode() {
	return sliMode;
    }

    public void setSliMode(String sliMode) {
	this.sliMode = sliMode;
    }

    public String getVendorClassName() {
	return vendorClassName;
    }

    public void setVendorClassName(String vendorClassName) {
	this.vendorClassName = vendorClassName;
    }

    public Long getBankId() {
	return bankId;
    }

    public void setBankId(Long bankId) {
	this.bankId = bankId;
    }

    public String getAccOldHeadCode() {
		return accOldHeadCode;
	}

	public void setAccOldHeadCode(String accOldHeadCode) {
		this.accOldHeadCode = accOldHeadCode;
	}

	@Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((gstNum == null) ? 0 : gstNum.hashCode());
	result = prime * result + ((mobileNum == null) ? 0 : mobileNum.hashCode());
	result = prime * result + ((panNum == null) ? 0 : panNum.hashCode());
	result = prime * result + ((uidNum == null) ? 0 : uidNum.hashCode());
	result = prime * result + ((vatNum == null) ? 0 : vatNum.hashCode());
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
	VendorMasterUploadDto other = (VendorMasterUploadDto) obj;
	if (gstNum == null) {
	    if (other.gstNum != null)
		return false;
	} else if (!gstNum.equals(other.gstNum))
	    return false;
	if (mobileNum == null) {
	    if (other.mobileNum != null)
		return false;
	} else if (!mobileNum.equals(other.mobileNum))
	    return false;
	if (panNum == null) {
	    if (other.panNum != null)
		return false;
	} else if (!panNum.equals(other.panNum))
	    return false;
	if (uidNum == null) {
	    if (other.uidNum != null)
		return false;
	} else if (!uidNum.equals(other.uidNum))
	    return false;
	if (vatNum == null) {
	    if (other.vatNum != null)
		return false;
	} else if (!vatNum.equals(other.vatNum))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	return true;
    }


}
