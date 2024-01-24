package com.abm.mainet.rnl.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ritesh.patil
 *
 */
public class TenantMaster implements Serializable {

    private static final long serialVersionUID = 1302023207936911422L;
    private Long tntId;
    private String code;
    private Long orgId;
    private String address1;
    private String address2;
    private Long type;
    private Long title;
    private String fName;
    private String mName;
    private String lName;
    private String mobileNumber;
    private String emailId;
    private String phoneNumber;
    private String faxNumber;
    private String panNumber;
    private Integer aadharNumber;
    private Long createdBy;
    private Long updatedBy;
    private String imagesPath;
    private String docsPath;
    private String hiddeValue;
    private Integer pinCode;
    private String tntOrgName;
    private int langId;
    private Date createdDate;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUp;
    private Character isActive;

    public Character getIsActive() {
        return isActive;
    }

    public void setIsActive(final Character isActive) {
        this.isActive = isActive;
    }

    private List<TenantOwnerMaster> tenantOwnerMasters = new ArrayList<>();
    private List<TenantOwnerMaster> tenantOwnereditDetails = new ArrayList<>();

    public String getTntOrgName() {
        return tntOrgName;
    }

    public void setTntOrgName(final String tntOrgName) {
        this.tntOrgName = tntOrgName;
    }

    public Integer getPinCode() {
        return pinCode;
    }

    public void setPinCode(final Integer pinCode) {
        this.pinCode = pinCode;
    }

    public Long getTntId() {
        return tntId;
    }

    public void setTntId(final Long tntId) {
        this.tntId = tntId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(final String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(final String address2) {
        this.address2 = address2;
    }

    public Long getType() {
        return type;
    }

    public void setType(final Long type) {
        this.type = type;
    }

    public Long getTitle() {
        return title;
    }

    public void setTitle(final Long title) {
        this.title = title;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(final String fName) {
        this.fName = fName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(final String mName) {
        this.mName = mName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(final String lName) {
        this.lName = lName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(final String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(final String emailId) {
        this.emailId = emailId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(final String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(final String panNumber) {
        this.panNumber = panNumber;
    }

    public Integer getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(final Integer aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getImagesPath() {
        return imagesPath;
    }

    public void setImagesPath(final String imagesPath) {
        this.imagesPath = imagesPath;
    }

    public String getDocsPath() {
        return docsPath;
    }

    public void setDocsPath(final String docsPath) {
        this.docsPath = docsPath;
    }

    public String getHiddeValue() {
        return hiddeValue;
    }

    public void setHiddeValue(final String hiddeValue) {
        this.hiddeValue = hiddeValue;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(final int langId) {
        this.langId = langId;
    }

    public List<TenantOwnerMaster> getTenantOwnerMasters() {
        return tenantOwnerMasters;
    }

    public void setTenantOwnerMasters(
            final List<TenantOwnerMaster> tenantOwnerMasters) {
        this.tenantOwnerMasters = tenantOwnerMasters;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUp() {
		return lgIpMacUp;
	}

	public void setLgIpMacUp(String lgIpMacUp) {
		this.lgIpMacUp = lgIpMacUp;
	}

	public List<TenantOwnerMaster> getTenantOwnereditDetails() {
        return tenantOwnereditDetails;
    }

    public void setTenantOwnereditDetails(
            final List<TenantOwnerMaster> tenantOwnereditDetails) {
        this.tenantOwnereditDetails = tenantOwnereditDetails;
    }

}
